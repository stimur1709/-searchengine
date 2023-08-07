package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoPage {

    private String site;
    private String siteName;
    private String uri;
    private String title;
    private String snippet;
    private double relevance;

}
