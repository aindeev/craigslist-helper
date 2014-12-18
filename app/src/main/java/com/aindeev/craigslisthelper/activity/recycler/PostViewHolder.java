package com.aindeev.craigslisthelper.activity.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.posts.Post;
import com.cengalabs.flatui.views.FlatTextView;

/**
 * Created by aindeev on 14-12-09.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    protected FlatTextView title;

    public PostViewHolder(View view) {
        super(view);
        this.title = (FlatTextView) view.findViewById(R.id.title);
    }

    public void setPost(Post post) {
        title.setText(post.getName());
        // TODO proper formatting for post
    }

}
