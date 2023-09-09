package searchengine.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.Response;
import searchengine.dto.statistics.SearchData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.exception.SiteException;
import searchengine.services.IndexingService;
import searchengine.services.SearchComponent;
import searchengine.services.SiteService;

import java.util.concurrent.ExecutionException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    private final IndexingService indexingService;
    private final SiteService siteService;
    private final SearchComponent searchComponent;

    public ApiController(IndexingService indexingService,
                         SiteService siteService,
                         SearchComponent searchComponent) {
        this.indexingService = indexingService;
        this.siteService = siteService;
        this.searchComponent = searchComponent;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ok(siteService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<Response> startIndexing() throws InterruptedException, ExecutionException, SiteException {
        return ok(indexingService.startIndexing());
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<Response> stopIndexing() {
        return ok(indexingService.stopIndexing());
    }

    @PostMapping("/indexPage")
    public ResponseEntity<Response> lemma(@RequestParam String url) throws SiteException {
        return ok(indexingService.startIndexing(url));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchData> search(@RequestParam String query,
                                             @RequestParam(required = false) String site,
                                             @RequestParam(defaultValue = "0") int offset,
                                             @RequestParam(defaultValue = "200") int limit) {
        return ok(searchComponent.searching(query, site, offset, limit));
    }


}
