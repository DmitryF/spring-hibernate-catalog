package com.dzmitryf.catalog.model.book;

import com.dzmitryf.catalog.model.base.BaseEntity;
import com.dzmitryf.catalog.model.base.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="BOOKS", schema="hbschema")
public class Book extends BaseEntity {

    private String name;

    private String authorName;

    private String description;

    private Long countPages;

    private Language language = Language.UNDEFINED;

    public Book(){}

    public Book(String name, String authorName, Long countPages){
        setName(name);
        setAuthorName(authorName);
        setCountPages(countPages);
    }

    @Column(name="NAME", unique=false, nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="AUTHOR_NAME", unique=false, nullable=true)
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Column(name="DESCRIPTION", unique=false, nullable=true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="COUNT_PAGES", unique=false, nullable=true)
    public Long getCountPages() {
        return countPages;
    }

    public void setCountPages(Long countPages) {
        this.countPages = countPages;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="LANGUAGE", unique=false, nullable=true)
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
