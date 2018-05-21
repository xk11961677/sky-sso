package com.zyk.sso.client.controller;

import com.zyk.sso.client.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 **/
@Controller
@RequestMapping("/resource")
@Slf4j
public class ResourceController extends BaseController {

    /**
     * 跳转注册页面
     *
     * @return
     */
    @RequestMapping(value = "test", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView test(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(Const.SERVICE_TICKET);
        log.info("resource test session b:{}",attribute);
        modelAndView.setViewName("/register");
        return modelAndView;
    }

}
