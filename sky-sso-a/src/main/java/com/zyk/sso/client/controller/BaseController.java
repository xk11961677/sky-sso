package com.zyk.sso.client.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected final static String DEFAULT_IMG = "/static/front/img/head_default.png";

    /**
     * 获取项目绝对路径
     * @param request
     * @return
     */
    protected String getBasePath(HttpServletRequest request) {
        String base = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + base;
        return basePath;
    }


}
