package com.empire.traderoutes.service;

import com.empire.traderoutes.model.Planet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class PlanetServiceTest {

	@Autowired
	private PlanetService planetService;

	@Test
	public void testFindByName() {
		Optional<Planet> planet = planetService.findByName("Tatooine");
		Assertions.assertTrue(planet.isPresent(), "Planet Tatooine should exist");
		System.out.println("Planet found: " + planet.get().getCode());
	}
}
