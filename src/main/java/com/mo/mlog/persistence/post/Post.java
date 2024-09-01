package com.mo.mlog.persistence.post;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.persistence.AbstractEntity;
import com.mo.mlog.persistence.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import static com.mo.mlog.common.util.UrlSlugUtil.generateSlug;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Comment("게시글")
public class Post extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("pk")
	private Long id;

	@Column(nullable = false, length = 100)
	@Comment("제목")
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	@Comment("내용")
	private String content;

	@Column(nullable = false, columnDefinition = "TEXT")
	@Comment("html 태그 없는 내용")
	private String plainContent;

	@Column(nullable = false, length = 150)
	@Comment("내용 미리보기")
	private String preview;

	@Column
	@Comment("썸네일")
	private String thumbnail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id", nullable = false)
	@Comment("태그 pk")
	private Tag tag;

	@Column(unique = true, nullable = false, columnDefinition = "TEXT")
	@Comment("슬러그")
	private String slug;

	@Builder
	public Post(String title, String content, String plainContent, String preview, String thumbnail, Tag tag) {
		this.title = title;
		this.content = content;
		this.plainContent = plainContent;
		this.preview = preview;
		this.thumbnail = thumbnail;
		this.tag = tag;
		this.slug = generateSlug(title);
	}

	/**
	 * 게시글 업데이트
	 *
	 * @param request 게시글 수정 정보
	 */
	public void updatePost(PostRequest request) {
		this.title = request.title();
		this.content = request.content();
		this.plainContent = request.plainContent();
		this.preview = request.plainContent().substring(0, Math.min(150, request.plainContent().length()));
	}
}
