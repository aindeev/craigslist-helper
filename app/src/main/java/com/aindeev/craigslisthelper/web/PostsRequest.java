package com.aindeev.craigslisthelper.web;

import android.util.Log;

import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.util.*;
import com.aindeev.craigslisthelper.util.Boolean;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aindeev on 14-12-09.
 */
public class PostsRequest extends Request<List<Post>> {

    private static String POSTS_URL = "https://accounts.craigslist.org/login/home";

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login")
    };

    List<Post> posts;

    public PostsRequest() {
        super();
        posts = new ArrayList<Post>();
    }

    @Override
    public String getUrl() {
        return POSTS_URL;
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
        return null;
    }

    @Override
    public void onRequestSuccess(int i, Header[] headers, byte[] bytes) {
        // TODO parse all posts into a list
        String response = new String(bytes);

        Log.d("PostsRequest", response);
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {
        posts = null;
        Log.d("PostsRequest", new String(bytes));
    }

    @Override
    public void beforeRequest() {
        CraigslistClient client = CraigslistClient.instance();
        client.clearCookies();
    }

    @Override
    public List<Post> getResult() {
        return posts;
    }

}
