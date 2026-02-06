package com.challenge.challege.repository;

import com.challenge.challege.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTopicId(Long topicId, Pageable pageable);
}
