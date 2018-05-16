var common = {};
$().ready(function () {

    common.login = 1;//是否可以未登录访问: 1:否 2:是

    /*$.ajaxSetup({
        beforeSend: function (XMLHttpRequest) {

        },
        error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数

        }
    });*/


    common.getCurrentCustomer = function() {
        $.ajax({
            type: "post",
            url: _ROOT_+"/customer/getCurrentCustomer",
            data: {
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    $("#sub_nav").show();
                    $("#login").show();
                    $("#current_photo").append('<img src="'+common.photo(data.result.photo)+'" align="absmiddle" style="width:40px;border-radius:40px;background: #fff;" >');
                    $("#current_name").html(common.nullTrim(data.result.name));
                    common.getCurrentCustomerSize();
                }else {
                    if(common.login==1) {
                        common.msg(data.msg);
                    }else {
                        $("#un_login").show();
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    common.getCurrentCustomerSize = function() {
        $.ajax({
            type: "post",
            url: _ROOT_+"/customer/getCurrentCustomerSize",
            data: {
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var d = data.result;
                    $("#limit_percent").val(d.percent);

                    $("#limit_size").html(d.size+'/'+d.limitSize+'G');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }

    common.nullTrim = function(data) {
        if(data=='null' || !data) {
            return '';
        }else {
            return data.replace(/^\s+/,"").replace(/\s+$/,"");
        }
    }

    common.photo = function (photo) {
        if(common.nullTrim(photo)=='') {
            return _ROOT_+"/static/front/img/head_default.png";
        }
        return photo;
    }

    common.unLogin = function () {
        layer.open({
            type: 1
            , offset: 'auto'
            , title: '未登录提示'
            , content: '<div style="padding: 20px 100px;">请先登录!</div>'
            , btn: '确定'
            , btnAlign: 'r'
            , shade: 0.8
            , yes: function () {
                layer.closeAll();
                window.location.href = _ROOT_ + "/index/toLogin";
            }
        });
    }

    common.msg = function(content) {
        if("请登录后在操作"==content) {
            common.unLogin();
        }else {
            layer.msg(content, {icon: 1,time: 2000});
        }
    }

    common.success = function (content) {
        layer.msg(content, {icon: 1,time: 2000, end:function(){
                location.reload();
            }});
    }

    common.shade = function () {
        var shade = layer.load(1, {shade: [0.8, '#393D49']});
        $("#layui-layer1").html("");
        return shade;
    }
});