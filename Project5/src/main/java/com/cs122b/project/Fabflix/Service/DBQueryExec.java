package com.cs122b.project.Fabflix.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
@Scope("singleton")
public class DBQueryExec {
    private Connection connection;
    public DBQueryExec(@Value("${searchthreadPoolEnabled}") Boolean enabled,@Value("${database.dbname}") String dbName,@Value("${database.username}") String userName,@Value("${database.password}") String password){

        if(enabled==true)return;
        // Connect to the test database
        try {
            // Incorporate mySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql:///" + dbName + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&autoReconnect=true&useSSL=false",
                    userName, password);

        }catch (Exception e){
           e.printStackTrace();

        }

        if (connection != null) {
            System.out.println("Connection established!!");
            System.out.println();
        }
    }


    public ResultSet executeQuery(PreparedStatement stmt) throws Exception{

        return stmt.executeQuery();

    }

    //Connection conn=dataSource.getConnection();
    public Connection getConnection(DataSource datasource) throws Exception{


        return datasource.getConnection();
    }
    //PreparedStatement stmt=conn.prepareStatement(querystr) ;
    public PreparedStatement prepareStatement(Connection conn,String query) throws Exception{

        if(connection!=null)return connection.prepareStatement(query);
        return conn.prepareStatement(query);
    }

}
