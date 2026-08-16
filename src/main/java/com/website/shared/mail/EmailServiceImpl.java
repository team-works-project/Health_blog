package com.website.shared.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromAddress;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void sendNewPostNotification(
            String toEmail, String authorDisplayName, String postTitle, String postId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject(authorDisplayName + " just published a new post");
            helper.setText(buildHtml(authorDisplayName, postTitle, postId), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to build/send notification email to {}", toEmail, e);
        }
    }

    private String buildHtml(String authorDisplayName, String postTitle, String postId) {
        String postUrl = frontendUrl + "/posts/" + postId;
        return "<html>"
                + "<body style=\"margin:0;padding:24px;background:#f4f6f8;font-family:Arial,Helvetica,sans-serif;\">"
                + "  <div style=\"max-width:480px;margin:0 auto;background:#ffffff;border-radius:8px;padding:24px;\">"
                + "    <h2 style=\"margin:0 0 12px;color:#1f2937;font-size:20px;\">New post from "
                + escapeHtml(authorDisplayName) + "</h2>"
                + "    <p style=\"margin:0 0 20px;color:#374151;font-size:15px;line-height:1.5;\">"
                + escapeHtml(postTitle) + "</p>"
                + "    <a href=\"" + postUrl + "\" "
                + "style=\"display:inline-block;padding:10px 18px;background:#2563eb;color:#ffffff;"
                + "text-decoration:none;border-radius:6px;font-size:14px;\">Read the post</a>"
                + "    <p style=\"margin:24px 0 0;color:#9ca3af;font-size:12px;\">"
                + "You're receiving this because you follow " + escapeHtml(authorDisplayName)
                + " on Health Blog.</p>"
                + "  </div>"
                + "</body>"
                + "</html>";
    }

    private String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
