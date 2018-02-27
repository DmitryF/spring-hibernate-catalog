package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.model.user.User;
import com.dzmitryf.catalog.services.CommentService;
import com.dzmitryf.catalog.services.impl.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CommentService commentService;

    /**
     * Get all comments in database
     * @return comments list or {@literal ApiServiceException} if none found
     * @throws ApiServiceException if comments not found
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<Comment> getAllComments(Locale locale) throws Exception{
        try {
            LOGGER.info(messageSource.getMessage("comment.controller.get.comment.all", null, locale));
            List<Comment> comments = commentService.getAllComments(locale);
            LOGGER.info(messageSource.getMessage("comment.controller.response.comments", new Object[]{comments.size()}, locale));
            return comments;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("comment.controller.error.get.comment.all", null, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Get comment by id
     * @param commentId
     * @param locale
     * @return comment with given id {@literal ApiServiceException} if none found
     * @throws ApiServiceException if comment not found
     */
    @RequestMapping(value = "{commentId}", method = RequestMethod.GET)
    public @ResponseBody Comment getCmmentById(@PathVariable("commentId") Long commentId, Locale locale) throws Exception{
        try {
            LOGGER.info(messageSource.getMessage("comment.controller.get.comment.by.id", new Object[]{commentId}, locale));
            Comment comment = commentService.getById(commentId, locale);
            LOGGER.info(messageSource.getMessage("comment.controller.response.comment", new Object[]{comment}, locale));
            return comment;
        } catch (ApiServiceException e){
            throw e;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("comment.controller.error.get.comment.by.id", new Object[]{commentId}, locale), e);
            throw new ApiServiceException(messageSource.getMessage("application.error.internal", null, locale));
        }
    }

    /**
     * Comment controller exception handler
     * @param apiException
     * @return {@link ErrorResponse} with given status from {@link ApiServiceException}
     */
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ApiServiceException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getStatusCode().value(), apiException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, apiException.getStatusCode());
    }
}
