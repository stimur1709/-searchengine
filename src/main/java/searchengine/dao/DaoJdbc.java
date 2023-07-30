package searchengine.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public interface DaoJdbc {

    void saveAll(String sql, BatchPreparedStatementSetter pss);

}
