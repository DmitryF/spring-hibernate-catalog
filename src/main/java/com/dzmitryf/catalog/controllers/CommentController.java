package com.dzmitryf.catalog.controllers;

import com.dzmitryf.catalog.model.comment.Comment;
import com.dzmitryf.catalog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Get all comments in database
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<Comment> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return comments;
    }

    /**
     * Get comment by id
     * @return the comment with given id
     */
    @RequestMapping(value = "{commentId}", method = RequestMethod.GET)
    public @ResponseBody Comment getCmmentById(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.getById(commentId);
        return comment;
    }
}
