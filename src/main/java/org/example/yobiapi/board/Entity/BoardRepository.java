package org.example.yobiapi.board.Entity;

import org.example.yobiapi.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardId(Integer boardId);
    List<BoardProjection> findAllByTitleContaining(String title);
    List<BoardProjection> findAllByCategoryContaining(String category);
    List<BoardProjection> findAllByUser(User user);
}
