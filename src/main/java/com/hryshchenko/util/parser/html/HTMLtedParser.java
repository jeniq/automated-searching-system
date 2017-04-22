package com.hryshchenko.util.parser.html;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.hryshchenko.service.sourceAPI.TedAPI.COURSE_LINK;

@Component
public class HTMLtedParser {

    public JSONObject parseHtml(String url) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Document html;

        try {
            html = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Elements articles = html.select("article");

        for (Element element : articles) {
            JSONObject item = new JSONObject();
            item.put("title",
                    element.select("a").first().text());
            item.put("description",
                    element.select("div.search__result__description").text());
            item.put("source_id",
                    element.select("a").first().attr("href"));
            item.put("link",
                    COURSE_LINK + element.select("a").first().attr("href"));
            item.put("image",
                    element.select("img.thumb__image").attr("src"));
            item.put("language", "English");
            array.put(item);
        }

        jsonObject.put("elements", array);

        return jsonObject;
    }
}
