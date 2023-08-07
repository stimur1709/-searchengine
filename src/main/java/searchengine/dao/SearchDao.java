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
             select s.url as site, s.name as site_name, p.path as uri, p.content as snippet, 0 as relevance\s
             from page p
                      join index i on p.id = i.page_id
                      join lemma l on l.id = i.id
                      join site s on s.id = p.site_id
             where l.lemma in (%s) %s
             order by l.frequency
            """;

    @Autowired
    protected SearchDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<InfoPage> getContent(String site, List<String> lemmas) {
        String sql = String.format(
                SQL_BY_SITE_AND_LEMMAS,
                String.join(", ", lemmas.stream().map(s -> "'" + s + "'").toList()),
                site == null ? " " : (" and s.url = " + site)
        );
        return getContent(sql, new BeanPropertyRowMapper<>(InfoPage.class));
    }

}
