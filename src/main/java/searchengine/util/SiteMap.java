package searchengine.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.dto.statistics.HtmlDocument;
import searchengine.model.Site;
import searchengine.services.PageService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class SiteMap extends RecursiveTask<Site> {

    private final Site site;
    private final String url;
    private final PageService pageService;

    public SiteMap(Site site, PageService pageService) {
        this.site = site;
        this.url = site.getUrl();
        this.pageService = pageService;
    }

    public SiteMap(SiteMap siteMap, String url) {
        this.site = siteMap.site;
        this.url = url;
        this.pageService = siteMap.pageService;
    }

    @Override
    public Site compute() {
        Set<SiteMap> subTask = new HashSet<>();
        if (Thread.currentThread().isInterrupted()) {
            log.error(String.valueOf(111));
            return this.site;
        }
        try {
            getChildren(subTask);
        } catch (Exception e) {
            return this.site;
        }

        for (SiteMap sitemap : subTask) {
            sitemap.join();
        }
        return this.site;
    }

    private void getChildren(Set<SiteMap> subTask) {
        HtmlDocument html = JSOUPConnection.connectionURL(url);
        if (html.getDocument() != null) {
            Elements elements = html.getDocument().select("a");
            for (Element element : elements) {
                String attr = element.absUrl("href");
                if (UrlInfo.isCorrectUrl(attr, site.getUrl()) && !MapStorage.containsUrl(site.getId(), attr)) {
                    adding(attr, subTask);
                }
            }
        }
    }

    private void adding(String attr, Set<SiteMap> subTask) {
        MapStorage.add(site.getId(), attr);
        SiteMap siteMap = new SiteMap(this, attr);
        siteMap.fork();
        subTask.add(siteMap);
        pageService.save(site, attr);
    }

}
