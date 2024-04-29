package com.piseth.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.card.dto.CardDTO;
import com.piseth.card.entity.Card;
import com.piseth.card.mapper.CardMapper;
import com.piseth.card.service.CardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/cards")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardMapper cardMapper;
	
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody CardDTO dto){
		
		Card card = cardService.save(cardMapper.toCard(dto));
		return ResponseEntity.status(HttpStatus.CREATED).body(card);
		
	}
	
	@GetMapping
	public ResponseEntity<?> list(){
		return ResponseEntity.ok(cardService.getList());
	}
	
	@GetMapping("{customerId}")
	public ResponseEntity<?> getByCustomerId(			
			@RequestHeader("vibolbank-correlation-id") String correlationId,
			@PathVariable("myCustomerId") Long customerId)
	{
		log.debug("Correlation ID Found In CARD= {}",correlationId);
		return ResponseEntity.ok(cardService.getByCustomerId(customerId));
	}
	
	

}
