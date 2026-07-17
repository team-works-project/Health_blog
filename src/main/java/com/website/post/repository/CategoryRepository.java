package com.website.post.repository;


import com.website.post.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, String> {
    boolean existsByNameIgnoreCase(String name);

    Page<Category> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
