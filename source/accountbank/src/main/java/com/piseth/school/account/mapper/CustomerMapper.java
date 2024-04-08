package com.piseth.school.account.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.piseth.school.account.dto.CustomerDTO;
import com.piseth.school.account.entity.Customer;

@Component
public class CustomerMapper {

	public Customer toCustomer(CustomerDTO dto) {

		Customer customer = new Customer();
		customer.setName(dto.getName());
		customer.setEmail(dto.getEmail());
		customer.setMobileNumber(dto.getMobileNumber());
		customer.setCreateDate(LocalDate.parse(dto.getCreateDate()));

		return customer;
	}
	
	
	//convert from customer to CustomerDTO
	public CustomerDTO toCustomerDTO(Customer entity) {

		CustomerDTO dto = new CustomerDTO();
		dto.setName(dto.getName());
		dto.setEmail(dto.getEmail());
		dto.setMobileNumber(dto.getMobileNumber());
		dto.setCreateDate(entity.getCreateDate() !=null ? entity.getCreateDate().toString():"");

		return dto;
	}

}
