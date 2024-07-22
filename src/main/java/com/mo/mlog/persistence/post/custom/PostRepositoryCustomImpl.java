package com.mo.mlog.persistence.post.custom;

import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.mo.mlog.persistence.post.QPost.post;
import static com.mo.mlog.persistence.tag.QTag.tag;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory factory;

	/**
	 * 게시글 전체조회
	 */
	@Override
	public List<ListPostResponse> getPostList() {

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
}
