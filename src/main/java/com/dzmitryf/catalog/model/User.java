package com.dzmitryf.catalog.model;


import com.dzmitryf.catalog.model.base.BaseEntity;
import com.dzmitryf.catalog.model.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="USERS", schema="hbschema")
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private Set<Book> books = new HashSet<Book>(0);

    public User(){}

    public User(String firstName, String lastName){
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Column(name="FIRST_NAME", unique=false, nullable=false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="LAST_NAME", unique=false, nullable=false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_BOOK", schema = "hbschema", joinColumns = {
            @JoinColumn(name = "USER_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "BOOK_ID",
                    nullable = false, updatable = false) })
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
