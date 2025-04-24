package com.empire.traderoutes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxesDTO {
	private double originDefenseCost;
	private double destinationDefenseCost;
	private double eliteDefenseCost;
}
