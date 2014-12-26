package com.aindeev.craigslisthelper.web.parser;

import com.aindeev.craigslisthelper.posts.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by aindeev on 14-12-09.
 */
public class PostParser {

    private static String POST_TABLE_KEY = "summary";
    private static String POST_TABLE_VALUE = "postings";

    private static String TR_TAG = "tr";
    private static String TD_TAG = "td";
    private static String FORM_TAG = "form";

    private static String RENEWED_VAL = "renewed";

    private static String CELL_ID_CLASS = "postingID";
    private static String CELL_NAME_CLASS = "title";
    private static String CELL_DATE_CLASS = "dates";
    private static String CELL_BUTTONS_CLASS = "buttons";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public static List<Post> parse(String html) {
        List<Post> posts = new ArrayList<Post>();

        try {
            Document document = Jsoup.parse(html);

            Elements postTables = document.getElementsByAttributeValue(POST_TABLE_KEY, POST_TABLE_VALUE);
            for (Element table : postTables) {
                Elements tableEntries = table.getElementsByTag(TR_TAG);
                for (Element tableEntry : tableEntries) {
                    Elements tableCells = tableEntry.getElementsByTag(TD_TAG);
                    if (tableCells.isEmpty())
                        continue;

                    Post post = new Post();
                    for (Element tableCell : tableCells) {
                        if (tableCell.className().equals(CELL_ID_CLASS))
                            post.setId(tableCell.child(0).text());
                        else if (tableCell.className().equals(CELL_NAME_CLASS))
                            post.setName(tableCell.child(0).text());
                        else if (tableCell.className().equals(CELL_DATE_CLASS)) {
                            Elements renewed = tableCell.getElementsContainingText(RENEWED_VAL);
                            if (renewed.isEmpty())
                                post.setDatePosted(dateFormat.parse(tableCell.child(0).text()));
                            else {
                                String renewedText = renewed.first().text();
                                int start = renewedText.indexOf(RENEWED_VAL) + RENEWED_VAL.length() + 1;
                                String renewedDate = renewedText.substring(start);
                                post.setDatePosted(dateFormat.parse(renewedDate));
                            }
                        } else if (tableCell.className().equals(CELL_BUTTONS_CLASS)) {
                            post = CryptParser.parse(post, tableCell.html());
                        }
                    }

                    if (post.getDatePosted() != null &&
                            post.getId() != null &&
                            post.getName() != null) {
                        post.setRenewable(post.getCryptByAction(Post.ManageActionType.RENEW) != null);
                        post.setRepostable(post.getCryptByAction(Post.ManageActionType.REPOST) != null);
                        posts.add(post);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return posts;
    }

}
