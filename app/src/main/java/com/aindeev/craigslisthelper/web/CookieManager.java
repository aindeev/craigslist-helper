package com.aindeev.craigslisthelper.web;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;

import java.util.Iterator;
import java.util.List;

/**
 * Created by aindeev on 14-12-07.
 */
public class CookieManager {

    PersistentCookieStore cookieStore;

    public CookieManager(Context context)
    {
        cookieStore = new PersistentCookieStore(context);
    }

    public String getCookieValue(String domain, String name) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Iterator<Cookie> iter = cookies.iterator(); iter.hasNext();) {
            Cookie cookie = iter.next();
            if (cookie.getDomain().equals(domain) &&
                    cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public void setCookie(String domain, String name, String value) {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setDomain(domain);
        cookieStore.addCookie(cookie);
    }

    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public void clearCookies() {
        cookieStore.getCookies().clear();
    }
}
