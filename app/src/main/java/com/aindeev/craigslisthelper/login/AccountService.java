package com.aindeev.craigslisthelper.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return new AccountAuthenticator(this).getIBinder();
    }

}

