package com.aindeev.craigslisthelper.web;

import android.app.Activity;

import com.aindeev.craigslisthelper.posts.Post;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aindeev on 14-12-23.
 */
public class ManageRequest extends AuthRequest<Boolean> {

    private static String MANAGE_URL = "https://post.craigslist.org/manage/";
    private static String CRYPT_NAME = "crypt";
    private static String ACTION_NAME = "action";
    private static String GO_NAME = "go";

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_HOST_NAME, "post.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login/home")
    };

    private Post post;
    private Post.ManageActionType actionType;
    private boolean success = false;

    public ManageRequest(Activity activity, Post post, Post.ManageActionType actionType) {
        super(activity, RequestType.POST);
        this.post = post;
        this.actionType = actionType;
    }

    @Override
    public String getUrl() {
        return MANAGE_URL + post.getId();
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
        params.add(GO_NAME, actionType.toString());
        params.add(ACTION_NAME, actionType.toString());
        params.add(CRYPT_NAME, post.getCryptByAction(actionType));
        return params;
    }

    @Override
    public void onRequestSuccess(int i, Header[] headers, byte[] bytes) {
        success = true;
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public Boolean getResult() {
        return success;
    }
}
