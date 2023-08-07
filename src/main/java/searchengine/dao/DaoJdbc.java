package searchengine.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface DaoJdbc<E> {

    void saveAll(String sql, BatchPreparedStatementSetter pss);

    List<E> getContent(String sql, Object[] args, RowMapper<E> rowMapper);

    List<E> getContent(String sql, RowMapper<E> rowMapper);

}
