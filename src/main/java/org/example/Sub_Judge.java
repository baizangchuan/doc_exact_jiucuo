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

public class Sub_Judge {
    public static int sub_judge(int key_metrics_flag, ArrayList<ArrayList<String>> normResultGuide) throws Exception {
        for (List<String> item : normResultGuide) {
            String firstElement = item.get(0);
            if ("physical_exam".equals(firstElement) || "specialty_situation".equals(firstElement)) {
//                System.out.println("需要二级解析。");
                key_metrics_flag = 1;
                return key_metrics_flag;
            }
        }
        return key_metrics_flag;
    }
}
