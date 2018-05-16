package com.zyk.sso.auth.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author sky
 * @description gson view , 暂时不支持jsonp
 * @create 2017-10-29 下午3:26
 */
public class GsonView extends AbstractView {

    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

//    public static final String DEFAULT_JSONP_CONTENT_TYPE = "application/javascript";

//    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");

//    private boolean extractValueFromSingleKeyModel = false;
    /**
     * 输出长度
     */
    private boolean updateContentLength = true;

    /**
     * 是否缓存
     */
    private boolean disableCaching = true;

    protected Charset charset = Charset.forName("UTF-8");

    private Gson gson = null;

    public GsonView() {
        this.setContentType(DEFAULT_CONTENT_TYPE);
        this.setExposePathVariables(false);
        gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class,new StringNullAdapter()).registerTypeAdapter(Json.class,new SwaggerJsonSerializer()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    protected Object filterModel(Map<String, Object> model) {
        HashMap result = new HashMap(model.size());
        Set renderedAttributes = model.keySet();
        Iterator iterator = model.entrySet().iterator();

        Map.Entry entry;
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        /*if(this.extractValueFromSingleKeyModel && result.size() == 1) {
            iterator = result.entrySet().iterator();
            if(iterator.hasNext()) {
                entry = (Map.Entry)iterator.next();
                return entry.getValue();
            }
        }*/

        return result;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object value = this.filterModel(model);
        /*
        jsonp 处理
        String jsonpParameterValue = this.getJsonpParameterValue(request);
        if(jsonpParameterValue != null) {
            JSONPObject jsonpObject = new JSONPObject(jsonpParameterValue);
            jsonpObject.addParameter(value);
            value = jsonpObject;
        }*/

        String json = "";
        Map param = (Map) value;
        if (param.size() == 1) {
            Iterator iterator = model.entrySet().iterator();
            Map.Entry entry = (Map.Entry) iterator.next();
            json = gson.toJson(entry.getValue());
        }else {
            json = gson.toJson(value);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = (new String(json).getBytes(charset));
        int len = bytes.length;
        if(this.updateContentLength) {
          response.setContentLength(len);
        }

        baos.write(bytes);

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        baos.close();
        out.flush();
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        this.setResponseContentType(request, response);
        response.setCharacterEncoding(charset.name());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1L);
        }
    }

    @Override
    protected void setResponseContentType(HttpServletRequest request, HttpServletResponse response) {
        /*if(this.getJsonpParameterValue(request) != null) {
            response.setContentType(DEFAULT_JSONP_CONTENT_TYPE);
        } else {
            super.setResponseContentType(request, response);
        }*/
        super.setResponseContentType(request, response);
    }
}
