package com.example.demo.beans;

import org.apache.camel.Exchange;

public class InvoiceFilter {

	
	  public boolean filter(Exchange exchange) {
	        String invoice = (String) exchange.getIn().getBody();
	        return invoice.contains("<isPrepaid>false</isPrepaid>");
	        
	    }
	
	
}
