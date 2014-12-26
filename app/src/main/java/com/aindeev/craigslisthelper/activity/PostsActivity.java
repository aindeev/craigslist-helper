package com.aindeev.craigslisthelper.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.activity.BaseActivity;
import com.aindeev.craigslisthelper.activity.recycler.DividerItemDecoration;
import com.aindeev.craigslisthelper.activity.recycler.PostsViewAdapter;
import com.aindeev.craigslisthelper.posts.Post;
import com.aindeev.craigslisthelper.web.PostsRequest;
import com.aindeev.craigslisthelper.web.RequestCallback;

import java.util.ArrayList;
import java.util.List;


public class PostsActivity extends BaseActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private PostsViewAdapter postsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setProgressBarIndeterminateVisibility(true);
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

        fetchPostsAsync();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_posts;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void fetchPostsAsync() {
        PostsRequest postsRequest = new PostsRequest(this);
        postsRequest.execute(new RequestCallback<List<Post>>() {
            @Override
            public void onRequestDone(List<Post> value) {
                setProgressBarIndeterminateVisibility(false);
                swipeContainer.setRefreshing(false);

                if (value == null) {
                    Log.e("PostsActivity", "Failed to fetch posts data!");
                } else {
                    postsViewAdapter.addItems(value);
                }
            }
        });
    }
}
