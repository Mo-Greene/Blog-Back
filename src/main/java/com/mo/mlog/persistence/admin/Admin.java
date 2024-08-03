package com.mo.mlog.persistence.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("관리자")
public class Admin {

	@Id
	@Column(name = "provider_id")
	@Comment("sns provider pk")
	private Long providerId;

	@Column(length = 20, nullable = false)
	@Comment("login name")
	private String login;

	@Column(length = 20, nullable = false)
	@Comment("github node id")
	private String nodeId;
}
