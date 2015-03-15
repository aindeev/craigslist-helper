package com.aindeev.craigslisthelper.web;

import android.app.Activity;

import com.aindeev.craigslisthelper.posts.FormData;
import com.aindeev.craigslisthelper.posts.Post;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Map;

/**
 * Created by aindeev on 14-12-23.
 */
public class FormRequest extends AuthRequest<String> {

    private static String MANAGE_URL = "https://post.craigslist.org/manage/";
    private static String CRYPT_NAME = "crypt";
    private static String ACTION_NAME = "action";
    private static String GO_NAME = "go";

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_HOST_NAME, "post.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login/home")
    };

    private FormData formData = null;
    private String response = null;

    public FormRequest(Activity activity, FormData formData) {
        super(activity, RequestType.POST);
        this.formData = formData;
    }

    @Override
    public String getUrl() {
        return formData.getFormUrl();
    }

    @Override
    public Header[] getHeaders() {
        return staticHeaders;
    }

    @Override
    public Map<String, String> getStaticParams() {
        return null;
    }

    @Override
    public RequestParams getParams() {
        RequestParams params = new RequestParams();
        for (Map.Entry <String, String> entry : formData.getData().entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }
        return params;
    }

    @Override
    public void onRequestSuccess(int i, Header[] headers, byte[] bytes) {
        response = new String(bytes);
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public String getResult() {
        return response;
    }

}
