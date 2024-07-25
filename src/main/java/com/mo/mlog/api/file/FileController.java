package com.mo.mlog.api.file;

import com.mo.mlog.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	/**
	 * 프리사인드 url 반환
	 */
	@GetMapping
	public CommonResponse<?> getPresignedUrl() {

		return CommonResponse.ok(fileService.generatePresignedUrlForPut());
	}
}
