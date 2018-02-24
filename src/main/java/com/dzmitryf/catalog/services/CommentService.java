package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.comment.Comment;

import java.util.List;
import java.util.Locale;

/**
 * Interface for work with service of comments.
 */
public interface CommentService extends CrudService<Comment> {

    /**
     * Retrieves all comments of user
     * @param user
     * @param locale
     * @return the comments with given user
     * @throws Exception
     */
    List<Comment> getAllCommentsByUser(User user, Locale locale) throws Exception;

    /**
     * Retrieves all comments
     * @param locale
     * @return comments list
     * @throws Exception
     */
    List<Comment> getAllComments(Locale locale) throws Exception;
}
