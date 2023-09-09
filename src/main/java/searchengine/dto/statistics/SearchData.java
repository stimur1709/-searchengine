package searchengine.dto.statistics;

import lombok.Data;
import searchengine.model.InfoPage;

import java.util.List;

@Data
public class SearchData {

    private final boolean result;
    private final long count;
    private final List<InfoPage> data;

    public SearchData(List<InfoPage> data) {
        this.result = true;
        this.count = data.size();
        this.data = data;
    }

}
