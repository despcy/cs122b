package com.cs122b.project;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    //call and handle output exception

    //ActorParser(genreate starID) then CastParser

    //MainParser
    public static void main(String[] args) {
	// write your code here
        // Connect to the test database
        Connection connection;
        try {
            // Incorporate mySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
             connection = DriverManager.getConnection("jdbc:mysql:///moviedb?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT",
                    "mytestuser", "mypassword");
            if (connection != null) {
                System.out.println("Connection established!!");
                System.out.println();
            }




        MainParser mainp=new MainParser();
        mainp.parse();
        HashMap<String,Movie> movMap=mainp.getResult();
            //=======filter and handle movie
            HashSet<String> midSet=getAllMovieID(connection);
            ArrayList<Movie> movies=new ArrayList<>();
            for(String key:movMap.keySet()){
                if(midSet.contains(key))continue;
                movies.add(movMap.get(key));


            }
            //========
            System.out.println("Adding movies table");
            batchAddMovie(connection,movies);
        //batch add genre
            System.out.println("Updating genre..");
        batchAddgenres(connection,mainp.getGenreMovMap());

            ActorParser actorp=new ActorParser();
            actorp.parse();
            ArrayList<Star> stars=actorp.getResult();

            System.out.println("Adding stars");
            batchInsertStar(connection,stars);


        CastParser castp=new CastParser();
        castp.parse();
        HashMap<String, ArrayList<String>> movieStarMap=castp.getResult();
        HashMap<String, ArrayList<String>> filteredMovieStarMap=new HashMap<>();
        //remove illegal data(foreign key)
            for(String mid:movieStarMap.keySet()){
                if(!movMap.containsKey(mid)){
                    //remove movie

                    continue;
                }else{

                    ArrayList<String> starnames=filteredMovieStarMap.getOrDefault(mid,new ArrayList<>());;
                    for(String name: movieStarMap.get(mid)){
                        if(actorp.starname.contains(name)){
                            starnames.add(name);
                        }
                    }
                    filteredMovieStarMap.put(mid,starnames);
                }
            }
        //batch add stars_in_movies
            System.out.println("adding star_in_movies");
        batchInsertStarInMovies(connection,filteredMovieStarMap);





        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();

        }
    }

    public static void batchInsertStarInMovies(Connection connection,HashMap<String, ArrayList<String>> movieStarMap){
        String sqlInsertRecord="insert into stars_in_movies (starId, movieId) values(?,?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement psInsertRecord=connection.prepareStatement(sqlInsertRecord);


            for(String mid:movieStarMap.keySet())
            {
                ArrayList<String> snames=movieStarMap.get(mid);
                for(String sname:snames){
                    psInsertRecord.setString(1,nameToId(sname));
                    psInsertRecord.setString(2,mid);
//                    System.out.println(sname);
//                    System.out.println(mid);
//                    System.out.println("---");
                    psInsertRecord.addBatch();
                }


            }

            int[] iNoRows=psInsertRecord.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void batchInsertStar(Connection connection,ArrayList<Star> stars){
        String sqlInsertRecord="insert into stars (id,name,birthYear) values(?,?,?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement psInsertRecord=connection.prepareStatement(sqlInsertRecord);


            for(Star s:stars)
            {

                    psInsertRecord.setString(1,nameToId(s.getName()));
                    psInsertRecord.setString(2,s.getName());
                    psInsertRecord.setInt(3,s.getBirthYear());
                    psInsertRecord.addBatch();



            }

            int[] iNoRows=psInsertRecord.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void batchAddMovie(Connection connection,ArrayList<Movie> movies){
        String sqlInsertRecord="insert into movies (id,title,year,director) values(?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement psInsertRecord=connection.prepareStatement(sqlInsertRecord);


            for(Movie m:movies)
            {

                psInsertRecord.setString(1,m.getId());
                psInsertRecord.setString(2,m.getTitle());
                psInsertRecord.setInt(3,m.getYear());
                psInsertRecord.setString(4,m.getDirector());
                psInsertRecord.addBatch();



            }

            int[] iNoRows=psInsertRecord.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static HashSet<String> getAllMovieID(Connection connection){
        HashSet<String> result=new HashSet<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select id from movies");
            while(rs.next()){
                result.add(rs.getString("id"));
            }
        }catch (Exception e){

        }
        return result;
    }

    public static void batchAddgenres(Connection connection,HashMap<String,ArrayList<String>> genreMovMap){
        //insert genres table
        HashMap<String,Integer> genreDB=getGenreInDB(connection);
        String sqlInsertRecord="insert into genres (name) values(?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement psInsertRecord=connection.prepareStatement(sqlInsertRecord);


            for(String gen:genreMovMap.keySet())
            {

                if(genreDB.containsKey(gen))continue;
                psInsertRecord.setString(1,gen);

                psInsertRecord.addBatch();



            }

            int[] iNoRows=psInsertRecord.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        genreDB=getGenreInDB(connection);
        //insert genresinmovies

        sqlInsertRecord="insert into genres_in_movies (genreId,movieId) values(?,?)";
        try {
            connection.setAutoCommit(false);

            PreparedStatement psInsertRecord=connection.prepareStatement(sqlInsertRecord);


            for(String gen:genreMovMap.keySet())
            {

                ArrayList<String> mids=genreMovMap.get(gen);
                for(String mid : mids){
                    psInsertRecord.setInt(1,genreDB.get(gen));
                    psInsertRecord.setString(2,mid);
                    psInsertRecord.addBatch();
                }





            }

            int[] iNoRows=psInsertRecord.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  static HashMap<String,Integer> getGenreInDB(Connection connection){
        HashMap<String,Integer> result=new HashMap<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from genres");
            while(rs.next()){
                result.put(rs.getString("name"),rs.getInt("id"));
            }
        }catch (Exception e){

        }
        return result;

    }

    public static String nameToId(String starname){
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(starname.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.substring(0,8)+"0";
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
