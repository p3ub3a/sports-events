
## **Sports-events k8s environment** 

#### Folder structure:
* **/deployments** contains the following deployments: Quarkus JVM, Quarkus native & Spring Boot and their databases;
* **/persistentvolume** has the volumes needed for Keycloak database;
* **/ping** has 3 curling pods for each application needed to test the startup time & scalability;
* **/service** contains each application's service;
* **/storage** has config-maps for environment info and secrets for database credentials.

#### terminate-pods.sh:
Is a script that tests the startup time and scalability of each application, it takes the following arguments:
* $1: the name of the application, can be: *quarkus-jvm*, *quarkus-native*, *springboot*;
* $2: the number of seconds between pod deletion;
* $3: the number of pod delete iterations.

Script steps:
* check if arguments are valid;
* start the ping application of the specified instance ($1);
* iterate $3 times, at the end of each iteration sleep $2 seconds;
* update *ping-result.log* with the result of the execution;
* create *ping-$1.log* with the logs from the ping pod.