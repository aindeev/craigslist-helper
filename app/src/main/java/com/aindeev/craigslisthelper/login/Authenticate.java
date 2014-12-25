package com.aindeev.craigslisthelper.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.aindeev.craigslisthelper.App;
import com.aindeev.craigslisthelper.util.Preferences;
import com.aindeev.craigslisthelper.web.CraigslistClient;

public class Authenticate {

    private static AccountManager accountManager = AccountManager.get(App.getContext());

    public static void doAuthentication(final Activity activity, final AuthenticateCallback authCb, final boolean invalidate) {
        Account[] accounts = accountManager.getAccountsByType(CraigslistAuthenticator.accountType);
        String savedAccount = new Preferences(App.getContext()).getUsername();
        Account account = null;

        if (!savedAccount.isEmpty()) {
            for (Account acc : accounts) {
                if (acc.name.equals(savedAccount)) {
                    account = acc;
                }
            }
        }

        final AuthenticateCallback authCbFinal = authCb;

        if (account == null)
            addNewAccount(activity, authCb);
        else {
            accountManager.getAuthToken(account,
                    CraigslistAuthenticator.authTokenType,
                    null, activity, new AccountManagerCallback<Bundle>() {
                        @Override
                        public void run(AccountManagerFuture<Bundle> future) {
                            try {
                                Bundle bundle = future.getResult();
                                String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN, "");

                                if (invalidate || authToken.isEmpty()) {
                                    invalidateToken(authToken);
                                    doAuthentication(activity, authCb, false);
                                } else {
                                    CraigslistClient.instance().setAuthCookie(authToken);
                                    authCbFinal.onAuthenticateSuccess();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        }
    }

    public static void addNewAccount(Activity activity, AuthenticateCallback authCb) {
        final Activity activityFinal = activity;
        final AuthenticateCallback authCbFinal = authCb;
        final Context context = activity.getBaseContext();
        accountManager.addAccount(CraigslistAuthenticator.accountType,
                CraigslistAuthenticator.authTokenType,
                null, null, activity, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle bundle = future.getResult();
                            String user = bundle.getString(AccountManager.KEY_ACCOUNT_NAME, "");
                            new Preferences(context).setUsername(user);
                            doAuthentication(activityFinal, authCbFinal, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
    }


    public static void invalidateToken(String authToken) {
        accountManager.invalidateAuthToken(CraigslistAuthenticator.accountType, authToken);
    }

}
