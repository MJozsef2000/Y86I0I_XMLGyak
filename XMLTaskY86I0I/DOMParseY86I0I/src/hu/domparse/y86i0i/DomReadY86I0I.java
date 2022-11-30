package hu.domparse.y86i0i;

import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomReadY86I0I {
  public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {
    // XML File opening
    String[] szemelyzet_fields = {
        "pozicio",
        "szolgalati_hely",
        "csaladi_allapot"
    };
    String[] alkalmazott_fields = {
        "eletkor",
        "nev",
        "lakhely"
    };
    String[] beosztas_fields = {
        "muszak"
    };
    String[] borton_epulet_fields = {
        "cellak_szama",
        "kapacitas",
        "epulet_neve",
        "epites"
    };
    String[] cellak_fields = {
        "cella_kapacitas",
        "emelet",
        "ablak"
    };
    String[] rab_fields = {
        "nev",
        "eletkor",
        "letoltendo"
    };
    String[][] fields = {
        szemelyzet_fields,
        alkalmazott_fields,
        beosztas_fields,
        borton_epulet_fields,
        cellak_fields,
        rab_fields
    };
    String[] sub_roots = {
        "szemelyzet",
        "alkalmazott",
        "beosztas",
        "borton_epulet",
        "cellak",
        "rab"
    };
    String[] id_list = {
        "SZEM_ID",
        "ALK_ID",
        "BE_ID",
        "BOR_ID",
        "CEL_ID",
        "RAB_ID"
    };
    File xmlFile = new File("XMLY86I0I.xml");

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder dBuilder = factory.newDocumentBuilder();

    Document doc = dBuilder.parse(xmlFile);

    doc.getDocumentElement().normalize();

    System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

    int index = 0;
    for (String element : sub_roots) {
      NodeList nList = doc.getElementsByTagName(element);
      for (int i = 0; i < nList.getLength(); i++) {
        Node nNode = nList.item(i);

        System.out.println("\nCurrent Element: " + nNode.getNodeName());

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) nNode;
          String uid = elem.getAttribute(id_list[index]);
          System.out.println(id_list[index] + ": " + uid);
          for (String field : fields[index]) {
            Node node = elem.getElementsByTagName(field).item(0);
            String data = node.getTextContent();
            System.out.println(field + ": " + data);
          }
        }
      }
      index++;
    }
  }
}
