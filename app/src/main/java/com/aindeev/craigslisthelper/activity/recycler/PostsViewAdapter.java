package com.aindeev.craigslisthelper.activity.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.posts.Post;
import java.util.List;

/**
 * Created by aindeev on 14-12-09.
 */

public class PostsViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Post> postList;
    private Context context;

    public PostsViewAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_view, null);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        Post post = postList.get(i);
        postViewHolder.setPost(post);
    }

    @Override
    public int getItemCount() {
        return (null != postList ? postList.size() : 0);
    }

    public void addItems(List<Post> posts) {
        int location = this.postList.size();
        for (Post post : posts) {
            postList.add(location, post);
            notifyItemInserted(location);
            location++;
        }
    }
}