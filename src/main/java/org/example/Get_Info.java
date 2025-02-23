//这里似乎都是些没什么用的函数
package org.example;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Get_Info {
    public static String getContentFromJson(String json) throws IOException {
        String content;
        String jsonData = new String(Files.readAllBytes(Paths.get(json)));
        JSONObject jsonObject = new JSONObject(jsonData);
        content = jsonObject.getJSONObject("args").getString("content");
        return content;
    }

    public static String getContentFromXml(String xml, String xml_path) throws IOException {
        try {
            // 创建一个DocumentBuilderFactory实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // 使用工厂实例创建一个DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 使用DocumentBuilder解析XML文件，返回一个Document对象
            Document doc = builder.parse(xml_path);

            // 获取XML文档的根元素
            Element root = doc.getDocumentElement();

            // 定义一个StringBuilder来拼接文本内容
            StringBuilder textContentBuilder = new StringBuilder();

            // 获取根元素下的子节点列表
            NodeList nodeList = root.getChildNodes();

            // 遍历子节点列表
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                // 如果节点是元素节点
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    // 获取元素的标签名
//                    System.out.println("标签名：" + element.getTagName());

                    // 获取元素的文本内容并拼接到StringBuilder中
                    String textContent = element.getTextContent().trim();
                    textContent = textContent.replace("\n", "").replace("\r", "");
                    textContent = textContent.replaceAll("\\s+", "");
                    textContentBuilder.append(textContent);
                }
            }

            return textContentBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
