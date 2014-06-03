package xml;

import java.util.List;

import model.TopicInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TopicContentHandler extends DefaultHandler{
	private List<TopicInfo> infos = null;
	private TopicInfo topicInfo = null;
	private String tagName = null;
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tmp = new String(ch,start,length);
		if(tagName.equals("id")){
			topicInfo.setId(tmp);
		}else if(tagName.equals("publisher")){
			topicInfo.setPublisher(tmp);
		}else if(tagName.equals("update")){
			topicInfo.setUpdate(tmp);
		}else if(tagName.equals("detail")){
			topicInfo.setDetail(tmp);
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
		if(qName.equals("topic")){
			infos.add(topicInfo);
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
		if(tagName.equals("topic")){
			topicInfo = new TopicInfo();
		}
	}
	public List<TopicInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<TopicInfo> infos) {
		this.infos = infos;
	}
	public TopicContentHandler(List<TopicInfo> infos) {
		super();
		this.infos = infos;
	}
	
}
