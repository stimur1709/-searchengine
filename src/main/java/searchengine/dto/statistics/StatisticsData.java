package searchengine.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsData {

    private TotalStatistics total;
    private List<DetailedStatisticsItem> detailed;

    public StatisticsData(List<DetailedStatisticsItem> detailed) {
        this.detailed = detailed;
        this.total = new TotalStatistics(detailed);
    }
}
