package org.example.yobiapi.board.Entity;

import org.example.yobiapi.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardId(Integer boardId);
    Page<BoardProjection> findAllByTitleContaining(String title, Pageable pageable);
    Page<BoardProjection> findAllByCategoryContaining(String category, Pageable pageable);
    Page<BoardProjection> findAllByUser(User user, Pageable pageable);
}
