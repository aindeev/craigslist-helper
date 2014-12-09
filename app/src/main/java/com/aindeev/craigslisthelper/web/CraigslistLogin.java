package com.aindeev.craigslisthelper.web;

import android.util.Log;

import com.aindeev.craigslisthelper.util.Boolean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by aindeev on 14-12-07.
 */
public class CraigslistLogin {

    private static String LOGIN_URL = "https://accounts.craigslist.org/login";
    private static String EMAIL_INPUT = "inputEmailHandle";
    private static String PASSWORD_INPUT = "inputPassword";

    private static Map<String,String> staticParams = new HashMap<String, String>(){{
        put("p", "0");
        put("rp","");
        put("rt","");
        put("step","confirmation");
    }};

    public CraigslistLogin() {
    }

    public static void addStaticParams(RequestParams params) {
        for (Map.Entry<String, String> entry : staticParams.entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }
    }

    public static boolean login(String email, String password) {
        CraigslistClient client = CraigslistClient.instance();
        client.clearCookies();

        final Boolean success = new Boolean(false);

        RequestParams params = new RequestParams();

        params.add(PASSWORD_INPUT, password);
        params.add(EMAIL_INPUT, email);
        addStaticParams(params);

        Header[] headers = new Header[6];
        headers[0] = new BasicHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
        headers[1] = new BasicHeader("Origin",
                "https://accounts.craigslist.org");
        headers[2] = new BasicHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers[3] = new BasicHeader("Referer",
                "https://accounts.craigslist.org/login");
        headers[4] = new BasicHeader("Accept-Encoding",
                "gzip,deflate");
        headers[5] = new BasicHeader("Accept-Language",
                "en-GB,en-US;q=0.8,en;q=0.6");


        client.doPost(false, LOGIN_URL, headers, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                success.setValue(true);
                Log.d("CraigslistLogin", new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                success.setValue(false);
                Log.d("CraigslistLogin", new String(bytes));
            }
        });
        return success.isValue();
    }

}
