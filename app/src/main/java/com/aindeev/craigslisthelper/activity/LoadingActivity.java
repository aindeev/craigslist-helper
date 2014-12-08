package com.aindeev.craigslisthelper.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;

import com.aindeev.craigslisthelper.R;
import com.aindeev.craigslisthelper.login.CraigslistAuthenticator;
import com.aindeev.craigslisthelper.util.Preferences;
import com.aindeev.craigslisthelper.web.CraigslistClient;

public class LoadingActivity extends Activity {

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition ts = new Slide();
            ts.setStartDelay(200);
            ts.setDuration(500);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        setContentView(R.layout.activty_splash);
        accountManager = AccountManager.get(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        doAuthentication();
    }

    private void doAuthentication() {
        Account[] accounts = accountManager.getAccountsByType(CraigslistAuthenticator.accountType);
        String savedAccount = new Preferences(this.getBaseContext()).getUsername();
        Account account = null;

        if (!savedAccount.isEmpty()) {
            for (Account acc : accounts) {
                if (acc.name.equals(savedAccount)) {
                    account = acc;
                }
            }
        }

        final Context context = this.getBaseContext();

        if (account == null)
            addNewAccount();
        else {
            accountManager.getAuthToken(account,
                    CraigslistAuthenticator.authTokenType,
                    null, this, new AccountManagerCallback<Bundle>() {
                        @Override
                        public void run(AccountManagerFuture<Bundle> future) {
                            try {
                                Bundle bundle = future.getResult();
                                String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN, "");

                                if (!authToken.isEmpty()) {
                                    CraigslistClient.instance().setAuthCookie(authToken);
                                }

                                startListActivity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        }
    }

    private void addNewAccount() {
        final Context context = this.getBaseContext();
        accountManager.addAccount(CraigslistAuthenticator.accountType,
                CraigslistAuthenticator.authTokenType,
                null, null, this, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            String user = bundle.getString(AccountManager.KEY_ACCOUNT_NAME, "");
                            new Preferences(context).setUsername(user);
                            doAuthentication();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
    }

    public void startListActivity() {
        Intent intent = new Intent(this.getBaseContext(), PostsActivity.class);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}
