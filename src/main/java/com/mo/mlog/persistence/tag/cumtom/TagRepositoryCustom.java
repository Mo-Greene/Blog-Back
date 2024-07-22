package com.mo.mlog.persistence.tag.cumtom;

import com.mo.mlog.api.tag.dto.request.TagRequest;
import com.mo.mlog.api.tag.dto.response.TagResponse;

import java.util.List;
import java.util.Optional;

public interface TagRepositoryCustom {

	Optional<List<TagResponse>> findTag(TagRequest tagRequest);
}
