package com.cs122b.project.Fabflix.Service;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class DBQueryExec {
    public ResultSet executeQuery(PreparedStatement stmt) throws Exception{
        return stmt.executeQuery();

    }
}
