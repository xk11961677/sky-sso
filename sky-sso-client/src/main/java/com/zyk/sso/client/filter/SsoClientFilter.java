package com.zyk.sso.client.filter;

import com.zyk.sso.client.constants.Const;
import com.zyk.sso.client.utils.HttpClientUtil;
import com.zyk.sso.client.utils.JwtUtil;
import com.zyk.sso.client.utils.TokenState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

public class SsoClientFilter extends OncePerRequestFilter {

    @Getter
    @Setter
    private String SSO_VERIFY = "http://www.sso.com:8086/sso/verify";

    @Getter
    @Setter
    private String SSO_TOLOGIN = "http://www.sso.com:8086/sso/toLogin";

    @Getter
    @Setter
    private String LOGOUT = "/customer/logout";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Assert.notNull(LOGOUT,"logout url must required");

        boolean isLogin = false;

        HttpSession session = request.getSession();

        String st = Objects.toString(session.getAttribute(Const.SERVICE_TICKET), "");

        String url = Objects.toString(request.getRequestURL(), "");

        String qs = request.getQueryString();

        if (!StringUtils.isEmpty(st)) {
            isLogin = true;
        } else {
            if (!StringUtils.isEmpty(qs) && qs.contains(Const.PARAM_TICKET)) {
                String stValue = qs.replace(Const.PARAM_TICKET + "=", "");
                st = HttpClientUtil.sendGetRequest(SSO_VERIFY + "?" + Const.PARAM_TICKET + "=" + stValue+"&logout="+ getContextPath(request) + LOGOUT, "UTF-8");
                if (!StringUtils.isEmpty(st)) {
                    st = st.replace("\"", "");
                    Map<String, Object> map = JwtUtil.validToken(st);
                    String state = Objects.toString(map.get("state"), "");
                    if (TokenState.VALID.getState().equals(state)) {
                        isLogin = true;
                        session.setAttribute(Const.SERVICE_TICKET, st);
                    }
                }
            }
        }
        if (isLogin) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(SSO_TOLOGIN + "?service=" + url);
        }
    }


    private String getContextPath(HttpServletRequest request) {
        String base = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + base;
        return basePath;
    }
}