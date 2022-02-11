package xml;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Reader {
    private String filepath;

    public Reader(String path) {
        filepath = path;

    }

    public String getFilepath() {
        return filepath;
    }

    public HashMap<String, String> fetchQuerySyntax(String queryID) throws Exception {

        Document doc = null;

        try {
            File file = new File(filepath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println("Error reading XML file");
            e.printStackTrace();
        }

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        XPathExpression exp = xp.compile("//sql[@id=\"" + queryID + "\"]");
        NodeList nl = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);

        if (nl.getLength() != 0) {
            Element qnode = (Element) nl.item(0);

            HashMap<String, String> res = new HashMap<String, String>();

            res.put("tag", qnode.getTagName());
            res.put("paramType", qnode.getAttribute("paramType"));
            res.put("query", qnode.getTextContent().trim());

            return res;
        } else {
            throw new Exception("Invalid Query ID");
        }        
    }
}
