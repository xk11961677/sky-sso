var stationInfo = {}
$().ready(function () {

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


});