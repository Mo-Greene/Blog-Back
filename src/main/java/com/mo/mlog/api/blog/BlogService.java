package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.mo.mlog.common.exception.EntityException;
import com.mo.mlog.persistence.post.Post;
import com.mo.mlog.persistence.post.PostRepository;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mo.mlog.common.util.PreviewTextUtil.previewText;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final TagRepository tagRepository;
	private final PostRepository postRepository;

	// TODO: 2024-07-14 Mo-Greene : 게시글 전체조회 검색조건 추후 + 무한스크롤 형식!
	/**
	 * 게시글 전체조회
	 */
	@Transactional(readOnly = true)
	public List<ListPostResponse> getPostList() {

		return postRepository.getPostList();
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param postId 게시글 pk
	 */
	@Transactional(readOnly = true)
	public DetailPostResponse findPostById(Long postId) {

		return postRepository.findPostDetail(postId).orElseThrow(EntityException::new);
	}

	/**
	 * 게시글 저장
	 *
	 * @param request 게시글 저장
	 */
	@Transactional
	public void savePost(PostRequest request) {

		Tag tag = tagRepository.findById(request.tagId()).orElseThrow(EntityException::new);

		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.plainContent(request.plainContent())
			.preview(previewText(request.plainContent()))
			.tag(tag)
			.build();

		postRepository.save(post);
	}

	/**
	 * 게시글 수정
	 *
	 * @param postId  게시글 pk
	 * @param request 게시글 정보
	 */
	@Transactional
	public void updatePost(Long postId, PostRequest request) {

		Post post = postRepository.findById(postId).orElseThrow(EntityException::new);
		post.updatePost(request);
	}

	// TODO: 2024-07-14 Mo-Greene : 게시글 삭제

	// TODO: 2024-07-15 Mo-Greene : 이미지 업로드

	// TODO: 2024-07-15 Mo-Greene : 댓글 목록 무한스크롤
}
