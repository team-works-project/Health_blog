package com.website.follow.service;

import com.website.follow.dto.FollowResponse;
import com.website.shared.metadata.Metadata;

public interface FollowService {

    FollowResponse follow(Metadata metadata, String targetUserId);

    void unfollow(Metadata metadata, String targetUserId);
}
