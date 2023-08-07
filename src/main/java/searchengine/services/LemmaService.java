package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.dao.IndexDao;
import searchengine.dao.LemmaDao;
import searchengine.model.LemmaTable;
import searchengine.model.Page;
import searchengine.repository.LemmaRepository;
import searchengine.util.LemmaStorage;
import searchengine.util.Morphology;

import java.util.List;

@Service
public class LemmaService extends ModelServiceImpl<LemmaTable, Long, LemmaRepository> {

    private final LemmaDao lemmaDao;
    private final IndexDao indexDao;
    private final LemmaRepository lemmaRepository;

    @Autowired
    protected LemmaService(LemmaRepository repository, LemmaDao lemmaDao, IndexDao indexDao,
                           LemmaRepository lemmaRepository) {
        super(repository);
        this.lemmaDao = lemmaDao;
        this.indexDao = indexDao;
        this.lemmaRepository = lemmaRepository;
    }

    public synchronized void indexingContent(Page page, String text) {
        if (page.getCode() != 0 && page.getCode() < 400) {
            LemmaStorage storage = Morphology.getMorphology(text);
            Long siteId = page.getSiteId();
            List<LemmaTable> list = storage.getList(siteId);

            saveAll(list);

            List<String> lemmas = list
                    .stream()
                    .map(LemmaTable::getLemma)
                    .toList();

            List<LemmaTable> lemmaList = repository.findBySiteIdAndLemmaIn(siteId, lemmas);

            for (LemmaTable lemmaTable : lemmaList) {
                for (LemmaTable lemma : list) {
                    if (lemma.getLemma().equals(lemmaTable.getLemma())) {
                        lemmaTable.setFrequency(lemma.getFrequency());
                    }
                }
            }

            indexDao.saveAll(lemmaList, page.getId());
        }
    }

    @Override
    public void saveAll(List<LemmaTable> list) {
        lemmaDao.saveAll(list);
    }

    @Override
    public void deleteById(Long aLong) {
        lemmaRepository.deleteBySiteId(aLong);
    }

    public void deleteByInId(List<Long> list) {
        lemmaRepository.deleteByInSiteId(list);
    }
}
