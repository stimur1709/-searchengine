package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import searchengine.dao.SearchDao;
import searchengine.dto.statistics.HtmlDocument;
import searchengine.model.InfoPage;
import searchengine.util.JSOUPConnection;
import searchengine.util.Morphology;
import searchengine.util.Snippet;

import java.util.List;

@Component
public class SearchComponent {

    private final SearchDao searchDao;

    @Autowired
    public SearchComponent(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    public List<InfoPage> searching(String query, String site, int offset, int limit) {
        List<String> searchWords = Morphology.getNormalWords(query);
        List<InfoPage> content = searchDao.getContent(site, searchWords, offset, limit);
        searching(content, searchWords);
        return content;
    }

    public void searching(List<InfoPage> content, List<String> searchWords) {
        for (InfoPage infoPage : content) {
            HtmlDocument document = JSOUPConnection.htmlParse(infoPage.getSnippet());
            String snippet = paginationElements(document.getElements(), searchWords);
            infoPage.setSnippet(snippet);
        }
    }

    public String paginationElements(List<String> elements, List<String> searchWords) {
        Snippet snippet = new Snippet();
        for (String element : elements) {
            if (!element.isBlank()) {
                List<Integer> indexes = Morphology.getIndexContains(searchWords, element);
                snippet.update(indexes, element);
            }
        }
        return vold(snippet);
    }

    public String vold(Snippet snippet) {
        String[] result = snippet.getText().split(" ");
        snippet.getIndexes().forEach(index -> result[index] = "<b>" + result[index] + "</b>");
        return String.join(" ", result);
    }
}
