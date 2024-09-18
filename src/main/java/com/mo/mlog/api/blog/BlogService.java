package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.mo.mlog.common.exception.EntityException;
import com.mo.mlog.persistence.post.Post;
import com.mo.mlog.persistence.post.PostRepository;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final TagRepository tagRepository;
	private final PostRepository postRepository;
	private final BlogAsync blogAsync;

	/**
	 * 최근 게시글 조회
	 */
	@Transactional(readOnly = true)
	public List<ListPostResponse> getPostLatestList() {

		return postRepository.getPostLatestList();
	}

	/**
	 * 게시글 전체조회 no offset 페이지네이션
	 */
	@Transactional(readOnly = true)
	public Page<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request) {

		return postRepository.getPostList(pageable, request);
	}

	/**
	 * 게시글 상세조회
	 *
	 * @param slug 제목 슬러그
	 */
	@Transactional(readOnly = true)
	public DetailPostResponse findPostBySlug(String slug) {

		return postRepository.findPostDetail(slug).orElseThrow(EntityException::new);
	}

	/**
	 * 게시글 저장
	 *
	 * @param request 게시글 저장
	 */
	@Transactional
	public void savePost(PostRequest request) {
		try {
			//깃헙 개인 리포 commit
			blogAsync.commitGithub(request.title(), request.content());

			Tag tag = tagRepository.findById(request.tagId()).orElseThrow(EntityException::new);

			String thumbnail = null;
			if (request.thumbnail() != null && !request.thumbnail().isEmpty()) {
				MultipartFile file = request.thumbnail();
				CompletableFuture<String> futureThumbnail = blogAsync.uploadFile(file);
				thumbnail = futureThumbnail.join();
			}

			var post = Post.toPostEntity(request, tag, thumbnail);
			postRepository.save(post);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
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
}
