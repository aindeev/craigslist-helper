package com.aindeev.craigslisthelper.web;

import android.util.Log;

import com.aindeev.craigslisthelper.util.Boolean;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aindeev on 14-12-07.
 */
public class LoginRequest extends Request<Boolean> {

    private static String LOGIN_URL = "https://accounts.craigslist.org/login";
    private static String EMAIL_INPUT = "inputEmailHandle";
    private static String PASSWORD_INPUT = "inputPassword";

    private static Map<String,String> staticParams = new HashMap<String, String>(){{
        put("p", "0");
        put("rp","");
        put("rt","");
        put("step","confirmation");
    }};

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login")
    };

    private String email;
    private String password;
    private boolean success;

    public LoginRequest(String email, String password) {
        super(RequestType.POST);
        this.email = email;
        this.password = password;
    }

    @Override
    public String getUrl() {
        return LOGIN_URL;
    }

    @Override
    public Header[] getHeaders() {
        return staticHeaders;
    }

    @Override
    public Map<String, String> getStaticParams() {
        return staticParams;
    }

    @Override
    public RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add(PASSWORD_INPUT, password);
        params.add(EMAIL_INPUT, email);
        addStaticParams(params);
        return params;
    }

    @Override
    public void onRequestSuccess(int i, Header[] headers, byte[] bytes) {
        success = true;
        Log.d("CraigslistLogin", new String(bytes));
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {
        success = false;
        Log.d("CraigslistLogin", new String(bytes));
    }

    @Override
    public void beforeRequest() {
        CraigslistClient client = CraigslistClient.instance();
        client.clearCookies();
    }

    @Override
    public Boolean getResult() {
        return new Boolean(success);
    }
}
