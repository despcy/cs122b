package com.cs122b.service;

import com.cs122b.entity.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class DBService {

    @Value("${database.dbname}")
    private String dbName;
    @Value("${database.username}")
    private String userName;
    @Value("${database.password}")
    private String password;

    private Connection connection;
    public DBService(){


        // Connect to the test database
        try {
            // Incorporate mySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql:///" + dbName + "?autoReconnect=true&useSSL=false",
                    userName, password);

        }catch (Exception e){
            System.out.println("jdbc:mysql:///" + dbName + "?autoReconnect=true&useSSL=false  "+userName +" "+password);
            System.out.println(e.toString());

        }

        if (connection != null) {
            System.out.println("Connection established!!");
            System.out.println();
        }
    }


    public Movie getMovieByID(){

        return new Movie();
    }


}
