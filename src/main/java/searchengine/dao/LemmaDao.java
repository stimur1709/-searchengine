package searchengine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import searchengine.model.LemmaTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class LemmaDao extends DaoJdbcImpl<LemmaTable> {

    @Autowired
    protected LemmaDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void saveAll(List<LemmaTable> lemmaList) {
        String sql = """
                insert into lemma(site_id, lemma) values (?, ?)\s
                on conflict(site_id, lemma)\s
                do update set site_id = ?, lemma = ?""";

        super.saveAll(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String lemma = lemmaList.get(i).getLemma();
                Long siteId = lemmaList.get(i).getSiteId();
                ps.setLong(1, siteId);
                ps.setString(2, lemma);
                ps.setLong(3, siteId);
                ps.setString(4, lemma);
            }

            @Override
            public int getBatchSize() {
                return lemmaList.size();
            }
        });
    }

}
