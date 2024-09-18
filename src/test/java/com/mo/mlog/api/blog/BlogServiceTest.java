package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.PostRequest;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.mo.mlog.persistence.post.Post;
import com.mo.mlog.persistence.post.PostRepository;
import com.mo.mlog.persistence.tag.Tag;
import com.mo.mlog.persistence.tag.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

	@InjectMocks private BlogService blogService;
	@Mock private PostRepository postRepository;
	@Mock private TagRepository tagRepository;
	@Mock private BlogAsync blogAsync;
	@Mock private MultipartFile thumbnailMock;
	@Captor ArgumentCaptor<Post> postCaptor;

	@Test
	@DisplayName("최근 게시글 조회")
	void getPostLatestList() {
		//given
		List<ListPostResponse> expectedResponse = List.of(mock(ListPostResponse.class));
		when(postRepository.getPostLatestList()).thenReturn(expectedResponse);

		//when
		List<ListPostResponse> result = blogService.getPostLatestList();

		//then
		verify(postRepository, times(1)).getPostLatestList();
		assertEquals(expectedResponse, result);
	}

	@Test
	@DisplayName("게시글 저장 테스트")
	void savePost_withCallAsyncAndSavePost() {
		//given
		PostRequest request = new PostRequest(
			"Sample Title",
			"Sample Content",
			"Sample Plain Content",
			1L,
			thumbnailMock
		);

		Tag mockTag = new Tag("Sample Tag");
		when(tagRepository.findById(1L)).thenReturn(Optional.of(mockTag));
		when(thumbnailMock.isEmpty()).thenReturn(false);
		doNothing().when(blogAsync).commitGithub(anyString(), anyString());
		when(blogAsync.uploadFile(any(MultipartFile.class)))
			.thenReturn(CompletableFuture.completedFuture("sample-thumbnail-path"));

		//when
		blogService.savePost(request);

		//then
		verify(blogAsync, times(1)).commitGithub("Sample Title", "Sample Plain Content");
		verify(blogAsync, times(1)).uploadFile(thumbnailMock);
		verify(postRepository, times(1)).save(postCaptor.capture());
		Post savedPost = postCaptor.getValue();

		assertNotNull(savedPost);
		assertEquals("Sample Title", savedPost.getTitle());
		assertEquals("Sample Content", savedPost.getContent());
		assertEquals("Sample Plain Content", savedPost.getPlainContent());
		assertEquals("sample-thumbnail-path", savedPost.getThumbnail());
		assertEquals(mockTag, savedPost.getTag());
	}
}