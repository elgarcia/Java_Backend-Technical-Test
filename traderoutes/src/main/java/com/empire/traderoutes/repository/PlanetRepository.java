package com.empire.traderoutes.repository;

import com.empire.traderoutes.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, String> {
	Optional<Planet>    findByName(String name);
}
