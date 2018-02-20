package com.dzmitryf.catalog.model.user;


import com.dzmitryf.catalog.model.base.BaseEntity;
import com.dzmitryf.catalog.model.base.SecurityRole;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="USERS", schema="hbschema")
public class User extends BaseEntity {

    private String userName;

    private String password;

    private UserRole userRole;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private Set<Book> books = new HashSet<Book>(0);

    @JsonIgnore
    private Set<Comment> comments = new HashSet<>(0);

    public User(){}

    public User(String userName, String password, SecurityRole userRole){
        setUserName(userName);
        setPassword(password);
    }

    public User(/*String userName, String password, UserRole userRole, */String firstName, String lastName){
        /*setUserName(userName);
        setPassword(password);
        setUserRole(userRole);*/
        setFirstName(firstName);
        setLastName(lastName);
    }

    public void update(User user) {
        super.update(user);
        if (user != null){
            setFirstName(user.getFirstName());
            setLastName(user.getLastName());
            setBooks(user.getBooks());
        }
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Column(name="USER_NAME", unique=true, nullable=true)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.MERGE)
    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User [id=" + getId() +
                ", userName=" + getUserName() +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName()+
                "]";
    }
}
