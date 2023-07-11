package searchengine.util;

import lombok.extern.slf4j.Slf4j;
import searchengine.model.Site;
import searchengine.services.PageService;
import searchengine.services.SiteService;

import java.util.concurrent.ForkJoinPool;

@Slf4j
public class IndexingRunnable implements Runnable {

    private final PageService pageService;
    private final SiteService siteService;
    private final Site site;
    private final ForkJoinPool forkJoinPool;

    public IndexingRunnable(
            PageService pageService,
            SiteService siteService,
            Site site) {
        this.pageService = pageService;
        this.siteService = siteService;
        this.site = site;
        this.forkJoinPool = new ForkJoinPool();
    }

    @Override
    public void run() {
        startForkJoin(site);
    }

    public void cancel() {
        forkJoinPool.shutdownNow();
    }

    private void startForkJoin(Site site) {
        preparation(site);
        forkJoinPool.invoke(new SiteMap(site, pageService));
        ending(site);
    }

    private void preparation(Site site) {
        log.info("Началась индексация страницы: {}", site.getUrl());
        siteService.save(site.indexing());
        pageService.deleteBySiteId(site.getId());
    }

    private void ending(Site site) {
        long countPage = pageService.getCountBySiteId(site.getId());
        siteService.save(site.indexed(countPage));
        log.info("Закончилась индексация страницы: {}", site.getUrl());
        MapStorage.removeKey(site.getId());
    }

}
