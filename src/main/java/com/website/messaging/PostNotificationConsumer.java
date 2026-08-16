package com.website.messaging;

import com.website.follow.repository.FollowRepository;
import com.website.shared.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostNotificationConsumer {

    private final FollowRepository followRepository;
    private final EmailService emailService;

    @RabbitListener(queues = "${notification.rabbitmq.queue}")
    public void onPostCreated(PostNotificationMessage message) {
        List<String> followerEmails = followRepository.findFollowerEmails(message.getAuthorId());
        for (String email : followerEmails) {
            try {
                emailService.sendNewPostNotification(
                        email, message.getAuthorDisplayName(), message.getPostTitle(), message.getPostId());
            } catch (Exception e) {
                // One bad address shouldn't stop the rest of the followers from being notified.
                log.error("Failed to send new-post notification to {}", email, e);
            }
        }
    }
}
