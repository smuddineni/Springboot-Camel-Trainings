package com.example.demo.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class CamelCBRRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("file://source/requests/cbr/?noop=true").routeId("CBRRoute").autoStartup(false)
		.log("Message body:${body}")
		/*.filter(new Predicate() {
			@Override
			public boolean matches(Exchange exchange) {
				if(exchange.getIn().getHeader("CamelFileName").toString().endsWith("xml"))
					return true;
				else{
					System.out.println("Ignoring the file"+exchange.getIn().getHeader("CamelFileName"));
					return false;
				}
			}
		})*/
		.choice()
			.when(simple("${body} contains 'Postpaid'"))
				.to("direct:processPostPaidOrders")
			.otherwise()
				.to("direct:processPrePaidOrders")
				.end()
				.log("Process End")
		.end();
		
		
		from("direct:processPrePaidOrders")
		.log("Received a Prepaid Order")
		.end();
		
		from("direct:processPostPaidOrders")
		.log("Received a Postpaid Order")
		//invoke endpoint
		.delay(15000)
		.end();
		
	}

}
