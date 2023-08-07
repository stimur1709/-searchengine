package searchengine.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.exception.SiteException;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repository.SiteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SiteService extends ModelServiceImpl<Site, Long, SiteRepository> {

    private final ModelMapper modelMapper;

    @Autowired
    protected SiteService(SiteRepository repository, ModelMapper modelMapper) {
        super(repository);
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Site> findAll() {
        return repository.findByStatusNot(Status.INDEXING);
    }

    public StatisticsResponse getStatistics() {
        List<Site> sites = findAll();
        List<DetailedStatisticsItem> detailedStatisticsItems = sites
                .stream()
                .map(m -> modelMapper.map(m, DetailedStatisticsItem.class))
                .toList();
        return new StatisticsResponse(detailedStatisticsItems);
    }

    public Site findSiteByUrl(String url) throws SiteException {
        Optional<Site> site = repository.findByUrl(url);
        if (site.isPresent()) {
            if (site.get().getStatus() == Status.INDEXING) {
                throw new SiteException("Данная страница уже индексируется");
            } else {
                return site.get();
            }
        } else {
            throw new SiteException("Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
        }
    }

}
