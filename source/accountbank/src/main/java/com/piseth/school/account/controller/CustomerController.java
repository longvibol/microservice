package com.piseth.school.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.school.account.dto.CardResponseDTO;
import com.piseth.school.account.dto.CustomerDTO;
import com.piseth.school.account.dto.CustomerDetailDTO;
import com.piseth.school.account.dto.LoanResponseDTO;
import com.piseth.school.account.entity.Customer;
import com.piseth.school.account.mapper.CustomerMapper;
import com.piseth.school.account.service.CustomerService;
import com.piseth.school.account.service.client.CardFeignClient;
import com.piseth.school.account.service.client.LoanFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private CardFeignClient cardFeignClient;

	@Autowired
	private LoanFeignClient loanFeignClient;

	@PostMapping
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO dto) {

		Customer customer = customerMapper.toCustomer(dto);

		customer = customerService.save(customer);
		return ResponseEntity.ok(customer);
	}

	@GetMapping
	public ResponseEntity<?> getCustomers() {

		return ResponseEntity.ok(customerService.getCustomers());

	}

	@GetMapping("{customerId}")
	public ResponseEntity<?> getCustomers(@PathVariable Long customerId) {

		return ResponseEntity.ok(customerService.getById(customerId));

	}

	//@CircuitBreaker(name = "customerDetailSuport", fallbackMethod = "getCustomerDetailDefault")
	//@Retry(name = "retryCustomerDetail", fallbackMethod = "getCustomerDetailDefault")
	@GetMapping("customerDetail/{myCustomerId}")
	public ResponseEntity<CustomerDetailDTO> getCustomerDetail(
			@RequestHeader("vibolbank-correlation-id") String correlationId,
			@PathVariable("myCustomerId") Long customerId) {
		
		log.debug("Correlation ID Found In ACCOUNT= {}",correlationId);

		CustomerDetailDTO dto = new CustomerDetailDTO();
		Customer customer = customerService.getById(customerId);

		if (customer == null) {
			throw new RuntimeException("No customer found with this id");
		}

//		we want to convert from customer to customerDTO 	

		CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
		List<LoanResponseDTO> loanInfo = loanFeignClient.getLoanInfo(correlationId,customerId);
		List<CardResponseDTO> cardInfo = cardFeignClient.getCardInfo(correlationId,customerId);

		/*
		 * Below where we want to show : private CustomerDTO customer; private
		 * List<CardResponseDTO> cards; private List<LoanResponseDTO> loans;
		 * 
		 */

		dto.setCustomer(customerDTO);
		dto.setLoans(loanInfo);
		dto.setCards(cardInfo);

//		dto : is the CustomerDetail we want to set of output

		return ResponseEntity.ok(dto);

	}

	public ResponseEntity<CustomerDetailDTO> getCustomerDetailDefault(@PathVariable("myCustomerId") Long customerId,
			Throwable e) {

		CustomerDetailDTO dto = new CustomerDetailDTO();
		Customer customer = customerService.getById(customerId);

		if (customer == null) {
			throw new RuntimeException("No customer found with this id");
		}

		CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
		dto.setCustomer(customerDTO);
		
		System.out.println("Expection show = " +e);
		return ResponseEntity.ok(dto);

	}
	
	@GetMapping("/sayHello")
	@RateLimiter(name = "sayHelloLimiter", fallbackMethod = "sayHi")
	public String sayHello() {
		return "Hello, welcome to PisethBank";
	}
	
	public String sayHi(Throwable t) {
		return "Call back HI, welcome to PisethBank";
	}

}
