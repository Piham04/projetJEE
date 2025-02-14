package com.example.gymspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gymspring.model.Pack;
import java.util.List;
import java.util.Optional;


public interface PackRepository extends JpaRepository<Pack, Long> {
	Optional<Pack> findByOfferName(String offerName);
	List<Pack> findByDurationMonths(int durationMonths);
	List<Pack> findByMonthlyPrice(double monthlyPrice);
	List<Pack> findByActiveTrue();
	List <Pack> findByMonthlyPriceBetween(double minPrice, double maxPrice);
}
