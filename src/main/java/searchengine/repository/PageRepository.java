package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;
import searchengine.model.key.PageKey;

public interface PageRepository extends JpaRepository<Page, PageKey> {


    @Transactional
    @Modifying
    @Query("delete from Page p where p.pageKey.siteId = ?1")
    void deleteBySiteId(Long siteId);

    long countByPageKey_SiteId(Long siteId);

}
