package com.yame.common;

import com.yame.participantContent.ParticipantVO;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2018-02-25.
 */

public class XmlParse {

    ArrayList<ParticipantVO> list = new ArrayList<ParticipantVO>();

    public void parseStart() throws Exception{
        URL url = new URL("http://175.214.164.105:8282/YAMEWeb/com/userlist.do?tableName=test_table");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());

        Node tableName = doc.getElementsByTagName("tableName").item(0);
        NodeList itemList = doc.getElementsByTagName("item");

        Node item;

        System.out.println("tableName : " + tableName.getTextContent());
        for(int i = 0; i < itemList.getLength();i++)
        {
            item = itemList.item(i).getFirstChild();
            while(true)
            {
                ParticipantVO participantVO = new ParticipantVO();
                item = item.getNextSibling();
                if("num".equals(item.getNodeName()))
                    //System.out.print("번호 : " + item.getTextContent());
                    participantVO.setNum(item.getTextContent());
                else if("name".equals(item.getNodeName()))
                    //System.out.println(" 이름 : " + item.getTextContent());
                    participantVO.setName(item.getTextContent());

                list.add(participantVO);
                if(item.getNextSibling() == null)
                    break;
            }
        }
    }

    public ArrayList<ParticipantVO> exportArr()
    {
        return list;
    }

    private Document parseXML(InputStream stream) throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;

        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilderFactory.setIgnoringElementContentWhitespace(true);
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);

        }catch(Exception ex){
            throw ex;
        }

        return doc;
    }
}
