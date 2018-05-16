package com.zyk.sso.auth.common.util;

import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import springfox.documentation.spring.web.json.Json;

/**
 * @author sky
 * @description
 * @create 2017-10-29 下午2:53
 **/
public class ZykGsonHttpMessageConverter extends GsonHttpMessageConverter {

    public ZykGsonHttpMessageConverter() {
        //super.setGson(new GsonBuilder().registerTypeAdapter(Json.class,new SwaggerJsonSerializer()).serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create());
        super.setGson(new GsonBuilder().serializeNulls().registerTypeAdapter(String.class,new StringNullAdapter()).registerTypeAdapter(Json.class,new SwaggerJsonSerializer()).setDateFormat("yyyy-MM-dd HH:mm:ss").create());
    }
}
