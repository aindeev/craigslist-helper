package com.aindeev.craigslisthelper.login;

import android.content.Context;
import android.os.AsyncTask;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.web.CraigslistClient;
import com.aindeev.craigslisthelper.web.CraigslistLogin;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class AccountLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final LoginInterface loginInterface;
    private final String email;
    private final String password;
    private String authCookieValue;
    private final Context context;

    public AccountLoginTask(Context context, LoginInterface loginInterface, String email, String password) {
        this.context = context;
        this.loginInterface = loginInterface;
        this.email = email;
        this.password = password;
        this.authCookieValue = "";
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        CraigslistClient client = new CraigslistClient(context);
        boolean success = CraigslistLogin.login(client, email, password);
        if (success) {
            authCookieValue = client.getAuthCookie();
            return true;
        }

        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginInterface.onPostExecute(success, authCookieValue);
    }

    @Override
    protected void onCancelled() {
        loginInterface.onCancelled();
    }
}