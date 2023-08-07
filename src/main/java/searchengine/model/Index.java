package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Index implements Serializable {

    @Serial
    private static final long serialVersionUID = 1534214224141214207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PAGE_ID")
    private Long pageId;
    @Column(name = "LEMMA_ID")
    private Long lemmaId;
    private int rank;

}
