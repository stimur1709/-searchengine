package searchengine.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class DaoJdbcImpl<E> implements DaoJdbc<E> {

    private final JdbcTemplate jdbcTemplate;

    protected DaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(String sql, BatchPreparedStatementSetter pss) {
        jdbcTemplate.batchUpdate(sql, pss);
    }

    @Override
    public List<E> getContent(String sql, Object[] args, RowMapper<E> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    @Override
    public List<E> getContent(String sql, RowMapper<E> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }
}