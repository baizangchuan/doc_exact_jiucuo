package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;


public class FileExtract {

    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();
        try (ZipFile zipFile = new ZipFile(new File(zipFilePath), "GBK")) {  // 使用GBK编码
            Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                File newFile = new File(destDir, entry.getName());
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (InputStream is = zipFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> listAllTxtFiles(String destDir) {
        List<Path> txtFiles = new ArrayList<>();
        Path dirPath = Paths.get(destDir);

        try {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".txt")) {
                        txtFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return txtFiles;
    }
    public static void Created_path(String destDir){
        File directory = new File(destDir);

        // 检查目录是否存在
        if (!directory.exists()) {
            // 尝试创建目录及其所有不存在的父目录
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("目录创建成功: " + destDir);
            } else {
                System.out.println("目录创建失败: " + destDir);
            } }
            else {
                System.out.println("目录已存在: " + destDir);
            }
    }
    public static void single_content_for_zip(String content, String file_name, String org_name, String name, String description, String url, String user, String password){
            //入参
            // org_name=org_name;
            // name=name;
            // description=description;

            //数据库配置
            // String url = "jdbc:mysql://111.9.47.74:8922/emr_parser";
            // String user = "root";
            // String password = "Aliab12!2020";

            //创建用于存正则的表
            CreateTableForFile.Create_New_config_node("New_hp_config_node", url, user, password);
            //创建用于存文档的表
            CreateTableForFile.Create_New_emr_record("hp_emr_record", url, user, password);
            //创建用于展示所有doc_type的表
            CreateTableForFile.Create_template_config("hp_template_config", url, user, password);

            List<Map<String, String>>  key_table ;
            // List<List<Map<String, String>>> sub_content_list;
            int sample_num=0;

            // 使用 Files.readString 方法读取文件的所有内容
            // String content = Files.readString(file);
            // 获取文件名
            String fileName = file_name;
            System.out.println("文件名: " + fileName);
            String doc_type_zh=fileName;
            String doc_type_en=ChineseToPinyin.convertToPinyin(doc_type_zh);

            //由content得到doc_type
            ArrayList<HashMap<String, Object>> database_doctype_list = Get_File_docType.GetDataFromDatabase("new_template", url, user, password);//表名有问题
            System.err.println(database_doctype_list);
            String type_content=Get_docType.extractSubstring(content);//取content的前后各30个字符用于分类
            String doc_type = Get_File_docType.Get_DocType_zh(type_content, database_doctype_list);
            System.err.println(doc_type);

            //若doc_type存在，则启用解析，并登记到hp_emr_record（添加文本），并修改hp_template_config（相应doc_type匹配数+1），跳过后面步骤
            if (doc_type!=null){
                doc_type_en = Get_File_docType.Get_DocType_en(content, database_doctype_list);
                System.err.println(doc_type_en);
                doc_type_zh = doc_type;
                //解析（是不是还没写？要写什么？）

                //登记
                CreateTableForFile.Insert_emr_record("hp_emr_record",doc_type_zh,doc_type_en, content, org_name, url, user, password);
                CreateTableForFile.Modify_template_config("hp_template_config",doc_type_en, url, user, password);
                
                return;
            }

            // 输出文件内容
            System.out.println(content);
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
            CreateTableForFile.InsertDatabase("New_hp_config_node", doc_type_zh,doc_type_en, exited_table, org_name, url, user, password);

            //插入文档到数据库
            CreateTableForFile.Insert_emr_record("hp_emr_record",doc_type_zh,doc_type_en, content, org_name, url, user, password);

            //插入doc_type到文档展示
            CreateTableForFile.Insert_template_config("hp_template_config", doc_type_en, doc_type_en, org_name, 1, doc_type_zh, url, user, password);

            sample_num+=1;

            // break;



        // //创建parse_task table(若没有)
        // CreateTableForFile.Create_parse_task("hp_parse_task");
        // //插入parse_task table
        // sample_num=1;
        // CreateTableForFile.Insert_parse_task("hp_parse_task", name, description, sample_num, org_name);

        //文档分类（按照每类文书只有一个文档来）
        //读取匹配规则
        //建表存储

    }
    

    public static void main(String[] args) {



        // To 彭伊敏：这些都是必要的参数
        String org_name="hhj0909_1416";
        String name="hhj0909_1416";
        String description="hhj0909_1416";
        //数据库配置
        String url = "jdbc:mysql://111.9.47.74:8922/emr_parser";
        String user = "root";
        String password = "Aliab12!2020";


        //To 彭伊敏：这一段你不用管，这是文件处理模块，你好像已经在服务器里做过了
        String zipFilePath = "C:\\Users\\13205\\demo\\file\\415801168(1).zip";
        // String destDir = "C:\\Users\\13205\\demo\\file\\doc_txt\\4158011682";
        String destDir = "C:\\Users\\13205\\demo\\file\\doc_txt\\"+org_name;
        Created_path(destDir);
        unzip(zipFilePath, destDir);
        List<Path> txtFiles = listAllTxtFiles(destDir);//遍历文件（只做仅有一个文件夹的）（这里后面要改成相对路径）


        //To 彭伊敏：你喂入content的时候也要把这个content对应的txt的文件名（中文）一起喂进来
        List<String> content_list=new ArrayList<>();
        List<String> file_name_list=new ArrayList<>();

        for (Path file : txtFiles) {
            try {
                // 使用 Files.readString 方法读取文件的所有内容
                String content = Files.readString(file);
                content_list.add(content);
                // 获取文件名
                String fileName = file.getFileName().toString().replace(".txt", "");
                file_name_list.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        // // 获取content_list和file_name_list
        // String recordSource = "a1312cfee3c249e2940bf57dc99d71a1";
        // ForSpringboot.getContentAndNormRecordType(url, user, password, recordSource, content_list, file_name_list);

        //To 彭伊敏：最终就是用这个循环来喂入你的content
        for(int i=0; i<content_list.size(); i++){

            String content=content_list.get(i);
            String file_name=file_name_list.get(i);

            // To 彭伊敏：处理content是这个函数，这里面新建及修改的表，我写在了https://bvqlwk2vse5.feishu.cn/wiki/ZbeEwfC0mi6dgUkOZ27ciMEGnCd
            //规则是从之前的表‘new_template’中抽取的，注意不要把这个表删掉哈
            single_content_for_zip(content, file_name, org_name, name, description, url, user, password);

        }
        


    

        //To 彭伊敏：这一段也是必要的，需要出现在你那边的函数上
        //创建parse_task table(若没有)
        CreateTableForFile.Create_parse_task("hp_parse_task", url, user, password);
        //插入parse_task table
        int sample_num=content_list.size();
        CreateTableForFile.Insert_parse_task("hp_parse_task", name, description, sample_num, org_name, url, user, password);
}
}