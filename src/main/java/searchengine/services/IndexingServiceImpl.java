package searchengine.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.Response;
import searchengine.model.Site;
import searchengine.util.IndexingRunnable;

import java.util.ArrayList;
import java.util.List;

import static searchengine.util.ControlRunnable.start;

@Slf4j
@Service
public class IndexingServiceImpl implements IndexingService {

    private final SiteService siteService;
    private final PageService pageService;
    private final List<IndexingRunnable> indexingRunnable = new ArrayList<>();

    @Autowired
    public IndexingServiceImpl(SiteService siteService, PageService pageService) {
        this.siteService = siteService;
        this.pageService = pageService;
    }

    @Override
    public Response startIndexing() {
        List<Thread> threads;
        threads = createThreads(siteService.findAll());
        start(threads);
        return new Response(true);
    }

    @Override
    public Response stopIndexing() {
        indexingRunnable.forEach(IndexingRunnable::cancel);
        return new Response(true);
    }

    private List<Thread> createThreads(List<Site> sites) {
        List<Thread> list = new ArrayList<>();
        for (Site site : sites) {
            IndexingRunnable target = new IndexingRunnable(pageService, siteService, site);
            indexingRunnable.add(target);
            Thread thread = new Thread(target);
            list.add(thread);
        }
        return list;
    }


}
