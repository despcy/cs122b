# cs122b-spring20-team-60
cs122b-spring20-team-60 by Jingwen Mo & Chenxi Yang

## 1. Video
Here is our video link for our project1:


youtube: https://www.youtube.com/watch?v=dSH04PNTM_Q or 
UCI: https://uci.yuja.com/V/Video?v=978719&node=3879060&a=707441302&autoplay=1

## 2. Instruction of deployment

### Preparation:
1. Clone this repository using ``git clone https://github.com/UCI-Chenli-teaching/cs122b-spring20-team-60.git ``
2. Create users, database and tables on MySQL.
Go to content cs122b-spring20-team-60/project1/sql script/
Firstly, run setup.sql , which will create the user for you.
Then, run create_table.sql , which create the database and table for you.
Fianlly, run movie-data.sql , which will insert the data for you.

### Compile & Run with Maven:

```shell
cd project1
./mvnw clean install
```

Then you will get the .war file in target folder.


## 3. Contribution
In project 1, Jingwen Mo is in charge of the basic frontend and the spring mvc(Controller, Model, View) part in the backend and also communication between frontend and backend. While Chenxi Yang is responsible for the Servelet part, using JDBC to talk to the MySQL database and also took effort to make the UI look nicer, which is really nice btw.
