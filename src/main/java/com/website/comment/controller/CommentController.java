package com.website.comment.controller;

import com.website.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.website.shared.api.ControllerHandler.responseDeleted;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/comments")
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        commentService.deleteAsAdmin(id);
        return responseDeleted();
    }
}
