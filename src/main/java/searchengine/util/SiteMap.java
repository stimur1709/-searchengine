package searchengine.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import searchengine.dto.statistics.HtmlDocument;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.services.PageService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

@Slf4j
@Getter
@Setter
public class SiteMap extends RecursiveTask<Site> {

    private final Site site;
    private final String url;
    private final String path;
    private final PageService pageService;

    public SiteMap(Site site, PageService pageService) {
        this.site = site;
        this.url = site.getUrl();
        this.path = "/";
        this.pageService = pageService;
    }

    public SiteMap(SiteMap siteMap, String url, String path) {
        this.site = siteMap.site;
        this.url = url;
        this.path = path;
        this.pageService = siteMap.pageService;
    }

    @Override
    public Site compute() {
        if (site.getStatus() != Status.FAILED) {
            Set<SiteMap> subTask = new HashSet<>();
            getChildren(subTask);
            for (SiteMap sitemap : subTask) {
                sitemap.join();
            }
        }
        return this.site;
    }

    private void getChildren(Set<SiteMap> subTask) {
        HtmlDocument html = JSOUPConnection.connectionURL(url);
        if (html.getDocument() != null) {
            Elements elements = html.getDocument().select("a");
            for (Element element : elements) {
                if (site.getStatus() != Status.FAILED) {
                    adding(element, subTask);
                }
            }
        }
    }

    private void adding(Element element, Set<SiteMap> subTask) {
        String newUrl = element.absUrl("href");
        if (UrlInfo.isCorrectUrl(newUrl, site.getUrl())) {
            String newPath = UrlInfo.changeUrl(site.getUrl(), newUrl);
            if (!UrlStorage.containsUrl(site.getId(), newPath)) {
                SiteMap siteMap = new SiteMap(this, newUrl, newPath);
                siteMap.fork();
                subTask.add(siteMap);
                pageService.save(siteMap);
            }
        }
    }

}
