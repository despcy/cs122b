#bin/bash

cd Project2

./mvnw clean install

mv target/Fabflix-0.0.1-SNAPSHOT.war ~/tomcat/webapp/Fabflix2.war

