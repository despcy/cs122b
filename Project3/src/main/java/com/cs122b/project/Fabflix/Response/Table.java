package com.cs122b.project.Fabflix.Response;

import java.util.ArrayList;

public class Table {
    private String name;
    private ArrayList<Attr> attr;

    public ArrayList<Attr> getAttr() {
        return attr;
    }

    public void setAttr(ArrayList<Attr> attr) {
        this.attr = attr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
