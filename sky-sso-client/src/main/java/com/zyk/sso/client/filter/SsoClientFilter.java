package com.zyk.sso.client.filter;

import com.zyk.sso.client.constants.Const;
import com.zyk.sso.client.utils.ClientSessionStorage;
import com.zyk.sso.client.utils.HttpClientUtil;
import com.zyk.sso.client.utils.JwtUtil;
import com.zyk.sso.client.utils.TokenState;
import lombok.Getter;
import lombok.Setter;
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

    /**
     * 单点登录中心验证令牌地址
     */
    @Getter
    @Setter
    private String SSO_VERIFY = "/sso/verify";

    /**
     * 单点登录页面地址
     */
    @Getter
    @Setter
    private String SSO_TOLOGIN = "/sso/toLogin";

    /**
     * 客户端注销地址
     */
    @Getter
    @Setter
    private String CLIENT_LOGOUT = "/logout?logout=123456";

    /**
     * 单点登录域名
     */
    @Getter
    @Setter
    private String SSO_DOMIAN = "http://www.sso.com:8086";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        boolean isLogin = false;

        HttpSession session = request.getSession();

        String st = Objects.toString(session.getAttribute(Const.SERVICE_TICKET), "");

        String url = Objects.toString(request.getRequestURL(), "");

        String qs = request.getQueryString();

        if (!StringUtils.isEmpty(st)) {
            isLogin = true;
        } else {
            if (!StringUtils.isEmpty(qs) && qs.contains(Const.PARAM_TICKET)) {

                st = verifyTicket(qs, request);

                if (!StringUtils.isEmpty(st)) {
                    st = st.replace("\"", "");

                    Map<String, Object> map = JwtUtil.validToken(st);

                    String state = Objects.toString(map.get("state"), "");

                    if (TokenState.VALID.getState().equals(state)) {

                        session.setAttribute(Const.SERVICE_TICKET, st);

                        ClientSessionStorage.getInstance().setManagedSessions(st, session);

                        ClientSessionStorage.getInstance().setIdToSessionKeyMapping(session.getId(), st);

                        response.sendRedirect("" + request.getRequestURL());//重复跳转一次,将st去掉

                        return;
                    }
                }
            }
        }
        if (isLogin) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(SSO_DOMIAN + SSO_TOLOGIN + "?service=" + url);
        }
    }

    /**
     * 验证令牌
     *
     * @param qs
     * @param request
     * @return
     */
    private String verifyTicket(String qs, HttpServletRequest request) {
        String stValue = getQueryParam(qs, Const.PARAM_TICKET);

        StringBuffer sb = new StringBuffer();

        sb.append(SSO_DOMIAN + SSO_VERIFY).append("?").append(Const.PARAM_TICKET).append("=").append(stValue).append("&").append("logout").append("=").append(getContextPath(request)).append(CLIENT_LOGOUT);

        String st = HttpClientUtil.sendGetRequest(Objects.toString(sb), "UTF-8");
        return st;
    }

    /**
     * 从参数中获取值
     *
     * @param qs
     * @param key
     * @return
     */
    private String getQueryParam(String qs, String key) {
        String value = null;
        if (!StringUtils.isEmpty(qs)) {
            String[] ary = qs.split("&");
            for (int i = 0; i < ary.length; i++) {
                String str = ary[i];
                if (str.contains(key + "=")) {
                    value = str.replace(key + "=", "");
                }
            }
        }
        return value;
    }

    private String getContextPath(HttpServletRequest request) {
        String base = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + base;
        return basePath;
    }


}