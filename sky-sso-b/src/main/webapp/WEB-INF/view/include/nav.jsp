<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
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
                            <a href="<%=path%>/index/toDownload">
                                <div class="l">
                                    <p class="p1">蜂采</p>
                                    <p class="p2">移动基站勘查采集app</p>
                                </div>
                                <div class="r">
                                    <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="<%=path%>/index/index">
                                <div class="l">
                                    <p class="p1">蜂巢</p>
                                    <p class="p2">移动基站勘查采集pc</p>
                                </div>
                                <div class="r">
                                    <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="l">
                                    <p class="p1">花香</p>
                                    <p class="p2">大数据地理化</p>
                                </div>
                                <div class="r">
                                    <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="l">
                                    <p class="p1">蜂秘</p>
                                    <p class="p2">项目大数据分析</p>
                                </div>
                                <div class="r">
                                    <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="l">
                                    <p class="p1">我有任务</p>
                                    <p class="p2">通信设计任务交易平台</p>
                                </div>
                                <div class="r">
                                    <img src="<%=path%>/static/front/img/index_fd.png" alt="">
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <div class="l">
                                    <p class="p1">客户服务</p>
                                    <p class="p2">产品服务咨询</p>
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
            <div id="un_login" style="display: none;">
                <a  style="width: 50px;text-align: right;" href="<%=path%>/customer/toregister" class="zc">注册</a><!--
                --><a style="width: 50px;text-align: left;" href="<%=path%>/index/toLogin" class="zc">/登陆</a>
            </div>
            <div id="login" style="display: none">
                <ul class="rightTopDl clearfix">
                    <li id="current_photo">
                    </li>
                    <li id="current_name">

                    </li>
                    <li>
                        <img src="<%=path%>/static/front/img/setup_top04.png" alt="" class="vip">
                    </li>
                    <li>
                        <input id="limit_percent" type="range" value="0" disabled max="100" min="0" step="5" style="margin-top: 15px;"/>
                    </li>
                    <li id="limit_size">
                        3.5/5G
                    </li>
                    <li class="set">
                        <a href="<%=path%>/index/toPersonSetting">设置</a>
                    </li>
                    <li>
                        <a href="<%=path%>/customer/doQuit" style="cursor: pointer;text-decoration: none;color: #ccc;">退出</a>
                    </li>
                </ul>

            </div>
        </div>
    </div>
</div>