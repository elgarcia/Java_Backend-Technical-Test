package com.empire.traderoutes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelInfluence {
	@Id
	@Column(unique = true, nullable = false)
	private String  code;

	private int     rebelInfluence;
}
