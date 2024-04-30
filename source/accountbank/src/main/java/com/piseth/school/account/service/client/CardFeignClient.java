package com.piseth.school.account.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.piseth.school.account.dto.CardResponseDTO;

@FeignClient(name="card")
public interface CardFeignClient {

//	create card : GET: localhost:8070/api/cards/1 = we need to create the same responde we get 
	@GetMapping("/api/cards/{customerId}")
	
	List<CardResponseDTO> getCardInfo(
			
			@RequestHeader("pisethbank-correlation-id") String correlationId,
			@PathVariable("myCustomerId") Long customerId);
	
	// we create it base on card controller find by customerID  
	
	// It will auto connect to card service 

	
}
