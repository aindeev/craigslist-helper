package com.aindeev.craigslisthelper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;

import com.aindeev.craigslisthelper.login.CraigslistAuthenticator;
import com.aindeev.craigslisthelper.util.Preferences;
import com.aindeev.craigslisthelper.web.CraigslistClient;

import java.io.IOException;

public class LoadingActivity extends Activity implements AccountManagerCallback<Bundle> {

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_splash);

        accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(CraigslistAuthenticator.accountType);
        String savedAccount = new Preferences(this).getUsername();
        Account account = null;

        if (!savedAccount.isEmpty()) {
            for (Account acc : accounts) {
                if (acc.name == savedAccount) {
                    account = acc;
                }
            }
        }

        if (account == null) {
            accountManager.addAccount(CraigslistAuthenticator.accountType,
                    CraigslistAuthenticator.authTokenType,
                    null, null, this, this, null);
        } else {
            accountManager.getAuthToken(account,
                    CraigslistAuthenticator.authTokenType,
                    null, this, this, null);
        }
    }

    @Override
    public void run(AccountManagerFuture<Bundle> future) {
        try {
            Bundle bundle = future.getResult();

            new Preferences(this).setUsername(bundle.get(AccountManager.KEY_ACCOUNT_NAME).toString());

            CraigslistClient client = new CraigslistClient();
            client.setAuthCookie(bundle.get(AccountManager.KEY_AUTHTOKEN).toString());

            // TODO start new activity that handles the client

            // TODO handle the exceptions
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }
    }
}
