package com.aindeev.craigslisthelper.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.login.Authenticate;
import com.aindeev.craigslisthelper.login.AuthenticateCallback;

public class LoadingActivity extends Activity implements AuthenticateCallback {

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition ts = new Slide();
            ts.setStartDelay(200);
            ts.setDuration(500);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final LoadingActivity thisFinal = this;

        AsyncTask<Void, Void, Void> authTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Authenticate.doAuthentication(thisFinal, thisFinal, false);
                return null;
            }
        };
        authTask.execute();
    }

    public void startListActivity() {
        Intent intent = new Intent(this.getBaseContext(), PostsActivity.class);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void onAuthenticateSuccess() {
        startListActivity();
    }
}
