package org.example.yobiapi.report.Entity;

import org.example.yobiapi.likes.Entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<ReportAllProjection> findAllBy();
    List<ReportBoardProjection> findAllByBoardIsNotNull();
    List<ReportCommentProjection> findAllByCommentsIsNotNull();
    List<ReportRecipeProjection> findAllByRecipeIsNotNull();
    Report findByReportId(Integer id);
}
