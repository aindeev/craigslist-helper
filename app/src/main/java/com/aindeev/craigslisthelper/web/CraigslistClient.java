package com.aindeev.craigslisthelper.web;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

/**
 * Created by aindeev on 14-12-07.
 */

public class CraigslistClient extends WebClient {

    private static String AUTH_COOKIE_DOMAIN = "accounts.craigslist.org";
    private static String AUTH_COOKIE_NAME = "cl_session";

    CookieManager cookieManager;

    public CraigslistClient() {
        super();
        cookieManager = new CookieManager();
        cookieManager.setCookiesEnabled(true);
        this.setCookieManager(cookieManager);
    }

    public String getAuthCookie() {
        Cookie cookie = this.cookieManager.getCookie(AUTH_COOKIE_NAME);
        return cookie.getValue();
    }

    public void setAuthCookie(String cookieValue) {
        Cookie cookie = new Cookie(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME, cookieValue);
        cookieManager.addCookie(cookie);
    }
}
