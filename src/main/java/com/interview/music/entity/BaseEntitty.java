package com.interview.music.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * @author Sandeep on 7/4/2022
 */
@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseEntitty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID")
    private Long id;
    @Column
    private LocalDate created;
    @Column
    private LocalDate lastModified;

    public BaseEntitty(final LocalDate created, final LocalDate lastModified) {
        this.created = created;
        this.lastModified = lastModified;
    }

    public BaseEntitty(final long id, final LocalDate created, final LocalDate lastModified) {
        this.id = id;
        this.created = created;
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntitty)) return false;
        BaseEntitty that = (BaseEntitty) o;
        return id != null &&
                id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
