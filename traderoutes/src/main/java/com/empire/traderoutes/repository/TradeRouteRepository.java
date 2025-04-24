package com.empire.traderoutes.repository;

import com.empire.traderoutes.model.TradeRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRouteRepository extends JpaRepository<TradeRoute, Long>{
	Optional<TradeRoute> findByOriginAndDestination(String origin, String destination);
}
