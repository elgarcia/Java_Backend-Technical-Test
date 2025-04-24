package com.empire.traderoutes.controller;

import com.empire.traderoutes.dto.TaxesDTO;
import com.empire.traderoutes.service.FuelCostService;
import com.empire.traderoutes.dto.FuelCostDTO;
import com.empire.traderoutes.service.TradeRouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeRouteController.class)
public class TradeRouteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FuelCostService fuelCostService;

	@MockBean
	private TradeRouteService tradeRouteService;

	@Test
	public void getFuelCost_ShouldReturnFuelCostDTO() throws Exception {
		FuelCostDTO mockFuelCostDTO = new FuelCostDTO();
		mockFuelCostDTO.setTotalAmount(1770.8);
		mockFuelCostDTO.setPricesPerLunarDay(12.35);

		TaxesDTO taxes = new TaxesDTO();
		taxes.setOriginDefenseCost(345.52);
		taxes.setDestinationDefenseCost(215.95);
		taxes.setEliteDefenseCost(129.57);
		mockFuelCostDTO.setTaxes(taxes);

		when(fuelCostService.calculateFuelCost(anyString(), anyString())).thenReturn(mockFuelCostDTO);

		MvcResult result =  mockMvc.perform(get("/api/trade-routes/route?fromPlanet=planetA&toPlanet=planetB"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalAmount").value(1770.8))
				.andExpect(jsonPath("$.pricesPerLunarDay").value(12.35))
				.andExpect(jsonPath("$.taxes.originDefenseCost").value(345.52))
				.andExpect(jsonPath("$.taxes.destinationDefenseCost").value(215.95))
				.andExpect(jsonPath("$.taxes.eliteDefenseCost").value(129.57))
				.andReturn();

		System.out.println("Response Body: " + result.getResponse().getContentAsString());
	}
}
