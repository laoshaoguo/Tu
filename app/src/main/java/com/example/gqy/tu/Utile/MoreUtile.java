package com.example.gqy.tu.Utile;

import com.example.gqy.tu.Bean.ImageInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mszf on 2017/7/3.
 */

public class MoreUtile {
    public List<ImageInfo> geturl(String url) {
        Document html = null;
        try {
            html = Jsoup.connect(url).timeout(10000).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (html == null) {
            return null;
        }

        Elements urls = html.select("img[src$=.jpg]");
        List<ImageInfo> imgUrls = new ArrayList<>();
        for (Element eUrl : urls) {
            String src = eUrl.attr("src");
            if (isNotNull(src)) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setImgUrl(src);
                imgUrls.add(imageInfo);
            }
        }
        return imgUrls;
    }

    /**
     * 判断空
     *
     * @param rawData
     * @return
     */
    public static boolean isNotNull(String rawData) {
        if (null != rawData && !"".equals(rawData) && !"null".equals(rawData))
            return true;
        else
            return false;
    }
}
