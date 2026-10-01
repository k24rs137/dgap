package com.example.dgap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dgap.model.CommunityLike;
import com.example.dgap.model.CommunityPost;
import com.example.dgap.model.User;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {
    long countByPost(CommunityPost post);
    boolean existsByPostAndUser(CommunityPost post, User user);
    void deleteByPostAndUser(CommunityPost post, User user);
    void deleteByPost(CommunityPost post);
}
