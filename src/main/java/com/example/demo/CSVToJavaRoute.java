package com.example.demo;

import java.util.List;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class CSVToJavaRoute extends RouteBuilder{
	
	
	
	@Override
	public void configure() throws Exception {
		from("file://input_dir/csv/").routeId("csvtojava").autoStartup(false)
		.log("Message before marshall:${body}")
		//.unmarshal("unmarshallToCSV")
	    .unmarshal()
	    .csv()
				/*
				 * .process(e->{ List<Map<String,String>> csvcontent=(List<Map<String, String>>)
				 * e.getIn().getBody(); System.out.println("csvcontent"+csvcontent); })
				 */
		.log("Message after marshall:${body}")
		.end();
		
	}

}
