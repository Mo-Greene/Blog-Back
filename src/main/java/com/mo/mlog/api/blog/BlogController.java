package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

	private final BlogService blogService;

	/**
	 * 최근 게시글 조회
	 */
	@GetMapping("/latest")
	public CommonResponse<?> getLatestPost() {

		return CommonResponse.ok(blogService.getPostLatestList());
	}

	/**
	 * 게시글 전체조회 no offset pagination
	 */
	@GetMapping
	public CommonResponse<?> getPost(@PageableDefault(size = 20) Pageable pageable, SearchPostRequest request) {

		return CommonResponse.ok(blogService.getPostList(pageable, request));
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param postId 게시글 pk
	 */
	@GetMapping("/{postId}")
	public CommonResponse<?> findPostDetail(@PathVariable Long postId) {

		return CommonResponse.ok(blogService.findPostById(postId));
	}

	/**
	 * 게시글 저장
	 *
	 * @param request 게시글 정보
	 */
	@PostMapping
	public CommonResponse<?> savePost(@Valid PostRequest request) {

		blogService.savePost(request);
		return CommonResponse.ok();
	}

	/**
	 * 게시글 수정
	 *
	 * @param postId  게시글 pk
	 * @param request 수정 게시글 정보
	 */
	@PatchMapping("/{postId}")
	public CommonResponse<?> updatePost(@PathVariable Long postId,
	                                    @RequestBody @Valid PostRequest request) {

		blogService.updatePost(postId, request);
		return CommonResponse.ok();
	}
}
