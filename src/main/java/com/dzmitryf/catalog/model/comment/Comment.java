package com.dzmitryf.catalog.model.comment;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="COMMENTS", schema="hbschema")
public class Comment extends BaseEntity {

    private String message;

    private User user;

    public Comment(){}

    public Comment(User user, String message){
        setUser(user);
        setMessage(message);
    }

    public void update(Comment comment) {
        super.update(comment);
        if (comment != null){
            setMessage(comment.getMessage());
            setUser(comment.getUser());
        }
    }

    @Column(name = "MESSAGE", unique = false, nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment [id=" + getId() +
                ", message=" + getMessage() +
                "]";
    }
}
