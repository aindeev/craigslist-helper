package com.aindeev.craigslisthelper;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_splash);

        Handler handler = new Handler();
        final Activity parent = this;
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(parent, LoginActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(parent);
                ActivityCompat.startActivity(parent, new Intent(parent, LoginActivity.class),
                        options.toBundle());
                finish();
            }
        }, 2000);
    }
}
