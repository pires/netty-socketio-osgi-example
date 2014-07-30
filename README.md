Netty Socket.IO OSGi example
======================

Tested with Fabric8 1.1.0.CR1 and Java 7.

## Build

```
mvn clean package
```

## Service provisioning (fabric8-karaf)

### Create Fabric8 ensemble

```
fabric:create --clean --wait-for-provisioning
```


### Create profile

```
profile-create --parents default socketioapp

profile-edit --repositories mvn:com.github.pires/feature-hazelcast/0.1-SNAPSHOT/xml/features socketioapp
profile-edit --repositories mvn:com.github.pires/feature-jackson/0.1-SNAPSHOT/xml/features socketioapp
profile-edit --repositories mvn:com.github.pires/feature-socketio/0.1-SNAPSHOT/xml/features socketioapp

profile-edit --features hazelcast socketioapp
profile-edit --features jackson socketioapp
profile-edit --features netty-socketio socketioapp

profile-edit --bundles mvn:com.github.pires/netty-socketio-app/0.1-SNAPSHOT socketioapp
profile-edit --bundles mvn:org.objenesis/objenesis/2.1 socketioapp
profile-edit --bundles wrap:mvn:com.esotericsoftware.minlog/minlog/1.2 socketioapp
profile-edit --bundles mvn:com.esotericsoftware.kryo/kryo/2.24.0 socketioapp
```

### Deploy container

```
container-create-child --profile socketioapp root c1
```

### Done

Check you're listening in http://localhost:8080
