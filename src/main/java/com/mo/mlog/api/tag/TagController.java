package com.mo.mlog.api.tag;

import com.mo.mlog.api.tag.dto.request.TagRequest;
import com.mo.mlog.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	/**
	 * 태그 등록
	 *
	 * @param request 태그 이름
	 */
	@PostMapping
	public CommonResponse<?> createTag(@RequestBody TagRequest request) {

		tagService.createTag(request);
		return CommonResponse.ok();
	}

	/**
	 * 태그 조회
	 *
	 * @param request 태그 이름
	 */
	@GetMapping
	public CommonResponse<?> getTagList(TagRequest request) {

		return CommonResponse.ok(tagService.findTagList(request));
	}
}
