package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ForSpringboot {

    public static void main(String[] args) {
        String url = "jdbc:mysql://111.9.47.74:8922/emr_parser";
        String user = "root";
        String password = "Aliab12!2020";

        String recordSource = "a1312cfee3c249e2940bf57dc99d71a1";

        List<String> contentList = new ArrayList<>();
        List<String> normRecordTypeList = new ArrayList<>();
        List<Integer> id_list=new ArrayList<>();

        // 调用函数获取结果
        getContentAndNormRecordType(url, user, password, recordSource, contentList, normRecordTypeList, id_list);

        // 输出结果，验证正确性
        // System.out.println("Content List: " + contentList);
        System.out.println("Norm Record Type List: " + normRecordTypeList);
        System.out.println("ID List: " + id_list);
    }

    public static void getContentAndNormRecordType(String url, String user, String password, 
                                                   String recordSource, List<String> contentList, 
                                                   List<String> normRecordTypeList, List<Integer> id_list) {

        String query = "SELECT ID, content, record_type FROM hp_emr_record WHERE record_source = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, recordSource);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                id_list.add(rs.getInt("ID"));
                contentList.add(rs.getString("content"));
                normRecordTypeList.add(rs.getString("record_type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
