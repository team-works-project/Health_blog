package com.website.comment.repository;

import com.website.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Page<Comment> findByPostId(String postId, Pageable pageable);

    /**
     * @SQLRestriction on the Comment entity hides soft-deleted rows from every normal
     * query (findAll, findByPostId, etc). This one is a native query, which Hibernate
     * does not run the restriction against, so it's the only way to see deleted rows -
     * used for the admin moderation view.
     */
    @Query(value = "SELECT * FROM comments", nativeQuery = true,
            countQuery = "SELECT count(*) FROM comments")
    Page<Comment> findAllIncludingDeleted(Pageable pageable);
}