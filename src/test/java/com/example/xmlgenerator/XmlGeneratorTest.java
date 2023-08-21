package com.example.xmlgenerator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

@SpringBootTest
public class XmlGeneratorTest {


    @Test
    void generateTest() throws TransformerException, ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("company");
        doc.appendChild(rootElement);

        // staff 1001

        // add xml elements
        Element staff = doc.createElement("staff");
        // add staff to root
        rootElement.appendChild(staff);
        // add xml attribute
        staff.setAttribute("id", "1001");

        // alternative
        // Attr attr = doc.createAttribute("id");
        // attr.setValue("1001");
        // staff.setAttributeNode(attr);

        Element name = doc.createElement("name");
        name.setTextContent("mkyong");
        staff.appendChild(name);

        Element role = doc.createElement("role");
        role.setTextContent("support");
        staff.appendChild(role);

        Element salary = doc.createElement("salary");
        salary.setAttribute("currency", "USD");
        salary.setTextContent("5000");
        staff.appendChild(salary);

        Element bio = doc.createElement("bio");
        // add xml CDATA


        // staff 1002
        Element staff2 = doc.createElement("staff");
        // add staff to root
        rootElement.appendChild(staff2);
        staff2.setAttribute("id", "1002");

        Element name2 = doc.createElement("name");
        name2.setTextContent("yflow");
        staff2.appendChild(name2);

        Element role2 = doc.createElement("role");
        role2.setTextContent("admin");
        staff2.appendChild(role2);

        Element salary2 = doc.createElement("salary");
        salary2.setAttribute("currency", "EUD");
        salary2.setTextContent("8000");
        staff2.appendChild(salary2);

        Element bio2 = doc.createElement("bio");
        // add xml CDATA
        bio2.appendChild(doc.createCDATASection("a & b"));
        staff2.appendChild(bio2);

        // print XML to system console
        writeXml(doc, System.out);
    }



    // write doc to output stream
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

}
