package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import searchengine.model.key.PageKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Serializable {

    @Serial
    private static final long serialVersionUID = 1532235231950251207L;

    @EmbeddedId
    private PageKey pageKey;
    private int code;
    private String content;

}
