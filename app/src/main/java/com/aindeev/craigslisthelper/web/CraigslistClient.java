package com.aindeev.craigslisthelper.web;

import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by aindeev on 14-12-07.
 */
public class CraigslistClient {

    private WebClient client;
    private static CraigslistClient ourInstance = new CraigslistClient();

    public static CraigslistClient getInstance() {
        return ourInstance;
    }

    private CraigslistClient() {
        makeNewClient();
    }

    private void makeNewClient() {
        client = new WebClient();
    }

    public WebClient getClient() {
        return client;
    }
}
