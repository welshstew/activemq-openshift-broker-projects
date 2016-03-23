package org.swinchester.groovy

import org.apache.camel.builder.RouteBuilder

/**
 * Created by swinchester on 23/03/2016.
 */
class SimpleAMQRouteBuilder extends RouteBuilder {
    def queueUri = 'jms:queue:camel.test'

    public void configure() {

        String fileStuff = "hello world"
        from("timer://foo?period=5000").setBody(constant(fileStuff))
                .setHeader("TrackingID", constant("helloThere")).setHeader("CreationTime", constant("2016-02-31T07:05:29.881+05:30"))
                .to(queueUri)
                .to("log:SENT")

        from(queueUri).log("log:GOT")
    }
}