package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import com.example.demo.beans.Employee;


@Component
public class JaxBSampleRoute extends RouteBuilder{


	/*
	 * JAXBContext con = JAXBContext.newInstance(Employee.class);
	 * xmlDataFormat.setContext(con);
	 */

	// JSON Data Format
	JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
	

	@Override
	public void configure() throws Exception {
		from("file://input_dir/jaxbxml/").unmarshal("unmarshallTopojo").
		process(e->
				{
					Employee employee = e.getIn().getBody(Employee.class);
					employee.setEmpName("updated name");
					e.getIn().setBody(employee);
				}
		)
		.marshal(jsonDataFormat)
		.log("updated exchange body:${body}")
		.end();
		
	}
}