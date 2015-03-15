package com.aindeev.craigslisthelper.posts;

import java.util.HashMap;

/**
 * Created by aindeev on 15-01-10.
 */
public class FormData {

    private String formUrl;
    private HashMap<String, String> data;

    public FormData() {
        data = new HashMap<>();
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }
}
