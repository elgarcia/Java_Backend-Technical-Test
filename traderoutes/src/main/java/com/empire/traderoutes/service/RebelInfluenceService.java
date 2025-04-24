package com.empire.traderoutes.service;

import com.empire.traderoutes.dto.RebelInfluenceDTO;
import com.empire.traderoutes.model.RebelInfluence;
import com.empire.traderoutes.repository.RebelInfluenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebelInfluenceService {
	private final RebelInfluenceRepository rebelInfluenceRepository;

	@Transactional
	public void saveAll(List<RebelInfluence> rebelInfluences) {
		rebelInfluenceRepository.saveAll(rebelInfluences);
	}

	public Optional<RebelInfluence> findByCode(String code) {
		return rebelInfluenceRepository.findByCode(code);
	}

	@Transactional
	public void updateRebelInfluence(String code, int newInfluence) {
		rebelInfluenceRepository.findByCode(code).ifPresentOrElse(
				influence -> {
					influence.setRebelInfluence(newInfluence);
					rebelInfluenceRepository.save(influence);
				},
				() -> rebelInfluenceRepository.save(new RebelInfluence(code, newInfluence))
		);
	}
}
