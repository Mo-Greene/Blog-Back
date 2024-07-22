package com.mo.mlog.persistence.tag;

import com.mo.mlog.persistence.tag.cumtom.TagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
