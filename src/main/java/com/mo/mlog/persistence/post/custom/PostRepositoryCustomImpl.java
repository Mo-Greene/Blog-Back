package com.mo.mlog.persistence.post.custom;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.mo.mlog.persistence.post.QPost.post;
import static com.mo.mlog.persistence.tag.QTag.tag;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory factory;

	/**
	 * 최근 게시글
	 */
	@Override
	public List<ListPostResponse> getPostLatestList() {

		return factory
			.select(Projections.constructor(ListPostResponse.class,
				post.id.as("id"),
				post.title.as("title"),
				post.preview.as("preview"),
				post.thumbnail.as("thumbnail"),
				tag.id.as("tagId"),
				tag.name.as("tagName"),
				post.createdAt.as("createdAt")
			))
			.from(post)
			.join(tag).on(post.tag.eq(tag))
			.orderBy(post.id.desc())
			.limit(3)
			.fetch();
	}

	/**
	 * 게시글 전체조회
	 */
	@Override
	public List<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request) {

		return factory
			.select(Projections.constructor(ListPostResponse.class,
				post.id.as("id"),
				post.title.as("title"),
				post.preview.as("preview"),
				post.thumbnail.as("thumbnail"),
				tag.id.as("tagId"),
				tag.name.as("tagName"),
				post.createdAt.as("createdAt")
			))
			.from(post)
			.join(tag).on(post.tag.eq(tag))
			.where(
				ltIndex(request.lastIndex()),
				searchTagId(request.tagId()),
				searchTitle(request.title())
			)
			.orderBy(post.id.desc())
			.limit(pageable.getPageSize())
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
	private BooleanExpression ltIndex(Long lastIndex) {
		if (lastIndex == null) {
			return null;
		}

		return post.id.lt(lastIndex);
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
