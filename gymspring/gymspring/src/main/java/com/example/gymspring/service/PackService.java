package com.example.gymspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import com.example.gymspring.model.Pack;
import com.example.gymspring.repository.PackRepository;

@Service
@Transactional
public class PackService {
	private final PackRepository packRepository;
	public PackService(PackRepository packRepository) {
		this.packRepository = packRepository;
	}

	//Ajouter une offre
	public Pack addPack(Pack pack) {
		return packRepository.save(pack);
	}
	//Lister toutes les offres
	public List<Pack> getAllPacks() {
		return packRepository.findAll();
	}
	//Trouver une offre par son ID
	public Optional<Pack> getPackById(Long id) {
		return packRepository.findById(id);
	}
	//Obtenir uniquement les offres actives
	public List<Pack> getActivePacks() {
		return packRepository.findByActiveTrue();
	}
	//Mettre à jour une offre
	public Pack updatePack(Long id, Pack pack) {
		return packRepository.findById(id)
				.map(existingPack -> {
					if(pack.getOfferName() != null){
						existingPack.setOfferName(pack.getOfferName());
					}
					if(pack.getDurationMonths() > 0){
						existingPack.setDurationMonths(pack.getDurationMonths());
					}
					if(pack.getMonthlyPrice() > 0) {
						existingPack.setMonthlyPrice(pack.getMonthlyPrice());
					}
					if(pack.getDescription() != null){
						existingPack.setDescription(pack.getDescription());
					}
					return packRepository.save(existingPack);
				})
				.orElseThrow(() -> new EntityNotFoundException("Pack non trouvée avec l'ID : " + id));
	}
	//Supprimer une offre
	public void deletePack(Long id) {
		if (!packRepository.existsById(id)) {
			throw new EntityNotFoundException("Pack non trouvée avec l'ID : " + id);
		}
		packRepository.deleteById(id);
	}

	//Désactiver une offre
	public void disablePack(Long id) {
		Pack pack = packRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Pack non trouvée avec l'ID : " + id));
		pack.setActive(false);
		packRepository.save(pack);
	}
}
