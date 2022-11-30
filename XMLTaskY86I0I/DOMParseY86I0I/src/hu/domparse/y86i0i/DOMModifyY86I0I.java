package hu.domparse.y86i0i;

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
import org.w3c.dom.NamedNodeMap;

public class DOMModifyY86I0I {
  public static void main(String argv[])
      throws ParserConfigurationException, TransformerException, IOException, TransformerConfigurationException {
    try {
      File inputFile = new File("XMLY86I0I.xml");

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      Document doc = docBuilder.parse(inputFile);
      NodeList rabList = doc.getElementsByTagName("rab");
      // Átmegyünk az összes Rab-on
      for (int i = 0; i < rabList.getLength(); i++) {
        // Node változtatás
        Node rab = doc.getElementsByTagName("rab").item(i);
        // Attributomok lementése
        NamedNodeMap attr = rab.getAttributes();
        Node nodeAttr = attr.getNamedItem("RAB_ID");
        // ID Változtatás
        nodeAttr.setTextContent("RAB_" + (i));

        NodeList list = rab.getChildNodes();
        for (int t = 0; t < list.getLength(); t++) {
          Node node = list.item(t);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            if ("nev".equals(eElement.getNodeName())) {
              // Ha a név egyenlő ezzel
              if ("Gengszter Lajos".equals(eElement.getTextContent())) {
                // Változtassa meg erre
                eElement.setTextContent("Gengszter Lajos Junior (GL)");
              }
              if ("Gonosz Gergő".equals(eElement.getTextContent())) {
                eElement.setTextContent("Már Nem Gonosz Gergő");
              }
            }
          }

        }
      }
      NodeList bortonList = doc.getElementsByTagName("borton_epulet");
      for (int i = 0; i < bortonList.getLength(); i++) {
        Node borton = doc.getElementsByTagName("epulet_neve").item(i);
        NodeList childNodes = borton.getChildNodes();
        for (int count = 0; count < childNodes.getLength(); count++) {
          Node node = childNodes.item(count);
          // Az összes börtön épületnek kitöröljük a kapacitását
          if ("kapacitas".equals(node.getNodeName())) {
            borton.removeChild(node);
          }
        }
      }

      NodeList lakhelyList = doc.getElementsByTagName("lakhely");
      for (int i = 0; i < lakhelyList.getLength(); i++) {
        Node lakhely = doc.getElementsByTagName("lakhely").item(i);
        NodeList childNodes = lakhely.getChildNodes();
        for (int t = 0; t < childNodes.getLength(); t++) {
          Node node = childNodes.item(t);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            if ("iranyitoszam".equals(eElement.getNodeName())) {
              if ("3529".equals(eElement.getTextContent())) {
                eElement.setTextContent("3000");
              }
              if ("1032".equals(eElement.getTextContent())) {
                eElement.setTextContent("1000");
              }
            }
          }
        }
      }

      NodeList cellakList = doc.getElementsByTagName("cellak");
      for (int i = 0; i < cellakList.getLength(); i++) {
        Node cella = cellakList.item(i);

        String id = cella.getAttributes().getNamedItem("CEL_ID").getTextContent();

        // Létrehozza az új "honap" elemet
        Element wc = doc.createElement("van_wc");
        cella.appendChild(wc);

        // Az "id" értéke alapján ad értéket az új "honap" elemnek
        if (id.contains("a")) {
          wc.appendChild(doc.createTextNode("Van"));
        }
        if (id.contains("b")) {
          wc.appendChild(doc.createTextNode("Nincs"));
        }
        if (id.contains("c")) {
          wc.appendChild(doc.createTextNode("Van"));
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();

      DOMSource source = new DOMSource(doc);

      System.out.println("--Results--");
      StreamResult consoleResult = new StreamResult(System.out);
      transformer.transform(source, consoleResult);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
