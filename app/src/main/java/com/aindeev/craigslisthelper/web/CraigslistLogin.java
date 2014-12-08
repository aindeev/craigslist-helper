package com.aindeev.craigslisthelper.web;

/**
 * Created by aindeev on 14-12-07.
 */
public class CraigslistLogin {

    public CraigslistLogin() {
    }

    public static boolean login(CraigslistClient client, String email, String password) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

}
