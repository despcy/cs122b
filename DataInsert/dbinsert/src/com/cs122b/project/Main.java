package com.cs122b.project;

import java.util.HashMap;

public class Main {

    //call and handle output exception

    //ActorParser(genreate starID) then CastParser

    //MainParser
    public static void main(String[] args) {
	// write your code here
        MainParser mainp=new MainParser();
        mainp.parse();
        HashMap<String,Movie> movMap=mainp.getResult();
        CastParser castp=new CastParser();
        castp.parse();
        Array
        ActorParser actorp=new ActorParser();
        actorp.parse();


    }
}
