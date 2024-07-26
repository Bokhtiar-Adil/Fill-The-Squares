package dev.mrb.fillTheSquares.repositories;

import dev.mrb.fillTheSquares.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
