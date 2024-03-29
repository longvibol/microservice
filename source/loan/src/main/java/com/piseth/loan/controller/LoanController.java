package com.piseth.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.loan.dto.LoanDTO;
import com.piseth.loan.entity.Loan;
import com.piseth.loan.mapper.LoanMapper;
import com.piseth.loan.service.LoanService;

@RestController
@RequestMapping("api/loans")
public class LoanController {
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private LoanMapper loanMapper;
	
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody LoanDTO dto){
		
		Loan loan = loanService.save(loanMapper.toLoan(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(loan);
		
	}
	
	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(loanService.getList());
	}

}
