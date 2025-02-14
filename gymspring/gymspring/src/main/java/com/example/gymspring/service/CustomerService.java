package com.example.gymspring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.example.gymspring.model.Customer;
import com.example.gymspring.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
//Ajouter un client
	public Customer addCustomer(Customer customer) {
		customer.setRegistrationDate(LocalDate.now());
		return customerRepository.save(customer);
	}
//Lister les clients
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
//Consulter un client
	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}
//Trouver un client par son numéro de téléphone
	public Optional<Customer> getCustomerByPhone(String phone) {
		return customerRepository.findByPhone(phone);
	}
//Trouver les clients ayant un abonnement actif
	public List<Customer> getCustomersWithActiveSubscription() {
		return customerRepository.findByActiveTrue();
	}
//Trouver les clients après une certaine date
	public List<Customer> getCustomersRegisteredAfter(LocalDate registrationDate) {
		return customerRepository.findByRegistrationDateAfter(registrationDate);
	}
//Trouver un client par son nom ou son prénom
	public List<Customer> getCustomerByLastnameOrFirstname(String lastName, String firstName) {
		return customerRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(lastName, firstName);
	}
//Recherche avancée avec plusieurs critères
	public List<Customer> searchCustomers(String lastName, String firstName, String phone, LocalDate registrationDate, Boolean active) {
		return customerRepository.searchCustomers(lastName, firstName, phone, registrationDate ,active);
	}
//Mettre à jour un client
	public Customer updateCustomer(Long id, Customer customer){
		return customerRepository.findById(id)
		.map(existingCustomer -> {
			existingCustomer.setFirstName(customer.getFirstName());
			existingCustomer.setLastName(customer.getLastName());
			existingCustomer.setPhone(customer.getPhone());
			existingCustomer.setActive(customer.isActive());
			return customerRepository.save(existingCustomer);
		})
		.orElseThrow(() -> new EntityNotFoundException("Client non trouvé avec l'ID " + id));

	}
//Supprimer un client
	public void deleteCustomer(Long id) {
		if (!customerRepository.existsById(id)) {
			throw new EntityNotFoundException("Client non trouvé avec l'ID " + id);
		}
		customerRepository.deleteById(id);
	}
}