package com.example.demo.eip;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ContentEnrichRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("file://source/requests/contentenrich/?noop=true").autoStartup(false)
		.setProperty("Id",simple("${body}"))
		.log("ID:${exchangeProperty.Id}")
		.pollEnrich().simple("file://response/?fileName=text-${exchangeProperty.Id}.xml")
		.timeout(45564)
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("new content:"+exchange.getIn().getBody());
			}
		})
		.choice()
		.when(body().isNull())
			.log("file not found")
		.otherwise()
		.log("Enriched content:${body}")
		.end();
	}
	
	
	

}
