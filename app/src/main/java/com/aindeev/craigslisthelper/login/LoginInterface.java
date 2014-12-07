package com.aindeev.craigslisthelper.login;

/**
 * Created by aindeev on 14-12-07.
 */
public interface LoginInterface {

    void onPostExecute(final Boolean success, String authCookieValue);
    void onCancelled();

}
