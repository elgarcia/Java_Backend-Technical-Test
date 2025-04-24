package com.empire.traderoutes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelCostDTO {
	private double totalAmount;
	private double pricesPerLunarDay;
	private TaxesDTO taxes;
}