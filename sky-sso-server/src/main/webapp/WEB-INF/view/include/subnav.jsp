<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 顶部导航 -->
<div class="top3" style="display: none;" id="sub_nav">
    <div class="left clearfix">
        <c:if test="${opt == 1}">
            <p>项目成员</p>
            <ul class="clearfix" id="task_customer_ul">
            </ul>
        </c:if>
    </div>
    <div class="right" style="width: 420px">
        <ul>
            <li class="newNavBtn">
                <img src="<%=path%>/static/front/img/top_left.png" alt=""><a
                    style="cursor: pointer;text-decoration: none;color: #afb0b1;" href="<%=path%>/task/toTaskList">项目导航</a>
            </li>
            <li>
                <img src="<%=path%>/static/front/img/top_left.png" alt=""><a
                    style="text-decoration: none;color: #afb0b1;"
                    href="<%=path%>/basestation/toStationList">我的资料袋</a>
            </li>
            <li>
                <img src="<%=path%>/static/front/img/top_left.png" alt=""><a
                    style="text-decoration: none;color: #afb0b1;"
                    href="<%=path%>/recycle/toRecycleList">回收站</a>
            </li>
        </ul>
    </div>
</div>