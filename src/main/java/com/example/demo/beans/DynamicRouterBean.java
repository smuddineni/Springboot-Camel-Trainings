package com.example.demo.beans;
import org.apache.camel.Exchange;
import org.apache.camel.Header;

public class DynamicRouterBean {
	
	
			public String route1(Exchange exchange,String body, @Header(Exchange.SLIP_ENDPOINT) String previousRoute) {
				if (previousRoute == null) {
					
					System.out.println("steop 1111");
					return "direct://CreateDataBaseQuery";
					// check the body content and decide route
				} else if (body.toString().equals("select * from <DBTableName> where date > ${date:now:MMddyyyy}")) {
					System.out.println("applying simple expression on query:"+body);
			        return "direct:updateDataBaseQuery";
					// check the body content and decide route
				
				} else if(body.endsWith("2019")){
					System.out.println("constructed query:"+body);
					return "direct:invokeDB";
				}
				else{
				return null;
				}
			}
		}