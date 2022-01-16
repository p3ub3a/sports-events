# sports-events
### **Quarkus vs Springboot performance analysis:** 

#### Overview:

* the project aims to make a performance comparison between Quarkus and Springboot. The application used for testing has features similar to those found in real life projects; 
* one frontend implementation (*./sportsevents-ui*) which can connect to either Quarkus or Springboot backend;
* two backend implementations (Quarkus: *./events-service*, Springboot: *./events-service-spring*) should have the same behaviour;
* before running the applications, these 5 services should be started: *sportsevents-quarkus-db*, *sportsevents-spring-db*, *keycloak-db*, ,*keycloak*(user federation framework), *monitoring* (Prometheus service);
* all HTTP requests are authorized by Keycloak;
* *./grafana-dashboards* has Grafana json schemas used to monitor the JVM metric & HTTP request metrics provided by Prometheus;
* *./new-data* has files that are imported by the running backend server (the application updates its *Event* table with the information found in the file);
* each project has a *dependency-check-maven* plugin added in *pom.xml* (a OWasp plugin used to generate a library vulnerability report). Run *mvn test org.owasp:dependency-check-maven:check* to generate the report.

#### Application functionalities:

* the platform supports different users/roles (eg. admin, facilitator, player). There are 4 types of events (chess, pingpong, swimming & tennis);
* for each event there is one facilitator who will conduct a particular sports activity. A facilitator can close an event and designate the winner;
* players can join an event or leave an existing event that they are participating in;
* players can view future events, past events and their number of wins;
* a leaderboard can be seen by all users;
* players and facilitators can create complaints related to one Event;
* admins can create events, delete events and handle complaints;
* each day, a chron job runs and updates the event records (*Event* table) with the data found in *./new-data/*.

*work in progress* 
