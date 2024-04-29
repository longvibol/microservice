package com.piseth.bank.gatewayserver.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {	
public static final String CORRELATION_ID = "vibolbank-correlation-id";
	
//	generate correlation id and add to request 
	
	// get from header បើវាមាន header វាមកយើងនឹងចាប់ header vir 
	public String getCorrelationId(HttpHeaders requestHeader) {
		if(requestHeader.get(CORRELATION_ID) != null) {
			List<String> requestHeaderList = requestHeader.get(CORRELATION_ID);
			return requestHeaderList.stream().findFirst().get();
		}
		return null;
	}
	
	
	// We set the Request header (បើសិនគេបោះមកអត់មាន Header customer នឹងទេយើងនឹង បង្កើតអោយវា​
	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate()
				.request(exchange.getRequest().mutate().header(name, value).build()).build();
	}
	
	
//	យើងហៅ set the Request header មកប្រើអោយវាដាក់​ header 
	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}

}
