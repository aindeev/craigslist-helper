package com.aindeev.craigslisthelper.activity.recycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.ManageRequest;
import com.aindeev.craigslisthelper.web.RequestCallback;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aindeev on 14-12-09.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    private PostsViewAdapter adapter = null;
    private Activity activity = null;
    private Post post = null;
    private TextView title;
    private TextView lastUpdated;
    private ImageButton renewButton;
    private ImageButton deleteButton;

    public PostViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.post_title);
        lastUpdated = (TextView) view.findViewById(R.id.post_last_updated);
        renewButton = (ImageButton) view.findViewById(R.id.renew_button);
        deleteButton = (ImageButton) view.findViewById(R.id.delete_button);

        renewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (post == null)
                    return;

                if (post.isRenewable()) {
                    ManageRequest request = new ManageRequest(activity, post, Post.ManageActionType.RENEW);
                    request.execute(new RequestCallback<Boolean>() {
                        @Override
                        public void onRequestDone(Boolean value) {
                            if(activity != null) {
                                Toast.makeText(activity, "Post has been renewed", Toast.LENGTH_SHORT);
                                post.setDatePosted(Calendar.getInstance().getTime());
                                setPost(post);
                                adapter.notifyItemChanged(getPosition());
                            }
                        }
                    });
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post == null)
                    return;

                ManageRequest request = new ManageRequest(activity, post, Post.ManageActionType.DELETE);
                request.execute(new RequestCallback<Boolean>() {
                    @Override
                    public void onRequestDone(Boolean value) {
                        adapter.removeItem(getPosition());
                    }
                });
            }
        });
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAdapter(PostsViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setPost(Post post) {
        this.post = post;
        title.setText(post.getName());
        lastUpdated.setText(getTimeElapsed(post.getDatePosted()));

        if (!post.isRenewable() && !post.isRepostable()) {
            renewButton.setActivated(false);
        }
    }

    public String getTimeElapsed(Date pastTime) {
        Date now = Calendar.getInstance().getTime();
        long diff = now.getTime() - pastTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 0)
            if (diffDays == 1)
                return diffDays + " day ago";
            else
                return diffDays + " days ago";
        else if (diffHours > 0)
            if (diffHours == 1)
                return diffHours + " hour ago";
            else
                return diffHours + " hours ago";
        else if (diffMinutes > 0)
            if (diffMinutes == 1)
                return diffMinutes + " minute ago";
            else
                return diffMinutes + " minutes ago";
        else if (diffSeconds > 0)
            if (diffSeconds == 1)
                return diffSeconds + "second ago";
            else
            return diffSeconds + "seconds ago";
        else
            return "now";
    }
}
