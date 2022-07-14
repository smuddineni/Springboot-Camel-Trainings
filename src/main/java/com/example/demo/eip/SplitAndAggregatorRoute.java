package com.example.demo.eip;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.SimpleBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.beans.InvoiceFilter;
@Component
public class SplitAndAggregatorRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("file://source/requests/?fileName=SampleInvoice.xml&noop=true").routeId("SplitAndAggregator").autoStartup(true)
		.log("primary exchangeid:${exchangeId}")
		.log("Message body:${body}")
		.split().tokenizeXML("Invoice")
		.parallelProcessing()
		 .filter()
		  .method(new InvoiceFilter(), "filter")
		.log("exchangeid:${exchangeId}")
		.log("Index:${exchangeProperty.CamelSplitIndex}")
		.log("size:${exchangeProperty.CamelSplitSize}")
		.log("is complete:${exchangeProperty.CamelSplitComplete}")
		.log("Index body:${body}")
		//.to("")
		.aggregate(constant("123123"),new AggregationStrategy() {
			@Override
			public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
				System.out.println("newExchange::"+newExchange.getExchangeId());
				if (oldExchange == null) {
					System.out.println("This prints only once");
					newExchange.getIn().setHeader("total", simple(xpath("/Invoice/ItemsNet/text()").evaluate(newExchange, String.class)).getText());
					return newExchange;
				} else {			
					System.out.println("oldExchange ::"+oldExchange.getExchangeId());
					Message oldIn = oldExchange.getIn();
					Message newIn = newExchange.getIn();
					System.out.println("Previous total::"+oldExchange.getIn().getHeader("total"));
					oldIn.setHeader("total", getTotalCost(oldExchange,newExchange));
					System.out.println("New Total:"+oldIn.getHeader("total"));
					return oldExchange;
				}
			}
		}).eagerCheckCompletion()
		/**/
		.completionPredicate(simple("${exchangeProperty.CamelSplitComplete}"))
		//.completionTimeout(50000)
		//.completionSize(50)
		.log("Split Complete?? : ${exchangeProperty.CamelSplitComplete}")
		.log("SPLIT and Aggreation completed : ${body}")
		.end();
		

}
	
			private Integer getTotalCost(Exchange oldExchange, Exchange newExchange){
		
		SimpleBuilder ne1=simple(xpath("/Invoice/ItemsNet/text()").evaluate(newExchange, String.class));
		oldExchange.getIn().setBody(oldExchange.getIn().getBody().toString().concat(newExchange.getIn().getBody().toString()));
		System.out.println("previous total:"+oldExchange.getIn().getHeader("total"));
		 
			return Integer.parseInt(oldExchange.getIn().getHeader("total").toString())+Integer.parseInt(ne1.getText());
		
	
}

}