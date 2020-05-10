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
import java.util.HashMap;
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
	private HashMap<String,ArrayList<String>> result;
	String tmpVal;
	String curstar;
	String curmov;

	CastParser() {

		result = new HashMap<>();
	}
	public HashMap<String,ArrayList<String>> getResult(){
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
		if(qName.equalsIgnoreCase("m")){
			curstar="";
			curmov="";


		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if(qName.equalsIgnoreCase("f")){
			curmov=tmpVal;
		}else if(qName.equalsIgnoreCase("a")){
			curstar=tmpVal;
		}else if(qName.equalsIgnoreCase("m")){

			if(curmov==null){
				//Drop
				logger.warning("missing movie id..Drop");
				return;
			}
			if(curstar==null){
				//Drop
				logger.warning("missing star name...Drop");
				return;
			}

			ArrayList<String>slist=result.getOrDefault(curmov,new ArrayList<>());
			slist.add(curstar);
			result.put(curmov,slist);

		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		tmpVal = new String(ch, start, length);
	}

}