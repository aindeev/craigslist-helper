package com.aindeev.craigslisthelper.web;

import android.app.Activity;

import com.aindeev.craigslisthelper.App;
import com.aindeev.craigslisthelper.login.Authenticate;
import com.aindeev.craigslisthelper.login.AuthenticateCallback;

import org.apache.http.Header;

/**
 * Created by aindeev on 14-12-10.
 */
public abstract class AuthRequest<T> extends Request<T> implements AuthenticateCallback {

    private Activity context = null;

    /*
     *  context can be null, but in that case the request will not be able
     *  to run in the background if the there is no registered account
     */
    public AuthRequest(Activity context, RequestType type) {
        super(type);
        this.context = context;
    }

    public boolean isAuthenticated(String html)
    {
        // TODO make a better check
        return !html.contains("loginBox");
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        if (isAuthenticated(new String(bytes)))
            super.onSuccess(i, headers, bytes);
        else {
            isExecuting = false;

            if (context != null) {
                Authenticate.doAuthentication(context, this, true);
            } else
                /* TODO send user a notification that they need to login
                   for auto renewal to be working */
                return;
        }
    }
    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        super.onFailure(i, headers, bytes, throwable);
    }

    @Override
    public void onAuthenticateSuccess() {
        if (asyncCallback != null)
            this.execute(asyncCallback);
        else
            this.execute();
    }
}
