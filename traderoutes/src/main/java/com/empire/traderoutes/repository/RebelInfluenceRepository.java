package com.empire.traderoutes.repository;

import com.empire.traderoutes.model.RebelInfluence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RebelInfluenceRepository extends JpaRepository<RebelInfluence, String> {
	Optional<RebelInfluence>    findByCode(String code);
}
