package com.aindeev.craigslisthelper.web.parser;

import com.aindeev.craigslisthelper.posts.FormData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by aindeev on 14-12-25.
 */
public class FormParser {

    private static String FORM_TAG = "form";

    public static FormData parse(String buttonValue, String html) {
        FormData formData = new FormData();
        Document document = Jsoup.parse(html);
        boolean foundForm = false;

        Elements forms = document.getElementsByTag(FORM_TAG);
        for (Element form : forms) {

            for (Element button : form.getElementsByTag("button")) {
                if (button.val().equals(buttonValue)) {
                    foundForm = true;
                    break;
                }
            }

            if (foundForm) {
                Elements inputs = form.getElementsByTag("input");
                for (Element input : inputs) {
                    String name = input.nodeName();
                    String value = input.val();
                    formData.getData().put(name, value);
                }
            }
        }

        return formData;
    }
}
