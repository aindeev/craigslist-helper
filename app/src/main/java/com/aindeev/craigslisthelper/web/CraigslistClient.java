package com.aindeev.craigslisthelper.web;

import android.net.http.AndroidHttpClient;

import com.aindeev.craigslisthelper.App;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by aindeev on 14-12-07.
 */

public class CraigslistClient {

    public static String HEADER_USER_AGENT_NAME = "User-Agent";
    public static String HEADER_ORIGIN_NAME = "Origin";
    public static String HEADER_ACCEPT_NAME = "Accept";
    public static String HEADER_REFERER_NAME = "Referer";
    public static String HEADER_ACCEPT_ENCODING_NAME = "Accept-Encoding";
    public static String HEADER_ACCEPT_LANGUAGE_NAME = "Accept-Language";
    public static String HEADER_HOST_NAME = "Host";



    public static String HEADER_USER_AGENT_VALUE = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36";
    public static String HEADER_ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    public static String HEADER_ACCEPT_ENCODING_VALUE = "gzip,deflate";
    public static String HEADER_ACCEPT_LANGUAGE_VALUE = "en-GB,en-US;q=0.8,en;q=0.6";
    public static String HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    private static Header[] staticHeaders = {
            new BasicHeader(HEADER_USER_AGENT_NAME, HEADER_USER_AGENT_VALUE),
            new BasicHeader(HEADER_ACCEPT_NAME, HEADER_ACCEPT_VALUE),
            new BasicHeader(HEADER_ACCEPT_ENCODING_NAME, HEADER_ACCEPT_ENCODING_VALUE),
            new BasicHeader(HEADER_ACCEPT_LANGUAGE_NAME, HEADER_ACCEPT_LANGUAGE_VALUE)
    };

    private static String AUTH_COOKIE_DOMAIN = ".craigslist.org";
    private static String AUTH_COOKIE_NAME = "cl_session";

    AsyncHttpClient asyncClient;
    SyncHttpClient syncClient;

    CookieManager cookieManager;

    private static CraigslistClient instance = null;

    private CraigslistClient() {
        java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
        java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);

        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

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

    public RequestHandle doPost(boolean async, String url, Header[] headers, RequestParams params, ResponseHandlerInterface handler) {
        AsyncHttpClient client;

        if (async)
            client = asyncClient;
        else
            client = syncClient;

        Header[] allHeaders = ArrayUtils.addAll(staticHeaders, headers);
        return client.post(App.getContext(), url, allHeaders, params, HEADER_CONTENT_TYPE_VALUE, handler);
    }

    public RequestHandle doGet(boolean async, String url, Header[] headers, RequestParams params, ResponseHandlerInterface handler) {
        AsyncHttpClient client;

        if (async)
            client = asyncClient;
        else
            client = syncClient;

        Header[] allHeaders = ArrayUtils.addAll(staticHeaders, headers);
        return client.get(App.getContext(), url, allHeaders, params, handler);
    }
}
