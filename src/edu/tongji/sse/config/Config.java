package edu.tongji.sse.config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by huage on 2017/4/18.
 */
public class Config {
    public Config(){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            Document document = db.parse("src\\edu\\tongji\\sse\\config\\config.xml");
            NodeList booklist = document.getElementsByTagName("equiv");
            for (int i=0;i<booklist.getLength();i++){
                Node book = booklist.item(i);
                NodeList childnode = book.getChildNodes();
                System.out.println("第"+(i+1)+"个等价类");
                for (int j=0;j<childnode.getLength();j++) {
                    if (childnode.item(j).getNodeType()==Node.ELEMENT_NODE) {
                        System.out.println(childnode.item(j).getTextContent());
                    }
                }
            }

            NodeList neglect = document.getElementsByTagName("neglects");
            NodeList childnode = neglect.item(0).getChildNodes();
            for (int j=0;j<childnode.getLength();j++) {
                if (childnode.item(j).getNodeType()==Node.ELEMENT_NODE) {
                    System.out.println("忽略的neglect: " + childnode.item(j).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Config config =new Config();
    }
}
