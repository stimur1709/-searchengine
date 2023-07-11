package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.Site;
import searchengine.repository.SiteRepository;

@Service
public class SiteService extends ModelServiceImpl<Site, Long, SiteRepository> {

    @Autowired
    protected SiteService(SiteRepository repository) {
        super(repository);
    }

}
