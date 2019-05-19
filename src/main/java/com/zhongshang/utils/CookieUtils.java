package com.zhongshang.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }

    public static String getValueByName(HttpServletRequest request, String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }
        return ((cookie != null) ? cookie.getValue() : "");
    }

    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                   Integer expiry, String domain) {
        Cookie cookie = new Cookie(name, value);

        if (expiry != null) {
            cookie.setMaxAge(expiry.intValue());
        }
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        String ctx = request.getContextPath();
        cookie.setPath((StringUtils.isBlank(ctx)) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                   String domain) {
        Cookie cookie = new Cookie(name, value);

        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        String ctx = request.getContextPath();
        cookie.setPath((StringUtils.isBlank(ctx)) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, "");

        cookie.setMaxAge(0);
        String ctx = request.getContextPath();
        cookie.setPath((StringUtils.isBlank(ctx)) ? "/" : ctx);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }

    public static void cancleAllCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            for (Cookie c : cookies) {
                Cookie cookie = new Cookie(c.getName(), null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setDomain("");
                response.addCookie(cookie);
            }
        }
    }

    /**
     * 获取域名
     *
     * @param request
     * @return
     */
    public static String domain(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (serverName.contains("zhongshang.com")) {
            return "zhongshang.com";
        }
        return "zhongshang.com";
    }

}
