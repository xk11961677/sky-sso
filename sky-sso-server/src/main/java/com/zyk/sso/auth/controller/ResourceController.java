package com.zyk.sso.auth.controller;

import com.zyk.sso.auth.common.constants.Const;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

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
    @ResponseBody
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object aaa = session.getAttribute("aaa");
        System.out.println(aaa);
        return Objects.toString(session.getAttribute(Const.TGT_TICKET));
    }


    @RequestMapping(value = "add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String add(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("aaa","123");
        return Objects.toString(session.getAttribute(Const.TGT_TICKET));
    }
}
