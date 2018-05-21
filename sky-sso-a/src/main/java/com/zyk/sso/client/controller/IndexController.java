package com.zyk.sso.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 **/
@Controller
@RequestMapping("/index")
@Slf4j
public class IndexController extends BaseController {

    /**
     * 跳转注册页面
     * (默认此地址不会执行,会被退出拦截器拦截,可以修改先执行此操作，然后添加登出拦截器拦截参数进行SSO注销)
     *
     * @return
     */
    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request) {
        return "redirect:/index/logout?logout=123";
    }


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


}
