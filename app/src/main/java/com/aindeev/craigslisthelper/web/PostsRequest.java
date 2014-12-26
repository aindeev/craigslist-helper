package com.aindeev.craigslisthelper.web;

import android.app.Activity;
import android.util.Log;

import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.util.*;
import com.aindeev.craigslisthelper.util.Boolean;
import com.aindeev.craigslisthelper.web.parser.PostParser;
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
public class PostsRequest extends AuthRequest<List<Post>> {

    private static String POSTS_URL = "https://accounts.craigslist.org/login/home";

    private static Header[] staticHeaders = {
            new BasicHeader(CraigslistClient.HEADER_ORIGIN_NAME, "https://accounts.craigslist.org"),
            new BasicHeader(CraigslistClient.HEADER_REFERER_NAME, "https://accounts.craigslist.org/login")
    };

    List<Post> posts;

    public PostsRequest(Activity activity) {
        super(activity, RequestType.GET);
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
        posts = PostParser.parse(response);
    }

    @Override
    public void onRequestFailure(int i, Header[] headers, byte[] bytes) {
        posts = null;
    }

    @Override
    public void beforeRequest() {
    }

    @Override
    public List<Post> getResult() {
        return posts;
    }

}
