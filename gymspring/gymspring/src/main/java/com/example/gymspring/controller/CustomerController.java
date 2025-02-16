package com.example.gymspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import com.example.gymspring.model.Customer;
import com.example.gymspring.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
//Ajouter un client
@PostMapping
public ResponseEntity<Customer> addCustomer(@Validated @RequestBody Customer customer) {
	return ResponseEntity.ok(customerService.addCustomer(customer));
}
//Obtenir la liste des clients
@GetMapping
public ResponseEntity<List<Customer>> getAllCustomers() {
	return ResponseEntity.ok(customerService.getAllCustomers());
}

//Obtenir un client par son id
@GetMapping("/{id}")
public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
	return customerService.getCustomerById(id)
		.map(ResponseEntity::ok)
		.orElse(ResponseEntity.notFound().build());
}
//Obtenir les clients ayant un abonnement actif
@GetMapping("/active")
public ResponseEntity<List<Customer>> getCustomersWithActiveSubscription() {
	return ResponseEntity.ok(customerService.getCustomersWithActiveSubscription());
}

//Mise à jour d'un client
@PutMapping("/{id}")
public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer) {
	try {
		return ResponseEntity.ok(customerService.updateCustomer(id, customer));
	} catch (EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}
}

//Archiver un client
@PutMapping("/{id}/archive")
public ResponseEntity<String> archiveCustomer(@PathVariable Long id) {
    try {
        Customer customer = customerService.getCustomerById(id)
									.orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        customer.setActive(false);  // Désactiver le client
        customerService.updateCustomer(id, customer);
        return ResponseEntity.ok("Client archivé avec succès");
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}

//Supprimer un client
/*@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
	try {
		customerService.deleteCustomer(id);
		return ResponseEntity.ok("Client supprimé avec succès");
	} catch (EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}
}*/
	
}
