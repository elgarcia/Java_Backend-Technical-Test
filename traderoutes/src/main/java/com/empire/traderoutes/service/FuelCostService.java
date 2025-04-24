package com.empire.traderoutes.service;

import com.empire.traderoutes.dto.FuelCostDTO;
import com.empire.traderoutes.dto.TaxesDTO;
import com.empire.traderoutes.model.Planet;
import com.empire.traderoutes.model.RebelInfluence;
import com.empire.traderoutes.model.TradeRoute;
import com.empire.traderoutes.repository.TradeRouteRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class FuelCostService {
	private static final Logger logger = LoggerFactory.getLogger(FuelCostService.class);

	private final TradeRouteRepository  tradeRouteRepository;
	private final RebelInfluenceService rebelInfluenceService;
	private final PlanetService         planetService;

	private static final double PRICE_PER_LUNAR_DAY = 12.35;

	public FuelCostDTO calculateFuelCost(String originName, String destinationName) {
		logger.info("Calculating fuel cost for route: {} → {}", originName, destinationName);

		TradeRoute route = tradeRouteRepository.findByOriginAndDestination(originName, destinationName)
				.orElseThrow(() -> new RuntimeException("Trade route not found"));

		logger.info("Trade route found: Distance = {}", route.getDistance());

		Planet  originPlanet = planetService.findByName(originName)
				.orElseThrow(() -> new RuntimeException("Origin planet not found "));
		Planet  destinationPlanet = planetService.findByName(destinationName)
				.orElseThrow(() -> new RuntimeException("Destination planet not found"));

		String originCode = originPlanet.getCode();
		String destinationCode = destinationPlanet.getCode();
		logger.info("Origin planet code: {}", originCode);
		logger.info("Destination planet code: {}", destinationCode);

		RebelInfluence originInfluence = rebelInfluenceService.findByCode(originCode)
				.orElseThrow(() -> new RuntimeException("Origin planet rebel influence not found"));
		RebelInfluence destinationInfluence = rebelInfluenceService.findByCode(destinationCode)
				.orElseThrow(() -> new RuntimeException("Destination planet rebel influence not found"));

		double basePrice = route.getDistance() * PRICE_PER_LUNAR_DAY;
		basePrice = roundToTwoDecimalPlaces(basePrice);

		double originDefenseCost = basePrice * ((double) originInfluence.getRebelInfluence() / 100);
		double destinationDefenseCost = basePrice * ((double) destinationInfluence.getRebelInfluence() / 100);
		originDefenseCost = roundToTwoDecimalPlaces(originDefenseCost);
		destinationDefenseCost = roundToTwoDecimalPlaces(destinationDefenseCost);

		double totalInfluence = originInfluence.getRebelInfluence() + destinationInfluence.getRebelInfluence();
		double eliteDefenseCost = totalInfluence > 40 ? basePrice * ((totalInfluence - 40) / 100) : 0.0;
		eliteDefenseCost = roundToTwoDecimalPlaces(eliteDefenseCost);

		double totalAmount = basePrice + originDefenseCost + destinationDefenseCost + eliteDefenseCost;
		totalAmount = roundToTwoDecimalPlaces(totalAmount);
		logger.info("Total fuel cost: {}", totalAmount);

		return new FuelCostDTO(totalAmount, PRICE_PER_LUNAR_DAY,
				new TaxesDTO(originDefenseCost, destinationDefenseCost, eliteDefenseCost));
	}

	private double roundToTwoDecimalPlaces(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
