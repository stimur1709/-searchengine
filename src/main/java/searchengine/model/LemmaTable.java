package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LEMMA")
public class LemmaTable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1534212151950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SITE_ID")
    private Long siteId;
    private String lemma;
    private int frequency;

    @Transient
    private List<Index> indexList = new ArrayList<>();

    public LemmaTable(Long siteId, String lemma, int frequency) {
        this.siteId = siteId;
        this.lemma = lemma;
        this.frequency = frequency;
    }

}
