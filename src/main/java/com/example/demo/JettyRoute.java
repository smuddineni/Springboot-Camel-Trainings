package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class JettyRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		
		System.out.println("Im from jetty route..starting jetty endpoint");
		
		onException(NullPointerException.class)
		.log("we have a nullpointer error").useOriginalMessage()
		.to("amq:queue:errorNotifier")
		.end();
		
		
		onException(ArrayIndexOutOfBoundsException.class)
		.log("we have a ArrayIndexOutOfBoundsException error").useOriginalMessage()
		.to("file://notifiers/?fileName=error.txt")
		.end();
		
		from("amq:queue:initialAMQ").id("JettyEndpoint").autoStartup(true)
		.log("Message:${body}")
		.setBody(constant("Hello IPM....GoodMorning"))
		.end();
		
	}

}
