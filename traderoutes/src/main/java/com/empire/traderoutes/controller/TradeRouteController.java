package com.empire.traderoutes.controller;

import com.empire.traderoutes.dto.FuelCostDTO;
import com.empire.traderoutes.dto.TradeRouteDTO;
import com.empire.traderoutes.model.TradeRoute;
import com.empire.traderoutes.service.FuelCostService;
import com.empire.traderoutes.service.TradeRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trade-routes")
@RequiredArgsConstructor
public class TradeRouteController {
	private final TradeRouteService tradeRouteService;
	private final FuelCostService   fuelCostService;

	@GetMapping("/route")
	public ResponseEntity<FuelCostDTO> getFuelCost(@RequestParam String fromPlanet, @RequestParam String toPlanet) {
		FuelCostDTO fuelCostDTO = fuelCostService.calculateFuelCost(fromPlanet, toPlanet);
		return ResponseEntity.ok(fuelCostDTO);
	}

	@GetMapping
	public List<TradeRoute> getAllRoutes(){
		return tradeRouteService.getAllRoutes();
	}
}
