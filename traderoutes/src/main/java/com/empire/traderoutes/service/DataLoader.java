package com.empire.traderoutes.service;

import com.empire.traderoutes.dto.RebelInfluenceDTO;
import com.empire.traderoutes.model.RebelInfluence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.empire.traderoutes.dto.PlanetDTO;
import com.empire.traderoutes.dto.TradeRouteDTO;
import com.empire.traderoutes.model.Planet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataLoader {
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
	private final PlanetService         planetService;
	private final TradeRouteService     tradeRouteService;
	private final RebelInfluenceService rebelInfluenceService;
	private final RestTemplate          restTemplate;

	@Value("${api.planets.url}")
	private String planetsUrl;

	@Value("${api.distances.url}")
	private String distancesUrl;

	@Value("${api.rebelInfluence.url}")
	private String rebelInfluenceUrl;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		loadPlanets();
		loadRebelInfluence();
		loadTradeRoutes();
	}

	private void loadPlanets() {
		ResponseEntity<PlanetDTO[]> response = restTemplate.getForEntity(planetsUrl, PlanetDTO[].class);

		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			List<Planet> planets = Arrays.stream(response.getBody())
					.map(dto -> new Planet(dto.getCode(), dto.getPlanetName(), dto.getSector()))
					.toList();

			planetService.saveAllPlanets(planets);
			log.info("✅ Loaded {} planets into the database.", planets.size());
		} else {
			log.warn("❌ Failed to fetch planets data.");
		}
	}

	private void loadRebelInfluence() {
		try {
			ResponseEntity<RebelInfluenceDTO[]> response = restTemplate.getForEntity(rebelInfluenceUrl, RebelInfluenceDTO[].class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				List<RebelInfluence> rebelInfluences = Arrays.stream(response.getBody())
						.map(dto -> new RebelInfluence(dto.getCode(), dto.getRebelInfluence()))
						.toList();

				rebelInfluenceService.saveAll(rebelInfluences);
				log.info("✅ Loaded {} rebel influence records into the database.", rebelInfluences.size());
			} else {
				log.warn("❌ Failed to fetch rebel influence data.");
			}
		} catch (Exception e) {
			log.error("❌ Error fetching rebel influence data: {}", e.getMessage());
		}
	}

	private void loadTradeRoutes() {
		try{
			ResponseEntity<Map<String, List<TradeRouteDTO>>> response = restTemplate.exchange(
				distancesUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				tradeRouteService.saveTradeRoutes(response.getBody());
				log.info("✅ Loaded trade routes into the database.");
			} else {
				log.warn("❌ Failed to fetch trade routes data.");
			}
		} catch (Exception e){
			log.error("❌ Error fetching trade routes data: {}", e.getMessage());
		}
	}
}
