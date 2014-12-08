package com.aindeev.craigslisthelper.web;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.aindeev.craigslisthelper.App;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.Cookie;

/**
 * Created by aindeev on 14-12-07.
 */

public class CraigslistClient implements Parcelable {

    public static String PARCEL_NAME = "craigslist_client";

    private static String AUTH_COOKIE_DOMAIN = "accounts.craigslist.org";
    private static String AUTH_COOKIE_NAME = "cl_session";

    AsyncHttpClient client;
    CookieManager cookieManager;

    private static CraigslistClient instance = null;

    private CraigslistClient() {
        client = new AsyncHttpClient();
        cookieManager = new CookieManager(App.getContext());
        client.setCookieStore(cookieManager.getCookieStore());
    }

    public static CraigslistClient instance() {
        if (instance == null) {
            instance = new CraigslistClient();
        }
        return instance;
    }

    public String getAuthCookie() {
        // TODO put this back!!
        // return cookieManager.getCookieValue(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME);
        return "testvalue";
    }

    public void setAuthCookie(String cookieValue) {
        cookieManager.setCookie(AUTH_COOKIE_DOMAIN, AUTH_COOKIE_NAME, cookieValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
