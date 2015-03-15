package com.aindeev.craigslisthelper.workers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.activity.PostsActivity;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.FormRequest;
import com.aindeev.craigslisthelper.web.ManageRequest;
import com.aindeev.craigslisthelper.web.RepostRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by aindeev on 15-01-02.
 */
public class RenewTask extends AsyncTask<String, Void, Void> {

    private List<Post> posts;
    Activity activity;
    MaterialDialog dialog;
    int numRenewed = 0;
    boolean doTask = true;

    public RenewTask(Activity activity, List<Post> posts) {
        super();
        this.posts = new ArrayList<Post>();
        this.posts.addAll(posts);

        this.activity = activity;

        for(Post post : posts) {
            long daysPassed = getDateDiff(post.getDatePosted(), Calendar.getInstance().getTime(), TimeUnit.DAYS);
            if (!post.isRenewable() && daysPassed < 30) {
                posts.remove(post);
            }
        }

        if (posts.size() > 0) {
            View view = activity.getLayoutInflater().inflate(R.layout.renew_all_dialog, null);
            dialog = new MaterialDialog.Builder(activity)
                    .title(R.string.renew_all_message)
                    .customView(view)
                    .build();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            dialog = new MaterialDialog.Builder(activity)
                    .content("There are no posts at this time that can be renewed")
                    .positiveText("OK")
                    .build();
            dialog.show();
            doTask = false;
        }
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    @Override
    protected Void doInBackground(String... params) {
        if (doTask) {
            final TextView textView = (TextView) dialog.findViewById(R.id.renew_all_def_count);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("0 out of " + posts.size() + " posts renewed...");
                }
            });

            for (Post post : posts) {

                if (post.isRenewable()) {
                    ManageRequest request = new ManageRequest(activity, post, Post.ManageActionType.RENEW);
                    boolean renewed = request.execute();
                    if (renewed) {
                        post.setRenewable(false);
                    }
                } else {
                    RepostRequest repostRequest = new RepostRequest(activity, post);
                    repostRequest.execute();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(numRenewed + " out of " + posts.size() + " posts renewed...");
                        }
                    });

//                    try {
//                        Thread.sleep(1000 * 60); // sleep for 1 minutes
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }

                numRenewed++;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(numRenewed + " out of " + posts.size() + " posts renewed...");
                    }
                });

                Log.d("RenewTask", "renewed post...");
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (doTask) {
            if (activity instanceof PostsActivity) {
                ((PostsActivity) activity).fetchPostsAsync();
            }

            dialog.dismiss();
        }
    }
}
