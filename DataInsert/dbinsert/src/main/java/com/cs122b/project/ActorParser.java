package com.cs122b.project;

//use this to update stars table

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 <actor>
 <stagename>Bud Abbott</stagename>==========>name
 <dowstart>1939</dowstart>
 <dowend>1956</dowend>
 <familyname> Abbott</familyname>
 <firstname>William</firstname>
 <gender>M</gender>
 <dob>1895</dob>  =========>birthYear
 <dod>1974</dod>
 <roletype>straight comedian</roletype>
 <origin>\Am</origin>
 <studio>Universal</studio>
 <relationships>
 <relship>
 <reltype>Ww</reltype>
 <towhom>
 <relname>Lou Costello</relname>
 </towhom>
 </relship>
 </relationships>
 <notes/>
 </actor>
**/
public class ActorParser extends DefaultHandler {
    Logger logger = Logger.getLogger("ActorParser");
    FileHandler fh;
    private ArrayList<Star> result;
    public  HashSet<String> starname=new HashSet<>();
    Star curStar;
    String tmpVal;

    ActorParser(){

        result=new ArrayList<>();
    }


    public void parse(){
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("ActorParser.log");
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
            sp.parse("actors63.xml", this);

            fh.close();

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
        if(qName.equalsIgnoreCase("actor")){

            curStar=new Star();

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);


        if(qName.equalsIgnoreCase("dob")){
            if(tmpVal.trim()==""){
                curStar.setBirthYear(-1);
                return;
            }
            try{
                curStar.setBirthYear(Integer.valueOf(tmpVal.trim()));
            }catch (Exception e){
                logger.warning("bad format for star "+curStar.getName()+"  birthyear "+tmpVal);
                curStar.setBirthYear(-1);
            }

        }else if(qName.equalsIgnoreCase("stagename")){
            curStar.setName(tmpVal.trim());
        }else if(qName.equalsIgnoreCase("actor")){
            if(curStar==null){
                //Drop
                logger.warning("missing tag <star>");
                return;
            }

            if(curStar.getName()==null||curStar.getName()==""){
                //Drop
                logger.warning("missing star name...Drop");
                return;
            }
            if(curStar.birthYear==null){
                logger.warning("missing birth year for "+curStar.getName());
                curStar.setBirthYear(-1);
                return;
            }

           // System.out.println(curStar.getName());
            if(!starname.contains(curStar.getName())){
                result.add(curStar);
                starname.add(curStar.getName());
            }

//            if(starname.contains(curStar.name)){
//                System.out.println(curStar.name);
//            }
//            starname.add(curStar.getName());
//            System.out.println(result.size());
//            System.out.println(starname.size());
//            System.out.println("---------");

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        tmpVal = new String(ch, start, length);
    }

    public ArrayList<Star> getResult() {
        return result;
    }
}
