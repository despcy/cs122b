#!bin/bash

mvn clean install

wget http://infolab.stanford.edu/pub/movies/mains243.xml

wget http://infolab.stanford.edu/pub/movies/actors63.xml

wget http://infolab.stanford.edu/pub/movies/casts124.xml


mvn exec:java -Dexec.mainClass="com.cs122b.project.Main"


