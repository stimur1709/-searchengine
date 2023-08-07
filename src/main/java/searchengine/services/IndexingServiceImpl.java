package searchengine.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.Response;
import searchengine.exception.SiteException;
import searchengine.model.Site;
import searchengine.util.IndexingRunnable;

import java.util.List;

@Slf4j
@Service
public class IndexingServiceImpl implements IndexingService {

    private final SiteService siteService;
    private final IndexingRunnable indexingRunnable;

    @Autowired
    public IndexingServiceImpl(SiteService siteService, IndexingRunnable indexingRunnable) {
        this.siteService = siteService;
        this.indexingRunnable = indexingRunnable;
    }

    @Override
    public Response startIndexing() throws SiteException {
        List<Site> sites = siteService.findAll();
        if (sites.isEmpty()) {
            throw new SiteException("Индексация уже запущена");
        } else {
            indexingRunnable.startForkJoin(sites);
            return new Response(true);
        }
    }

    @Override
    public Response startIndexing(String url) throws SiteException {
        Site site = siteService.findSiteByUrl(url);
        indexingRunnable.startForkJoin(site);
        return new Response(true);
    }

    @Override
    public Response stopIndexing() {
        indexingRunnable.stop();
        return new Response(true);
    }

}
