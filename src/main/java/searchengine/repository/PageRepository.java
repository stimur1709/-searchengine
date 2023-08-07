package searchengine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.PageTable;

import java.util.Collection;
import java.util.List;

public interface PageRepository extends JpaRepository<PageTable, Long> {

    @Transactional
    @Modifying
    @Query("delete from PageTable p where p.siteId = ?1")
    void deleteBySiteId(Long siteId);

    @Transactional
    @Modifying
    @Query("delete from PageTable p where p.siteId in (?1)")
    void deleteByInSiteId(List<Long> siteId);

    long countBySiteId(Long siteId);

    @Query(value = "select p from PageTable p " +
            "join Index i on p.id = i.pageId " +
            "join LemmaTable l on l.id = i.id " +
            "where l.siteId = (select s.id from Site s where s.url like ?1) " +
            "and l.lemma in (?2) order by l.frequency")
    Page<PageTable> findBySiteIdAndLemma(String siteId, Collection<String> lemmas, Pageable pageable);

    @Query(value = "select p from PageTable p " +
            "join Index i on p.id = i.pageId " +
            "join LemmaTable l on l.id = i.id " +
            "where l.lemma in (?1) order by l.frequency")
    Page<PageTable> findByLemma(Collection<String> lemmas, Pageable pageable);

}
