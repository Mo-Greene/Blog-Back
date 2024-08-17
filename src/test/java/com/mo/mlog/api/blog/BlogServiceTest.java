package com.mo.mlog.api.blog;

import com.mo.mlog.api.blog.dto.request.SearchPostRequest;
import com.mo.mlog.api.blog.dto.response.ListPostResponse;
import com.mo.mlog.persistence.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

	@InjectMocks
	private BlogService blogService;
	@Mock
	private PostRepository postRepository;

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
	@DisplayName("게시글 전체 조회")
	void getPostList() {
		//given
		Pageable pageable = mock(Pageable.class);
		SearchPostRequest searchPostRequest = mock(SearchPostRequest.class);
		List<ListPostResponse> expectedResponse = List.of(mock(ListPostResponse.class));

		when(postRepository.getPostList(pageable, searchPostRequest)).thenReturn(expectedResponse);

		List<ListPostResponse> result = blogService.getPostList(pageable, searchPostRequest);

		verify(postRepository, times(1)).getPostList(pageable, searchPostRequest);
		assertEquals(expectedResponse, result);
	}

}