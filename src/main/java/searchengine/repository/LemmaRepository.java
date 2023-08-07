package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.LemmaTable;

import java.util.Collection;
import java.util.List;

public interface LemmaRepository extends JpaRepository<LemmaTable, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from lemma l where l.site_id = ?1", nativeQuery = true)
    void deleteBySiteId(Long siteId);

    @Transactional
    @Modifying
    @Query(value = "delete from lemma l where l.site_id in (?1)", nativeQuery = true)
    void deleteByInSiteId(List<Long> siteId);

    List<LemmaTable> findBySiteIdAndLemmaIn(Long siteId, Collection<String> lemmas);

}
