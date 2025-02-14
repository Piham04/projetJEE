package com.example.gymspring.controller;

import com.example.gymspring.model.Subscription;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import com.example.gymspring.service.SubscriptionService;

@RestController
@RequestMapping("api/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {
	private final SubscriptionService subscriptionService;

	public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	//Ajouter un abonnement
	@PostMapping
	public ResponseEntity<?> addSubscription(@Validated @RequestBody Subscription subscription) {
		try {
			return ResponseEntity.ok(subscriptionService.addSubscription(subscription));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//Recupérer tous les abonnements
	@GetMapping
	public ResponseEntity<List<Subscription>> getSubscriptions() {
		return ResponseEntity.ok(subscriptionService.getSubscriptions());
	}

	//Recupérer un abonnement par id
	@GetMapping("/{id}")
	public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	//Récupérer les abonnements par client
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Subscription>> getSubscriptionsByCustomerId(@PathVariable Long customerId) {
		return ResponseEntity.ok(subscriptionService.getSubscriptionsByCustomerId(customerId));
	}
	//Récupérer les abonnements par pack
	@GetMapping("/pack/{packId}")
	public ResponseEntity<List<Subscription>> getSubscriptionsByPackId(@PathVariable Long packId) {
		return ResponseEntity.ok(subscriptionService.getSubscriptionsByPackId(packId));
	}
	//Supprimer un abonnement
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubscription(@PathVariable Long id) {
		try {
			subscriptionService.deleteSubscription(id);
			return ResponseEntity.ok("Abonnement supprimé avec succès");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).body(e.getMessage());
		}
	}
}
