package com.aindeev.craigslisthelper.web;

import com.aindeev.craigslisthelper.login.LoginInterface;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by aindeev on 14-12-07.
 */
public class CraigslistLogin {

    public CraigslistLogin() {
    }

    public static boolean login(WebClient client, String email, String password) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }
        return false;
    }

}
