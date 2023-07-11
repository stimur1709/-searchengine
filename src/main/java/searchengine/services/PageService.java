package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.HtmlDocument;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.model.key.PageKey;
import searchengine.repository.PageRepository;
import searchengine.util.JSOUPConnection;

import static searchengine.util.UrlInfo.changeUrl;

@Service
public class PageService extends ModelServiceImpl<Page, PageKey, PageRepository> {

    @Autowired
    protected PageService(PageRepository repository) {
        super(repository);
    }

    public void save(Site site, String url) {
        String path = changeUrl(site.getUrl(), url);
        PageKey key = new PageKey(site.getId(), path);
        if (!path.isBlank()) {
            HtmlDocument html = JSOUPConnection.connectionURL(url);
            Page page = new Page(key, html.getCode(), html.getContent());
            super.save(page);
        }
    }

    public void deleteBySiteId(long siteId) {
        repository.deleteBySiteId(siteId);
    }

    public long getCountBySiteId(Long siteId) {
        return repository.countByPageKey_SiteId(siteId);
    }

}
