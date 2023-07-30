package searchengine.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DaoJdbcImpl implements DaoJdbc {

    private final JdbcTemplate jdbcTemplate;

    protected DaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(String sql, BatchPreparedStatementSetter pss) {
        jdbcTemplate.batchUpdate(sql, pss);
    }

}
