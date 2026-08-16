package com.website.follow.controller;

import com.website.follow.dto.FollowResponse;
import com.website.follow.service.FollowService;
import com.website.shared.entity.HttpBodyResponse;
import com.website.shared.metadata.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.website.shared.api.ControllerHandler.responseCreated;
import static com.website.shared.api.ControllerHandler.responseDeleted;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<HttpBodyResponse<FollowResponse>> follow(@PathVariable String userId) {
        return responseCreated(followService.follow(new Metadata(), userId));
    }

    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<Void> unfollow(@PathVariable String userId) {
        followService.unfollow(new Metadata(), userId);
        return responseDeleted();
    }
}
