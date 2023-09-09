package searchengine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import searchengine.model.InfoPage;

import java.util.List;

@Component
public class SearchDao extends DaoJdbcImpl<InfoPage> {

    private static final String SQL_BY_SITE_AND_LEMMAS = """
            with rank_index as (select i.page_id, sum(i.rank) sum_rank
                                from lemma l
                                         join index i on l.id = i.lemma_id
                                         join page p on p.id = i.page_id
                                         join site s on s.id = p.site_id
                                where l.lemma in (%s) %s
                                group by i.page_id
                                limit %d offset %d)
            select s.url site, s.name site_name, p.path uri, p.content snippet, r.sum_rank / max(r1.sum_rank) relevance
            from site s
                     join page p on s.id = p.site_id
                     join rank_index r on r.page_id = p.id
                     cross join rank_index r1
            group by s.url, s.name, p.path, p.content, r.sum_rank
            order by relevance desc""";

    @Autowired
    protected SearchDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<InfoPage> getContent(String site, List<String> lemmas, int offset, int limit) {
        String sql = String.format(
                SQL_BY_SITE_AND_LEMMAS,
                String.join(", ", lemmas.stream().map(s -> "'" + s + "'").toList()),
                site == null ? " " : (" and s.url = '" + site + "'"),
                limit,
                offset
        );
        return getContent(sql, new BeanPropertyRowMapper<>(InfoPage.class));
    }

}
