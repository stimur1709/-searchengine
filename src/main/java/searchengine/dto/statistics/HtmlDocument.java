package searchengine.dto.statistics;

import lombok.Getter;
import org.jsoup.nodes.Document;
import searchengine.util.PageProperties;

import java.util.List;

@Getter
public class HtmlDocument {

    private Document document;
    private int code;
    private String title;
    private List<String> elements;

    public HtmlDocument(Document document) {
        this.document = document;
        this.code = PageProperties.getCodeStatus(document);
    }

    public HtmlDocument(Document document, String title, List<String> elements) {
        this.document = document;
        this.title = title;
        this.elements = elements;
    }

    public HtmlDocument(int code) {
        this.code = code;
    }
}
