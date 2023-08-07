package searchengine.dto.statistics;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageDto {

    private final boolean result;
    private final long count;
    private final List<InfoPageDto> data;

    public PageDto(Page<InfoPageDto> data) {
        this.result = true;
        this.count = data.getTotalElements();
        this.data = data.getContent();
    }

}
