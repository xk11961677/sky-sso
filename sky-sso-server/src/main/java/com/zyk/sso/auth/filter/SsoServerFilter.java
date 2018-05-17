package com.zyk.sso.auth.filter;

import com.zyk.sso.auth.common.constants.Const;
import com.zyk.sso.auth.common.enumcode.TokenState;
import com.zyk.sso.auth.util.CookieUtil;
import com.zyk.sso.auth.util.JwtUtil;
import com.zyk.sso.auth.util.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SsoServerFilter extends OncePerRequestFilter {

    private RedisUtils redisUtils;

    public SsoServerFilter(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 1.检查是否存在全局会话，存在则增加票据参数，跳转回链接
     * 2.不存在全局会话则跳转登录地址
     * 3.判断验证票据地址，不拦截
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        String qs = request.getQueryString();

        HttpSession session = request.getSession();

        String tgt = Objects.toString(session.getAttribute(Const.TGT_TICKET), "");

        System.out.println("filter get tgt from session tgt:{}" + tgt);

        tgt = CookieUtil.getValue(request, Const.TGC_TICKET);

        System.out.println("filter get tgt from cookie tgt:{}" + tgt);

        if ((!StringUtils.isEmpty(tgt) && uri.contains("sso/verify") && qs.contains(Const.PARAM_TICKET) ) ||  uri.contains("sso/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String service = request.getParameter("service");

        if (!StringUtils.isEmpty(tgt) && !StringUtils.isEmpty(service)) {

            String mark = service.contains("?") ? "&" : "?";

            Map<String, Object> tgt_data = JwtUtil.validToken(tgt);

            String state = Objects.toString(tgt_data.get("state"));

            if (TokenState.VALID.getState().equals(state)) {
                Map<String, Object> value = (Map) tgt_data.get("data");
                value.put("type", Const.SERVICE_TICKET);
                String st = JwtUtil.createToken(value);
                redisUtils.set(st, tgt, 7200L, TimeUnit.SECONDS);
                service += (mark + Const.PARAM_TICKET + "=" + st);
                response.sendRedirect(service);
                return;
            }
        }

        if (uri.contains("sso/logout") || uri.contains("sso/login") || uri.contains("/sso/toLogin")) {
            filterChain.doFilter(request, response);
            return;
        }
        response.sendRedirect("/sso/toLogin");

    }
}
