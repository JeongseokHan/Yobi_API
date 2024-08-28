package org.example.yobiapi.report.Entity;

import org.example.yobiapi.likes.Entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<ReportAllProjection> findAllBy(Pageable pageable);
    Page<ReportBoardProjection> findAllByBoardIsNotNull(Pageable pageable);
    Page<ReportCommentProjection> findAllByCommentsIsNotNull(Pageable pageable);
    Page<ReportRecipeProjection> findAllByRecipeIsNotNull(Pageable pageable);
    Report findByReportId(Integer id);
}
