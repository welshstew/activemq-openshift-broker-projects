# activemq-broker-projects aka setting up activemq on openshift

There's a whole bunch of pre-reqs - including getting the CDK up and running and 
working with landrush for DNS resolution from your host to your vagrant CDK box. 

## Creating the keystore stuff

See the [activemq page](http://activemq.apache.org/how-do-i-use-ssl.html) for this...

    keytool -genkey -alias broker -keyalg RSA -keystore broker.ks
    keytool -export -alias broker -keystore broker.ks -file broker_cert
    keytool -genkey -alias client -keyalg RSA -keystore client.ks
    keytool -import -alias broker -keystore client.ts -file broker_cert

## Deploying the Broker Service Openshift

Please note that the keystore is embedded here, it shouldn't be - but this is just a POC to prove stuff.
Please see the client stuff below to explain the secrets

    #ensure docker variables etc are in your ENV
    oc login -u admin -p 
    oc new-project activemq-broker-ns
    export KUBERNETES_NAMESPACE=activemq-broker-ns
    
    #for the simple-spring-amq project...
    #note that the keystores are actually embedded in the project
    mvn -Pf8-local-deploy
    #delete the default rc created with the project
    oc delete rc/simple-spring-amq
    #create the rc for the broker
    oc create -f src/kube/simple-spring-amq-rc.yaml
    #confirm the new pod is running... oc get pods etc.
    #create the service to expose the amq pod
    oc create -f src/kube/simple-spring-amq-svc.yaml

## Creating the secrets for the client (on OSE)

To be executed at the simple-amq-client project level:
   
   oc secrets new client-truststore src/main/resources/client.ts
   
## Creating the client application (on OSE)

Note that the fabric8.env properties in the pom.xml will populate the environment, however,
we will need to use the simple-amq-client.yaml so the app has access to the truststore secret
To be executed at the simple-amq-client project level:

    oc create -f src/kube/simple-amq-client.yaml
    #confirm the logs and see that the camel is sending and receiving messages
    oc get pods
    oc logs -f pods/${newpodname}
    

## Creating a route and checking that the AMQ is accessible externally

    #remove the rc for the internal client...
    oc delete rc/simple-amq-client
    #create a route on openshift to expose AMQ over ssl   
    oc create -f ../simple-spring-amq/src/kube/simple-spring-amq-route.yaml
    #now the route is created you can confirm by hitting url https://simple-spring-amq-activemq-broker-ns.ose.cdk.vm/
    
If you can access the URL as above, then you should be able to run the simple-amq-client 
application with that as the broker URL, i.e. change the client.properties in the simple-amq-client.

    BROKER_URL=ssl://simple-spring-amq-activemq-broker-ns.ose.cdk.vm:443
    BROKER_USERNAME=admin
    BROKER_PASSWORD=admin
    BROKER_TRUSTSTORE_FILE=/Users/swinchester/sourcetree/activemq-broker-projects/simple-amq-client/src/main/resources/client.ts
    BROKER_TRUSTSTORE_PASSWORD=password

Run the project locally with 

    mvn camel:run
    
and you should be able to confirm that you are connected from the app to your pod running in your openshift CDK.

    
    

     
   
    

    
    
    
    
    
    
    
    
    
    
    
    
    
