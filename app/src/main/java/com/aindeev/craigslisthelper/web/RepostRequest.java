package com.aindeev.craigslisthelper.web;

import android.app.Activity;

import com.aindeev.craigslisthelper.posts.FormData;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.parser.FormParser;

/**
 * Created by aindeev on 15-01-10.
 */
public class RepostRequest {

    Activity activity;
    Post post;

    public RepostRequest(Activity activity, Post post) {
        this.activity = activity;
        this.post = post;
    }

    public boolean execute() {
        GetRepostRequest getRepostRequest = new GetRepostRequest(activity, post);
        FormData data = getRepostRequest.execute();

        FormRequest repostRequest = new FormRequest(activity, data);
        data = FormParser.parse("Continue", repostRequest.execute());

        FormRequest publishRequest = new FormRequest(activity, data);
        String response = publishRequest.execute();
        return (response != null);
    }

}
