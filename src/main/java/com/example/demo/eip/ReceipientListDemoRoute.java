package com.example.demo.eip;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ReceipientListDemoRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
        from("file://source/requests/?fileName=receipientList1.txt&noop=true").routeId("ReceipientListDemoRoute").autoStartup(false)
        .process(new Processor() {
            public void process(Exchange exchange) throws Exception {
            	System.out.println("receipientList");
                String recipients = "direct:hr";
                String employeeAction = exchange.getIn().getBody(String.class);
                 if (employeeAction.equals("new")) {
                    recipients += ",direct:account,direct:manager";
                 } else if (employeeAction.equals("resigns")) {
                     recipients += ",direct:account";
                 }
                 exchange.getIn().setHeader("departments", recipients);
               }
          })
        .log("recipientlist:${header.departments}")
          .recipientList(header("departments"));
        
        
        from("direct:account")
        .log("Account department notified '${body}'");
        
        from("direct:hr")
        .log("HR department notified '${body}'");
        
        from("direct:manager")
        .log("Manager notified '${body}'");
    }

		

}
