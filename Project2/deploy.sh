#bin/bash

sh ~/tomcat/bin/catalina.sh stop

rm ~/tomcat/webapps/ROOT.war

rm -rf ~/tomcat/webapps/ROOT

./mvnw clean install

mv target/Fabflix-0.0.1-SNAPSHOT.war ~/tomcat/webapp/ROOT.war

sh ~/tomcat/bin/catalina.sh start

