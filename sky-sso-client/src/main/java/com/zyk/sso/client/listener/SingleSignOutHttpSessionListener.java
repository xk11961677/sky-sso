package com.zyk.sso.client.listener;

import com.zyk.sso.client.utils.ClientSessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
public class SingleSignOutHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String st = ClientSessionStorage.getInstance().getIdToSessionKeyMapping(session.getId());
        if (!StringUtils.isEmpty(st)) {
            ClientSessionStorage.getInstance().removeIdToSessionKeyMapping(session.getId());
            HttpSession sessionStorage = ClientSessionStorage.getInstance().getManagedSessions(st);
            if (sessionStorage != null) {
                ClientSessionStorage.getInstance().removeManagedSessions(st);
                sessionStorage.invalidate();
            }
        }
        log.info(ClientSessionStorage.getInstance().MANAGED_SESSIONS.size() + "\t" + ClientSessionStorage.getInstance().ID_TO_SESSION_KEY_MAPPING.size());
    }
}
