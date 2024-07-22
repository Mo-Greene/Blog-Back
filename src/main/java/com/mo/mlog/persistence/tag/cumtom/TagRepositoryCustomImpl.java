package com.mo.mlog.persistence.tag.cumtom;

import com.mo.mlog.api.tag.dto.request.TagRequest;
import com.mo.mlog.api.tag.dto.response.TagResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.mo.mlog.persistence.tag.QTag.tag;

@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {

	private final JPAQueryFactory factory;

	/**
	 * 태그 목록 조회
	 *
	 * @param tagRequest 태그 이름
	 */
	@Override
	public Optional<List<TagResponse>> findTag(TagRequest tagRequest) {

		return Optional.ofNullable(
			factory
				.select(Projections.fields(TagResponse.class,
					tag.id.as("id"),
					tag.name.as("name")
				))
				.from(tag)
				.where(tag.name.contains(tagRequest.name()))
				.fetch()
		);
	}
}
