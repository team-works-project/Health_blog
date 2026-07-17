package com.website.post.service;


import com.website.post.dto.Request.PostRequest;
import com.website.post.dto.Response.PostDetailResponse;
import com.website.post.dto.Response.PostResponse;
import com.website.post.entity.Category;
import com.website.post.entity.Post;
import com.website.post.entity.Tag;
import com.website.post.mapper.PostMapper;
import com.website.post.repository.CategoryRepository;
import com.website.post.repository.PostRepository;
import com.website.post.repository.TagRepository;
import com.website.shared.metadata.Metadata;
import com.website.shared.metadata.MetadataHandler;
import com.website.shared.security.UserAccount;
import com.website.shared.security.UserRepository;
import jakarta.transaction.Transactional;
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
    @Override
    public PostResponse view(String id) {
        return postMapper.from(findById(id));
    }
    @Override
    @MetadataHandler
    @Transactional
    public PostDetailResponse create(Metadata metadata, PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setType(request.getType());
        post.setContent(request.getContent());
        post.setThumbnail(request.getThumbnail());
        post.setCategory(resolveCategory(request.getCategoryId()));
        post.setAuthor(resolveAuthor(metadata.getUserId()));
        post.setTags(resolveTags(request.getTagIds()));
        post.setCreatedBy(metadata.getEmail());
        post.setUpdatedBy(metadata.getEmail());
        return postMapper.fromDetail(postRepository.save(post));
    }

    @Override
    @MetadataHandler
    @Transactional
    public PostDetailResponse update(Metadata metadata, String id, PostRequest request) {
        Post post = findById(id);
        post.update(
                request.getTitle(), request.getType(), request.getContent(), request.getThumbnail(),
                resolveCategory(request.getCategoryId()), resolveTags(request.getTagIds()));
        post.setUpdatedBy(metadata.getEmail());
        return postMapper.fromDetail(postRepository.save(post));
    }

    @Override
    @Transactional
    public void delete(String id) {
        postRepository.delete(findById(id));
    }
    @Override
    @MetadataHandler
    @Transactional
    public PostDetailResponse enable(Metadata metadata, String id) {
        Post post = findById(id);
        post.enable();
        post.setUpdatedBy(metadata.getEmail());
        return postMapper.fromDetail(postRepository.save(post));
    }

    @Override
    @MetadataHandler
    @Transactional
    public PostDetailResponse disable(Metadata metadata, String id) {
        Post post = findById(id);
        post.disable();
        post.setUpdatedBy(metadata.getEmail());
        return postMapper.fromDetail(postRepository.save(post));
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