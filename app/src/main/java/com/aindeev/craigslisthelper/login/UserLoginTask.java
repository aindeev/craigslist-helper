package com.aindeev.craigslisthelper.login;

import android.os.AsyncTask;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.web.CraigslistLogin;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private final LoginInterface loginInterface;
    private final String email;
    private final String password;

    public UserLoginTask(LoginInterface loginInterface, String email, String password) {
        this.loginInterface = loginInterface;
        this.email = email;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        CraigslistLogin login = new CraigslistLogin();
        boolean success = login.login(email, password);
        if (success)
            return true;

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(email)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(password);
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        loginInterface.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        loginInterface.onCancelled();
    }
}