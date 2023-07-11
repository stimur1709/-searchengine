package searchengine.services;

import searchengine.dto.statistics.Response;

import java.util.concurrent.ExecutionException;

public interface IndexingService {

    Response startIndexing() throws InterruptedException, ExecutionException;

    Response stopIndexing();

}
