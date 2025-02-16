package com.example.gymspring.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import com.example.gymspring.model.Pack;
import com.example.gymspring.service.PackService;

@RestController
@RequestMapping("/api/packs")
@CrossOrigin(origins = "*")
public class PackController {
	private final PackService packService;
	public PackController(PackService packService) {
		this.packService = packService;
	}

	//Ajouter une offre
	@PostMapping
	public ResponseEntity<Pack> addPack(@Validated @RequestBody Pack pack) {
		return ResponseEntity.ok(packService.addPack(pack));
	}
	//Obtenir la liste des offres
	@GetMapping
	public ResponseEntity<List<Pack>> getAllPacks() {
		return ResponseEntity.ok(packService.getAllPacks());
	}
	//Obtenir une offre par son ID
	@GetMapping("/{id}")
	public ResponseEntity<Pack> getPackById(@PathVariable Long id) {
		return packService.getPackById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	//Obtenir uniquement les offres actives
	@GetMapping("/active")
	public ResponseEntity<List<Pack>> getActivePacks() {
		return ResponseEntity.ok(packService.getActivePacks());
	}
	//Mettre à jour une offre
	@PutMapping("/{id}")
	public ResponseEntity<Pack> updatePack(@PathVariable Long id, @Validated @RequestBody Pack pack) {
		return ResponseEntity.ok(packService.updatePack(id, pack));
	}
	//Supprimer une offre
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePack(@PathVariable Long id) {
		try {
			packService.deletePack(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	//Désactiver une offre
	@PutMapping("/{id}/disable")
	public ResponseEntity<String> disablePack(@PathVariable Long id) {
		packService.disablePack(id);
		return ResponseEntity.ok("Pack désactivée avec succès");
	}

}
