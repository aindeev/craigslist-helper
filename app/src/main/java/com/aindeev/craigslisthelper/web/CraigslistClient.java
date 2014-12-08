package com.aindeev.craigslisthelper.web;

import android.net.http.AndroidHttpClient;

import com.aindeev.craigslisthelper.App;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.client.HttpClient;

/**
 * Created by aindeev on 14-12-07.
 */

public class CraigslistClient {

    public static String PARCEL_NAME = "craigslist_client";

    private static String AUTH_COOKIE_DOMAIN = "accounts.craigslist.org";
    private static String AUTH_COOKIE_NAME = "cl_session";

    AsyncHttpClient asyncClient;
    SyncHttpClient syncClient;

    CookieManager cookieManager;

    private static CraigslistClient instance = null;

    private CraigslistClient() {
        asyncClient = new AsyncHttpClient();
        syncClient = new SyncHttpClient();
        cookieManager = new CookieManager(App.getContext());

        syncClient.setCookieStore(cookieManager.getCookieStore());
        asyncClient.setCookieStore(cookieManager.getCookieStore());
    }

    public static CraigslistClient instance() {
        if (instance == null) {
            instance = new CraigslistClient();
        }
        return instance;
    }

    public String getAuthCookie() {
        return cookieManager.getCookieValue(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME);
    }

    public void setAuthCookie(String cookieValue) {
        cookieManager.setCookie(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME, cookieValue);
    }
    
    public void clearCookies() {
        cookieManager.clearCookies();
    }

    public RequestHandle doPost(boolean async, String url, RequestParams params, ResponseHandlerInterface handler) {
        if (async)
            return asyncClient.post(url, params, handler);
        else
            return syncClient.post(url, params, handler);
    }
}
