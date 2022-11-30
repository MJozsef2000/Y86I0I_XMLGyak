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

public class DomQueryY86I0I {
  public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

    String[] borton_epulet_fields = {
        "cellak_szama",
        "kapacitas",
        "epulet_neve",
        "epites"
    };
    String[] rab_fields = {
        "nev",
        "eletkor",
        "letoltendo"
    };
    String[] cellak_fields = {
        "cella_kapacitas",
        "emelet",
        "ablak"
    };
    File xmlFile = new File("XMLY86I0I.xml");

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder dBuilder = factory.newDocumentBuilder();

    // Át parse-oljuk az XML file-unkat
    Document doc = dBuilder.parse(xmlFile);

    doc.getDocumentElement().normalize();
    System.out.println("---Nem kerjuk az A epulet mezoit!---");
    // A börtön épület mezőkből kérdezünk le, megkötések alapján
    NodeList nList = doc.getElementsByTagName("borton_epulet");
    for (int i = 0; i < nList.getLength(); i++) {
      Node nNode = nList.item(i);

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elem = (Element) nNode;
        String uid = elem.getAttribute("BOR_ID");
        // Az A ID-vel rendelkező börtön adatai nem kerülnek kiírásra
        if (uid.equals("A"))
          continue;
        System.out.println("BOR_ID" + ": " + uid);
        for (String field : borton_epulet_fields) {
          Node node = elem.getElementsByTagName(field).item(0);
          String data = node.getTextContent();
          try {
            int d = Integer.parseInt(data);
            // Nem írjuk ki a cellák számát, ha mennyiségük <=45
            if (d > 45)
              System.out.println(field + ": " + data);
          } catch (NumberFormatException ex) {
            System.out.println(field + ": " + data);
          }
        }
      }
    }
    System.out.println();
    System.out.println("---Lekerdezzuk a Rab mezobol minden rabot, aki az A epuletben van---");
    // Lekérdezünk a Rab mezőből minden rabot, aki az A épületben van
    nList = doc.getElementsByTagName("rab");
    for (int i = 0; i < nList.getLength(); i++) {
      Node nNode = nList.item(i);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elem = (Element) nNode;
        String uid = elem.getAttribute("RAB_ID");
        String connectid = elem.getAttribute("R-C");
        if (connectid.contains("a") == false)
          continue;
        System.out.println("RAB_ID" + ": " + uid);
        System.out.println("R-C" + ": " + connectid);
        for (String field : rab_fields) {
          Node node = elem.getElementsByTagName(field).item(0);
          String data = node.getTextContent();
          System.out.println(field + ": " + data);
        }
      }
    }
    System.out.println("---Minden cella, aminek a kapacitasa tobb, mint 2---");
    nList = doc.getElementsByTagName("cellak");
    for (int i = 0; i < nList.getLength(); i++) {
      Node nNode = nList.item(i);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elem = (Element) nNode;
        String uid = elem.getAttribute("CEL_ID");
        System.out.println("CEL_ID" + ": " + uid);
        for (String field : cellak_fields) {
          Node node = elem.getElementsByTagName(field).item(0);
          String data = node.getTextContent();
          if (field.equals("cella_kapacitas") == true) {
            if (Integer.parseInt(data) <= 2) {
              System.out.println("Ez a cella kicsi!");
              break;
            }
          }
          System.out.println(field + ": " + data);
        }
      }
    }

  }

}
