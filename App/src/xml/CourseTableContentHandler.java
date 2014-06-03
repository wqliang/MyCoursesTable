package xml;

import java.util.List;

import model.CourseInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class CourseTableContentHandler extends DefaultHandler{
	private List<CourseInfo> infos = null;
	private CourseInfo courseInfo = null;
	private String tagName = null;
	
	public CourseTableContentHandler(List<CourseInfo> infos) {
		super();
		this.infos = infos;
	}
	public List<CourseInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<CourseInfo> infos) {
		this.infos = infos;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tmp = new String(ch,start,length);
		if(tagName.equals("id")){
			courseInfo.setId(tmp);
		}else if(tagName.equals("name")){
			courseInfo.setName(tmp);
		}else if(tagName.equals("date")){
			courseInfo.setDate(Integer.parseInt(tmp));
		}else if(tagName.equals("start")){
			courseInfo.setStart(Integer.parseInt(tmp));
		}else if(tagName.equals("duration")){
			courseInfo.setDuration(Integer.parseInt(tmp));
		}else if(tagName.equals("addr")){
			courseInfo.setAddr(tmp);
		}else if(tagName.equals("teacher")){
			courseInfo.setTeacher(tmp);
		}
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("course")){
			infos.add(courseInfo);
		}
		tagName="";
	}
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.tagName = localName;
		if(tagName.equals("course")){
			courseInfo = new CourseInfo();
		}
	}
	
}
