package com.empire.traderoutes.service;

import com.empire.traderoutes.dto.TradeRouteDTO;
import com.empire.traderoutes.model.Planet;
import com.empire.traderoutes.model.TradeRoute;
import com.empire.traderoutes.repository.TradeRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TradeRouteService{
	private final TradeRouteRepository  tradeRouteRepository;
	private final PlanetService         planetService;
	private final RestTemplate restTemplate;

	public void saveTradeRoutes(Map<String, List<TradeRouteDTO>> tradeRoutesData) {
		tradeRouteRepository.deleteAll();
		List<TradeRoute>    tradeRoutes = new ArrayList<>();

		tradeRoutesData.forEach((originCode, destinations) ->
				planetService.findByCode(originCode).map(Planet::getName).ifPresent(origin ->
						destinations.forEach(dto ->
								planetService.findByCode(dto.getCode()).map(Planet::getName).ifPresent(destination -> {
										tradeRoutes.add(new TradeRoute(origin, destination, dto.getLunarYears()));}
								)
						)
				)
		);

		tradeRouteRepository.saveAll(tradeRoutes);
	}

	public List<TradeRoute> getAllRoutes(){
		return tradeRouteRepository.findAll();
	}
}
