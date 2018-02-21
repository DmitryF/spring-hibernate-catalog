package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.comment.Comment;

import java.util.List;

/**
 * Interface for work with service of comments.
 */
public interface CommentService extends CrudService<Comment> {

    /**
     * Retrieves all comments of user
     * @param user
     * @return the comments with given user
     */
    List<Comment> getAllCommentsByUser(User user);

    /**
     * Retrieves all comments
     * @return comments list
     */
    List<Comment> getAllComments();
}
