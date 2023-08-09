package searchengine.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.Response;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.exception.SiteException;
import searchengine.model.InfoPage;
import searchengine.services.IndexingService;
import searchengine.services.SearchComponent;
import searchengine.services.SiteService;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
        return ResponseEntity.ok(siteService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<Response> startIndexing() throws InterruptedException, ExecutionException, SiteException {
        return ResponseEntity.ok(indexingService.startIndexing());
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<Response> stopIndexing() {
        return ResponseEntity.ok(indexingService.stopIndexing());
    }

    @PostMapping("/indexPage")
    public ResponseEntity<Response> lemma(@RequestParam String url) throws SiteException {
        return ResponseEntity.ok(indexingService.startIndexing(url));
    }

    @GetMapping("/search")
    public ResponseEntity<List<InfoPage>> search(@RequestParam String query,
                                                 @RequestParam(required = false) String site,
                                                 @RequestParam(defaultValue = "0") int offset,
                                                 @RequestParam(defaultValue = "200") int limit) {
        return ResponseEntity.ok(searchComponent.searching(query, site, offset, limit));
    }


}
