package xml;

import java.util.List;

import model.FileInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FlieListContentHandler extends DefaultHandler{
	private List<FileInfo> infos = null;
	private FileInfo fI = null;
	private String tagName = null;
	
	
	public FlieListContentHandler(List<FileInfo> infos) {
		super();
		this.infos = infos;
	}

	public List<FileInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FileInfo> infos) {
		this.infos = infos;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.tagName = localName;
		if(tagName.equals("file")){
			fI = new FileInfo();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("file")){
			infos.add(fI);
		}
		tagName="";
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tmp = new String(ch,start,length);
		if(tagName.equals("id")){
			fI.setId(tmp);
		}else if(tagName.equals("name")){
			fI.setFilename(tmp);
		}else if(tagName.equals("size")){
			fI.setFilesize(tmp);
		}
	}
}
