package com.novardis.education.routes;
import com.novardis.education.configuration.TimeProperties;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrdersRoutes extends RouteBuilder {
    private final TimeProperties timeProperties;

    @Override
    public void configure() throws Exception {
        String topic = "orders";
        String broker = "localhost:9092";
        String path = "orders/create";

        getCamelContext().getGlobalOptions().put("CamelJacksonEnableTypeConverter", "true");
        getContext().getGlobalOptions().put("CamelJacksonTypeConverterToPojo", "true");

        onException(HttpOperationFailedException.class)
                .handled(true)
                .log("Order delivery failed");

        from("kafka:" + topic + "?brokers=" + broker)
                .transacted()
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .bean(RouteService.class, "addToDatabase")
                .to("http://localhost:8080/" + path)
                .bean(RouteService.class, "orderDelivered");

        from("timer://databaseTimer?period=" + timeProperties.getDatabaseTimerPeriod().toString())
                .setBody(constant("select * from orders where next_attempt <= '"))
                .bean(RouteService.class, "addCurrentTime")
                .to("jdbc:dataSource")
                .split(body()).streaming()
                .bean(RouteService.class, "getOrderDto")
                .to("kafka:" + topic + "?brokers=" + broker);
    }
}
