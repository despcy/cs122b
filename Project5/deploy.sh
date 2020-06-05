#bin/bash

sh ~/tomcat/bin/catalina.sh stop

rm ~/tomcat/webapps/ROOT.war

rm -rf ~/tomcat/webapps/ROOT

mvn install

mv target/Fabflix-0.0.1-SNAPSHOT.war ~/tomcat/webapps/ROOT.war

sh ~/tomcat/bin/catalina.sh start

