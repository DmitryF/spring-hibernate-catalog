package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.model.user.UserRole;
import com.dzmitryf.catalog.repositories.CommentRepository;
import com.dzmitryf.catalog.services.CommentService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Create a given comment. Use the returned instance for further operations as the save operation might have changed the
     * comment instance completely.
     * @param comment must note be {@literal null}
     * @param locale
     * @return the saved comment
     * @throws Exception
     */
    @Transactional
    @Override
    public Comment create(Comment comment, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.create.comment", new Object[]{comment}, locale));
        Comment createdComment = new Comment();
        try {
            createdComment.update(comment);
            entityManager.persist(createdComment);
            entityManager.flush();
            LOGGER.info(messageSource.getMessage("comment.service.created.comment", new Object[]{createdComment}, locale));
            return createdComment;
        } catch (EntityExistsException e) {
            LOGGER.info(messageSource.getMessage("comment.service.comment.already.exist",
                    new Object[]{comment.getId()}, locale));
            throw new ApiServiceException(messageSource.getMessage("comment.service.comment.already.exist",
                    new Object[]{comment.getId()}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (IllegalArgumentException e){
            LOGGER.info(messageSource.getMessage("comment.service.comment.not.entity", new Object[]{comment}, locale));
            throw new ApiServiceException(messageSource.getMessage("comment.service.comment.not.entity", new Object[]{comment}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("comment.service.error.create.comment", null, locale), e);
            throw e;
        }
    }

    /**
     * Retrieves an comment by its id.
     * @param id must not be {@literal null}.
     * @param locale
     * @return the comment with the given id or {@literal null} if none found
     * @throws Exception
     * @throws ApiServiceException if comment not found
     */
    @Override
    public Comment getById(Long id, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.get.comment.by.id", new Object[]{id}, locale));
        Comment comment = new Comment();
        try {
            comment = commentRepository.findOne(id);
            if (comment == null){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{id}, locale));
            throw new ApiServiceException(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{id}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        LOGGER.info(messageSource.getMessage("comment.service.found.comment", new Object[]{comment}, locale));
        return comment;
    }

    /**
     * Update a given comment. Use the returned instance for further operations as the save operation might have changed the
     * comment instance completely.
     * @param comment must note be {@literal null}
     * @param locale
     * @return the updated comment
     * @throws Exception
     * @throws ApiServiceException if comment not found
     */
    @Transactional
    @Override
    public Comment update(Comment comment, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.update.comment", new Object[]{comment}, locale));
        Comment updatedComment = new Comment();
        try {
            if (comment == null) {
                throw new IllegalArgumentException();
            }
            updatedComment = commentRepository.findOne(comment.getId());
            updatedComment.update(comment);
            commentRepository.flush();
            LOGGER.info(messageSource.getMessage("comment.service.updated.comment", new Object[]{updatedComment}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
        return updatedComment;
    }

    /**
     * Deletes a given comment.
     * @param comment must note be {@literal null}
     * @param locale
     * @throws Exception
     * @throws ApiServiceException if comment not found
     */
    @Override
    public void delete(Comment comment, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.delete.comment", new Object[]{comment}, locale));
        try {
            commentRepository.delete(comment);
            LOGGER.info(messageSource.getMessage("comment.service.deleted.comment", new Object[]{comment}, locale));
        } catch (IllegalArgumentException e) {
            LOGGER.info(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{0}, locale));
            throw new ApiServiceException(messageSource.getMessage("comment.service.comment.id.not.found", new Object[]{0}, locale),
                    HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Retrieves all comments of user
     * @param user
     * @param locale
     * @return the comments with given user
     * @throws Exception
     */
    @Transactional
    @Override
    public List<Comment> getAllCommentsByUser(User user, Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.get.comment.by.user", new Object[]{user}, locale));
        List<Comment> comments = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.comments WHERE user_id = ?1", Comment.class);
            query.setParameter(1, user.getId());
            comments = query.getResultList();
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("comment.service.error.get.comment.by.user", new Object[]{user}, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("comment.service.found.comments", new Object[]{comments.size()}, locale));
        return comments;
    }

    /**
     * Retrieves all comments
     * @param locale
     * @return comments list
     * @throws Exception
     */
    @Override
    public List<Comment> getAllComments(Locale locale) throws Exception{
        LOGGER.info(messageSource.getMessage("comment.service.get.comment.all", null, locale));
        List<Comment> comments = new ArrayList<>();
        try {
            comments = commentRepository.findAll();
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("comment.service.error.get.comment.all", null, locale), e);
            throw e;
        }
        LOGGER.info(messageSource.getMessage("comment.service.found.comments", new Object[]{comments.size()}, locale));
        return comments;
    }
}
