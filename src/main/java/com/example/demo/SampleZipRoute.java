package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

@org.springframework.stereotype.Component
public class SampleZipRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {


		from("timer://zip?period=30000").autoStartup(false)
		//.log("Recieved file:${file:name}")
		//.log("file headers:${headers}")
				 .setHeader(Exchange.FILE_NAME, constant("report.txt"))
				    .setBody(constant("goodmorning"))
				
		.marshal().zipFile()
		//.log("no of file:${exchangeProperty.CamelBatchSize}")
		.log("Recieved file:${file:name}")
		.log("file headers:${headers}")
		.to("file://output_dir/zip")
		.end();
		
	}
	
	

}
