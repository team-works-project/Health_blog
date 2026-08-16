package com.website.follow.repository;

import com.website.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, String> {

    boolean existsByFollower_IdAndFollowing_Id(String followerId, String followingId);

    Optional<Follow> findByFollower_IdAndFollowing_Id(String followerId, String followingId);

    long countByFollowing_Id(String followingId);

    /** Used by the notification consumer to find every email address to notify for an author. */
    @Query("select f.follower.email from Follow f where f.following.id = :followingId")
    List<String> findFollowerEmails(@Param("followingId") String followingId);

}
