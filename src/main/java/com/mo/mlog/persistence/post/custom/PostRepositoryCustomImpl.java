package com.mo.mlog.persistence.post.custom;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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
				post.slug.as("slug"),
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
	public Page<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request) {

		List<ListPostResponse> query = factory
			.select(Projections.constructor(ListPostResponse.class,
				post.id.as("id"),
				post.title.as("title"),
				post.preview.as("preview"),
				post.thumbnail.as("thumbnail"),
				post.slug.as("slug"),
				tag.id.as("tagId"),
				tag.name.as("tagName"),
				post.createdAt.as("createdAt")
			))
			.from(post)
			.leftJoin(tag).on(post.tag.eq(tag))
			.where(
				ltPostId(request.cursor()),
				searchTagId(request.tagId()),
				searchTitle(request.title())
			)
			.orderBy(post.id.desc())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> count = factory
			.select(post.count())
			.from(post)
			.leftJoin(tag).on(post.tag.eq(tag))
			.where(
				ltPostId(request.cursor()),
				searchTagId(request.tagId()),
				searchTitle(request.title())
			);

		return PageableExecutionUtils.getPage(query, pageable, count::fetchOne);
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param encodeSlug 게시글 슬러그
	 */
	@Override
	public Optional<DetailPostResponse> findPostDetail(String encodeSlug) {

		return Optional.ofNullable(factory
			.select(Projections.constructor(DetailPostResponse.class,
				post.id.as("id"),
				tag.name.as("tagName"),
				post.title.as("title"),
				post.content.as("content"),
				post.slug.as("slug"),
				post.createdAt.as("createdAt")
			))
			.from(post)
			.join(tag).on(post.tag.eq(tag))
			.where(post.slug.eq(encodeSlug))
			.fetchOne());
	}

	//no offset 검색조건
	private BooleanExpression ltPostId(Long cursor) {
		if (cursor == null) {
			return null;
		}
		return post.id.lt(cursor);
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
