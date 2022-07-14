package com.example.demo.eip;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.beans.DynamicRouterBean;

@Component
public class CamelDynamicRoute extends RouteBuilder{


		@Override
		public void configure() throws Exception {

			from("timer://wireTapDemo?fixedRate=true&period=5000").routeId("CamelDynamicRoute").autoStartup(false)
			.dynamicRouter(method(DynamicRouterBean.class, "route1"))
			.log("End of DynamciRoute");

			
			from("seda:writeDatabaseOutput")
			.to("file://output/?fileName=dynamicRouteOutput_${date:now:MMdd}.txt");
			
			from("direct:invokeDB").process(new Processor() {
				public void process(Exchange exchange) {
					exchange.getIn().setBody("Im from DB at:"+simple("${date:now:MMddyyyy:HH.mm.ss}").evaluate(exchange, String.class));
				}
			}).to("seda:writeDatabaseOutput");

			from("direct:updateDataBaseQuery").process(new Processor() {
				public void process(Exchange exchange) {
					String body = exchange.getIn().getBody().toString();
					body = simple(body).evaluate(exchange, String.class);
					System.out.println("updated query:"+body);
					exchange.getIn().setBody(body);
				}
			});
			
			from("direct:CreateDataBaseQuery").process(new Processor() {
				public void process(Exchange exchange) {
					exchange.getIn().setBody("select * from <DBTableName> where date > ${date:now:MMddyyyy}");
					System.out.println(exchange.getIn().getBody());
				}
			});
		}
		
}


		
	
