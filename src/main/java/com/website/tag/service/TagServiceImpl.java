package com.website.tag.service;

import com.website.tag.entity.Tag;
import com.website.tag.mapper.TagMapper;
import com.website.tag.dto.request.TagRequest;
import com.website.tag.dto.response.TagResponse;
import com.website.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public Page<TagResponse> list(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        Page<Tag> tags;
        if (keyword == null || keyword.isBlank()) {
            tags = tagRepository.findAll(pageable);
        } else {
            tags = tagRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return tags.map(tagMapper::from);
    }

    @Override
    public TagResponse view(String id) {
        return tagMapper.from(findById(id));
    }

    @Override
    @Transactional
    public TagResponse create(TagRequest request) {
        if (tagRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }
        Tag tag = tagMapper.from(request);
        return tagMapper.from(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public TagResponse update(String id, TagRequest request) {
        Tag tag = findById(id);
        if (!tag.getName().equalsIgnoreCase(request.getName())
                && tagRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }
        tagMapper.updateFrom(request, tag);
        return tagMapper.from(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void delete(String id) {
        tagRepository.delete(findById(id));
    }

    private Tag findById(String id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }
}