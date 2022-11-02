package domy86i0i;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Builder;

import javax.xml.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomWriteY86I0I {
	public static void main(String argv[]) throws ParserConfigurationException, TransformerException, IOException, TransformerConfigurationException {
		//XML File opening
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		Element root = doc.createElementNS("DOMY86I0I", "users");
		doc.appendChild(root);
		
		root.appendChild(createUser(doc, "1", "Pal", "Kiss", "Web Dev"));
		root.appendChild(createUser(doc, "2", "Piroska", "Zold", "Java programozo"));
		root.appendChild(createUser(doc, "3", "Boros", "Lajos", "Manager"));
		
		TransformerFactory tranformerFactory = TransformerFactory.newInstance();
		Transformer transf = tranformerFactory.newTransformer();
		
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.VERSION, "1.0");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("users1Y86I0I.xml");
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);
		
		transf.transform(source, console);
		transf.transform(source, file);
		
	}
	
	private static Node createUser(Document doc, String id, String firstName, String lastName, String profession) {
		
		Element user = doc.createElement("user");
		
		user.setAttribute("id", id);
		user.appendChild(createUserElement(doc, "firstname", firstName));
		user.appendChild(createUserElement(doc, "lastname", firstName));
		user.appendChild(createUserElement(doc, "profession", firstName));
		
		return user;
	}
	
	private static Node createUserElement(Document doc, String name, String values) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(values));
		
		return node;
	}
}
