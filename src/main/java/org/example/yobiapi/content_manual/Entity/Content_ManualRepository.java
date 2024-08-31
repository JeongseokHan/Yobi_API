package org.example.yobiapi.content_manual.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Content_ManualRepository extends JpaRepository<Content_Manual, Integer> {
    List<Content_Manual> findByContentTypeAndContentId(ContentType contentType, Integer contentId);
}
