// package org.example;

// import org.json.JSONObject;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Arrays;

// public class Base {
//     public static String doc_type;


//     public Base(String doc_type){
//         this.doc_type=doc_type;
//     }

//     public static String root="/data1/hhj/project/doc_extract_v_4/";
//     public static String json_path = root + "input/"+doc_type+"_input.json";

//     public static String rawSeg_Mark = "raw_seg";
//     public static String normResult_Mark = "norm_result";

//     public static String final_data_save_dir = root + "output/" + doc_type;
//     public static String finalDataFileName = root + "output/"+ doc_type+"/output_example.json";


//     public static final String JDBC_URL = "jdbc:mysql://111.9.47.74:8922/emr_parser";
//     public static final String DB_USER = "root";
//     public static final String DB_PASSWORD = "Aliab12!2020";


//     public static void main(String[] args) throws Exception {
//         //提取入参中的有效部分
//         String json_content = InfoExtracter.getContentFromInputJsonDir(json_path);
//     //    List<String> rawSegKeySet = Admit_Info.get_rawSegKeySet();

//         //判断doc_type
//         System.out.println(doc_type);
//         doc_type=InfoExtracter.Get_docType(json_content);
//         // System.out.println(doc_type);
//         // System.out.println("------------------------------------------------#########");

//         //由文书类型判断是否要二级解析
//         int key_metrics_flag = Template.Get_key_metrics_flag(doc_type);
 

//         //根据文书类型，从数据库提取相应的模板
//         DatabaseConnector.MyResult result = DatabaseConnector.get_rawSegKeySet(doc_type, json_content);//这里改写了函数，增加了入参：doc_type

//         List<String> rawSegKeySet = result.rawSegKeySet;
//         System.out.println(rawSegKeySet);
//         System.out.println("------------------------------------------------rawSegKeySet↑");

//         //验证数据库中模板的关键词在入参中都存在
//         rawSegKeySet=InfoExtracter.Get_Effective_RawSegKeySet(rawSegKeySet,json_content);
//         System.out.println(rawSegKeySet);
//         System.out.println("------------------------------------------------exited_rawSegKeySet↑");

//         //对数据库中获取的关键词排序，确保出现次序和json_content中是一样的，如果数据库中内容已经有序，不要下面的会更好
//         rawSegKeySet=DatabaseConnector.sortByContentOrder(rawSegKeySet, json_content);
//         System.out.println(rawSegKeySet);
//         System.out.println("------------------------------------------------new_rawSegKeySet↑");

//         //由模版对入参做粗解析，按顺序得到原始字段
//         List<List<String>> rawSeg = InfoExtracter.getRawSeg(json_content, rawSegKeySet);
//         System.out.println(rawSeg);
//         System.out.println("------------------------------------------------rawSeg↑");

//         //从模板获取原始字段的组合规则
//         ArrayList<ArrayList<String>> normResultGuide = result.normResultGuide;
//         System.out.println(normResultGuide);
//         System.out.println("------------------------------------------------normResultGuide↑");

//         //将组合规则按照相同的schema组合为树状
//         List<List<Object>> schema_tree_list = InfoExtracter.mergeLists(normResultGuide);
//         System.out.println(schema_tree_list);
//         System.out.println("------------------------------------------------schema_tree_list↑");
        
//         //由组合规则对原始字段做组合拼凑
//         JSONObject normResult = InfoExtracter.getNormResultFromSchemaTreeList(schema_tree_list, rawSeg);
//         System.out.println(normResult);
//         System.out.println("------------------------------------------------normResult↑");

//         // 二级辨析 在normResult中加上二级解析的结果
//        if (key_metrics_flag == 1) {
//            List<String> keyList = new ArrayList<>(List.of("T","P","R","BP"));
//            String endWord = "mmHg";
//            List<JSONObject> keyMetrics = InfoExtracter.subExtract(rawSeg, "体格检查", keyList, endWord);
//            normResult.put("key_metrics", keyMetrics);//这里的第四个结果缺个单位，要补一下
//        }

//        //对每份模版的normResult根据doc_type做微调
//        Template.Template_modify(doc_type, normResult, rawSeg, json_content);

//         //生成出参内容
//         // JSONObject finalData = Admit_Info.genFinalData(normResult, rawSeg, doc_type);
//         JSONObject finalData = Template.genFinalData(normResult, rawSeg, doc_type);

//         // 保存文件
//         try {
//             // 检查目录是否存在，如果不存在则创建目录
//             Path directory = Paths.get(final_data_save_dir);
//             if (!Files.exists(directory)) {
//                 Files.createDirectories(directory);
//             }

//             // 写入文件
//             Path filePath = directory.resolve(finalDataFileName);
//             Files.write(filePath, finalData.toString(4).getBytes());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         // 将数据存入数据库，具体要存什么有待讨论
// //        String sql = "INSERT INTO test (id, config_node_key, adm_column) VALUES (?, ?, ?)";
// //
// //        try (
// //                Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
// //                PreparedStatement preparedStatement = connection.prepareStatement(sql);
// //                ) {
// //            preparedStatement.setString(1, "1");
// //            preparedStatement.setString(2, "test");
// //            preparedStatement.setString(3, "hi");
// //
// //            int rowsAffected = preparedStatement.executeUpdate();
// ////            System.out.println(rowsAffected);
// //
// //        }catch (SQLException e) {
// //            e.printStackTrace();
// //        }


//         System.out.println("Done!");
//     }
// }

// // public class InnerBase {

    
// // }