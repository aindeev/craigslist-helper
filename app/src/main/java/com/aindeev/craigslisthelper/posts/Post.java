package com.aindeev.craigslisthelper.posts;

import java.util.Date;

/**
 * Created by aindeev on 14-12-09.
 */
public class Post {

    private String id;
    private String name;
    private Date datePosted;

    private String cryptDelete;
    private String cryptEdit;
    private String cryptRenew;
    private String cryptRepost;

    private boolean renewable;
    private boolean repostable;

    public enum ManageActionType {
        DELETE {
            public String toString() {
                return "delete";
            }
        },

        EDIT {
            public String toString() {
                return "edit";
            }
        },

        RENEW {
            public String toString() {
                return "renew";
            }
        },

        REPOST {
            public String toString() {
                return "repost";
            }
        }
    }

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

    public String getCryptByAction(ManageActionType type) {
        switch(type) {
            case DELETE:
                return cryptDelete;
            case EDIT:
                return cryptEdit;
            case RENEW:
                return cryptRenew;
            case REPOST:
                return cryptRepost;
        }

        return null;
    }

    public void setCryptByAction(ManageActionType type, String cryptVal) {
        switch(type) {
            case DELETE:
                cryptDelete = cryptVal;
                break;
            case EDIT:
                cryptEdit = cryptVal;
                break;
            case RENEW:
                cryptRenew = cryptVal;
                break;
            case REPOST:
                cryptRepost = cryptVal;
                break;
        }
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public boolean isRepostable() {
        return repostable;
    }

    public void setRepostable(boolean repostable) {
        this.repostable = repostable;
    }
}
