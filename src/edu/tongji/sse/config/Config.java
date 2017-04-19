package edu.tongji.sse.config;

import edu.tongji.sse.Word;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huage on 2017/4/18.
 */
public class Config {
    public  Config(){
        List<Long> tmpList;
        HashMap<Long, Long> equivHashMap = new HashMap<>();
        List<Long> neglectList = new ArrayList<>();
        Word word = new Word();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            Document document = db.parse("src\\edu\\tongji\\sse\\config\\config.xml");
            NodeList equivlist = document.getElementsByTagName("equiv");
            for (int i=0;i<equivlist.getLength();i++){
                Node book = equivlist.item(i);
                NodeList childnode = book.getChildNodes();
//                System.out.println("第"+(i+1)+"个等价类");
                tmpList =  new ArrayList<>();
                for (int j=0;j<childnode.getLength();j++) {
                    if (childnode.item(j).getNodeType()==Node.ELEMENT_NODE) {
                        tmpList.add(word.getOnelineCode(childnode.item(j).getTextContent()));
                    }
                }
                if(tmpList.size()>1) {
                    Long value = tmpList.get(0);
                    for (int j = 1; j < tmpList.size(); j++) {
                        equivHashMap.put(tmpList.get(j), value);
                    }
                }
            }

            NodeList neglect = document.getElementsByTagName("neglects");
            NodeList childnode = neglect.item(0).getChildNodes();
            for (int j=0;j<childnode.getLength();j++) {
                if (childnode.item(j).getNodeType()==Node.ELEMENT_NODE) {
                    neglectList.add(word.getOnelineCode(childnode.item(j).getTextContent()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Word.equivHashMap = equivHashMap;
        Word.neglectList = neglectList;

    }


    public static void main(String[] args) {
        Config config =new Config();

    }
}
