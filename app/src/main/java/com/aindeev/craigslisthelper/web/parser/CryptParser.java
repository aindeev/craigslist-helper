package com.aindeev.craigslisthelper.web.parser;

import com.aindeev.craigslisthelper.posts.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aindeev on 14-12-25.
 */
public class CryptParser {

    private static String FORM_TAG = "form";

    public static Post parse(Post post, String html) {

        Document document = Jsoup.parse(html);

        Elements forms = document.getElementsByTag(FORM_TAG);
        for (Element form : forms) {
            Elements crypts = form.getElementsByAttributeValue("name", "crypt");
            if (!crypts.isEmpty()) {
                String action = form.getElementsByAttributeValue("name", "action").first().val();
                switch (action) {
                    case "delete":
                        post.setCryptByAction(Post.ManageActionType.DELETE, crypts.first().val());
                        break;
                    case "edit":
                        post.setCryptByAction(Post.ManageActionType.EDIT, crypts.first().val());
                        break;
                    case "renew":
                        post.setCryptByAction(Post.ManageActionType.RENEW, crypts.first().val());
                        break;
                    case "repost":
                        post.setCryptByAction(Post.ManageActionType.REPOST, crypts.first().val());
                        break;
                }
            }
        }

        return post;
    }
}
