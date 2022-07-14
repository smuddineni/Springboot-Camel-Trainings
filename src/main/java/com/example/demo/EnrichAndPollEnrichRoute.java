package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EnrichAndPollEnrichRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		
		from("timer://enrich?period=10000").routeId("enrich").autoStartup(false).setBody(constant("goodmornign"))
				.enrich("file://enrichfolder/?fileName=enrich.txt")

				.end();

		from("timer://pollenrich?period=10000").routeId("enrich1").autoStartup(false).log("beofre pollenrich::${body}")
				.pollEnrich("file://enrichfolder/?fileName=enrich.txt", 10000).log("after pollenrich::${body}").end();
	}
}
