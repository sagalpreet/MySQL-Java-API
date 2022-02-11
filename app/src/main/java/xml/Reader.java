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
    /**
     * Reader class provides API to read query syntaxes from XML files
     * on the basis of the query id
     */
    private String filepath; // to store the path of file to read from

    public Reader(String path) {
        filepath = path; // constructor initializes the path

    }

    public String getFilepath() {
        return filepath; // getter for the path
    }

    public HashMap<String, String> fetchQuerySyntax(String queryID) throws Exception {
        /**
         * The function attempts to read the file
         * If unsuccessful, exception is raised
         * 
         * Otherwise, on the query syntax for 
         * specified query ID is returned as string
         * after reading it from the xml file
         */

        Document doc = null; // initialize doc

        try {
            // attempt to open and read the xml file

            File file = new File(filepath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            // any exception, whatsoever is thrown
            System.out.println("Error reading XML file");
            e.printStackTrace();
        }


        // the xml content is put to dom format
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();

        // query searched from the dom tree
        XPathExpression exp = xp.compile("//sql[@id=\"" + queryID + "\"]");
        NodeList nl = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);

        // checking if such a query exists
        // else throwing an exception
        if (nl.getLength() != 0) {
            Element qnode = (Element) nl.item(0);

            // the query is returned as a hashmap with query as a string
            // the corresponding tag
            // and the paramType
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
