package com.mo.mlog.persistence.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "tag", indexes = {
	@Index(name = "idx_tag_name", columnList = "name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Comment("게시글 태그")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("pk")
	private Long id;

	@Column(nullable = false, length = 20)
	@Comment("태그")
	private String name;

	@Builder
	public Tag(String name) {
		this.name = name;
	}
}
