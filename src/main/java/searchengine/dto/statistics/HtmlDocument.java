package searchengine.dto.statistics;

import lombok.Getter;
import org.jsoup.nodes.Document;
import searchengine.util.UrlInfo;

@Getter
public class HtmlDocument {

    private Document document;
    private final int code;

    public HtmlDocument(Document document) {
        this.document = document;
        this.code = UrlInfo.getCode(document);
    }

    public HtmlDocument(int code) {
        this.code = code;
    }
}
