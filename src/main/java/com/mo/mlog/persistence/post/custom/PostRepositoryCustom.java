package com.mo.mlog.persistence.post.custom;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

	List<ListPostResponse> getPostLatestList();

	List<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request);

	Optional<DetailPostResponse> findPostDetail(String encodeSlug);

}
