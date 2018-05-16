var station = {}
$().ready(function () {

    $("#task_btn_close").click(function () {
        $(".newNav").hide()
    })

    station.pagination;


    station.getStationList = function (pageNum) {
        var stationName = $("#station_name").val();
        $.ajax({
            type: "post",
            url: _ROOT_ + "/basestation/getStationList",
            data: {
                "pageNum": pageNum,
                "stationName": stationName
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    if (!station.pagination) {
                        station.init(data.result.pageCount, data.result.totalCount, data.result.pageSize, data.result.pageNum);
                    }
                    station.handlePaginationData(data.result.datas);
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    station.init = function (pageCount, totalCount, pageSize, pageNum) {
        $("#page").pagination({
            pageCount: pageCount,
            totalData: totalCount,
            current: pageNum,
            showData: pageSize,
            homePage: '首页',
            endPage: '末页',
            prevContent: '上一页',
            nextContent: '下一页',
            jump: true,
            jumpBtn: '跳转',
            //isHide:true,
            //mode:'fixed',
            coping: true,
            //keepShowPN: true,
            callback: station.handlePaginationClick
        }, function () {
            if (totalCount && totalCount > 0) {
                var html = "总数据 <span style='color: #ff0000;'>" + totalCount + "</span>条，每页显示 <span style='color: #ff0000;'>" + pageSize + "</span>条，总页数为 <span style='color: #ff0000;'>" + pageCount + "</span>页";
                $("#page_record").html(html);
            }
        });
    };

    station.handlePaginationClick = function (api) {
        station.getStationList(api.getCurrent());
        return false;
    };

    station.handlePaginationData = function (d) {
        $("#station_list").html("");
        for (var i = 0; i < d.length; i++) {
            var r = d[i];
            var html = '<tr>' +
                '<td>' + (i + 1) + '.</td>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '">' + r.stationname + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '">' + r.longitude + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '">' + r.latitude + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '">' + r.address + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '">' + r.createtime.substring(0, 10).replace(/-/g, ".") + '</td></a>' +
                '<td><input type="checkbox" name="station_id" value="' + r.stationid + '" station="' + r.stationonlyflag + '"></td>' +
                '</tr>';
            $("#station_list").append(html);
        }
    }


    station.getTaskList = function () {

        var taskName = $("#task_name").val();
        var index = layer.load(1, {
            shade: [0.6, '#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "post",
            url: _ROOT_ + "/task/getMyAllTaskList",
            data: {
                // "pageNum": 1,
                "taskName": taskName
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    $("#task_list").html("");
                    $(".newNav").show();
                    var d = data.result;
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
                    station.taskBindCheckBox();
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

    $("#station_all").bind("click", function () {
        if ($("#station_all").is(":checked")) {
            $("[name='station_id']").prop("checked", true);
        } else {
            $("[name='station_id']").prop("checked", false);
        }
    });

    station.taskBindCheckBox = function () {
        $("#task_all").bind("click", function () {
            if ($("#task_all").is(":checked")) {
                $("[name='task_id']").prop("checked", true);
            } else {
                $("[name='task_id']").prop("checked", false);
            }
        });
    }

    station.delStation = function () {
        var str = "";
        $("input:checkbox[name='station_id']:checked").each(function (i) {
            var val = $(this).val();
            str = str + val + "-";
        });

        if (str.length == 0) {
            layer.msg("请选择站点", {icon: 1, time: 2000});
            return;
        }

        str = str.substring(0, str.length - 1);
        $.ajax({
            type: "post",
            url: _ROOT_ + "/basestation/delStation",
            data: {
                "ids": str
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    common.success("删除成功");
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    $("#station_del").bind("click", function () {
        station.delStation();
    });


    $("#station_mv").bind("click", function () {
        var str = "";
        $("input:checkbox[name='station_id']:checked").each(function (i) {
            var val = $(this).val();
            str = str + val + "-";
        });

        if (str.length == 0) {
            layer.msg("请选择站点", {icon: 1, time: 2000});
            return;
        }
        station.getTaskList();
    });

    station.mvToTask = function () {
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
        if (task.indexOf("-") > -1) {
            layer.msg("请选择单个项目", {icon: 1, time: 2000});
            return;
        }

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

    $("#task_btn_st").bind("click", function () {
        station.mvToTask();
    });

    station.paginationTask;
    station.opt;
    station.taskId;

    station.getTaskStationList = function (taskId, opt, pageNum) {
        station.opt = opt;
        station.taskId = taskId;
        var stationName = $("#station_name").val();
        $.ajax({
            type: "post",
            url: _ROOT_ + "/basestation/getTaskStationList",
            data: {
                "pageNum": pageNum,
                "pageSize": 10,
                "taskId": taskId,
                "stationName": stationName
            },
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == 200) {
                    if (!station.paginationTask) {
                        station.initTask(data.result.pageCount, data.result.totalCount, data.result.pageSize, data.result.pageNum);
                    }
                    station.handlePaginationTaskData(data.result.datas);
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    station.initTask = function (pageCount, totalCount, pageSize, pageNum) {
        $("#page").pagination({
            pageCount: pageCount,
            totalData: totalCount,
            current: pageNum,
            showData: pageSize,
            homePage: '首页',
            endPage: '末页',
            prevContent: '上一页',
            nextContent: '下一页',
            jump: true,
            jumpBtn: '跳转',
            //isHide:true,
            //mode:'fixed',
            coping: true,
            //keepShowPN: true,
            callback: station.handlePaginationTaskClick
        }, function () {
            if (totalCount && totalCount > 0) {
                var html = "总数据 <span style='color: #ff0000;'>" + totalCount + "</span>条，每页显示 <span style='color: #ff0000;'>" + pageSize + "</span>条，总页数为 <span style='color: #ff0000;'>" + pageCount + "</span>页";
                $("#page_record").html(html);
            }
        });
    };

    station.handlePaginationTaskClick = function (api) {
        station.getTaskStationList(station.taskId, station.opt, api.getCurrent());
        return false;
    };

    station.handlePaginationTaskData = function (d) {
        $("#station_list").html("");
        for (var i = 0; i < d.length; i++) {
            var r = d[i];
            var color = "";
            var surveyor = "";
            if (r.loginId == r.surveyor && station.opt == 0) {
                color = "#b7271a";
                surveyor = r.loginId;
            }

            var html = '<tr>' +
                '<td>' + (i + 1) + '.</td>' +
                '<td><a style="color: ' + color + ';" href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '&taskId=' + station.taskId + '">' + r.stationname + '</a></td>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '&taskId=' + station.taskId + '">' + r.longitude + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '&taskId=' + station.taskId + '">' + r.latitude + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '&taskId=' + station.taskId + '">' + r.address + '</td></a>' +
                '<td><a href="' + _ROOT_ + '/basestation/toStationInfo?stationonlyflag=' + r.stationonlyflag + '&taskId=' + station.taskId + '">' + r.createtime.substring(0, 10).replace(/-/g, ".") + '</td></a>' +
                '<td><input type="checkbox" name="station_id" value="' + r.stationid + '" station="' + r.stationonlyflag + '" surveyor="' + surveyor + '"></td>' +
                '</tr>';
            $("#station_list").append(html);
        }
    }


    station.taskStationDel = function (taskId, opt) {
        var str = "";
        $("input:checkbox[name='station_id']:checked").each(function (i) {
            if (opt == 0) {
                if ($(this).attr("surveyor").length != 0) {
                    var val = $(this).val();
                    str = str + val + "-";
                }
            } else {
                var val = $(this).val();
                str = str + val + "-";
            }
        });
        var msg = "请选择站点";
        if (opt == 0) {
            msg = "请选择红色标识站点";
        }

        if (str.length == 0) {
            layer.msg(msg, {icon: 1, time: 2000});
            return;
        }

        str = str.substring(0, str.length - 1);
        $.ajax({
            type: "post",
            url: _ROOT_ + "/stationTask/taskStationDel",
            data: {
                "ids": str,
                "taskId": taskId
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    if (common.nullTrim(data.msg).length != 0) {
                        common.success("删除成功\t" + data.msg);
                    } else {
                        common.success("删除成功");
                    }
                } else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    station.download = function () {
        var str = "";
        $("input:checkbox[name='station_id']:checked").each(function (i) {
            var val = $(this).attr("station");
            str = str + val + "-";
        });

        if (str.length == 0) {
            layer.msg("请选择站点", {icon: 1, time: 2000});
            return;
        }
        str = str.substring(0, str.length - 1);
        /*if(str.indexOf("-")>-1) {
            layer.msg("仅支持单个站点下载,请重新选择", {icon: 1,time: 2000});
            return;
        }*/
        var url = _ROOT_ + "/customer/downFile";

        // var strArray = str.split("-");
        // for(var i=0;i<strArray.length;i++) {
        //     console.log("============"+strArray[i]);
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "stationOnlyFlags").attr("value", str));
        form.appendTo('body').submit().remove();
        // }
    }


    $("#station_download").bind("click", function () {
        station.download();
    });

})
;