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
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username,
                              @RequestParam("password") String password, @RequestParam("service") String service) {
        Date date = new Date();
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "291969452");//用户id
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60 * 2);

        String token = JwtUtil.createToken(payload);

        HttpSession session = request.getSession();

        session.setAttribute(Const.TGT_TICKET, token);

        CookieUtil.create(response, Const.TGC_TICKET, token, false, 7200, "sso.com");

        payload.put("type", Const.SERVICE_TICKET);

        String st = JwtUtil.createToken(payload);

        redisUtils.set(st, token, 7200L, TimeUnit.SECONDS);

        String mark = "?";

        if (service.indexOf("?") > -1) {
            mark = "&";
        }
        service += (mark + Const.PARAM_TICKET + "=" + st);

//        return "redirect:" + service;

        ModelAndView view = new ModelAndView();
        view.addObject("service", service);
        view.setViewName("/setCookie");
        return view;
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
    public String verify(@RequestParam("ticket") String ticket, @RequestParam("logout") String logout) {
        String principal = null;

        try {
            String tgt = (String) redisUtils.get(ticket);
            if (!StringUtils.isEmpty(tgt)) {
                redisUtils.lPush(tgt, logout, 7200L, TimeUnit.SECONDS);
            }

            redisUtils.remove(ticket);

            Map<String, Object> tgt_data = JwtUtil.validToken(ticket);

            String state = Objects.toString(tgt_data.get("state"));

            if (TokenState.VALID.getState().equals(state)) {
                Map<String, Object> value = (Map) tgt_data.get("data");
                Date date = new Date();
                value.put("iat", date.getTime());//生成时间
                value.put("ext", date.getTime() + 1000 * 60 * 60 * 2);
                principal = JwtUtil.createToken(value);
            }
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
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "service") String service) {
        HttpSession session = request.getSession();

        String tgt = Objects.toString(session.getAttribute(Const.TGT_TICKET));

        log.info("get tgt from session tgt:{}", tgt);

        tgt = CookieUtil.getValue(request, Const.TGC_TICKET);

        log.info("get tgt from cookie tgt:{}", tgt);

        try {
            if (redisUtils.exists(tgt)) {
                List<Object> list = redisUtils.lRange(tgt, 0, -1);
                list.forEach(l -> {
                    String s = HttpClientUtil.sendGetRequest(Objects.toString(l), "UTF-8");
                    log.info("http client post logout :{}", s);
                });
//                redisUtils.remove(tgt);
            }
        } catch (Exception e) {
            log.error("注销其他应用系统失败:{}", e);
        }

        session.removeAttribute(Const.TGT_TICKET);

        session.invalidate();

        CookieUtil.clear(response, Const.TGC_TICKET, "sso.com");

        service = "/sso/toLogin?service=" + service;

        ModelAndView view = new ModelAndView();
        view.addObject("service", service);
        view.setViewName("/setCookie");
        return view;
    }


}
