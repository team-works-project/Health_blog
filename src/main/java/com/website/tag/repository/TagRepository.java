package com.website.tag.repository;

import com.website.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    boolean existsByNameIgnoreCase(String name);

    Page<Tag> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
