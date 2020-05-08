package com.cs122b.project;


//used to update star_in_movies

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

	<dirfilms>
		<dirid>AA</dirid>
		<is>Asquith</is>
		<castnote>Actors and notes.</castnote>
		<filmc>
			<m>
				<f>AA13</f>===========>movie ID
				<t>Pygmalion</t>
				<a>Leslie Howard</a>==============>star name
				<p>Sci</p>
				<r>smug professor</r>
				<n>Higgins</n>
				<awards>
					<award>AAN</award>
				</awards>
			</m>
			<m>
				<f>AA13</f>
				<t>Pygmalion</t>
				<a>Wendy Hiller</a>
				<p>Inn</p>
				<r>flower girl</r>
				<n>Eliza</n>
				<awards>
					<award>AAN</award>
				</awards>
			</m>


 **/
public class CastParser extends DefaultHandler {
	Logger logger = Logger.getLogger("CastParser");
	FileHandler fh;
	private ArrayList<StarInMovie> result;
	StarInMovie curSIM;
	String tmpVal;

	CastParser() {

		result = new ArrayList<>();
	}
	public ArrayList<StarInMovie> getResult(){
		return result;
	}

	public void parse() {
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// This block configure the logger with handler and formatter
			fh = new FileHandler("CastParser.log");
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
			sp.parse("casts124.xml", this);

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
		if(qName.equalsIgnoreCase("m")){

			curSIM=new StarInMovie();

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if(qName.equalsIgnoreCase("f")){
			curSIM.setMovieID(tmpVal);
		}else if(qName.equalsIgnoreCase("a")){
			curSIM.setStarName(tmpVal);
		}else if(qName.equalsIgnoreCase("m")){
			if(curSIM==null){
				//Drop
				logger.warning("missing tag <m>");
				return;
			}
			if(curSIM.getMovieID()==null){
				//Drop
				logger.warning("missing movie id..Drop");
				return;
			}
			if(curSIM.getStarName()==null){
				//Drop
				logger.warning("missing star name...Drop");
				return;
			}

			result.add(curSIM);

		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		tmpVal = new String(ch, start, length);
	}

}