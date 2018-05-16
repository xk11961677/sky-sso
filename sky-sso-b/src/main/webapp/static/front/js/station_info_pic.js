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
                        if (common.nullTrim(d.photos).length>0) {
                            var arr = d.photos.split(";");
                            var html = '';
                            var n = 0;
                            for (var i = 0; i < arr.length; i++) {
                                if (n==0) {
                                    html += '<tr style="text-align: left;">';
                                }
                                n++;
                                html +=
                                    '<td>\n' +
                                    '    <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/tower/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '</td>\n';

                                if (n==4) {
                                    n = 0;
                                    html += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#tower_info").append(html);
                                stationInfo.lazy($("#tower_info"));
                            }
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
                        if (common.nullTrim(d.photos).length>0) {
                            var arr = d.photos.split(";");
                            var n = 0;
                            var html = '';
                            for (var i = 0; i < arr.length; i++) {
                                if (n==0) {
                                    html += '<tr  style="text-align: left;">';
                                }
                                n++;
                                html += '' +
                                    '   <td>\n' +
                                    '       <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/acbox/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '   </td>\n';
                                if(n==4) {
                                    n = 0;
                                    html += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#ax_box_info").append(html);
                                stationInfo.lazy($("#ax_box_info"));
                            }
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
                        if (common.nullTrim(d.photos).length>0) {
                            var arr = d.photos.split(";");
                            var n = 0;
                            var html = '';
                            for (var i = 0; i < arr.length; i++) {
                                if (n==0) {
                                    html += '<tr  style="text-align: left;">';
                                }
                                n++;
                                html +=
                                    '  <td>\n' +
                                    '    <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/dcbox/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '  </td>\n';
                                if(n==4) {
                                    n = 0;
                                    html += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#dx_box_info").append(html);
                                stationInfo.lazy($("#dx_box_info"));
                            }
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
                        if (common.nullTrim(d.photosterminalsequence).length>0) {
                            var arr = d.photosterminalsequence.split(";");
                            var n = 0;
                            var html = '';
                            for (var i = 0; i < arr.length; i++) {
                                if (n==0) {
                                    html += '<tr  style="text-align: left;">';
                                }
                                n++;
                                html +=
                                    '   <td>\n' +
                                    '       <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/terminal/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '   </td>\n';
                                if(n==4) {
                                    n = 0;
                                    html += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#battery_group_terminal_info").append(html);
                                stationInfo.lazy($("#battery_group_terminal_info"));
                            }
                        }


                        if (common.nullTrim(d.photosbatterygroup).length>0) {
                            var n1 = 0;
                            var html1 = '';
                            var arr = d.photosbatterygroup.split(";");
                            for (var i = 0; i < arr.length; i++) {
                                if (n1==0) {
                                    html1 += '<tr  style="text-align: left;">';
                                }
                                n1++;
                                html1 += '' +
                                    '    <td>\n' +
                                    '        <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/battery/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '    </td>\n';
                                if(n1==4) {
                                    n1 = 0;
                                    html1 += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#battery_group_info").append(html1);
                                stationInfo.lazy($("#battery_group_info"));
                            }

                        }


                        if (common.nullTrim(d.photosbatterygroup).length>0) {
                            var n2 = 0;
                            var html2 = '';
                            var arr = d.photosbatterygroup.split(";");
                            for (var i = 0; i < arr.length; i++) {
                                if (n2==0) {
                                    html2 += '<tr  style="text-align: left;">';
                                }
                                n2++;
                                html2 += '' +
                                    '                    <td>\n' +
                                    '                        <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/rectification/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '                    </td>\n' +
                                    '                ';
                                if(n2==4) {
                                    n2 = 0;
                                    html2 += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#battery_group_info").append(html2);
                                stationInfo.lazy($("#battery_group_info"));
                            }

                        }


                        if (common.nullTrim(d.photosbattery).length>0) {
                            var arr = d.photosbattery.split(";");
                            var n3 = 0;
                            var html3 = '';
                            for (var i = 0; i < arr.length; i++) {
                                if (n3==0) {
                                    html3 += '<tr  style="text-align: left;">';
                                }
                                n3++;
                                html3 += '' +
                                    '                    <td>\n' +
                                    '                        <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/battery/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '                    </td>\n' +
                                    '                ';
                                if(n3==4) {
                                    n3 = 0;
                                    html3 += '</tr>';
                                }
                            }
                            if(arr.length>0) {
                                $("#battery_info").append(html3);
                                stationInfo.lazy($("#battery_info"));
                            }
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


    stationInfo.getDevice = function (stationOnlyFlag) {
        $.ajax({
            type: "post",
            url: _ROOT_ + "/device/getDeviceInfo",
            data: {
                "stationOnlyFlag": stationOnlyFlag
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    if (d) {
                        var arr = d.image.split(";");
                        var n3 = 0;
                        var html3 = '';
                        if(common.nullTrim(d.image).length>0) {
                            for (var i = 0; i < arr.length; i++) {
                                if (n3==0) {
                                    html3 += '<tr  style="text-align: left;">';
                                }
                                html3 += '' +
                                    '<td>' +
                                    '  <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/device/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                    '</td>' +
                                    '';
                                if(n3==4) {
                                    n3 = 0;
                                    html3 += '</tr>';
                                }
                            }
                        }

                         var arrt = d.imgs.split(";");

                         if(common.nullTrim(d.imgs).length>0) {
                             for (var i = 0; i < arrt.length; i++) {
                                 if (n3==0) {
                                     html3 += '<tr  style="text-align: left;">';
                                 }
                                 html3 += '' +
                                     '<td>' +
                                     '  <img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/device/deviceImgs/' + arrt[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                     '</td>' +
                                     '';
                                 if(n3==4) {
                                     n3 = 0;
                                     html3 += '</tr>';
                                 }
                             }
                         }
                         if(common.nullTrim(html3).length!=0) {
                             $("#device_info").append(html3);
                             stationInfo.lazy($("#device_info"));
                         }


                         var n = 0;

                         for (var h = 0; h < d.list.length; h++) {
                             var r = d.list[h];
                             if (common.nullTrim(r.imgs).length > 0) {
                                 var arr = r.imgs.split(";");
                                 for (var i = 0; i < arr.length; i++) {
                                     if (n==0) {
                                         var html = '';
                                         html += '<tr  style="text-align: left;">';
                                     }
                                     html += '<td>' +
                                             '<img class="lazy" data-original="' + _ROOT_ + '/static/surveytestpic/' + stationOnlyFlag + '/device/deviceModel/' + arr[i] + '" onerror="stationInfo.nofind();" style="width: 300px;height: 500px;">\n' +
                                           '</td>';
                                     if(n==4) {
                                         n = 0;
                                         html += '</tr>';
                                         $("#device_model").append(html);
                                     }
                                 }
                                 if(arr.length>0) {
                                     stationInfo.lazy($("#device_model"));
                                 }
                             }
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
                    $(".layui-layer-dialog").css("top", "150px");
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
                    $(".layui-layer-dialog").css("top", "150px");
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

    stationInfo.nofind = function (){
        var img = event.srcElement;
        img.src = "";
        img.onerror = null;
    }


    stationInfo.lazy = function(container) {
        $("img.lazy").lazyload({
            // placeholder : "img/grey.gif", //用图片提前占位
            // placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
            effect: "fadeIn", // 载入使用何种效果
            // effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
            threshold: 200, // 提前开始加载
            // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
            // event: 'click',  // 事件触发时才加载
            // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
            container: container,  // 对某容器中的图片实现效果
            // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
            failurelimit : 10 // 图片排序混乱时
            // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
        });
    }


});