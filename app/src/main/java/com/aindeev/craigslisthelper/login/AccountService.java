package com.aindeev.craigslisthelper.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {

    private CraigslistAuthenticator authenticator;
    @Override
    public void onCreate() {
        authenticator = new CraigslistAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = null;
        if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
            binder = authenticator.getIBinder();
        return binder;
    }

}

