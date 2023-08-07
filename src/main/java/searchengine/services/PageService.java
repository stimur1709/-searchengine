package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.HtmlDocument;
import searchengine.model.PageTable;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.util.JSOUPConnection;
import searchengine.util.SiteMap;

import java.util.List;

@Service
public class PageService extends ModelServiceImpl<PageTable, Long, PageRepository> {

    private final LemmaService lemmaService;

    @Autowired
    protected PageService(PageRepository repository, LemmaService lemmaService) {
        super(repository);
        this.lemmaService = lemmaService;
    }

    public void save(SiteMap siteMap) {
        Site site = siteMap.getSite();
        String path = siteMap.getPath();
        if (!path.isBlank()) {
            HtmlDocument doc = JSOUPConnection.connectionURL(site.getUrl() + path);
            if (doc.getDocument() != null) {
                PageTable pageTable = super.save(new PageTable(site.getId(), path, doc.getCode(), doc.getDocument().html()));
                lemmaService.indexingContent(pageTable, doc.getDocument().text());
            }
        }
    }

    public void deleteBySiteId(long siteId) {
        repository.deleteBySiteId(siteId);
    }

    public void deleteByInSiteId(List<Long> siteId) {
        repository.deleteByInSiteId(siteId);
    }

    public long getCountBySiteId(Long siteId) {
        return repository.countBySiteId(siteId);
    }

    public List<PageTable> findPages(String site, List<String> lemmas, Pageable pageable) {
        if (site == null) {
            return repository.findByLemma(lemmas, pageable).getContent();
        } else {
            return repository.findBySiteIdAndLemma(site, lemmas, pageable).getContent();
        }
    }


}
