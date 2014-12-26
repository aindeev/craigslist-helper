package com.aindeev.craigslisthelper.activity.recycler;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        postViewHolder.setAdapter(this);
        postViewHolder.setActivity((Activity)context);
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
            Post duplicate = findPostbyId(post.getId());
            if (duplicate != null) {
                int index = postList.indexOf(duplicate);
                postList.remove(index);
                postList.add(index, post);
                notifyItemChanged(index);
            } else {
                postList.add(location, post);
                notifyItemInserted(location);
                location++;
            }
        }
    }

    public Post findPostbyId(String id) {
        for (Post post : postList) {
            if (post.getId().equals(id))
                return post;
        }
        return null;
    }

    public void removeItem(int position) {
        postList.remove(position);
        notifyItemRemoved(position);
    }
}