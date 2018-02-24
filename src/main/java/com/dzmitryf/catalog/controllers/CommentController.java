package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * Get all comments in database
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<Comment> getAllComments(Locale locale) throws Exception{
        LOGGER.info("Request get all comments");
        List<Comment> comments = commentService.getAllComments(locale);
        LOGGER.info("Response has {} comments", comments.size());
        return comments;
    }

    /**
     * Get comment by id
     * @return the comment with given id
     */
    @RequestMapping(value = "{commentId}", method = RequestMethod.GET)
    public @ResponseBody Comment getCmmentById(@PathVariable("commentId") Long commentId, Locale locale) throws Exception{
        LOGGER.info("Request get comment by id: {}", commentId);
        Comment comment = commentService.getById(commentId, locale);
        LOGGER.info("Response has comment: {}", comment);
        return comment;
    }
}
