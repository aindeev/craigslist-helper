package com.aindeev.craigslisthelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by aindeev on 14-12-07.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }

}
