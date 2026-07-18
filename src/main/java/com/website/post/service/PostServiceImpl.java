package com.website.post.service;


import com.website.post.dto.Response.PostResponse;
import com.website.category_create.entity.Category;
import com.website.post.entity.Post;
import com.website.tag_create.entity.Tag;
import com.website.post.mapper.PostMapper;
import com.website.category_create.repository.CategoryRepository;
import com.website.post.repository.PostRepository;
import com.website.tag_create.repository.TagRepository;
import com.website.shared.security.UserAccount;
import com.website.shared.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

    @Override
    public Page<PostResponse> list(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        Page<Post> posts = (keyword == null || keyword.isBlank())
                ? postRepository.findAll(pageable)
                : postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        return posts.map(postMapper::from);
    }

    private Post findById(String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    private Category resolveCategory(String categoryId) {
        if (categoryId == null || categoryId.isBlank()) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    private UserAccount resolveAuthor(String authorId) {
        return userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
    }

    private Set<Tag> resolveTags(List<String> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(tagRepository.findAllById(tagIds));
    }


}