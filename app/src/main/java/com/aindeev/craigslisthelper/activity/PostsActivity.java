package com.aindeev.craigslisthelper.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.activity.recycler.DividerItemDecoration;
import com.aindeev.craigslisthelper.activity.recycler.PostsViewAdapter;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.PostsRequest;
import com.aindeev.craigslisthelper.web.RequestCallback;
import com.aindeev.craigslisthelper.workers.RenewTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class PostsActivity extends BaseActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private PostsViewAdapter postsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(getBaseContext().getResources().getDrawable(R.drawable.divider_line),
                        false, true));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportProgressBarIndeterminateVisibility(true);
        postsViewAdapter = new PostsViewAdapter(this, new ArrayList<Post>());
        recyclerView.setAdapter(postsViewAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPostsAsync();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.requestFocus();

        fetchPostsAsync();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_posts;
    }

    public void fetchPostsAsync() {
        if (!swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(true);

        PostsRequest postsRequest = new PostsRequest(this);
        postsRequest.execute(new RequestCallback<List<Post>>() {
            @Override
            public void onRequestDone(List<Post> value) {
                setSupportProgressBarIndeterminateVisibility(false);
                swipeContainer.setRefreshing(false);

                if (value == null) {
                    Log.e("PostsActivity", "Failed to fetch posts data!");
                } else {
                    postsViewAdapter.addItems(value);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_renew_all) {
            PostsViewAdapter adapter = (PostsViewAdapter)recyclerView.getAdapter();
            RenewTask worker = new RenewTask(this, adapter.getPostList());
            worker.execute((String[]) null);

//            View view = this.getLayoutInflater().inflate(R.layout.renew_all_dialog, null);
//            MaterialDialog dialog = new MaterialDialog.Builder(this)
//                    .customView(view)
//                    .title(R.string.renew_all_message)
//                    .build();
//
//            //dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            dialog.show();
//
//            TextView textView = (TextView)dialog.findViewById(R.id.renew_all_def_count);
//            if (textView != null)
//                textView.setText("0 out of 5 posts renewed...");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
