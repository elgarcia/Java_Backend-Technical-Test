package com.empire.traderoutes.service;

import com.empire.traderoutes.dto.FuelCostDTO;
import com.empire.traderoutes.model.Planet;
import com.empire.traderoutes.model.RebelInfluence;
import com.empire.traderoutes.model.TradeRoute;
import com.empire.traderoutes.repository.TradeRouteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FuelCostServiceTest {

	@Mock
	private TradeRouteRepository tradeRouteRepository;

	@Mock
	private RebelInfluenceService rebelInfluenceService;

	@InjectMocks
	private FuelCostService fuelCostService;

	@Mock
	private PlanetService   planetService;


	@Test
	public void calculateFuelCost_ShouldReturnCorrectTotalAmount() {
		String  originName = "Tatooine";
		String  destinationName = "Alderaan";

		TradeRoute route = new TradeRoute(originName, destinationName, 87.43);
		double  distance = route.getDistance();
		double  basePrice = route.getDistance() * 12.35;

		when(tradeRouteRepository.findByOriginAndDestination(originName, destinationName))
				.thenReturn(Optional.of(route));

		when(planetService.findByName("Tatooine")).thenReturn(Optional.of(new Planet("TAT", "Tatooine", "1A")));
		when(planetService.findByName("Alderaan")).thenReturn(Optional.of(new Planet("ALD", "Alderaan", "1A")));

		RebelInfluence originInfluence = new RebelInfluence("TAT", 32);  // 32% influence
		RebelInfluence destinationInfluence = new RebelInfluence("ALD", 20);  // 20% influence
		when(rebelInfluenceService.findByCode("TAT")).thenReturn(Optional.of(originInfluence));
		when(rebelInfluenceService.findByCode("ALD")).thenReturn(Optional.of(destinationInfluence));

		FuelCostDTO result = fuelCostService.calculateFuelCost(originName, destinationName);

		System.out.println("Base Price: " + basePrice);
		System.out.println("Origin Defense Cost: " + result.getTaxes().getOriginDefenseCost());
		System.out.println("Destination Defense Cost: " + result.getTaxes().getDestinationDefenseCost());
		System.out.println("Elite Defense Cost: " + result.getTaxes().getEliteDefenseCost());
		System.out.println("Total Amount: " + result.getTotalAmount());

		assertNotNull(result);
		assertEquals(1770.8, result.getTotalAmount(), 0.01);
		assertEquals(12.35, result.getPricesPerLunarDay(), 0.01);
		assertEquals(345.52, result.getTaxes().getOriginDefenseCost(), 0.01);
		assertEquals(215.95, result.getTaxes().getDestinationDefenseCost(), 0.01);
		assertEquals(129.57, result.getTaxes().getEliteDefenseCost(), 0.01);
	}

	@Test
	public void calculateFuelCost_ShouldThrowExceptionIfTradeRouteNotFound() {
		when(tradeRouteRepository.findByOriginAndDestination(anyString(), anyString()))
				.thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> fuelCostService.calculateFuelCost("NonExistentOrigin", "NonExistentDestination"));
		assertEquals("Trade route not found", exception.getMessage());
	}

	@Test
	public void calculateFuelCost_ShouldThrowExceptionIfRebelInfluenceNotFound() {
		TradeRoute route = new TradeRoute("Tatooine", "Alderaan", 87.43);
		when(tradeRouteRepository.findByOriginAndDestination("Tatooine", "Alderaan"))
				.thenReturn(Optional.of(route));

		when(planetService.findByName("Tatooine"))
				.thenReturn(Optional.of(new Planet("TAT", "Tatooine", "1A")));
		when(planetService.findByName("Alderaan"))
				.thenReturn(Optional.of(new Planet("ALD", "Alderaan", "1A")));

		when(rebelInfluenceService.findByCode("TAT"))
				.thenReturn(Optional.of(new RebelInfluence("TAT", 32)));
		when(rebelInfluenceService.findByCode("ALD"))
				.thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> fuelCostService.calculateFuelCost("Tatooine", "Alderaan"));
		assertEquals("Destination planet rebel influence not found", exception.getMessage());
	}
}

