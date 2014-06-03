import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import controllers.CourseController;
import classes.Course;

public class XMLHandler {
	
	private CourseController ccer;
	
	public String getResponse(String require){
		ccer = new CourseController();
		Course cour = null;
		//cour = ccer.getone();
		cour = ccer.getOneCourse();
		String result = toXml(cour);
		//if (cour == null)
		//	result = "course is null";
		//if (result == null)//////////////////////////////////////////////////
		//	result = "toXml return null";////////////////////////////////////
		return result;
	}
	
	public String toXml(Course cour) {
		String xmlStr = null;
		try {
			StringWriter writerStr = new StringWriter();
			Result resultStr = new StreamResult(writerStr);
			SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler th = sff.newTransformerHandler();
			Transformer transformer = th.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			th.setResult(resultStr);
			
			th.startDocument();
			AttributesImpl attr = new AttributesImpl();
			th.startElement("", "classes", "classes", attr);
			attr.clear();
			
			attr.addAttribute("", "", "operation", "", "insert");
			th.startElement("", "", "list", attr);
			attr.clear();
			
			th.startElement("", "", "course", attr);
			attr.clear();
			
			th.startElement("", "", "name", attr);
			th.characters(cour.getName().toCharArray(), 0, cour.getName().length());
			th.endElement("", "", "name");
			
			th.startElement("", "", "addr", attr);
			th.characters(cour.getAddr().toCharArray(), 0, cour.getAddr().length());
			th.endElement("", "", "addr");
			
			th.startElement("", "", "teacher", attr);
			th.characters(cour.getTeacher().toCharArray(), 0, cour.getTeacher().length());
			th.endElement("",  "", "teacher");
			
			//th.startElement("", "", "start", attr);
			//th.characters(Integer.toString(cour.getStart()).toCharArray(), 0, 
			//		Integer.toString(cour.getStart()).length());
			//th.endElement("", "", "start");
			
			//th.startElement("",  "", "duration", attr);
			//th.characters(Integer.toString(cour.getDuration()).toCharArray(), 0,
			//		Integer.toString(cour.getDuration()).length());
			//th.endElement("",  "", "duration");
			
			//th.startElement("",  "", "weekday", attr);
			//th.characters(Integer.toString(cour.getWeekday()).toCharArray(), 0,
			//		Integer.toString(cour.getWeekday()).length());
			//th.endElement("",  "", "weekday");
			
			th.endElement("", "", "course");
			th.endElement("", "", "list");
			th.endElement("", "classes", "classes");
			th.endDocument();
			
			xmlStr = writerStr.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			xmlStr = "TransformerConfigurationException";
		} catch (SAXException e) {
			xmlStr = "SAXException";
		} catch (Exception e) {
			if (cour == null)
				xmlStr = "Exception: cour is null";
		}
		//Log.e("TEST","Éú³ÉµÄ"+xmlStr);
		return xmlStr;
	}
}
