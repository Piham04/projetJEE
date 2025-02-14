package com.example.gymspring.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.example.gymspring.model.Subscription;
import com.example.gymspring.repository.SubscriptionRepository;

@Service
@Transactional
public class SubscriptionService {
	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionService(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	//Ajouter un abonnement
	public Subscription addSubscription(Subscription subscription) {
		if(subscription.getStartDate().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("La date de début doit être aujourd'hui ou dans le futur");
		}
		return subscriptionRepository.save(subscription);
	}

	//Recupérer tous les abonnements
	public List<Subscription> getSubscriptions() {
		return subscriptionRepository.findAll();
	}

	//Recupérer un abonnement par id
	public Subscription getSubscriptionById(Long id) {
		return subscriptionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'id " + id));
	}

	//Récupérer les abonnements par client
	public List<Subscription> getSubscriptionsByCustomerId(Long customerId) {
		List<Subscription> subscriptions = subscriptionRepository.findByCustomerId(customerId);
		return subscriptions.isEmpty() ? List.of() : subscriptions;
	}

	//Récupérer les abonnements par pack
	public List<Subscription> getSubscriptionsByPackId(Long packId) {
		return subscriptionRepository.findByPackId(packId);
	}

	//Supprimer un abonnement
	public void deleteSubscription(Long id) {
		if (!subscriptionRepository.existsById(id)) {
			throw new EntityNotFoundException("Abonnement non trouvé avec l'id " + id);
		}
		subscriptionRepository.deleteById(id);
	}
}
