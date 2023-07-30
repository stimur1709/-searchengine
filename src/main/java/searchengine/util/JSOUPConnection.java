package searchengine.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import searchengine.dto.statistics.HtmlDocument;

import java.io.IOException;

@Slf4j
public class JSOUPConnection {

    private JSOUPConnection() {

    }

    public static HtmlDocument connectionURL(String url) {
        Connection connect = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com");
        try {
            Thread.sleep(1000);
            return new HtmlDocument(connect.get());
        } catch (IOException | InterruptedException e) {
            return new HtmlDocument(UrlInfo.getCode(e.getMessage()));
        }
    }


}