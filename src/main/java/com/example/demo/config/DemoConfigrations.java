package com.example.demo.config;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.beans.Employee;

@Configuration
public class DemoConfigrations {
	
	
	@Bean(name="marshallToCSV")
	 public CsvDataFormat csvFormat() {
	 ArrayList<String> header=new ArrayList();
	 header.add("empname");
	 header.add("address");
	 CsvDataFormat csv=new CsvDataFormat(); 
	 csv.setHeader(header);
	 csv.setDelimiter("|");
	 csv.setHeaderDisabled("false"); 
	 csv.setSkipHeaderRecord("false");
	return csv;
	 }
	
	
	@Bean(name="unmarshallToCSV")
	 public CsvDataFormat uncsvFormat() {
	CsvDataFormat unmarshall=new  CsvDataFormat();
	unmarshall.setDelimiter(",");
	unmarshall.setUseMaps("true");
	
	return unmarshall;
	}
	
	
	
	@Bean(name="unmarshallTopojo")
	 public JaxbDataFormat unmarshallxml() throws JAXBException {
	JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
	jaxbDataFormat.setContext(JAXBContext.newInstance(Employee.class));
	return jaxbDataFormat;
	}

}
