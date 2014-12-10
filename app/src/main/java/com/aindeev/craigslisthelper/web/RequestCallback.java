package com.aindeev.craigslisthelper.web;

/**
 * Created by aindeev on 14-12-09.
 */
public interface RequestCallback<T> {

    /*
     *  If the request failed the value will be NULL
     */
    void onRequestDone(T value);

}
