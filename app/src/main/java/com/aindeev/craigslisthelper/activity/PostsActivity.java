package com.aindeev.craigslisthelper.activity;

import android.os.AsyncTask;
import android.os.Bundle;
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


public class PostsActivity extends BaseActivity implements RequestCallback<List<Post>>{

    RecyclerView recyclerView;
    PostsViewAdapter postsViewAdapter;
    AsyncTask<Void, Void, Void> getPostsTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setProgressBarIndeterminateVisibility(true);
        PostsRequest postsRequest = new PostsRequest();
        postsRequest.execute(this);

        postsViewAdapter = new PostsViewAdapter(this, new ArrayList<Post>());
        recyclerView.setAdapter(postsViewAdapter);
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

    @Override
    public void onRequestDone(List<Post> value) {
        setProgressBarIndeterminateVisibility(false);

        if (value == null) {
            Log.e("PostsAcitvity", "Failed to fetch posts data!");
            // TODO could not fetch information
        } else {
            postsViewAdapter.addItems(value);
        }
    }
}
