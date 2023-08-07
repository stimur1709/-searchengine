package searchengine.services;

import searchengine.dto.statistics.Response;
import searchengine.exception.SiteException;

import java.util.concurrent.ExecutionException;

public interface IndexingService {

    Response startIndexing() throws InterruptedException, ExecutionException, SiteException;

    Response startIndexing(String url) throws SiteException;

    Response stopIndexing();

}
