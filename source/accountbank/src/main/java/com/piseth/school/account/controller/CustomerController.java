package com.piseth.school.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.school.account.dto.CustomerDTO;
import com.piseth.school.account.entity.Customer;
import com.piseth.school.account.mapper.CustomerMapper;
import com.piseth.school.account.service.CustomerService;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;

	@PostMapping
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO dto) {

		Customer customer = customerMapper.toCustomer(dto);

		customer = customerService.save(customer);
		return ResponseEntity.ok(customer);
	}
	
	@GetMapping
	public ResponseEntity<?> getCustomers(){
		
		return ResponseEntity.ok(customerService.getCustomers());
		
	}
	
	@GetMapping("{customerId}")
	public ResponseEntity<?> getCustomers(@PathVariable Long customerId){
		
		return ResponseEntity.ok(customerService.getById(customerId));
		
	}
	

}
