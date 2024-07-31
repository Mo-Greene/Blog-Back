package com.mo.mlog.api.blog;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.DetailPostResponse;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.mo.mlog.common.exception.EntityException;
import com.mo.mlog.common.util.GithubUtil;
import com.mo.mlog.persistence.post.Post;
import com.mo.mlog.persistence.post.PostRepository;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.mo.mlog.common.util.PreviewTextUtil.previewText;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final TagRepository tagRepository;
	private final PostRepository postRepository;
	private final AmazonS3Client amazonS3Client;
	private final GithubUtil githubUtil;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private static final String BUCKET_THUMBNAIL = "thumbnail/";

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
	public List<ListPostResponse> getPostList(Pageable pageable, SearchPostRequest request) {

		return postRepository.getPostList(pageable, request);
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

		String thumbnail = null;
		if (request.thumbnail() != null && !request.thumbnail().isEmpty()) {
			MultipartFile file = request.thumbnail();
			thumbnail = uploadFile(file);
		}

		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.plainContent(request.plainContent())
			.preview(previewText(request.plainContent()))
			.tag(tag)
			.thumbnail(thumbnail)
			.build();

		postRepository.save(post);

		//깃헙 개인 리포 commit
		githubUtil.commitGithub(request.title(), request.content());
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

	/**
	 * 파일 업로드
	 *
	 * @param file 썸네일
	 */
	private String uploadFile(MultipartFile file) {

		String name = String.valueOf(UUID.randomUUID());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucket, BUCKET_THUMBNAIL + name, file.getInputStream(), objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
		}

		return BUCKET_THUMBNAIL + name;
	}
}
