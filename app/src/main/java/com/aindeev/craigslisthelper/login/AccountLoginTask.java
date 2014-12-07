package com.aindeev.craigslisthelper.login;

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

    public AccountLoginTask(LoginInterface loginInterface, String email, String password) {
        this.loginInterface = loginInterface;
        this.email = email;
        this.password = password;
        this.authCookieValue = "";
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        CraigslistClient client = new CraigslistClient();
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