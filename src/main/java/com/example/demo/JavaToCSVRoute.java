package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class JavaToCSVRoute extends SpringRouteBuilder{
	
	
	
	@Override
	public void configure() throws Exception {
		from("timer://example?period=6000").autoStartup(false)
		.log("Timer triggered:${body}")
		.process(e->{
			
			List<Map<String,String>> records=new ArrayList<Map<String,String>>(); 
			HashMap<String,String> map=new HashMap();
			map.put("empname", "emp1");
			map.put("address", "banglore");
			records.add(map);
			map=new HashMap();
			map.put("empname", "emp2");
			map.put("address", "hyderabad");
			records.add(map);
			map=new HashMap();
			map.put("empname", "emp3");
			map.put("address", "pune");
			records.add(map);
			e.getIn().setBody(records);
			
		})
		
		.log("Message before marshall:${body}")
	    .marshal("marshallToCSV")
		.log("Message after marshall:${body}")
		.end();
		
		
	}

}
