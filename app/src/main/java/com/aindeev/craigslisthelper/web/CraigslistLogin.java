package com.aindeev.craigslisthelper.web;

import android.util.Log;

import com.aindeev.craigslisthelper.util.Boolean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

/**
 * Created by aindeev on 14-12-07.
 */
public class CraigslistLogin {

    private static String LOGIN_URL = "https://accounts.craigslist.org/login";
    private static String EMAIL_INPUT = "inputEmailHandle";
    private static String PASSWORD_INPUT = "inputPassword";

    public CraigslistLogin() {
    }

    public static boolean login(String email, String password) {
        CraigslistClient client = CraigslistClient.instance();
        client.clearCookies();

        final Boolean success = new Boolean(false);

        RequestParams params = new RequestParams();
        params.add(EMAIL_INPUT, email);
        params.add(PASSWORD_INPUT, password);
        client.doPost(false, LOGIN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                success.setValue(true);
                Log.e("CraigslistLogin", "Success!");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                success.setValue(false);
                Log.e("CraigslistLogin", "Failure!");
            }
        });
        Log.e("CraigslistLogin", "We are past the doPost");


        return success.isValue();
    }

}
