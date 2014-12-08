package com.aindeev.craigslisthelper.web;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.Cookie;

/**
 * Created by aindeev on 14-12-07.
 */

public class CraigslistClient {

    private static String AUTH_COOKIE_DOMAIN = "accounts.craigslist.org";
    private static String AUTH_COOKIE_NAME = "cl_session";

    AsyncHttpClient client;
    CookieManager cookieManager;

    public CraigslistClient(Context context) {
        client = new AsyncHttpClient();
        cookieManager = new CookieManager(context);
        client.setCookieStore(cookieManager.getCookieStore());
    }

    public String getAuthCookie() {
        // TODO put this back!!
        // return cookieManager.getCookieValue(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME);
        return "testvalue";
    }

    public void setAuthCookie(String cookieValue) {
        cookieManager.setCookie(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME, cookieValue);
    }
}
