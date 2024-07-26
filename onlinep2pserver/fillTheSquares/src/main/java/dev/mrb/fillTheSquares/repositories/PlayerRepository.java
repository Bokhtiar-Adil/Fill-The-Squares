package dev.mrb.fillTheSquares.repositories;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.mrb.fillTheSquares.entities.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>{
}
