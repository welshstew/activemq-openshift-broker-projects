package org.swinchester.groovy

import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Value

/**
 * Created by swinchester on 23/03/2016.
 */
class SimpleAMQRouteBuilder extends RouteBuilder {

    @Value("AMQ_CLIENT_TYPE")
    private String producerOrConsumer;

    @Value("AMQ_CONSUMER_PRODUCER_START_URI")
    private String startURI;

    @Value("AMQ_CONSUMER_PRODUCER_END_URI")
    private String endURI;

    public void configure() {

        if(producerOrConsumer == 'producer'){
            from(startURI)
                .setBody('''{ "hello": "world" }''')
                .setHeader("hello", "world")
                .log("Sending message...")
                .to(endURI).log("Sent!");
        }

        if(producerOrConsumer == 'consumer'){
            from(startURI).log('''Got message: ${body}''')
        }
    }
}