package com.dzmitryf.catalog.repositories;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves comments by user
     * @param user
     * @return the comments with given user
     */
    List<Comment> findAllByUser(User user);
}
