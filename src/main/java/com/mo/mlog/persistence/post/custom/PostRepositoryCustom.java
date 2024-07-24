package com.mo.mlog.persistence.post.custom;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

	List<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request);

	Optional<DetailPostResponse> findPostDetail(Long postId);

}
