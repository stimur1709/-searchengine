package searchengine.dto.statistics;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InfoPageDto {

    private final String site;
    private final String siteName;
    private final String uri;
    private final String title;
    private final String snippet;
    private final double relevance;

}
