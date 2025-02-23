// To 敏子：直接看53行
package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Main {

    // public static String root="C:\\Users\\13205\\demo\\";

    // public static List<String> doc_type_list = new ArrayList<>(Arrays.asList(
    //     "admit_info",// 0
    //     "discharge_info",// 1 
    //     "first_case_info",// 2
    //     "operation_info",// 3
    //     "case_info", // 4 hhj 
    //     "inform_info", // 5 hhj
    //     "postoperative_first_case_info"// 6 hhj
    //     ));
    // public static String doc_type = doc_type_list.get(6);

    // // public static String xml_path = root + "xml_fac/党入院记录.xml";

    // public static String json_path = root + "input/"+doc_type+"_input.json";

    // public static String rawSeg_Mark = "raw_seg";
    // public static String normResult_Mark = "norm_result";

    // public static String final_data_save_dir = root + "output/" + doc_type;
    // public static String finalDataFileName = root + "output/"+ doc_type+"/output_example.json";




    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();


        //数据库配置
        String url = "jdbc:mysql://111.9.47.74:13000/emr_parser";
        String user = "root";
        String password = "Aliab12!2020";
    
        //提取入参中的有效部分
        String json_content = new String(Files.readAllBytes(Paths.get("C:\\Users\\13205\\demo\\file\\doc_txt\\hhj0909_1416\\4158011682\\出院记录.txt")), "UTF-8");

        //To 敏子：最终只要调用这一个函数：输入文本，数据库配置，输出解析好的结果
        JSONObject finalData=text_to_FinalData(json_content, url, user, password);
        System.out.println("finalData");



    //                 // 保存文件
    //         try {
    //             // 检查目录是否存在，如果不存在则创建目录
    //             Path directory = Paths.get(final_data_save_dir);
    //             if (!Files.exists(directory)) {
    //                 Files.createDirectories(directory);
    //             }
    
    //             // 写入文件
    //             Path filePath = directory.resolve(finalDataFileName);
    //             System.err.println(final_data_save_dir);
    //             System.err.println(finalDataFileName);
    
    //             Files.write(filePath, finalData.toString(4).getBytes());
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    
    }





    public static JSONObject text_to_FinalData(String json_content,String url, String user, String password) {
            //获取doc_type
            String doc_type = "";
            ArrayList<HashMap<String, Object>> database_doctype_list = Get_File_docType.GetDataFromDatabase("new_template", url, user, password);
            System.out.println(database_doctype_list);       
            System.out.println("------------------------------------------------database_doctype_list ↑");
            
            if(Get_docType.Get_DocType_zh(json_content, database_doctype_list)!=null){
                doc_type = Get_docType.Get_DocType_en(json_content, database_doctype_list);
                System.out.println(doc_type);    
                System.out.println("------------------------------------------------doc_type ↑");
            } //！这里好像有问题：没有找出最精确的doc_type
            else{
                System.out.println("doc_type为空！");
            }
            // else{//doc_type为空的情况, 建新表
            //     String time_info=GetTime.getCurrentTimeFormatted();
            //     String doc_type_zh="新模板_此处要改为文档标识符_"+time_info;
            //     String doc_type_en="new_tamplate_你要将此为文档的英文名如admit_info或拼音"+time_info; 
            //     String org_name="12123";
            //     Create_new_table.Create_new_table_for_no_type_content(json_content, doc_type_zh, doc_type_en, org_name, url, user, password);
            //     //这里应该做成不返回内容，或者返回特殊内容，然后退出
                
            // }
    
            
    
    
            DatabaseConnector.Table_pair table = DatabaseConnector.get_Table(doc_type, json_content, url, user, password);//这里改写了函数，增加了入参：doc_type
            List<Map<String, String>>  key_table = table.main_body;
            List<List<Map<String, String>>> sub_content_list=table.sub_content_list;
            System.err.println(sub_content_list);
            System.out.println("------------------------------------------------sub_content_list ↑");
    
            System.out.println(key_table);
            System.out.println("------------------------------------------------key_table ↑");
    
    
    
            List<Map<String, String>> exited_table = Table.filterTable(key_table, json_content);
            System.out.println(Table.extract_List_by_key(exited_table,"name"));
            System.out.println("------------------------------------------------exited_table ↑");
    
            exited_table=DatabaseConnector.sortTable(exited_table, json_content);
            System.out.println(Table.extract_List_by_key(exited_table,"name"));
            System.out.println("------------------------------------------------sorted_table ↑");
    
    
            List<List<String>> rawSeg = Table.getRawSeg(json_content,exited_table);
            System.out.println(rawSeg);
            System.out.println("------------------------------------------------rawSeg↑");
    
            //从模板获取原始字段的组合规则
            ArrayList<ArrayList<String>> normResultGuide = Table.get_normResultGuide(exited_table);
            System.out.println(normResultGuide);
            System.out.println("------------------------------------------------normResultGuide↑");
    
    
            //将组合规则按照相同的schema组合为树状
            List<List<Object>> schema_tree_list = InfoExtracter.mergeLists(normResultGuide);
            System.out.println(schema_tree_list);
            System.out.println("------------------------------------------------schema_tree_list↑");
            
            //由组合规则对原始字段做组合拼凑
            JSONObject normResult = InfoExtracter.getNormResultFromSchemaTreeList(schema_tree_list, rawSeg);
            System.out.println(normResult);
            System.out.println("------------------------------------------------normResult↑");
    
            // SubExtract.Print_test(sub_content_list,rawSeg);
            JSONObject sub_NormResult= SubExtract.get_sub_NormResult(sub_content_list, rawSeg);
            SubExtract.mergeJsonObjects(normResult, sub_NormResult);
    
            //由组合规则对原始字段做组合拼凑
            System.out.println(normResult);
            System.out.println("------------------------------------------------new normResult↑");
    
            //生成出参内容
            // JSONObject finalData = Admit_Info.genFinalData(normResult, rawSeg, doc_type);
            JSONObject finalData = Template.genFinalData(normResult, rawSeg, doc_type);
            // System.out.println(finalData);
    


            return finalData;
    }
}