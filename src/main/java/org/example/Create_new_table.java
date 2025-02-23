package org.example;

import java.util.List;
import java.util.Map;

public class Create_new_table {
    public static void Create_new_table_for_no_type_content(String content, String doc_type_zh, String doc_type_en, String org_name, String url, String user, String password) {
                List<Map<String, String>>  key_table ;

                key_table=Database_old_table.get_Table("", content);//由content能匹配到的所有rawSegKeySet
                // System.out.println(key_table);
                // System.out.println("------------------------------------------------key_table ↑");

                List<Map<String, String>> exited_table = Database_old_table.filterTable(key_table, content);
                System.out.println(Table.extract_List_by_key(exited_table,"name"));
                System.out.println(exited_table);
                System.out.println("------------------------------------------------exited_table ↑");
        
                exited_table=Database_old_table.sortTable(exited_table, content);
                System.out.println(Table.extract_List_by_key(exited_table,"name"));
                System.out.println(exited_table);
                System.out.println("------------------------------------------------sorted_table ↑");
                
                //插入正则到数据库
                CreateTableForFile.InsertDatabase("new_template", doc_type_zh,doc_type_en, exited_table, org_name, url, user, password);

                //插入文档到数据库
                CreateTableForFile.Insert_emr_record("hp_emr_record",doc_type_zh,doc_type_en, content, org_name, url, user, password);

                //插入doc_type到文档展示
                CreateTableForFile.Insert_template_config("hp_template_config", doc_type_en, doc_type_en, org_name, 1, doc_type_zh, url, user, password);

                // sample_num+=1;
                System.err.println("----识别到了一个新的文书类型，已创建！");
                
                // CreateTableForFile.Modify_parse_task("hp_parse_task", name, description, sample_num, org_name);

    }
}
