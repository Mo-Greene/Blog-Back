package com.mo.mlog.persistence.admin;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("관리자")
public class Admin {

	@Id
	@Column(length = 20)
	@Comment("아이디")
	private String username;

	@Column(length = 150)
	@Comment("비밀번호")
	private String password;
}
