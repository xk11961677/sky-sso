var stationInfo = {}
$().ready(function () {
    stationInfo.getTower = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/tower/getTower",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        var html = '<tr>\n' +
                            '<td width="%4" style="border-bottom:none;background: #fff;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>'+
                            '<td style="width: 11%;border-top: 1px solid #999999;" class="nrb">铁塔属性：</td>\n' +
                            '<td style="text-align: left;width: 20%;border-top: 1px solid #999999;" class="nlb">' + common.nullTrim(d.housetypevalue) + '</td>\n' +
                            '<td style="width: 13%;border-top: 1px solid #999999;" class="nrb">铁塔类型：</td>\n' +
                            '<td style="text-align: left;width: 20%;border-top: 1px solid #999999;" class="nlb">' + common.nullTrim(d.typevalue) + '</td>\n' +
                            '<td style="width: 16%;border-top: 1px solid #999999;" class="nrb">塔房相对位置：</td>\n' +
                            '<td style="text-align: left;width: 20%;border-top: 1px solid #999999;" class="nlb">' + common.nullTrim(d.housepositionvalue) + '</td>\n' +
                            '</tr>';

                        html += '<tr>\n' +
                            '<td width="%4">&nbsp;</td>'+
                            '<td style="width: 11%;" class="nrb">铁塔高度：</td>\n' +
                            '<td style="text-align: left;width: 20%;" class="nlb">' + common.nullTrim(d.height) + '</td>\n' +
                            '<td style="width: 13%;" class="nrb">平台数量（层）：</td>\n' +
                            '<td style="text-align: left;width: 20%;" class="nlb">' + d.platformNumber + '</td>\n' +
                            '<td style="width: 16%;" class="nrb">抱杆数量（合计）：</td>\n' +
                            '<td style="text-align: left;width: 20%;" class="nlb">' + d.lineCount + '</td>\n' +
                            '</tr>';

                        $("#tower_info").append(html);
                        stationInfo.getTowerPlatform(d.towerid);
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getTowerPlatform = function (towerId) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/tower/getTowerPlatform",
            data: {
                "towerId": towerId,
                "type": 0
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        for (var i = 0; i < d.length; i++) {
                            var r = d[i];
                            var numberZN = "";
                            if (r.platindex == 1) {
                                numberZN = "一";
                            } else if (r.platindex == 2) {
                                numberZN = "二";
                            } else if (r.platindex == 3) {
                                numberZN = "三";
                            } else if (r.platindex == 4) {
                                numberZN = "四";
                            }


                            var arry = r.list;

                            if (arry != null && arry.length!=0) {
                                var m = 0;
                                if (arry.length % 3 > 0) {
                                    m = parseInt(arry.length / 3) + 1;
                                } else {
                                    m = parseInt(arry.length / 3);
                                }

                                var html = '<tr>\n' +
                                    '                    <td rowspan="' + (7 * m) + '" width="4%" style="vertical-align: middle;padding: 0 10px;">第' + numberZN + '平台</td>\n' +
                                    '                </tr>\n' +
                                    '                <tr>\n' +
                                    '                    <td style="width: 11%;" class="nrb">用户属性：</td>\n' +
                                    '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.communicationoperatorvalue + '</td>\n' +
                                    '                    <td style="width: 13%;" class="nrb">平台高度：</td>\n' +
                                    '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.height + '</td>\n' +
                                    '                    <td style="width: 16%;" class="nrb">抱杆信息（占用/现有）：</td>\n' +
                                    '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.baogan + '</td>\n' +
                                    '                </tr>';

                                for (var i = 0; i < arry.length; i += 3) {
                                    var title = "天馈系统" + (i + 1);
                                    var guagao = 1;
                                    if (r.height) {
                                        guagao = r.height + 1;
                                    }
                                    var jiaodu = arry[i].directiondegree;
                                    var xitong = arry[i].modevalue;
                                    var duankou = arry[i].portvalue;

                                    var guagao1 = "";
                                    var jiaodu1 = "";
                                    var xitong1 = "";
                                    var duankou1 = "";

                                    var guagao2 = "";
                                    var jiaodu2 = "";
                                    var xitong2 = "";
                                    var duankou2 = "";

                                    var title1 = "";
                                    var title2 = "";

                                    var a = i + 1;
                                    var b = i + 2;
                                    if (a < arry.length) {
                                        a = -1;
                                        title1 = "天馈系统 " + (i + 2);
                                        guagao1 = guagao;
                                        jiaodu1 = arry[i + 1].directiondegree;
                                        xitong1 = arry[i + 1].modevalue;
                                        duankou1 = arry[i + 1].portvalue;
                                    }
                                    if (b < arry.length) {
                                        b = -1;
                                        title2 = "天馈系统 " + (i + 3);

                                        guagao2 = guagao;
                                        jiaodu2 = arry[i + 2].directiondegree;
                                        xitong2 = arry[i + 2].modevalue;
                                        duankou2 = arry[i + 2].portvalue;
                                    }
                                    html += '<tr>\n' +
                                        '       <td colspan="2">' + title + '</td>\n' +
                                        '       <td colspan="2">' + title1 + '</td>\n' +
                                        '       <td colspan="2">' + title2 + '</td>\n' +
                                        '    </tr>';


                                    html += '<tr>\n' +
                                        '                    <td style="width: 11%;" class="nrb">天线挂高：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + guagao + '</td>\n' +
                                        '                    <td style="width: 13%;" class="nrb">天线挂高：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + guagao1 + '</td>\n' +
                                        '                    <td style="width: 16%;" class="nrb">天线挂高：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + guagao2 + '</td>\n' +
                                        '                </tr>\n' +
                                        '                <tr>\n' +
                                        '                    <td style="width: 11%;" class="nrb">方位角：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + jiaodu + '</td>\n' +
                                        '                    <td style="width: 13%;" class="nrb">方位角：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + jiaodu1 + '</td>\n' +
                                        '                    <td style="width: 16%;" class="nrb">方位角：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + jiaodu2 + '</td>\n' +
                                        '                </tr>\n' +
                                        '                <tr>\n' +
                                        '                    <td style="width: 11%;" class="nrb">系统类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + xitong + '</td>\n' +
                                        '                    <td style="width: 13%;" class="nrb">系统类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + xitong1 + '</td>\n' +
                                        '                    <td style="width: 16%;" class="nrb">系统类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + xitong2 + '</td>\n' +
                                        '                </tr>\n' +
                                        '                <tr>\n' +
                                        '                    <td style="width: 11%;" class="nrb">端口类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + duankou + '</td>\n' +
                                        '                    <td style="width: 13%;" class="nrb">端口类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + duankou1 + '</td>\n' +
                                        '                    <td style="width: 16%;" class="nrb">端口类型：</td>\n' +
                                        '                    <td style="text-align: left;width: 20%;" class="nlb">' + duankou2 + '</td>\n' +
                                        '                </tr>\n';


                                }
                            }

                            $("#tower_platform").append(html);
                        }
                    }
                    stationInfo.getTowerPlatformGps(towerId);
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getTowerPlatformGps = function (towerId) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/tower/getTowerPlatform",
            data: {
                "towerId": towerId,
                "type": 1
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        for (var i = 0; i < d.length; i++) {
                            var r = d[i];
                            var html = '<tr>\n' +
                                '                    <td rowspan="7"  style="width:4%;vertical-align: middle;border-top: 1px solid #999999;">GPS</td>\n' +
                                '                </tr>\n' +
                                '                <tr>\n' +
                                '                    <td style="width: 11%;" class="nrb">平台高度：</td>\n' +
                                '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.height + '</td>\n' +
                                '                    <td style="width: 13%;" class="nrb">抱杆数量：</td>\n' +
                                '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.baogan + '</td>\n' +
                                '                    <td style="width: 18%;" class="nrb">天线数量：</td>\n' +
                                '                    <td style="text-align: left;width: 20%;" class="nlb">' + r.lineCount + '</td>\n' +
                                '                </tr>';
                            $("#tower_platform").append(html);
                        }
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getBatterCapability = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/batterybility/getBatteryCapability",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        var html = '<tr>' +
                            '<td>' +
                            '设备总功耗： <span>' + common.nullTrim(d.dissipation) + '</span>' +
                            '</td>' +
                            '<td>' +
                            '电池容量： <span>' + common.nullTrim(d.capability) + '</span>' +
                            '</td>' +
                            '<td>' +
                            '整流器模型值： <span>' + common.nullTrim(d.rectifiermodulevalue) + '</span>' +
                            '</td>' +
                            '</tr>';
                        $("#batter_capability_info").append(html);
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getAcBox = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/acbox/getAcBox",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        stationInfo.getTerminal(d.acboxid, 1, 'ax','');
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getDcBox = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/dcbox/getDcBox",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        stationInfo.getTerminal(d.dcboxid, 0, 'dx','');
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    stationInfo.getBatterGroup = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/batterygroup/getBatteryGroup",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        stationInfo.getTerminal(d.batterygroupid, 2, 'bg_one',1);
                        stationInfo.getTerminal(d.batterygroupid, 2, 'bg_two',2);
                        stationInfo.getTerminalOther(d, 3, 'battery_group_terminal_info');
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getTerminalOther = function (data, fkType, HtmlId) {
        var s = data.switchbatterymodel;
        var l = data.lineelectricity;
        var fkId = data.batterygroupid;

        $.ajax({
            type: "post",
            url: _ROOT_ + "/dcbox/getTerminal",
            data: {
                "fkId": fkId,
                "fkType": fkType
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        var temp = d.length;
                        if(temp==0) {
                            temp == 1;
                        }
                        var html = '<tr>\n' +
                    '                    <td rowspan="'+temp*2+'" width="4%" style="padding: 0 10px;">电源模块</td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td colspan="4" class="nrb">开关电源型号：</td>\n' +
                    '                    <td colspan="4" class="nlb">'+s+'</td>\n' +
                    '                    <td colspan="4" class="nrb">运行电流：</td>\n' +
                    '                    <td colspan="4" class="nlb">'+l+'</td>\n' +
                    '                </tr>\n' ;

                        for(var i=0;i<d.length;i++) {
                            var r = d[i];
                            html +=
                            '                <tr>\n' +
                            '                    <td colspan="4">整流能力：</td>\n' +
                            '                    <td colspan="4">'+r.electricityvalue+'</td>\n' +
                            '                    <td colspan="4">整流模块数量（现有/损坏）：</td>\n' +
                            '                    <td colspan="4">'+r.canusevalue+'</td>\n' +
                            '                </tr>';
                        }

                        $("#battery_group_terminal_info").append(html);
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }



    stationInfo.getTerminal = function (fkId, fkType, HtmlId,type) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/dcbox/getTerminal",
            data: {
                "fkId": fkId,
                "fkType": fkType,
                "type":type
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        var rs = d.rs;
                        var kk = d.kk;
                        $.each(rs, function (key, values) {
                            $("#"+HtmlId+"_rs_" + key + "").html(values);
                        });

                        $.each(kk, function (key, values) {
                            $("#"+HtmlId+"_kk_" + key + "").html(values);
                        });
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getDevice = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/device/getDeviceInfo",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        for (var i = 0; i < d.list.length; i++) {
                            var r = d.list[i];
                            var spec = "";
                            if (r.spec) {
                                spec = r.spec;
                            } else if (r.width) {
                                spec = r.length + "*" + r.width + "*" + r.height;
                            }

                            var hm = '<tr>\n' +
                                '                    <td style="width: 20%;" >' + (i + 1) + '</td>\n' +
                                '                    <td style="width: 20%;" >' + r.name + '</td>\n' +
                                '                    <td style="width: 20%;" >' + spec + '</td>\n' +
                                '                    <td style="width: 20%;" >' + r.indexs + '</td>\n' +
                                '                    <td style="width: 20%;" >' + r.remarks + '</td>\n' +
                                '                 </tr>';
                            $("#device_info").append(hm);
                        }
                    }
                } else {
                    common.msg(data.msg);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.getTypeDesc = function (type) {
        //1.自由摆放 2.内墙 3.外墙 4.墙中间 5.空调 6.内侧门 7.外侧门 8.内侧地线排 9.外侧地线排
        if (type == 1) {
            return "自由摆放";
        } else if (type == 2) {
            return "内墙";
        } else if (type == 3) {
            return "外墙";
        } else if (type == 4) {
            return "墙中间";
        } else if (type == 5) {
            return "空调";
        } else if (type == 6) {
            return "内侧门";
        } else if (type == 7) {
            return "外侧门";
        } else if (type == 8) {
            return "内侧地线排";
        } else if (type == 9) {
            return "外侧地线排";
        } else {
            return "";
        }
    }


    stationInfo.getTaskList = function () {

        var taskName = $("#task_name").val();
        var index = layer.load(1, {
            shade: [0.6, '#fff'] //0.1透明度的白色背景
        });
        $(".layui-layer-dialog").css("top", "150px");
        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/getMyTaskList",
            data: {
                "pageNum": 1,
                "taskName": taskName
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    $("#task_list").html("");
                    $(".newNav").show();
                    var d = data.result.datas;
                    for (var i = 0; i < d.length; i++) {
                        var r = d[i];
                        var html = '<tr>' +
                            '<td>' + (i + 1) + '.</td>' +
                            '<td style="width: 60%;text-align:left;">' + r.taskname + '</td>' +
                            '<td>' + r.createtime.substring(0, 10).replace(/-/g, ".") + '</td>' +
                            '<td><input type="checkbox" value="' + r.taskid + '" name="task_id"></td>' +
                            '</tr>';
                        $("#task_list").append(html);
                    }
                    stationInfo.taskBindCheckBox();
                    layer.close(index);
                } else {
                    common.msg(data.msg);
                    layer.close(index);
                    $(".layui-layer-dialog").css("top", "150px");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.close(index);
            }
        })
    }

    stationInfo.taskBindCheckBox = function () {
        $("#task_all").bind("click", function () {
            if ($("#task_all").is(":checked")) {
                $("[name='task_id']").prop("checked", true);
            } else {
                $("[name='task_id']").prop("checked", false);
            }
        });
    }


    stationInfo.mvToTask = function () {
        var str = $("#station_id").val();

        var task = "";
        $("input:checkbox[name='task_id']:checked").each(function (i) {
            var val = $(this).val();
            task = task + val + "-";
        });
        if (task.length == 0) {
            layer.msg("请选择项目", {icon: 1, time: 2000});
            return;
        }
        task = task.substring(0, task.length - 1);

        $.ajax({
            type: "post",
            url: _ROOT_ + "/stationTask/mvToTask",
            data: {
                "taskIds": task,
                "stationIds": str
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("操作成功");
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    stationInfo.delStation = function () {
        var str = $("#station_id").val();
        $.ajax({
            type: "post",
            url: _ROOT_ + "/basestation/delStation",
            data: {
                "ids": str
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    layer.msg("删除成功", {
                        icon: 1, time: 2000, end: function () {
                            location = _ROOT_ + "/basestation/toStationList";
                        }
                    });
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#task_btn_st").bind("click", function () {
        stationInfo.mvToTask();
    });

    $("#task_btn_close").click(function () {
        $(".newNav").hide()
    })

    $("#station_mv").bind("click", function () {
        stationInfo.getTaskList();
    });

    $("#station_del").bind("click", function () {
        layer.confirm('您确定删除吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            stationInfo.delStation();
        }, function () {
            layer.closeAll();
        });
        $(".layui-layer-dialog").css("top", "150px");
    });


});