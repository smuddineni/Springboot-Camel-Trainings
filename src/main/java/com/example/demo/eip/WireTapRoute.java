package com.example.demo.eip;

import javax.xml.ws.http.HTTPException;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class WireTapRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		
		onException(NullPointerException.class,HTTPException.class)
		.log("we have a nullpointer error").useOriginalMessage()
		.to("direct:technicalErrors")
		.end();
		
		
		onException(HTTPException.class)
		.log("we have a ArrayIndexOutOfBoundsException error").useOriginalMessage()
		.to("file://notifiers/?fileName=error.txt&allowNullBody=true")
		.continued(true)
		.end();
		
		
		from("direct:technicalErrors")
		.log("error received:${exception.message}").end();
		
		
		
		from("timer://wireTapDemo?fixedRate=true&period=60000")
		.routeId("WireTapDemo").autoStartup(true)
		.log("Timer Started, Quering DB")
		.setBody(constant("select * from <Database_table>"))
		.log("Query:${body}")
		.throwException(new HTTPException(404))
		.log("im invoked after exception........")
		.log("im invoked after exception........")
		
		.log("im invoked after exception........")
		.end();

}
	
}
