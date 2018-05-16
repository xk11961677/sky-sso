<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    request.setCharacterEncoding("utf-8");
%>
<link rel="stylesheet" href="<%=path%>/static/front/css/a2.css">
<%@include file="include/base_header.jsp"%>
<%@include file="include/header.jsp"%>

<div class="container">
    <%@include file="include/nav.jsp"%>

    <div class="wrap page2">
        <div class="sendWrap">
            <p><span><a href="<%=path%>/index/toLogin" style="cursor: pointer;color: #dfdfdf;text-decoration: none;">登陆</a></span> | <span class="ac">注册</span></p>
            <form id="registerForm" action="" style="">
                <ul>
                <li>
                    <div class="left">
                        手机号:
                    </div>
                    <div class="right" style="line-height: 80px;height: 80px;float: none;">
                        <input type="text" name="mobile" id="mobile">
                    </div>
                </li>
                <li style="height: 10px;line-height: 10px;color: #ff0000;font-size: 14px;">
                    <div style="padding-left: 200px;"></div>
                </li>
                <li>
                    <div class="left">
                        设置密码:
                    </div>
                    <div class="right" style="line-height: 80px;height: 80px;float: none;">
                        <input type="password" name="password" id="password">
                    </div>
                </li>
                <li style="height: 10px;line-height: 10px;color: #ff0000;font-size: 14px;">
                    <div style="padding-left: 200px;"></div>
                </li>
                <li>
                    <div class="left">
                        确认密码:
                    </div>
                    <div class="right" style="line-height: 80px;height: 80px;float: none;">
                        <input type="password" name="repassword" id="repassword" >
                    </div>
                </li>
                <li style="height: 10px;line-height: 10px;color: #ff0000;font-size: 14px;">
                    <div style="padding-left: 200px;"></div>
                </li>
                <li>
                    <div class="left">
                        手机验证码:
                    </div>
                    <div class="right">
                        <input type="text" id="validateCode" name="validateCode" style="width: 50%;">
                        <span id="getValidateCode" style="cursor: pointer">
                            <img src="<%=path%>/static/front/img/u4122_j.png" alt="">点击获取
                        </span>
                    </div>
                </li>
                <li style="height: 10px;line-height: 10px;color: #ff0000;font-size: 14px;">
                    <div style="padding-left: 200px;"></div>
                </li>
                <li style="margin-top: 15px;">
                    <div class="left">
                    </div>
                    <div class="right">
                        <span id="registerButton" style="display: inline-block;width: 50%;height: 44px;border-radius: 5px;text-align: center;line-height: 44px; background: #4898d1;color: #fff;cursor: pointer">提交</span>
                    </div>
                </li>
            </ul>
            </form>
        </div>
    </div>

    <%@ include file = "include/foot.jsp" %>
    <script type="text/javascript" src="<%=path%>/static/front/js/jquery.validate.min.js"></script>
    <script src="<%=path%>/static/front/js/md5.js"></script>
    <script src="<%=path%>/static/front/js/register.js"></script>
</div>
</body>
</html>