package com.example.dgap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dgap.model.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    List<CommunityPost> findAllByOrderByCreatedAtDesc();
}
