package com.empire.traderoutes.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeRoute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    id;

	private String  origin;

	private String  destination;

	private Double  distance; //in lunar days

	public TradeRoute(String origin, String destination, Double distance) {
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
	}
}
