package com.example.gymspring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gymspring.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	//Recupérer les abonnements actifs (de clients avec abonnements actifs)
	@Query("SELECT s FROM Subscription s WHERE s.customer.active = true")
	List<Subscription> findByCustomerActiveTrue();

	//Trouver les abonnements par client
	List<Subscription> findByCustomerId(Long customerId);

	//Trouver les abonnements par pack(offre)
	List<Subscription> findByPackId(Long packId);

	//Trouver les abonnements à partir d'une certaine date
	List<Subscription> findByStartDateAfter(LocalDate startDate);
}