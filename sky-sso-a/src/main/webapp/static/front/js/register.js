$().ready(function () {
    var validate = $("#registerForm").validate({
        invalidHandler: function (form, validator) {
            return false;
        },
        rules: {
            mobile: {
                required: true,
                isMobile: true,
                remote: {
                    url: _ROOT_ + "/customer/checkRegister",
                    type: "post",
                    dataType: "json",
                    data: {
                        mobile: function () {
                            return $("#mobile").val();
                        }
                    }
                }
            },
            password: {
                required: true,
            },
            repassword: {
                required: true,
                isMatch: true
            },
            validateCode: {
                required: true
            }
        },
        messages: {
            mobile: {
                required: "请输入手机号",
                isMobile: "格式不正确",
                remote: "手机已经注册"
            },
            password: {
                required: "请输入密码",
            },
            repassword: {
                required: "请输入确认密码",
                isMatch: "密码不一致"
            },
            validateCode: {
                required: "请填写注册码"
            }
        },
        errorPlacement: function (error, element) { //错误信息位置设置方法
            error.appendTo(element.parent().parent().next().children("div")); //这里的element是录入数据的对象
        }
    });
    jQuery.validator.addMethod("isMatch", function (value, element) {
        if ($("#password").val() != $("#repassword").val())
            return false;
        return true;
    }, "密码不一致");
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "格式不正确");


    $("#registerButton").click(function () {
        $("#registerButton").attr("disabled","true");
        if ($("#registerForm").valid()) {
            var validateCode = $("#validateCode").val();
            if (validateCode != "") {
                var mobile = $("#mobile").val();
                var password = $("#password").val();
                password = $.md5(password);
                $.ajax({
                    type: "POST",
                    url: _ROOT_ + "/customer/register.json",
                    dataType: "json",
                    data: {"mobile": mobile, "password": password, "validateCode": validateCode},
                    success: function (data) {
                        if(data.code==200) {
                            layer.msg("注册成功", {icon: 1,time: 2000, end:function(){
                                    location.href = _ROOT_ + "/login.jsp";
                                }});
                        } else {
                            common.msg(data.msg);
                            $("#registerButton").attr("disabled","false");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $("#registerButton").attr("disabled","false");
                    }
                });
            } else {
                $("#validateCode").focus();
                $("#validateCode").attr("placeholder", "请输入验证码");
                $("#registerButton").attr("disabled","false");
            }
        }else {
            $("#registerButton").attr("disabled","false");
        }
    });


    $("#getValidateCode").click(function () {
        var phone = $("#mobile").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if (phone.length == 11 && mobile.test(phone)) {
            time("getValidateCode");
            $.ajax({
                type: "post",
                url: _ROOT_ + "/customer/sendValidateCode.json",
                dataType: "json",
                data: {"mobile": phone},
                success: function (data) {
                    if(data.code==200) {
                        common.msg("发送成功");
                    } else {
                        wait = 0;
                        common.msg("验证码发送错误,请稍后再试!");
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    common.msg('请求失败' + errorThrown);
                }
            });
        } else {
            common.msg("请输入手机号");
            $("#mobile").focus();
        }
    });
});

wait = 60;

function time(o) {
    var obj = $("#" + o);
    if (wait == 0) {
        obj.removeAttr("disabled");
        obj.text("获取验证码");
        wait = 60;
    } else {
        obj.attr("disabled", true);
        obj.text("重新发送(" + wait + ")");
        wait--;
        setTimeout(function () {
                time(o);
            },
            1000);
    }
}
