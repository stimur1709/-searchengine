package searchengine.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsResponse {
    private boolean result;
    private StatisticsData statistics;

    public StatisticsResponse(List<DetailedStatisticsItem> detailed) {
        this.result = true;
        this.statistics = new StatisticsData(detailed);
    }

}
