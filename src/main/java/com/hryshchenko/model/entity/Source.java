package com.hryshchenko.model.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "source")
public class Source {

    @Id
    @Column(name = "source_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "source_seq_gen")
    @SequenceGenerator(name = "source_seq_gen", sequenceName = "source_source_id_seq")
    private Long id;
    @Column
    private String name;
    @Column
    private String link;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
    private Set<Course> courses;

    public Source() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
