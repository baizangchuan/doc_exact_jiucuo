package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database_old_table {
    //构造数据类
    public static class Table_pair{
        List<Map<String, String>> main_body= new ArrayList<>();
        List<List<Map<String, String>>> sub_content_list= new ArrayList<>();
        List<Map<String, String>> all_line = new ArrayList<>();

        public Table_pair(List<Map<String, String>> main_body,  List<List<Map<String, String>>> sub_content_list){
            this.main_body=main_body;
            this.sub_content_list=sub_content_list;
        }
    }

    public static List<Map<String, String>> get_Table(String TableName, String doc_type, String content, String url,String user,String password) {
        // 数据库连接信息
        // String url = "jdbc:mysql://111.9.47.74:8922/emr_parser"; // 根据你的数据库配置修改
        // String user = "root"; // 根据你的数据库用户名修改
        // String password = "Aliab12!2020"; // 根据你的数据库密码修改

        // 声明连接和语句对象
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> table_line = new HashMap<>();
        

        try {
            // 连接数据库
            connection = DriverManager.getConnection(url, user, password);

            // 创建SQL语句
            // String sql = "SELECT * FROM hp_config_node"; // 根据你的表格名称修改
            String sql = "SELECT * FROM "+TableName; // 参考规则库

            // 创建并执行SQL语句
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            // 打印表格信息
            while (resultSet.next()) {
                // 这里想要通用可以把病程记录换成按索引读取template_list
                String node = resultSet.getString("template_config_code");
                if (node.equals(doc_type)) {
                    
                    table_line = new HashMap<>();
                    table_line.put("name",resultSet.getString("config_node_key").replace("\\s+", "\\s*"));
                    table_line.put("schema",resultSet.getString("adm_column"));
                    table_line.put("root_node",resultSet.getString("first_node"));//先不测试这里
                    table_line.put("is_regu",resultSet.getString("is_regular_expression"));//先不测试这里
                    table_line.put("regu",resultSet.getString("config_node_key").replace("\\s+", "\\s*"));//先不测试这里
                    table.add(table_line);
                }
                //新文件类型
                if(doc_type.equals("")){
                    table_line = new HashMap<>();
                    table_line.put("name",resultSet.getString("config_node_key").replace("\\s+", "\\s*"));
                    table_line.put("schema",resultSet.getString("adm_column"));
                    table_line.put("root_node",resultSet.getString("first_node"));//先不测试这里
                    table_line.put("is_regu",resultSet.getString("is_regular_expression"));//先不测试这里
                    table_line.put("regu",resultSet.getString("config_node_key").replace("\\s+", "\\s*"));//先不测试这里
                    table.add(table_line);
                }
            }
            // System.out.println(table);
            // Table_pair table_pair=Get_sub_content(table);
            // List<Map<String, String>> main_body =table_pair.main_body;
            // List<List<Map<String, String>>> sub_content_list =table_pair.sub_content_list;

            resultSet.close();

            String sql_template = "SELECT * FROM hp_template_config";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_template);

            // 定义一个template_list存储模板名称
            List<String> templateList = new ArrayList<>();

            while (resultSet.next()) {
                templateList.add(resultSet.getString("record_type"));
            }
            resultSet.close();
            for (String template : templateList) {
//                System.out.println(template);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接和语句对象
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return table;
    }
    
        public static List<Map<String, String>> filterTable(List<Map<String, String>> keyTable, String content) {
        List<Map<String, String>> matchedTable = new ArrayList<>();

        // 将中文和英文的空格都转换为正则表达式中的\s
        String modifiedContent = content.replaceAll("[\\s\u00A0]", " ");

        for (Map<String, String> row : keyTable) {
            String namePattern = row.get("name");
            if (namePattern != null) {
                // 将name中的空格替换为\s*
                String modifiedPattern = namePattern.replaceAll("[\\s\u00A0]", "\\\\s*");

                // 创建正则表达式匹配器
                Pattern pattern = Pattern.compile(modifiedPattern);
                Matcher matcher = pattern.matcher(modifiedContent);

                if (matcher.find()) {
                    row.put("name", matcher.group());
                    matchedTable.add(row);
                }
            }
        }

        return matchedTable;
    }
    private static class ElementWithIndex {
        Map<String, String> element;
        int index;
        int rawIndex;

        ElementWithIndex(Map<String, String> element, int index, int rawIndex) {
            this.element = element;
            this.index = index;
            this.rawIndex = rawIndex;
        }
    }

    public static List<Map<String, String>> sortTable(List<Map<String, String>> table, String content) {
        List<ElementWithIndex> elementsWithIndices = new ArrayList<>();
        
        // Create a list of elements with their indices in the content
        for (int i = 0; i < table.size(); i++) {
            Map<String, String> entry = table.get(i);
            String name = entry.get("name");
            if (name != null) {
                int index = content.indexOf(name);
                if (index != -1) {
                    elementsWithIndices.add(new ElementWithIndex(entry, index, i));
                    content = content.substring(0, index) + new String(new char[name.length()]).replace("\0", " ") + content.substring(index + name.length());
                }
            }
        }

        // Sort the list based on the indices in content
        elementsWithIndices.sort(Comparator.comparingInt(e -> e.index));

        // Extract the sorted elements
        List<Map<String, String>> sortedList = new ArrayList<>();
        for (ElementWithIndex e : elementsWithIndices) {
            sortedList.add(e.element);
        }

        return sortedList;
    }


}
