package com.website.shared.mail;

public interface EmailService {

    void sendNewPostNotification(String toEmail, String authorDisplayName, String postTitle, String postId);
}
