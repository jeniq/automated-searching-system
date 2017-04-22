package com.hryshchenko.util.parser.html;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HTMLPrometheusParser {

    public JSONObject parseHtml(String url, String value) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Document html;

        try {
            html = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Elements articles = html.select("div.gdlr-item");

        for (Element element : articles) {
            if (element.select("a").text().isEmpty() ||
                    element.select("div.feature-media-content>div>p").text().isEmpty() ||
                    element.select("img").attr("src").isEmpty()) {
                continue;
            }
            JSONObject item = new JSONObject();
            item.put("title",
                    element.select("a").text());
            item.put("description",
                    element.select("div.feature-media-content>div>p").text());
            item.put("source_id",
                    element.select("a").attr("href"));
            item.put("link",
                    element.select("a").attr("href"));
            item.put("image",
                    element.select("img").attr("src"));
            item.put("language", "Ukrainian");
            if (item.get("title").toString().toLowerCase().contains(value.toLowerCase()) ||
                    item.get("description").toString().toLowerCase().contains(value.toLowerCase())) {
                array.put(item);
            }
        }

        jsonObject.put("elements", array);

        return jsonObject;
    }

}
