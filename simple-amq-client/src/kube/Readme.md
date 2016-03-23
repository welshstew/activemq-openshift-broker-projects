oc secrets new client-truststore src/main/resources/client.ts

outside of openshift we need to use the router address:

    BROKER_URL=ssl://my-broker-${NAMESPACE}.ose.cdk.vm:443
    
inside openshift (in the namespace) we need to use the service name:

    BROKER_URL=ssl://my-broker:61616
    
