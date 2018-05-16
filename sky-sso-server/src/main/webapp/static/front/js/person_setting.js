var person = {};
$().ready(function () {

    person.getCustomerInfo = function() {
        $.ajax({
            type: "post",
            url: _ROOT_+"/customer/getCurrentCustomer",
            data: {
            },
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 200) {
                    var info = data.result;
                    if(info) {
                        $("#realName").val(common.nullTrim(info.name));
                        $("#photo_big").append('<img src="'+common.photo(info.photo)+'" align="absmiddle" style="width:150px;border-radius:150px;background: #fff;box-shadow:0px 0px 12px #7E7E7E;" >');
                        person.init(info.photo);
                    }

                }else {
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        })
    }


    person.init = function(imgSrc) {
        var options = {
            thumbBox: '.thumbBox',
            spinner: '.spinner',
            imgSrc: common.photo(imgSrc)
        }
        var cropper = $('.imageBox').cropbox(options);
        $('#upload-file').on('change', function(){
            var reader = new FileReader();
            reader.onload = function(e) {
                options.imgSrc = e.target.result;
                cropper = $('.imageBox').cropbox(options);
            }
            reader.readAsDataURL(this.files[0]);
            this.files = [];
        })
        $('#btnCrop').on('click', function(){
            var img = cropper.getDataURL();
            $('.cropped').html('');
            $('.cropped').append('<img id="imgCroop" src="'+img+'" align="absmiddle" style="width:40px;margin-top:4px;border-radius:40px;box-shadow:0px 0px 12px #7E7E7E;" ><p>40px*40px</p>');
            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:150px;margin-top:4px;border-radius:150px;box-shadow:0px 0px 12px #7E7E7E;"><p>150px*150px</p>');
            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
        })

        //放大
        $('#btnZoomIn').on('click', function(){
            cropper.zoomIn();
        })

        //缩小
        $('#btnZoomOut').on('click', function(){
            cropper.zoomOut();
        })
    }


    person.upload = function() {
        var index = layer.load(1, {
            shade: [0.6,'#fff'] //0.1透明度的白色背景
        });

        var name = $("#realName").val();
        var image = $("#imgCroop").attr("src");
        if(common.nullTrim(name).length==0) {
            common.msg("请填写姓名");
            layer.close(index);
            return;
        }

        $.ajax({
            type: "post",
            url: _ROOT_+"/customer/updateCutomer",
            data: {
                "name":name,
                "image":image
            },
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.code == 200) {
                   common.success("修改成功");
                }else {
                    layer.close(index);
                    common.msg(data.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                layer.close(index);
            }
        })
    }

    $("#sub_btn").bind("click",function() {
        person.upload();
    });

    $("#cancel_btn").bind("click",function() {
        window.history.go(-1);
    });
})