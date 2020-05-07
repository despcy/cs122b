package com.cs122b.project.Fabflix.Response;

import java.util.ArrayList;

public class DashData {
    private String admin;
    private ArrayList<Table> tables;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
