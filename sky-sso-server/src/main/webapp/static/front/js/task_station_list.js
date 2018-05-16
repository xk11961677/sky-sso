var taskStation = {}
$().ready(function () {


    $(".close").click(function () {
        $(".newObject").hide()
    });

    taskStation.getCustomerByMobile = function () {
        var mobile = $("#task_customer").val();
        var mobileReg = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if (common.nullTrim(mobile).length == 0) {
            return;
        }
        if (!mobileReg.test(mobile)) {
            return;
        }

        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/getCustomerByMobile",
            data: {
                "mobile": mobile
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var r = data.result;
                    if (r == 1) {
                        common.msg("默认创建人已经添加!");
                    } else if (r != null) {
                        var html = '<img id="task_customer_search_resp" src="' + common.photo(r.photo) + '" alt="' + r.name + '" class="bigImg" customer_id="' + r.customerid + '">';
                        $("#task_customer_search").html(html);
                    } else {
                        $("#task_customer_search").html("<span style=\"position: absolute;width: 150px;top: 45px;right: 20px;\">无此用户</span>");
                    }
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#task_customer").bind("keyup", function () {
        $("#task_customer_search").html("");
        taskStation.getCustomerByMobile();
    });


    $("#task_customer_btn").bind("click", function () {
        var node = $("#task_customer_search_resp");
        if (node && node != undefined && node.attr("customer_id")) {
            var customerId = node.attr("customer_id");
            var alt = node.attr("alt");
            var photo = node.attr("src");

            var imgArr = $("#task_customer_list").children("img");
            var flag = true;
            $.each(imgArr, function (index, n) {
                var customerIdImg = $(n).attr("customer_id");
                if (customerIdImg == customerId) {
                    flag = false;
                    return;
                }
            });
            if (flag) {
                var html = '<img src="' + photo + '" alt="' + alt + '" customer_id="' + customerId + '" style="cursor: pointer">';
                var nodeHtml = $(html);
                nodeHtml.bind("click", function () {
                    $(nodeHtml).remove();
                });
                $("#task_customer_list").append(nodeHtml);
            } else {
                common.msg(alt + " 已经添加,请勿重复添加!");
            }
        } else {
            common.msg("请输入手机号码,查询用户!");
        }
    });


    taskStation.getTaskCustomer = function (taskId) {
        var index = layer.load(1, {
            shade: [0.6, '#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/getTaskCustomer",
            data: {
                "taskId": taskId
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    $("#task_customer_list").html("");
                    $(".newObject").show();
                    var d = data.result;
                    for (var i = 0; i < d.length; i++) {
                        var r = d[i];
                        var html = '<img src="' + common.photo(r.photo) + '" alt="' + r.name + '" customer_id="' + r.customerid + '" style="cursor: pointer;border-radius:40px;">';
                        var nodeHtml = $(html);
                        if (r.customerid != r.creater) {
                            nodeHtml.bind("click", function () {
                                $(this).remove();
                            });
                        }
                        $("#task_customer_list").append(nodeHtml);
                    }
                    layer.close(index);
                } else {
                    common.msg(data.msg);
                    layer.close(index);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.close(index);
            }
        })
    }


    taskStation.updateTaskCustomer = function (taskId) {
        var customerIds = "";
        var imgArr = $("#task_customer_list").children("img");
        $.each(imgArr, function (index, n) {
            var customerIdImg = $(n).attr("customer_id");
            if (customerIds.indexOf(customerIdImg) <= -1) {
                customerIds += customerIdImg + "-";
            }
        });
        if (common.nullTrim(customerIds).length != 0) {
            customerIds = customerIds.substring(0, customerIds.length - 1);
        }

        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/updateTaskCustomer",
            data: {
                "taskId": taskId,
                "customerIds": customerIds
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("创建成功");
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    taskStation.getTopTaskCustomer = function (taskId) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/getTaskCustomer",
            data: {
                "taskId": taskId
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    for (var i = 0; i < d.length; i++) {
                        var r = d[i];
                        var html = '<li><img src="' + common.photo(r.photo) + '" alt="" style="width: 35px;height: 35px;border-radius:40px;"></li>';
                        if (i < 9) {
                            $("#task_customer_ul").append(html);
                        }
                    }
                    var btn = $('<li class="add" style="cursor: pointer"> + </li>');
                    $("#task_customer_ul").append(btn);
                    btn.bind("click", function () {
                        $("#task_customer").val("");
                        taskStation.getTaskCustomer(taskId);
                    });
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    taskStation.lockStation = function (taskId) {
        var str = "";
        $("input:checkbox[name='station_id']:checked").each(function (i) {
            var val = $(this).val();
            str = str + val + "-";
        });
        str = str.substring(0, str.length - 1);
        if (str.indexOf("-") > -1) {
            layer.msg("请选择单个站点", {icon: 1, time: 2000});
            return;
        }
        $.ajax({
            type: "post",
            url: _ROOT_ + "/stationTask/lockStation",
            data: {
                "taskId": taskId,
                "stationId": str
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("锁定成功");
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })

    }

    /**
     * 项目上传站点信息
     * @param taskItemId
     * @param taskId
     */
    taskStation.uploadStation = function (taskId) {
        $("#upload_station_file").click();
        $("#upload_station_file").bind("change", function () {
            var index = layer.load(1, {
                shade: [0.6, '#fff'] //0.1透明度的白色背景
            });
            $.ajaxFileUpload({
                url: _ROOT_ + '/basestation/doStationImport',
                secureuri: false,//是否启用安全机制
                fileElementId: 'upload_station_file',//file的id
                data: {"taskId": taskId},
                dataType: 'text/html',//返回的类型
                success: function (result) {
                    result = JSON.parse(result);
                    if (result.code == 200) {
                        layer.close(index);
                        if (common.nullTrim(result.msg).length == 0) {
                            location.reload();
                        }else {
                            layer.msg(result.msg, {icon: 1,time: 2000, end:function(){
                                    location.reload();
                            }});
                        }
                    } else {
                        common.msg(result.msg);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.close(index);
                }
            });
        });
    }

});