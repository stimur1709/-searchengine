package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
public class Site implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "STATUS_TIME")
    private Date statusTime;
    @Column(name = "LAST_ERROR")
    private String lastError;
    private String url;
    private String name;

    public Site indexing() {
        this.status = Status.INDEXING;
        this.lastError = null;
        return this;
    }

    public Site indexed(long pageCount) {
        this.status = pageCount > 0 ? Status.INDEXED : Status.FAILED;
        this.lastError = pageCount > 0 ? null : "Ошибка индексации: сайт не доступен";
        return this;
    }

}