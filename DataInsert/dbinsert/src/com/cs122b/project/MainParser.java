package com.cs122b.project;


//use this to update movies table and genres_in_movies and genres

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * <movies>
 *     <!-- Movie Year: 1922 - Early British Movies by Alfred Hitchcock. -->
 *     <directorfilms>
 *         <director>
 *             <dirid>H</dirid>
 *             <dirstart>@1922</dirstart>
 *             <dirname>Hitchcock</dirname>============>director name(backup)
 *             <coverage>all early British</coverage>
 *         </director>
 *         <films>
 *             <film>
 *                 <fid>H1</fid>==============>movie ID (<filmed>also acceptable)
 *                 <t>Always Tell Your Wife</t>===========>movie Title
 *                 <year>1922</year>==========>year(fix space)
 *                 <dirs>
 *                     <dir>
 *                         <dirk>R</dirk>
 *                         <dirn>Se.Hicks</dirn>============>director name
 *                     </dir>
 *                     <dir>
 *                         <dirk>R</dirk>
 *                         <dirn>Hitchcock</dirn>
 *                     </dir>
 *                 </dirs>
 *                 <prods>
 *                     <prod>
 *                         <prodk>R</prodk>
 *                         <pname>Lasky</pname>
 *                     </prod>
 *                 </prods>
 *                 <studios>
 *                     <studio>Famous</studio>
 *                 </studios>
 *                 <prcs>
 *                     <prc>sbw</prc>
 *                 </prcs>
 *                 <cats>
 *                     <cat>Dram</cat>===============>genres
 *                 </cats>
 *                 <awards></awards>
 *                 <loc></loc>
 *                 <notes/>
 *             </film>
 *             <film>
 *
 */
public class MainParser extends DefaultHandler {
    Logger logger = Logger.getLogger("MainParser");
    FileHandler fh;
    private ArrayList<Movie> result;
    Movie curMovie;
    String tmpVal;
    String dirbackup="";

    public ArrayList<Movie> getResult(){
        return result;
    }
    MainParser(){

        result=new ArrayList<>();

    }

    public void parse(){
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("MainParser.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("mains243.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        tmpVal="";
        if(qName.equalsIgnoreCase("film")){

            curMovie=new Movie();

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equalsIgnoreCase("dirname")){
            dirbackup=tmpVal;
        }
        if(qName.equalsIgnoreCase("film")){
            if(curMovie!=null){
                //check if everything is added
                if(curMovie.getId()==null){
                    logger.warning("MovieID Missing, drop this data");
                    return;
                }
                if(curMovie.getDirector()==null){
                    if(dirbackup!=""){
                        curMovie.setDirector(dirbackup);
                    }else {
                        curMovie.setDirector("Unknown");
                        logger.warning("MovieID " + curMovie.getId() + " has no director!");
                    }
                }
                if(curMovie.getTitle()==null){
                    curMovie.setTitle("Unknown");
                    logger.warning("MovieID "+curMovie.getId()+" has no title!");
                }
                if(curMovie.getYear()==null){
                    curMovie.setYear(0);
                    logger.warning("MovieID "+curMovie.getId()+" has invalied year data!");
                }
                if(curMovie.genres.size()==0) {
                  // logger.info("MovieID "+curMovie.getId()+" has no genre!");
                }

                result.add(curMovie);
            }else{
                logger.warning("Bad format exception--missing <film>");
            }


        }else if(qName.equalsIgnoreCase("filmed")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <filmed>");
            }else{
                if(tmpVal!=""){
                    logger.warning("Use <filmed> tag as movie ID "+tmpVal);
                    curMovie.setId(tmpVal);
                }
            }
        }else if(qName.equalsIgnoreCase("fid")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <fid>");
            }else{
                if(tmpVal!=""){
                    curMovie.setId(tmpVal);
                }
            }
        }else if(qName.equalsIgnoreCase("year")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <year>");
            }else{
                if(tmpVal!=""){
                    try {
                        curMovie.setYear(Integer.valueOf(tmpVal.trim()));//Remove space
                    }catch (Exception e){
                       if(!curMovie.getId().isEmpty()){
                           logger.warning("MovieID "+curMovie.getId()+" has invalied year format "+tmpVal+"!");
                           curMovie.setYear(0);

                       }
                    }
                }
            }
        }else if(qName.equalsIgnoreCase("t")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <t>");
            }else{
                if(tmpVal!=""){
                    curMovie.setTitle(tmpVal);
                }
            }
        }else if(qName.equalsIgnoreCase("dirn")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <dirn>");
            }else{
                if(tmpVal!=""){
                    curMovie.setDirector(tmpVal);//only last director will be stored
                }
            }
        }else if(qName.equalsIgnoreCase("cat")){
            if(curMovie==null){
                logger.warning("Bad format exception--missing <film> for <cat>");
            }else{
                if(tmpVal!=""){
                    curMovie.genres.add(tmpVal);//only last director will be stored
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        tmpVal = new String(ch, start, length);
    }
}
