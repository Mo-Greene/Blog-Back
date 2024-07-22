package com.mo.mlog.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mo.mlog.persistence.post.custom.PostRepositoryCustom;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
