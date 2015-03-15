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

    private static String POST_TABLE_CLASS = "accthp_postings";

    private static String TR_TAG = "tr";
    private static String TD_TAG = "td";
    private static String FORM_TAG = "form";
    private static String SMALL_TAG = "small";

    private static String RENEWED_VAL = "renewed";

    private static String CELL_ID_CLASS = "postingID";
    private static String CELL_STATUS_CLASS = "status";
    private static String CELL_NAME_CLASS = "title";
    private static String CELL_DATE_CLASS = "dates";
    private static String CELL_BUTTONS_CLASS = "buttons";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public static List<Post> parse(String html) {
        List<Post> posts = new ArrayList<Post>();

        try {
            Document document = Jsoup.parse(html);

            Elements postTables = document.getElementsByClass(POST_TABLE_CLASS);
            for (Element table : postTables) {
                Elements tableEntries = table.getElementsByTag(TR_TAG);
                for (Element tableEntry : tableEntries) {
                    Elements tableCells = tableEntry.getElementsByTag(TD_TAG);
                    if (tableCells.isEmpty())
                        continue;

                    Post post = new Post();
                    for (Element tableCell : tableCells) {
                        if (tableCell.hasClass(CELL_ID_CLASS))
                            post.setId(tableCell.text().trim());
                        else if (tableCell.hasClass(CELL_STATUS_CLASS)) {
                            String status = tableCell.getElementsByTag("small").first().text();
                            if (status.equals("Deleted") || status.equals("Expired"))
                                post.setStatus(Post.PostStatus.DELETED);
                            else if (status.equals("Active"))
                                post.setStatus(Post.PostStatus.ACTIVE);
                            else
                                post.setStatus(Post.PostStatus.OTHER);
                        } else if (tableCell.hasClass(CELL_NAME_CLASS)) {
                            Elements links = tableCell.getElementsByTag("a");
                            if (links.size() > 0)
                                post.setName(links.first().text());
                            else
                                post.setName(tableCell.text());
                        } else if (tableCell.hasClass(CELL_DATE_CLASS)) {
                            if (!tableCell.text().contains(RENEWED_VAL)) {
                                post.setDatePosted(dateFormat.parse(tableCell.text()));
                                post.setDateUpdated(post.getDatePosted());
                            } else {
                                String renewedText = tableCell.text();
                                int renewedStart = renewedText.indexOf(RENEWED_VAL);
                                int start = renewedStart + RENEWED_VAL.length() + 1;
                                String postedDate = renewedText.substring(0, renewedStart - 2);
                                String renewedDate = renewedText.substring(start);

                                post.setDatePosted(dateFormat.parse(postedDate));
                                post.setDateUpdated(dateFormat.parse(renewedDate));
                            }
                        } else if (tableCell.hasClass(CELL_BUTTONS_CLASS)) {
                            post = CryptParser.parse(post, tableCell.html());
                        }
                    }

                    if (post.getDateUpdated() != null &&
                            post.getId() != null &&
                            post.getName() != null) {
                        post.setRenewable(post.getCryptByAction(Post.ManageActionType.RENEW) != null);
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
