package com.dzmitryf.catalog.model.book;

import com.dzmitryf.catalog.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Genre
 */
@Entity
@Table(name="GENRES", schema="hbschema")
public class Genre extends BaseEntity {

    private GenreEnum name;

    private String translate;

    @JsonIgnore
    private Set<Book> books;

    public Genre(){
        name = GenreEnum.undefined;
        translate = "";
    }

    public Genre(GenreEnum genre, String translate){
        setName(genre);
        setTranslate(translate);
    }

    public void update(Genre genre) {
        super.update(genre);
        if (genre != null) {
            setName(genre.getName());
            setTranslate(genre.getTranslate());
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name="NAME", unique=true, nullable=false)
    public GenreEnum getName() {
        return name;
    }

    public void setName(GenreEnum name) {
        this.name = name;
    }

    @Transient
    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre", cascade = CascadeType.PERSIST)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "Genre [id=" + getId() +
                ", name=" + getName() +
                "]";
    }
}
