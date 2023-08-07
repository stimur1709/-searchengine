package searchengine.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import searchengine.dto.statistics.Response;
import searchengine.exception.SiteException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public Response siteException(SiteException siteException) {
        return new Response(false, siteException.getMessage());
    }
}
