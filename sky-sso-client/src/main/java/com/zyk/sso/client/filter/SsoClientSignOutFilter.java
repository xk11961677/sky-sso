package com.zyk.sso.client.filter;

import com.zyk.sso.client.constants.Const;
import com.zyk.sso.client.utils.ClientSessionStorage;
import com.zyk.sso.client.utils.HttpClientUtil;
import com.zyk.sso.client.utils.JwtUtil;
import com.zyk.sso.client.utils.TokenState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SsoClientSignOutFilter extends OncePerRequestFilter {

    /**
     * 单点登录注销地址
     */
    @Getter
    @Setter
    private String SSO_LOGOUT = "/sso/logout";

    /**
     * 默认登录后进入地址
     */
    @Getter
    @Setter
    private String DEFAULT_INDEX = "/resource/test";

    /**
     * 注销参数
     */
    private String LOGOUT = "logout";

    /**
     * 单点登录域名
     */
    @Getter
    @Setter
    private String SSO_DOMIAN = "http://www.sso.com:8086";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        boolean isSignOut = false;

        String callback = request.getParameter("callback"); //

        String loginSuccessUrl = request.getParameter("loginSuccessUrl");   //登录成功后进去地址

        String signOutTicket = request.getParameter("signOutTicket");   //SSO注销时令牌

        String qs = request.getQueryString();

        String path = "";

        if (!StringUtils.isEmpty(qs) && qs.contains(LOGOUT)) {
            if (!StringUtils.isEmpty(signOutTicket)) {    //认证中心注销时使用
                HttpSession session = ClientSessionStorage.getInstance().getManagedSessions(signOutTicket);
                if (session == null) {
                    session = request.getSession();
                }
                String sessionId = session.getId();
                session.removeAttribute(Const.SERVICE_TICKET);
                session.invalidate();
                ClientSessionStorage.getInstance().removeManagedSessions(signOutTicket);
                ClientSessionStorage.getInstance().removeIdToSessionKeyMapping(sessionId);
            }
            isSignOut = true;
            String index = !StringUtils.isEmpty(loginSuccessUrl) ? loginSuccessUrl : getContextPath(request) + DEFAULT_INDEX;

            String url = "?service=" + index;

            url += (!StringUtils.isEmpty(callback) ? "&callback=" + callback : "");

            path = (SSO_DOMIAN + SSO_LOGOUT + url);
        }

        if (!isSignOut) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(path);
        }
    }


    private String getContextPath(HttpServletRequest request) {
        String base = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + base;
        return basePath;
    }
}