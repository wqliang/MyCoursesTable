package xml;

import java.util.List;

import model.NotificationInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NotificationContentHandler extends DefaultHandler{
	private List<NotificationInfo> infos = null;
	private NotificationInfo notificationInfo = null;
	private String tagName = null;
	public List<NotificationInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<NotificationInfo> infos) {
		this.infos = infos;
	}
	public NotificationContentHandler(List<NotificationInfo> infos) {
		super();
		this.infos = infos;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tmp = new String(ch,start,length);
		if(tagName.equals("id")){
			notificationInfo.setId(tmp);
		}else if(tagName.equals("publisher")){
			notificationInfo.setPublisher(tmp);
		}else if(tagName.equals("time")){
			notificationInfo.setDate(tmp);
		}else if(tagName.equals("detail")){
			notificationInfo.setDetail(tmp);
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
		if(qName.equals("notification")){
			infos.add(notificationInfo);
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
		if(tagName.equals("notification")){
			notificationInfo = new NotificationInfo();
		}
	}
	
	
}
