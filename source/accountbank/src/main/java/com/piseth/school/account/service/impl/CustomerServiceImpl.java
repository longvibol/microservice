package com.piseth.school.account.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.piseth.school.account.entity.Customer;
import com.piseth.school.account.repository.CustomerRepository;
import com.piseth.school.account.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {

		return customerRepository.save(customer);

	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getById(Long id) {
		return customerRepository.findById(id).
				orElseThrow(()-> new RuntimeException("Customer not found"));
	}

}
