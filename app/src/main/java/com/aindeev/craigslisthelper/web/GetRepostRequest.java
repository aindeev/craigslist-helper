package com.aindeev.craigslisthelper.web;

import android.app.Activity;

import com.aindeev.craigslisthelper.posts.FormData;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.parser.FormParser;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aindeev on 14-12-23.
 */
public class GetRepostRequest extends AuthRequest<FormData> {

    private static String MANAGE_URL = "https://post.craigslist.org/manage/";
    private static String ACTION_NAME = "action";
    private static String GO_NAME = "go";

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_HOST_NAME, "post.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login/home")
    };

    private static Map<String,String> staticParams = new HashMap<String, String>(){{
        put(ACTION_NAME, "repost");
        put(GO_NAME, "repost");
    }};


    private Post post;
    private FormData formData = null;

    public GetRepostRequest(Activity activity, Post post) {
        super(activity, RequestType.GET);
        this.post = post;
    }

    @Override
    public String getUrl() {
        return MANAGE_URL + post.getId() + "/";
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
        addStaticParams(params);
        return params;
    }

    @Override
    public void onRequestSuccess(int i, Header[] headers, byte[] bytes) {
        FormParser.parse("Continue", new String(bytes));
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public FormData getResult() {
        return formData;
    }

}
