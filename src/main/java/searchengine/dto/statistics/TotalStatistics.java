package searchengine.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class TotalStatistics {
    private int sites;
    private int pages;
    private int lemmas;
    private boolean indexing;

    public TotalStatistics(List<DetailedStatisticsItem> detailed) {
        this.sites = detailed.size();
        this.pages = 0;
        this.lemmas = 0;
        detailed.forEach(statisticsItem -> {
            this.pages += statisticsItem.getPages();
            this.lemmas += statisticsItem.getLemmas();
        });
    }
}
