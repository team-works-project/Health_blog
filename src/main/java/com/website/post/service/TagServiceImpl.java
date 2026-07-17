package com.website.post.service;


import com.website.post.dto.Request.TagRequest;
import com.website.post.dto.Response.TagResponse;
import com.website.post.entity.Tag;
import com.website.post.mapper.TagMapper;
import com.website.post.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    @Override
    public Page<TagResponse> list(Integer page, Integer size, String keyword) {
        return null;
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



}
