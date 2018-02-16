package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.User;
import com.dzmitryf.catalog.model.book.Book;
import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.repositories.CommentRepository;
import com.dzmitryf.catalog.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Comment create(Comment entity) {
        LOGGER.info("Creating a new comment: {}", entity);
        try {
            entityManager.persist(entity);
            entityManager.flush();
            LOGGER.info("Created a new comment: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while creating a new comment: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public Comment getById(Long id) {
        LOGGER.info("Finding a comment by id: {}", id);
        Comment comment = null;
        try {
            comment = commentRepository.findOne(id);
        } catch (Exception e) {
            LOGGER.error("Error while finding a comment by id: ", e);
            return null;
        }
        LOGGER.info("Found a comment : {}", comment);
        return comment;
    }

    @Transactional
    @Override
    public Comment update(Comment entity) {
        LOGGER.info("Updating a book: {}", entity);
        try {
            Comment currentComment = commentRepository.findOne(entity.getId());
            currentComment.update(entity);
            commentRepository.flush();
            LOGGER.info("Updated a book: {}", currentComment);
        } catch (Exception e){
            LOGGER.error("Error while updating a book: ", e);
            return null;
        }
        return entity;
    }

    @Override
    public void delete(Comment entity) {
        LOGGER.info("Deleting a comment: {}", entity);
        try {
            commentRepository.delete(entity);
            LOGGER.info("Deleted a comment: {}", entity);
        } catch (Exception e){
            LOGGER.error("Error while deleting a comment: ", e);
        }
    }

    @Transactional
    @Override
    public List<Comment> getAllCommentsOfUser(User user) {
        LOGGER.info("Finding comments by user: {}", user);
        List<Comment> comments = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.comments WHERE user_id = ?1", Comment.class);
            query.setParameter(1, user.getId());
            comments = query.getResultList();
            entityManager.flush();
            LOGGER.info("Found {} comments", comments.size());
        } catch (Exception e){
            LOGGER.error("Error while finding a comments by user: ", e);
        }
        return comments;
    }
}
