package dev.mrb.fillTheSquares.repositories;

import dev.mrb.fillTheSquares.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
    @Query("SELECT m.matchId FROM MatchEntity m WHERE m.state = 'WAITING'")
    Optional<Long> findAvailableMatch();
}
