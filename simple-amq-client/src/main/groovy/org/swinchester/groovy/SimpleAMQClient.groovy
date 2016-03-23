//package org.swinchester.groovy
//
//import org.apache.activemq.ActiveMQSslConnectionFactory
//import org.apache.camel.component.jms.JmsComponent
//
///**
// * Created by swinchester on 23/03/2016.
// */
//class SimpleAMQClient {
//
//
//    def static setupCamel() {
//        //def brokerUrl = 'ssl://my-broker-symphony-eu-amq.ose.cdk.vm:443'
//
//        def brokerUrl = 'ssl://localhost:61616'
//
//        def camelCtx = new org.apache.camel.impl.DefaultCamelContext()
//        camelCtx.registry = new org.apache.camel.impl.SimpleRegistry()
//
//        def conFac = new ActiveMQSslConnectionFactory(brokerURL: brokerUrl,
//                                                        userName: 'admin',
//                                                        password: 'admin',
//                                                        trustStore: this.class.getResource('/client.ts').getPath(),
//                                                        trustStorePassword: 'password')
//
//        def jms = new JmsComponent()
//        jms.setConnectionFactory(conFac)
//        jms.setUseMessageIDAsCorrelationID(true)
//        camelCtx.registry.registry.put("jms", jms)
//        camelCtx.addRoutes(new SimpleAMQRouteBuilder())
//        camelCtx.start()
//        // Stop Camel when the JVM is shut down
//        Runtime.runtime.addShutdownHook({ ->
//            camelCtx.stop()
//        })
//        synchronized(this){ this.wait() }
//    }
//
//    static void main(String [] args){
//        setupCamel()
//    }
//}
//
//
//
//
//
//
//
//
