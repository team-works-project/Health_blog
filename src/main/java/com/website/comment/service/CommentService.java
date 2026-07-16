package com.website.comment.service;

import com.website.comment.dto.request.CommentRequest;
import com.website.comment.dto.response.CommentResponse;
import com.website.shared.metadata.Metadata;
import org.springframework.data.domain.Page;


public interface CommentService {

    void deleteAsAdmin(String commentId);
}
