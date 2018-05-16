<%@page pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登陆注册</title>
    <link rel="stylesheet" href="<%=path%>/static/front/css/swiper.min.css">
    <link rel="stylesheet" href="<%=path%>/static/front/css/style.css">
    <link rel="stylesheet" href="<%=path%>/static/front/css/a2.css">
</head>
<body>
<div class="container">
    <!-- 顶部导航 -->
    <div class="top">
        <div class="top1">
            <a href="<%=path%>/index/index"><img src="<%=path%>/static/front/img/top_logo.png" alt=""></a>
        </div>
        <div class="top2">
            <div class="left">
                <ul>
                    <li class="all">
                        全部导航
                        <ul class="allNav">
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">1蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">2蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">3蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">4蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">5蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="">
                                    <div class="l">
                                        <p class="p1">6蜂彩</p>
                                        <p class="p2">移动基站勘查采集app</p>
                                    </div>
                                    <div class="r">
                                        <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                    </div>
                                </a>
                            </li>

                        </ul>
                    </li>
                    <%--<li>
                    最新活动
                    </li>--%>
                    <li>
                        <a href="<%=path%>/index/toDownload">产品下载</a>
                    </li>
                    <%--<li>
                        <a href="#">解决方案</a>
                    </li>--%>
                    <li>
                        <a href="<%=path%>/index/toQuickTour">快速教程</a>
                    </li>
                    <li>
                        <a href="<%=path%>/index/toTechSupport">技术支持</a>
                    </li>
                </ul>
            </div>
            <div class="right">
                <div>
                    <%--<a href="a2.html" class="zc">注册登陆</a>--%>
                </div>
            </div>
        </div>
    </div>

    <div class="wrap page2">
        <div class="sendWrap">
            <p><span class="ac">登陆</span> | <span><a href="<%=path%>/customer/toregister" style="cursor: pointer;color: #dfdfdf;text-decoration: none;">注册</a></span></p>
            <form action="http://localhost:8086/sso/login" method="POST" id="survey_form">
                <input type="hidden" name="redirect" value="http://localhost:8087/resource/test">
                <ul>
                    <li>
                        <div class="left">
                            手机号:
                        </div>
                        <div class="right">
                            <input type="text" name="username" placeholder="手机号码"/>
                            <p style="height: 33px;width: 80%;padding-top: 0px;font-size: 14px;margin-top: -30px;margin-bottom: 10px;text-align: left;color:red"></p>
                        </div>
                    </li>
                    <li>
                        <div class="left" style="margin-top: 20px;">
                            密码:
                        </div>
                        <div class="right">
                            <input type="password" name="password"  placeholder="密码" />
                            <p style="height: 33px;width: 80%;padding-top: 0px;font-size: 14px;margin-top: -30px;margin-bottom: 10px;text-align: left;color:red"></p>
                        </div>

                    </li>
                    <%--<li>
                        <div class="left">
                            手机验证码:
                        </div>
                        <div class="right">
                            <input type="text" style="width: 50%;">
                        </div>
                    </li>--%>
                    <li>
                        <div class="left">
                        </div>
                        <div class="right">
                            <span style="cursor:pointer;display: inline-block;width: 50%;height: 44px;border-radius: 5px;text-align: center;line-height: 44px; background: #4898d1;color: #fff;" id="survey_btn">登陆</span>
                        </div>
                    </li>
                    <li>
                        <div class="left"></div>
                        <div class="right" style="font-size: 14px;color: red;display: none;" id="loginErr">
                            账号或密码错误
                        </div>
                    </li>
                </ul>
            </form>
        </div>
    </div>

    <div class="foot">
        <div class="wrap clearfix">
            <div class="fot_top">
                <ul>
                    <li>关于我们</li>
                    <li>APP下载</li>
                    <li>加入我们</li>
                    <li>用户体验提升</li>

                    <li class="pull-right">
                        官方客服QQ: 281814270 18001169695
                    </li>
                </ul>
            </div>
            <div class="footL">
                <ul>
                    <li>猪八戒网</li>
                    <li>3GPP</li>
                    <li>中国工业和信息化部</li>
                    <li>中国通信标准化协会</li>
                    <li>MSCBSC移动通信论坛</li>
                    <li>华为技术有限公司</li>
                </ul>
                <p>Copyright 2005-2018 fengchaojisuan.com 版权所有 <a style="color: #9da3a8;text-decoration: none;" href="http://www.miitbeian.gov.cn/publish/query/indexFirst.action" target="_blank">渝ICP备 18000308号-2</a>
                    &nbsp;
                    <a rel="nofollow" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=50011202501354" style="color: #9da3a8;text-decoration: none;">
                        <img src="<%=path%>/static/front/img/back_ga.png" style="height: 14px;">渝公网安备 50011202501354号
                    </a>
                    重庆自由客信息技术有限公司
                    互联网违法和不良信息举报电话：023-88390110 邮箱：ziyouke818@126.com</p>
            </div>
            <div class="footR">
                <div class="l">
                    <img src="<%=path%>/static/front/img/footer.png" alt="">
                    <p>官方公众号</p>
                </div>
                <div class="r">
                    <p>andriod下载</p>
                    <p>IOS下载</p>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="<%=path %>/static/front/js/jquery-1.12.3.min.js"></script>
<script src="<%=path%>/static/front/js/md5.js"></script>
<script src="<%=path %>/static/front/js/jquery.validate.min.js"></script>
<script>

    $().ready(function () {
        if('${error}'.length!=0) {
            $("#loginErr").show();
            setTimeout(function() {
                $("#loginErr").hide();
            },3000)
        }
        jQuery.validator.addMethod("isMobile", function (value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "格式不正确");

        $("#survey_form").validate({
            invalidHandler: function (form, validator) {
                return false;
            },
            onfocusout:false,
            rules: {
                mobile: {
                    required: true,
                    isMobile: true
                },
                password: {
                    required: true,
                }
            },
            messages: {
                mobile: {
                    required: "请输入手机号",
                    isMobile: "格式不正确"
                },
                password: {
                    required: "请输入密码",
                }
            },
            errorPlacement: function (error, element) { //错误信息位置设置方法
                $("#loginErr").hide();
                error.appendTo(element.parent().children("p")); //这里的element是录入数据的对象
            }
        });

        $("#survey_btn").click(function () {
            if ($("#survey_form").valid()) {
                $("input[name='password']").val($.md5($("input[name='password']").val()));
                $("#survey_form").submit();
            }
        })

        $("body").keydown(function(event) {
            if(event.keyCode ==13) {
                $("#survey_btn").click();
            }
        });
    });
</script>

</body>
</html>