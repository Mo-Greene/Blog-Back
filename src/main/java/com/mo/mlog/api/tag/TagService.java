package com.mo.mlog.api.tag;

import com.mo.mlog.api.tag.dto.request.TagRequest;
import com.mo.mlog.api.tag.dto.response.TagResponse;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

	private final TagRepository tagRepository;

	/**
	 * 태그 등록
	 */
	@Transactional
	public void createTag(TagRequest request) {

		Tag tag = Tag.builder()
			.name(request.name())
			.build();
		tagRepository.save(tag);
	}

	/**
	 * 태그 조회
	 *
	 * @param request 태그 이름
	 */
	@Transactional(readOnly = true)
	public List<TagResponse> findTagList(TagRequest request) {

		return tagRepository.findTag(request).orElse(Collections.emptyList());
	}
}
