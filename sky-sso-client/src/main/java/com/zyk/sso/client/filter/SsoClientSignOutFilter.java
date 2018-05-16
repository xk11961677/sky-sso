package com.zyk.sso.client.filter;

import com.zyk.sso.client.constants.Const;
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

    @Getter
    @Setter
    private String SSO_LOGOUT = "http://www.sso.com:8086/sso/logout";

    @Getter
    @Setter
    private String DEFAULT_INDEX = "/resource/test";

    private String LOGOUT = "/customer/logout";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        boolean isSignOut = false;

        HttpSession session = request.getSession();

        String uri = request.getRequestURI();

        String service = request.getParameter("service");

        if (uri.contains(LOGOUT)) {

            session.removeAttribute(Const.SERVICE_TICKET);

            session.invalidate();

            isSignOut = true;
        }
        if(StringUtils.isEmpty(service)) {
            service = getContextPath(request) + DEFAULT_INDEX;
        }
        String path = SSO_LOGOUT + "?service=" + service;

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