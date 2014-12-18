package com.aindeev.craigslisthelper.posts;

import java.util.Date;

/**
 * Created by aindeev on 14-12-09.
 */
public class Post {

    private String id;
    private String name;
    private Date datePosted;

    public Post() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
