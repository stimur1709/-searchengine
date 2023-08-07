package searchengine.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.services.LemmaService;
import searchengine.services.PageService;
import searchengine.services.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Slf4j
@Component
public class IndexingRunnable {

    private final PageService pageService;
    private final SiteService siteService;
    private final LemmaService lemmaService;
    private List<SiteMap> siteMaps = new ArrayList<>();


    @Autowired
    public IndexingRunnable(PageService pageService, SiteService siteService, LemmaService lemmaService) {
        this.pageService = pageService;
        this.siteService = siteService;
        this.lemmaService = lemmaService;
    }

    @Async
    public void startForkJoin(List<Site> sites) {
        preparation(sites);
        List<ForkJoinTask<Site>> tasks = sites.stream().map(site -> new ForkJoinPool().submit(createTask(site))).toList();
        tasks.forEach(p -> {
            try {
                p.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        ending(sites);
    }

    @Async
    public void startForkJoin(Site site) {
        preparation(site);
        new ForkJoinPool().invoke(createTask(site));
        ending(site);
        clearSiteMaps();
    }

    public void stop() {
        siteMaps.forEach(s -> s.getSite().stop());
        clearSiteMaps();
    }

    private void preparation(List<Site> site) {
        List<String> urls = site.stream().map(Site::getUrl).toList();
        log.info("Началась подготовка к индексации страниц: {}", urls);
        site.forEach(Site::indexing);
        siteService.saveAll(site);
        pageService.deleteByInSiteId(site.stream().map(Site::getId).toList());
        lemmaService.deleteByInId(site.stream().map(Site::getId).toList());
        log.info("Началась индексация страниц: {}", urls);
    }


    private void preparation(Site site) {
        log.info("Началась подготовка к индексация страницы: {}", site.getUrl());
        siteService.save(site.indexing());
        pageService.deleteBySiteId(site.getId());
        lemmaService.deleteById(site.getId());
        log.info("Началась индексация страницы: {}", site.getUrl());
    }

    private void ending(List<Site> sites) {
        for (Site site : sites) {
            ending(site);
        }
        clearSiteMaps();
    }

    private void ending(Site site) {
        siteService.save(site.getStatus() == Status.FAILED ? site : site.indexed(pageService.getCountBySiteId(site.getId())));
        log.info("Закончилась индексация страницы: {} - {}", site.getUrl(), site.getLastError());
        UrlStorage.removeKey(site.getId());
    }

    private void clearSiteMaps() {
        siteMaps = new ArrayList<>();
    }

    public SiteMap createTask(Site site) {
        SiteMap task = new SiteMap(site, pageService);
        siteMaps.add(task);
        return task;
    }

}
