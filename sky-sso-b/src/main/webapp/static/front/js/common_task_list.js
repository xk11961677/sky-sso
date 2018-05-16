$().ready(function () {
    var task = {}

    task.pagination;

    task.getTaskList = function(pageNum) {
        var taskName = $("#task_name").val();
        $.ajax({
            type: "post",
            url: _ROOT_+"/task/getTaskList",
            data: {
                "pageNum": pageNum,
                "taskName": taskName
            },
            dataType: "json",
            success: function (data) {
                if(data.code==200) {
                    if(!task.pagination) {
                        task.init(data.result.pageCount, data.result.totalCount, data.result.pageSize, data.result.pageNum);
                    }
                    task.handlePaginationData(data.result.datas);
                }else {
                    common.msg(data.msg);
                }
            },
            error :function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    task.init = function (pageCount, totalCount, pageSize, pageNum) {
        $("#page").pagination({
            pageCount: pageCount,
            totalData: totalCount,
            current: pageNum,
            showData: pageSize,
            homePage: '首页',
            endPage: '末页',
            prevContent: '上一页',
            nextContent: '下一页',
            jump:true,
            jumpBtn:'跳转',
            //isHide:true,
            //mode:'fixed',
            coping: true,
            //keepShowPN: true,
            callback: task.handlePaginationClick
        },function() {
            if(totalCount && totalCount>0) {
                var html = "总数据 <span style='color: #ff0000;'>" + totalCount + "</span>条，每页显示 <span style='color: #ff0000;'>" + pageSize + "</span>条，总页数为 <span style='color: #ff0000;'>" + pageCount + "</span>页";
                $("#page_record").html(html);
            }
        });
    };

    task.handlePaginationClick = function (api) {
        task.getTaskList(api.getCurrent());
        return false;
    };

    task.handlePaginationData = function (d) {
        $("#task_list").html("");
        for(var i=0;i<d.length;i++) {
            var r = d[i];
            var cbHtml = '<input type="checkbox" name="task_id" value="'+r.taskid+'">';
            var html = '<tr>' +
                '<td>'+(i+1)+'.</td>' +
                '<td style="width: 70%;"><a href="'+_ROOT_+'/basestation/toTaskStationList?taskId='+r.taskid+'">'+r.taskname+'</a></td>' +
                '<td>'+r.createtime.substring(0,10).replace(/-/g,".")+'</td>' +
                '<td style="text-align: right;padding-right: 17px;">'+cbHtml+'</td>' +
                '</tr>';
            $("#task_list").append(html);
        }
    }


    $("#task_search").bind("click",function() {
        $("#task_list").html("");
        task.getTaskList();
    });

    $("#task_all").bind("click", function () {
        if ($("#task_all").is(":checked")) {
            $("[name='task_id']").prop("checked", true);
        } else {
            $("[name='task_id']").prop("checked", false);
        }
    });

    $("#task_create").bind("click",function() {
        $("#task_name_create").val("");
        $("#task_customer_list").html("");
        $("#task_customer_search").html("");
        $("#task_customer").val("");
        $(".newObject").show();
    });

    $(".close").click(function(){
        $(".newObject").hide();
    });



    task.quitTask = function () {
        var str = "";
        $("input:checkbox[name='task_id']:checked").each(function (i) {
            var val = $(this).val();
            str = str + val + "-";
        });

        if(str.length==0) {
            layer.msg("请选择项目", {icon: 1,time: 2000});
            return;
        }

        str = str.substring(0,str.length-1);
        $.ajax({
            type: "post",
            url: _ROOT_+"/task/quitTask",
            data: {
                "ids": str
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("退出成功");
                }else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#task_quit").bind("click",function() {
        task.quitTask();
    });


    task.getCustomerByMobile = function() {
        var mobile = $("#task_customer").val();
        var mobileReg = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(common.nullTrim(mobile).length==0) {
            return;
        }
        if(!mobileReg.test(mobile)) {
            return;
        }

        $.ajax({
            type: "post",
            url: _ROOT_+"/task/getCustomerByMobile",
            data: {
                "mobile": mobile
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var r = data.result;
                    if(r==1) {
                        common.msg("默认创建人已经添加!");
                    }else if(r!=null) {
                        var html = '<img id="task_customer_search_resp" src="'+common.photo(r.photo)+'" alt="'+r.name+'" class="bigImg" customer_id="'+r.customerid+'">';
                        $("#task_customer_search").html(html);
                    }else {
                        $("#task_customer_search").html("<span style=\"position: absolute;width: 150px;top: 45px;right: 20px;\">无此用户</span>");
                    }
                }else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#task_customer").bind("keyup",function() {
        $("#task_customer_search").html("");
        task.getCustomerByMobile();
    });


    $("#task_customer_btn").bind("click",function() {
        var node = $("#task_customer_search_resp");
        if(node && node !=undefined &&  node.attr("customer_id")) {
            var customerId = node.attr("customer_id");
            var alt = node.attr("alt");
            var photo = node.attr("src");

            var imgArr = $("#task_customer_list").children("img");
            var flag = true;
            $.each(imgArr,function(index,n) {
                var customerIdImg = $(n).attr("customer_id");
                if(customerIdImg==customerId) {
                    flag = false;
                    return;
                }
            });
            if(flag) {
                var html = '<img src="'+photo+'" alt="'+alt+'" customer_id="'+customerId+'" style="cursor: pointer">';
                var nodeHtml = $(html);
                nodeHtml.bind("click",function() {
                    $(this).remove();
                });
                $("#task_customer_list").append(nodeHtml);
            }else {
                common.msg(alt+" 已经添加,请勿重复添加!");
            }
        }else {
            common.msg("请输入手机号码,查询用户!");
        }
    });

    task.createTask = function() {
        var taskName = $("#task_name_create").val();
        if(common.nullTrim(taskName).length==0) {
            common.msg("请输入项目名称");
            return;
        }
        var customerIds = "";
        var imgArr = $("#task_customer_list").children("img");
        $.each(imgArr,function(index,n) {
            var customerIdImg = $(n).attr("customer_id");
            if(customerIds.indexOf(customerIdImg)<=-1) {
                customerIds +=  customerIdImg+"-";
            }
        });
        if(common.nullTrim(customerIds).length!=0){
            customerIds = customerIds.substring(0,customerIds.length-1);
        }

        $.ajax({
            type: "post",
            url: _ROOT_+"/task/createTask",
            data: {
                "taskName": taskName,
                "customerIds":customerIds
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("创建成功");
                }else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#task_create_btn").bind("click",function() {
        task.createTask();
    });

    task.getTaskList();
});