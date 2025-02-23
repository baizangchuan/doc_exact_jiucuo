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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubExtract {
    // DatabaseConnector.MyResult result = DatabaseConnector.get_rawSegKeySet(doc_type, json_content);//这里改写了函数，增加了入参：doc_type

    //由每行中二级解析指向的根节点，从rawSeg中找出对应的内容
    public static String  get_root_content(List<List<String>> rawSeg, String root_node){
        String root_content="";
        for(List<String> line :rawSeg){
            String combinedRegex = root_node;
            Pattern pattern = Pattern.compile(combinedRegex);
            Matcher matcher = pattern.matcher(line.get(0));
            if (matcher.find()) {
                root_content=line.get(1);
                return root_content;
            }
        }
        return root_content;
    }

    public static void Print_test(List<List<Map<String, String>>> sub_content_list, List<List<String>> rawSeg){
        for (List<Map<String, String>>sub_content : sub_content_list){
            String root_node=sub_content.get(0).get("root_node").replace("\\s+", "\\s*");
            String root_content=get_root_content(rawSeg,root_node);
            System.err.println(root_content);
            System.err.println("-------------------------------root_content ↑");

            // sub_content = Table.filterTable(sub_content, root_content);

            List<List<String>> sub_rawSeg = Table.getRawSeg(root_content, sub_content);
            System.out.println(sub_rawSeg);
            System.out.println("------------------------------------------------ sub_rawSeg↑");
    
            //从模板获取原始字段的组合规则
            ArrayList<ArrayList<String>> sub_normResultGuide = Table.get_normResultGuide(sub_content);
            System.out.println(sub_normResultGuide);
            System.out.println("------------------------------------------------sub_normResultGuide↑");
    
    
            //将组合规则按照相同的schema组合为树状
            List<List<Object>> sub_schema_tree_list = InfoExtracter.mergeLists(sub_normResultGuide);
            System.out.println(sub_schema_tree_list);
            System.out.println("------------------------------------------------sub_schema_tree_list↑");
            
            //由组合规则对原始字段做组合拼凑
            JSONObject sub_normResult = InfoExtracter.getNormResultFromSchemaTreeList(sub_schema_tree_list, sub_rawSeg);
            System.out.println(sub_normResult);
            System.out.println("------------------------------------------------sub_normResult ↑");


        }
    }
    public static void mergeJsonObjects(JSONObject target, JSONObject source) {
        // 遍历source JSONObject中的所有键值对
        Iterator<String> keys = source.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            // 将source中的键值对添加到target中
            target.put(key, source.get(key));
        }
    }

    //根据first_level_node和rawSeg的内容，得到sub_level_content
    public static JSONObject get_sub_NormResult(List<List<Map<String, String>>> sub_content_list, List<List<String>> rawSeg){
        JSONObject sub_normResult_all=new JSONObject();
        for (List<Map<String, String>>sub_content : sub_content_list){
            String root_node=sub_content.get(0).get("root_node").replace("\\s+", "\\s*");
            String root_content=get_root_content(rawSeg,root_node);
            System.err.println(root_content);
            System.err.println("-------------------------------root_content ↑");

            sub_content = Table.filterTable(sub_content, root_content);

            List<List<String>> sub_rawSeg = Table.getRawSeg(root_content, sub_content);
            System.out.println(sub_rawSeg);
            System.out.println("------------------------------------------------ sub_rawSeg↑");
    
            //从模板获取原始字段的组合规则
            ArrayList<ArrayList<String>> sub_normResultGuide = Table.get_normResultGuide(sub_content);
            System.out.println(sub_normResultGuide);
            System.out.println("------------------------------------------------sub_normResultGuide↑");
    
    
            //将组合规则按照相同的schema组合为树状
            List<List<Object>> sub_schema_tree_list = InfoExtracter.mergeLists(sub_normResultGuide);
            System.out.println(sub_schema_tree_list);
            System.out.println("------------------------------------------------sub_schema_tree_list↑");
            
            //由组合规则对原始字段做组合拼凑
            JSONObject sub_normResult = InfoExtracter.getNormResultFromSchemaTreeList(sub_schema_tree_list, sub_rawSeg);
            System.out.println(sub_normResult);
            System.out.println("------------------------------------------------sub_normResult ↑");

            mergeJsonObjects(sub_normResult_all,sub_normResult);

        }

        // for sub_content in sub_content_list:
        // //下面的部分和Main函数里对json_content提取的内容差不多，只是把json_content改成了sub_content，rawSegKeySet和normResultGuide可能要自己写

        //     String root_content =
        //     //获取二级解析的解析对象sub_content
        //     sub_content = Get_sub_content_content(sub_content, rawSeg);
            
        //     //获取二级解析的关键词列表rawSegKeySet
        //     rawSegKeySet=Get_rawSegKeySet(sub_content);
            
        //     rawSegKeySet=InfoExtracter.Get_Effective_RawSegKeySet(rawSegKeySet,sub_content);
            
        //     List<List<String>> sub_rawSeg = InfoExtracter.getRawSeg(sub_content, rawSegKeySet);
            
        //     //可能要重写
        //     ArrayList<ArrayList<String>> normResultGuide = sub_content.normResultGuide;
            
        //     List<List<Object>> sub_schema_tree_list = InfoExtracter.mergeLists(normResultGuide);
            
        //     JSONObject normResult_item = InfoExtracter.getNormResultFromSchemaTreeList(sub_schema_tree_list, sub_rawSeg);

        //     Merge_normResult(normResult,normResult_item);
        
        return sub_normResult_all;
    }


}
