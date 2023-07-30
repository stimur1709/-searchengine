package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.Response;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.exception.SiteException;
import searchengine.services.IndexingService;
import searchengine.services.SiteService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final IndexingService indexingService;
    private final SiteService siteService;

    public ApiController(IndexingService indexingService, SiteService siteService) {
        this.indexingService = indexingService;
        this.siteService = siteService;
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


}
