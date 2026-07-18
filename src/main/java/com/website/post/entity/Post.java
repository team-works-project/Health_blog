package com.website.post.entity;


import com.website.category_create.entity.Category;
import com.website.shared.security.UserAccount;
import com.website.tag_create.entity.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserAccount author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    private boolean enabled = true;
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    public void update(String title, PostType type, String content, String thumbnail, Category category, Set<Tag> tags) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.thumbnail = thumbnail;
        this.category = category;
        this.tags = tags;
        this.updatedAt = Instant.now();
    }
}
