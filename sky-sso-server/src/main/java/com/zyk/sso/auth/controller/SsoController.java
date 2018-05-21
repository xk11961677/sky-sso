package com.zyk.sso.auth.controller;

import com.zyk.sso.auth.common.constants.Const;
import com.zyk.sso.auth.common.enumcode.TokenState;
import com.zyk.sso.auth.util.CookieUtil;
import com.zyk.sso.auth.util.HttpClientUtil;
import com.zyk.sso.auth.util.JwtUtil;
import com.zyk.sso.auth.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping(value = "/sso")
public class SsoController extends BaseController {

    @Resource
    private RedisUtils redisUtils;

    /**
     * 跳转登录页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "toLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView toLogin(HttpServletRequest request, @RequestParam("service") String service) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        modelAndView.addObject("service", service);
        return modelAndView;
    }

    /**
     * 登录
     *
     * @param request
     * @param username
     * @param password
     * @param service
     * @return
     */
    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public String login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username,
                        @RequestParam("password") String password, @RequestParam("service") String service) {
        Date date = new Date();
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "291969452");//用户id
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60 * 2);

        String tgt = JwtUtil.createToken(payload);

        CookieUtil.create(response, Const.TGC_TICKET, tgt, false, 7200, "sso.com");

        payload.put("type", Const.SERVICE_TICKET);

        String st = JwtUtil.createToken(payload);

        redisUtils.set(st, tgt, 7200L, TimeUnit.SECONDS);

        String mark = service.contains("?") ? "&" : "?";

        service += (mark + Const.PARAM_TICKET + "=" + st);

        return "redirect:" + service;
    }

    /**
     * 验证令牌是否有效
     *
     * @param ticket
     * @param logout
     * @return
     */
    @RequestMapping(value = "verify", method = {RequestMethod.GET})
    @ResponseBody
    public String verify(HttpServletRequest request, @RequestParam("ticket") String ticket, @RequestParam("logout") String logout) {
        String principal = null;

        try {

            Map<String, Object> tgt_data = JwtUtil.validToken(ticket);

            String state = Objects.toString(tgt_data.get("state"));

            if (TokenState.VALID.getState().equals(state)) {
                Map<String, Object> value = (Map) tgt_data.get("data");
                Date date = new Date();
                value.put("iat", date.getTime());//生成时间
                value.put("ext", date.getTime() + 1000 * 60 * 60 * 2);
                principal = JwtUtil.createToken(value);
            }

            String tgt = (String) redisUtils.get(ticket);
            String mark = logout.contains("?") ? "&" : "?";
            logout += (mark + "signOutTicket=" + principal);
            if (!StringUtils.isEmpty(tgt)) {
                redisUtils.lPush(tgt, logout, 7200L, TimeUnit.SECONDS);
            }

            redisUtils.remove(ticket);
        } catch (Exception e) {
            log.error("验证令牌出现错误:{}", e);
        }
        return principal;
    }

    /**
     * 注销
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "service", required = false) String service,
                         @RequestParam(value = "callback", required = false) String callback) {

        String tgt = CookieUtil.getValue(request, Const.TGC_TICKET);

        if (!StringUtils.isEmpty(tgt)) {
            if (redisUtils.exists(tgt)) {
                List<Object> list = redisUtils.lRange(tgt, 0, -1);
                list.forEach(l -> HttpClientUtil.sendGetRequest(Objects.toString(l), "UTF-8"));
                redisUtils.remove(tgt);
            }
            CookieUtil.clear(response, Const.TGC_TICKET, "sso.com");

            service = !StringUtils.isEmpty(callback) ? callback : "/sso/toLogin?service=" + service;
        }
        return "redirect:" + service;
    }


}
