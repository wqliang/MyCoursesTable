package controllers;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import classes.Comment;
import classes.Course;
import classes.Topic;
import classes.User;

public class OperationHandler {
	public String getResponse(User user, String op){
		UserController uc = new UserController();
		if (uc.login(user)){
			if ("login".equals(op))
				return "succeed";
    		CourseController cc = new CourseController();
    		ArrayList<Course> cs = cc.getAllCourse(user.getUid());
    		String result = toXML(cs);
    		return result;
    	}
    	else{
    		return "fail";
    	}
	}
	
	public String toXML(ArrayList<Course> cs){
		String xmlStr = null;
		try {
			StringWriter writerStr = new StringWriter();
			Result resultStr = new StreamResult(writerStr);
			SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler th = sff.newTransformerHandler();
			Transformer transformer = th.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "gbk");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			th.setResult(resultStr);
			
			th.startDocument();
			AttributesImpl attr = new AttributesImpl();
			th.startElement("", "courses", "courses", attr);
			attr.clear();
			Course cour = null;
			for (int i = 0; i < cs.size(); i++){
				cour = cs.get(i);
				
				th.startElement("", "", "course", attr);
				attr.clear();
				
				th.startElement("", "", "id", attr);
				th.characters(Integer.toString(cour.getCid()).toCharArray(), 0,
						Integer.toString(cour.getCid()).length());
				th.endElement("", "", "id");
				attr.clear();
				
				th.startElement("", "", "name", attr);
				th.characters(cour.getName().toCharArray(), 0, cour.getName().length());
				th.endElement("", "", "name");
			
				th.startElement("",  "", "date", attr);
				th.characters(Integer.toString(cour.getWeekday()).toCharArray(), 0,
					Integer.toString(cour.getWeekday()).length());
				th.endElement("",  "", "date");
				
				th.startElement("", "", "start", attr);
				th.characters(Integer.toString(cour.getStart()).toCharArray(), 0, 
					Integer.toString(cour.getStart()).length());
				th.endElement("", "", "start");
			
				th.startElement("",  "", "duration", attr);
				th.characters(Integer.toString(cour.getDuration()).toCharArray(), 0,
					Integer.toString(cour.getDuration()).length());
				th.endElement("",  "", "duration");
				
				th.startElement("", "", "addr", attr);
				th.characters(cour.getAddr().toCharArray(), 0, cour.getAddr().length());
				th.endElement("", "", "addr");
			
				th.startElement("", "", "teacher", attr);
				th.characters(cour.getTeacher().toCharArray(), 0, cour.getTeacher().length());
				th.endElement("",  "", "teacher");
			
				th.endElement("", "", "course");
			}
			th.endElement("", "courses", "courses");
			th.endDocument();
			
			xmlStr = writerStr.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Course> cs):"+e.getMessage());
		} catch (SAXException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Course> cs):"+e.getMessage());
		} catch (Exception e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Course> cs):"+e.getMessage());
		}
		return xmlStr;
	}
	
	public String getResponse(Topic topic){
		TopicController tc = new TopicController();
		if (tc.addTopic(topic)){
			return toXML("true");
		}
		else
			return toXML("false");
	}
	
	public String toXML(String resp){
		String xmlStr = null;
		try {
			StringWriter writerStr = new StringWriter();
			Result resultStr = new StreamResult(writerStr);
			SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler th = sff.newTransformerHandler();
			Transformer transformer = th.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "gbk");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			th.setResult(resultStr);
			
			th.startDocument();
			AttributesImpl attr = new AttributesImpl();
			attr.clear();
			th.startElement("", "response", "response", attr);
			th.characters(resp.toCharArray(), 0, resp.length());
			th.endElement("", "response", "response");
			th.endDocument();
			xmlStr = writerStr.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(String):"+e.getMessage());
		} catch (SAXException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(String):"+e.getMessage());
		}
		return xmlStr;
	}
	
	public String getResponse(int cid){
		TopicController tc = new TopicController();
		ArrayList<Topic> ts = tc.getTopic(cid);
		String result = toXML2(ts);
		return result;
	}
	
	public String toXML2(ArrayList<Topic> ts){
		String xmlStr = null;
		try {
			StringWriter writerStr = new StringWriter();
			Result resultStr = new StreamResult(writerStr);
			SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler th = sff.newTransformerHandler();
			Transformer transformer = th.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "gbk");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			th.setResult(resultStr);
			
			th.startDocument();
			AttributesImpl attr = new AttributesImpl();
			th.startElement("", "topics", "topics", attr);
			attr.clear();
			Topic topic = null;
			for (int i = 0; i < ts.size(); i++){
				topic = ts.get(i);
				
				th.startElement("", "", "topic", attr);
				attr.clear();
				
				th.startElement("", "", "publisher", attr);
				th.characters(topic.getPublisher().toCharArray(), 0,
						topic.getPublisher().length());
				th.endElement("", "", "publisher");
				attr.clear();
				
				th.startElement("", "", "title", attr);
				th.characters(topic.getTitle().toCharArray(), 0, topic.getTitle().length());
				th.endElement("", "", "title");
			
				th.startElement("",  "", "update", attr);
				th.characters(topic.getTime().toCharArray(), 0, topic.getTime().length());
				th.endElement("",  "", "update");
				
			
				th.endElement("", "", "topic");
			}
			th.endElement("", "topics", "topics");
			th.endDocument();
			
			xmlStr = writerStr.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Topic>):"+e.getMessage());
		} catch (SAXException e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Topic>):"+e.getMessage());
		} catch (Exception e) {
			MCTLog log = new MCTLog();
	        log.write("Exception in OperationHandler.toXML(ArrayList<Topic>):"+e.getMessage());
		}
		return xmlStr;
	}
	
	public String getResponse(Comment cmm){
		CommentController cmc = new CommentController();
		if (cmc.addComment(cmm)){
			return "succeed";
		}
		else
			return "fail";
	}
	
}
