package com.example.gymspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gymspring.model.Customer;
import java.util.List;
import java.time.LocalDate;



public interface CustomerRepository extends JpaRepository<Customer, Long> {

//Trouver un client par son numéro de téléphone
	Optional <Customer> findByPhone(String phone);
//Trouver les clients ayant un abonnement actif
	List<Customer> findByActiveTrue();
//Trouver les clients après une certaine date
	List<Customer> findByRegistrationDateAfter(LocalDate registrationDate);

//Trouver un client par son nom ou son prénom
	List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);

//Recherche avancée avec plusieurs critères
	@Query("SELECT c FROM Customer c WHERE "+
		"(:lastName IS NULL OR c.lastName LIKE %:lastName%) AND"+
		"(:firstName IS NULL OR c.firstName LIKE %:firstName%) AND"+
		"(:phone IS NULL OR c.phone = :phone) AND"+
		"(:registrationDate IS NULL OR c.registrationDate = :registrationDate) AND"+
		"(:active IS NULL OR c.active = :active)")
	List<Customer> searchCustomers(
		@Param("lastName") String lastName,
		@Param("firstName") String firstName,
		@Param("phone") String phone,
		@Param("registrationDate") LocalDate registrationDate,
		@Param("active") Boolean active
	);
}
