package com.zyk.sso.client.controller;

import com.zyk.sso.client.service.CustomerService;
import com.zyk.sso.client.utils.CookieUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lj
 * @description
 * @create 2017-10-28 上午11:56
 **/
@Controller
@RequestMapping("/customer")
@Slf4j
@Api(value = "用户管理", tags = {"用户管理"})
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    private static final String jwtTokenCookieName = "JWT-TOKEN";

    /**
     * 跳转注册页面
     *
     * @return
     */
    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView logout(HttpServletResponse response) {
        CookieUtil.clear(response,jwtTokenCookieName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }


    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }



}
