package com.alten.skillsmanagement.repository;

import com.alten.skillsmanagement.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
