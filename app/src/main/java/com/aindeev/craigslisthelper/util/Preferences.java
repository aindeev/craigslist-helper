package com.aindeev.craigslisthelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.aindeev.craigslisthelper.R;

/**
 * Created by aindeev on 14-12-07.
 */
public class Preferences {

    private static String USERNAME_PREF_NAME = "username";

    private Context context;
    SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.context = context;
        String appId = context.getResources().getString(R.string.app_id);
        this.sharedPreferences = this.context.getSharedPreferences(appId, this.context.MODE_PRIVATE);
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME_PREF_NAME, "");
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_PREF_NAME, username);
    }
}
