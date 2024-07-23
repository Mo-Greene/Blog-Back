package com.mo.mlog.persistence.post.custom;

import static com.mo.mlog.persistence.post.QPost.*;
import static com.mo.mlog.persistence.tag.QTag.*;

import java.util.List;
import java.util.Optional;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory factory;

	/**
	 * 게시글 전체조회
	 */
	@Override
	public List<ListPostResponse> getPostList(SearchPostRequest request) {

		return factory
			.select(Projections.constructor(ListPostResponse.class,
											post.id.as("id"),
											post.title.as("title"),
											post.preview.as("preview"),
											post.thumbnail.as("thumbnail"),
											tag.name.as("tagName"),
											post.createdAt.as("createdAt")
			))
			.from(post)
			.join(tag).on(post.tag.eq(tag))
			.where(
				ltPostId(request.lastPostId()),
				searchTagId(request.tagId()),
				searchTitle(request.title())
			)
			.orderBy(post.createdAt.desc())
			.fetch();
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param postId 게시글 pk
	 */
	@Override
	public Optional<DetailPostResponse> findPostDetail(Long postId) {

		return Optional.ofNullable(factory
									   .select(Projections.constructor(DetailPostResponse.class,
																	   post.id.as("id"),
																	   tag.name.as("tagName"),
																	   post.title.as("title"),
																	   post.content.as("content"),
																	   post.createdAt.as("createdAt")
									   ))
									   .from(post)
									   .join(tag).on(post.tag.eq(tag))
									   .where(post.id.eq(postId))
									   .fetchOne());
	}

	//게시글 no offset 페이지네이션
	private BooleanExpression ltPostId(Long lastPostId) {
		if (lastPostId == null) {
			return null;
		}

		return post.id.lt(lastPostId);
	}

	//검색조건 tagId
	private BooleanExpression searchTagId(Long tagId) {
		if (tagId == null) {
			return null;
		}

		return post.tag.id.eq(tagId);
	}

	//검색조건 title
	private BooleanExpression searchTitle(String title) {
		if (title == null) {
			return null;
		}

		return post.title.contains(title);
	}
}
