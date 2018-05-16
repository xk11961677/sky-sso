package com.zyk.sso.auth.util;

import org.apache.commons.lang.StringUtils;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author sky
 */
public class ServletUtils {
    /**
     * 从HttpServletRequest中获取调用当前页面的上个页面
     *
     * @param request HttpServletRequest
     * @return 返回值
     */
    public static String getRefUrl(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    /**
     * 从HttpServletRequest中获取IP地址
     *
     * @param request HttpServletRequest
     * @return 返回值
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");   //未适用任何代理服务
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");  //适用于nginx转发
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  //使用客户端代理
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.length() > 15) {
            ip = StringUtils.substringBefore(ip, ",");
        }
        return ip;
    }

    /**
     * 设置HttpServletResponse输出内容，编码为utf-8格式
     *
     * @param object   输出内容
     * @param response HttpServletResponse
     */
    public static void setResponseOut(Object object, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
            out.print(object);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * 显示图片到浏览器
     *
     * @param response HttpServletResponse
     * @param bytes    文件名称，绝对路径
     * @throws IOException 异常
     */
    public static void showImageToBrower(byte[] bytes, HttpServletResponse response) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes));
        showImageToBrower(bi, response);
    }

    /**
     * 显示图片到浏览器
     *
     * @param response HttpServletResponse
     * @param filename 文件名称，绝对路径
     * @throws IOException 异常
     */
    public static void showImageToBrower(String filename, HttpServletResponse response) throws IOException {
        BufferedImage bi = ImageIO.read(new File(filename));
        showImageToBrower(bi, response);
    }

    /**
     * 显示图片到浏览器
     *
     * @param response    HttpServletResponse
     * @param inputStream 文件流
     * @throws IOException 异常
     */
    public static void showImageToBrower(InputStream inputStream, HttpServletResponse response) throws IOException {
        BufferedImage bi = ImageIO.read(inputStream);
        showImageToBrower(bi, response);
    }

    /**
     * 显示图片到浏览器
     *
     * @param response HttpServletResponse
     * @param bi       BufferedImage
     * @throws IOException 异常
     */
    public static void showImageToBrower(BufferedImage bi, HttpServletResponse response) throws IOException {
        ImageIO.write(bi, "JPEG", response.getOutputStream());
    }


    /**
     * 设置HttpServletResponse对象不缓存数据
     *
     * @param response HttpServletResponse
     */
    public static void setResponseNoCache(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);
    }
}
