package com.website.follow.service;

import com.website.follow.dto.FollowResponse;
import com.website.follow.entity.Follow;
import com.website.follow.repository.FollowRepository;
import com.website.shared.metadata.Metadata;
import com.website.shared.metadata.MetadataHandler;
import com.website.shared.security.AuthorityEnum;
import com.website.shared.security.UserAccount;
import com.website.shared.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    @MetadataHandler
    @Transactional
    public FollowResponse follow(Metadata metadata, String targetUserId) {
        if (metadata.getUserId().equals(targetUserId)) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }
        UserAccount target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (target.getAuthority() != AuthorityEnum.ADMIN) {
            throw new IllegalArgumentException("You can only follow an admin");
        }
        if (followRepository.existsByFollower_IdAndFollowing_Id(metadata.getUserId(), targetUserId)) {
            throw new IllegalArgumentException("You already follow this admin");
        }
        UserAccount follower = userRepository.findById(metadata.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(target);
        Follow saved = followRepository.save(follow);

        return FollowResponse.builder()
                .id(saved.getId())
                .followerId(follower.getId())
                .followingId(target.getId())
                .followingDisplayName(target.getDisplayName())
                .build();
    }

    @Override
    @MetadataHandler
    @Transactional
    public void unfollow(Metadata metadata, String targetUserId) {
        Follow follow = followRepository.findByFollower_IdAndFollowing_Id(metadata.getUserId(), targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("You are not following this admin"));
        followRepository.delete(follow);
    }
}
