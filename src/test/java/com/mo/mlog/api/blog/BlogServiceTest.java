package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.persistence.post.Post;
import com.mo.mlog.persistence.post.PostRepository;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private TagRepository tagRepository;

	@InjectMocks
	private BlogService blogService;

	private PostRequest postRequest;
	private Tag tag;

	@BeforeEach
	void setUp() {
		postRequest = new PostRequest(
			"Test Title",
			"Test Content",
			"Test Content",
			2L
		);

		tag = Tag.builder()
			.name("SpringBoot")
			.build();
	}

	@Test
	void testSavePost() {
		when(tagRepository.findById(eq(2L))).thenReturn(Optional.of(tag));

		blogService.savePost(postRequest);

		verify(tagRepository).findById(eq(2L));
		verify(postRepository).save(any(Post.class));
		verifyNoMoreInteractions(tagRepository, postRepository);

	}

}