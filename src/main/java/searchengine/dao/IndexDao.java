package searchengine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import searchengine.model.Index;
import searchengine.model.LemmaTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class IndexDao extends DaoJdbcImpl<Index> {

    @Autowired
    protected IndexDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void saveAll(List<LemmaTable> lemmaList, long pageId) {
        String sql = """
                insert into index(page_id, lemma_id, rank) values (?, ?, ?)\s
                on conflict(page_id, lemma_id)\s
                do update set page_id = ?, lemma_id = ?, rank = ?""";

        super.saveAll(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                long lemmaId = lemmaList.get(i).getId();
                int rank = lemmaList.get(i).getFrequency();
                ps.setLong(1, pageId);
                ps.setLong(2, lemmaId);
                ps.setLong(3, rank);
                ps.setLong(4, pageId);
                ps.setLong(5, lemmaId);
                ps.setLong(6, rank);
            }

            @Override
            public int getBatchSize() {
                return lemmaList.size();
            }
        });
    }

}
