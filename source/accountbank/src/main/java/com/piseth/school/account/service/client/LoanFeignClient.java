package com.piseth.school.account.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.piseth.school.account.dto.LoanResponseDTO;

@FeignClient(name = "loan")
//@FeignClient(name = "loan", url = "http://localhost:8090")
public interface LoanFeignClient {

//	create card : GET: localhost:8070/api/loans/1 = we need to create the same responde we get 
	
	@GetMapping("/api/loans/{customerId}")	
	List<LoanResponseDTO> getLoanInfo(
			@RequestHeader("pisethbank-correlation-id") String correlationId,
			@PathVariable Long customerId);
	
	// we create it base on loan controller find by customerID
}
