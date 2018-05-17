package com.zyk.sso.client.listener;

import com.zyk.sso.client.utils.HashMapBackedSessionMappingStorage;
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
        String st = HashMapBackedSessionMappingStorage.getInstance().ID_TO_SESSION_KEY_MAPPING.get(session.getId());
        if(!StringUtils.isEmpty(st)) {
            HashMapBackedSessionMappingStorage.getInstance().ID_TO_SESSION_KEY_MAPPING.remove(session.getId());
            HttpSession sessionStorage = HashMapBackedSessionMappingStorage.getInstance().MANAGED_SESSIONS.get(st);
            if(sessionStorage != null) {
                HashMapBackedSessionMappingStorage.getInstance().MANAGED_SESSIONS.remove(st);
                sessionStorage.invalidate();
            }
        }
        log.info(HashMapBackedSessionMappingStorage.getInstance().MANAGED_SESSIONS.size()+"\t"+HashMapBackedSessionMappingStorage.getInstance().ID_TO_SESSION_KEY_MAPPING.size());

    }
}
