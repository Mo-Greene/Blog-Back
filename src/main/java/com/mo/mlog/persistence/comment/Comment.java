package com.mo.mlog.persistence.comment;

import org.hibernate.annotations.DynamicUpdate;

import com.mo.mlog.persistence.AbstractEntity;
import com.mo.mlog.persistence.post.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment", indexes = {
	@Index(name = "idx_post_id", columnList = "post_id"),
	@Index(name = "idx_parent_comment_id", columnList = "parent_comment_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@org.hibernate.annotations.Comment("댓글")
public class Comment extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@org.hibernate.annotations.Comment("pk")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	@org.hibernate.annotations.Comment("게시글 fk")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	@org.hibernate.annotations.Comment("부모 댓글 pk")
	private Comment parentCommentId;
}
