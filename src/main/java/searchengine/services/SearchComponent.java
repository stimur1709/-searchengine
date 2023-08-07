package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dao.SearchDao;
import searchengine.model.InfoPage;
import searchengine.util.Morphology;

import java.util.List;

@Component
public class SearchComponent {

    private final SearchDao searchDao;

    @Autowired
    public SearchComponent(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    public List<InfoPage> searching(String query, String site, int offset, int limit) {
        List<String> normalWords = Morphology.getNormalWords(query);
        List<InfoPage> content = searchDao.getContent(site, normalWords);
        searchingInHtmlCode(content);
        return content;
    }

    public void searchingInHtmlCode(List<InfoPage> content) {
        for (InfoPage infoPage : content) {
            String snippet = infoPage.getSnippet();
            Document document = Jsoup.parse(snippet);
            Element elTitle = document.getElementsByTag("title").first();
            infoPage.setTitle(elTitle == null ? null : elTitle.text());
            Element body = document.body();
            for (Element element : body.getAllElements()) {
                if (!element.text().isBlank()) {
                    System.out.println(element.text());
                }
            }
            infoPage.setSnippet(body.text());
        }
    }
}
