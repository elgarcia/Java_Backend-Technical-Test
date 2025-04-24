package com.empire.traderoutes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Planet {
	@Id
	private String code;

	private String name;
	private String sector;
}
