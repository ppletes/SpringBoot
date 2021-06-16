package com.persongeneratorapp.designpatterns.strategy;

import com.persongeneratorapp.model.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FormatXML implements Strategy {

    @Override
    public void writeData(Set<Person> persons) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("persons");
            document.appendChild(root);

            for (Person f : persons) {

                Element person = document.createElement("person");
                root.appendChild(person);

                Element firstName = document.createElement("firstname");
                firstName.appendChild(document.createTextNode(f.getGivenName()));
                person.appendChild(firstName);

                Element lastname = document.createElement("lastname");
                lastname.appendChild(document.createTextNode(f.getFamilyName()));
                person.appendChild(lastname);

                String[] s = f.getAge().split(" ");

                Element age = document.createElement("age");
                age.appendChild(document.createTextNode(s[0]));
                person.appendChild(age);


                Element ageType = document.createElement("ageType");
                ageType.appendChild(document.createTextNode(f.getAgeType()));
                person.appendChild(ageType);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("file.xml"));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public void readData() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder loader = null;
        try {
            loader = factory.newDocumentBuilder();
            Document document = loader.parse("file.xml");
            DocumentTraversal traversal = (DocumentTraversal) document;
            NodeIterator iterator = traversal.createNodeIterator(
                    document.getDocumentElement(), NodeFilter.SHOW_TEXT, null, true);

            System.err.println("XML FILE: ");
            for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {

                String text = n.getTextContent().trim();

                if (!text.isEmpty()) {
                    System.out.println(text);
                }
            }
            System.out.println("\n");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
