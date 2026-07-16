package com.website.comment.service;

import com.website.comment.dto.request.CommentRequest;
import com.website.comment.dto.response.CommentResponse;
import com.website.comment.entity.Comment;
import com.website.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void deleteAsAdmin(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setDeletedAt(Instant.now());
        commentRepository.save(comment);
    }
}
