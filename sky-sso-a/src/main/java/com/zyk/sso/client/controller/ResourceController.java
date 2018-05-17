package com.zyk.sso.client.controller;

import com.zyk.sso.client.constants.Const;
import com.zyk.sso.client.service.CustomerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description
 * @create 2017-10-28 上午11:56
 **/
@Controller
@RequestMapping("/resource")
@Slf4j
@Api(value = "用户管理", tags = {"用户管理"})
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
        log.info("resource test session a:{}",attribute);
        modelAndView.setViewName("/register");
        return modelAndView;
    }

}
