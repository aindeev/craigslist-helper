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

    public boolean testAuth(String html)
    {
        return false;
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        if (testAuth(new String(bytes)))
            super.onSuccess(i, headers, bytes);
        else
            Authenticate.doAuthentication(context, this);
    }
    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        onRequestFailure(i, headers, bytes);
        if (asyncCallback != null)
            asyncCallback.onRequestDone(getResult());
        isExecuting = false;
    }

    @Override
    public void onAuthenticateSuccess() {
        if (asyncCallback != null)
            this.execute(asyncCallback);
        else
            this.execute();
    }
}
