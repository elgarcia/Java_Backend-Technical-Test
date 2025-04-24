package com.empire.traderoutes.service;

import com.empire.traderoutes.model.Planet;
import com.empire.traderoutes.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanetService {
	private final PlanetRepository  repository;

	public void saveAllPlanets(List<Planet> planets){
		repository.saveAll(planets);
	}

	public Optional<Planet> findByName(String name){
		return repository.findByName(name);
	}

	public Optional<Planet> findByCode(String code){
		return repository.findById(code);
	}

	public List<Planet> findAll() {
		return repository.findAll();
	}
}
