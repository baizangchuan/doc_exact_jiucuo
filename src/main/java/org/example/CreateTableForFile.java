package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class CreateTableForFile {
    


    // JDBC URL, 用户名和密码
    // private static final String JDBC_URL = "jdbc:mysql://111.9.47.74:8922/emr_parser";
    // private static final String JDBC_USER = "root";
    // private static final String JDBC_PASSWORD = "Aliab12!2020";

    public static void Create_New_config_node(String table_name, String url, String user, String password) {        
        Connection connection = null;
        Statement statement = null;
        
        
        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);
            // 创建Statement对象
            statement = connection.createStatement();

             // 定义创建表的SQL语句
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
            + "ID BIGINT(11) NOT NULL AUTO_INCREMENT, "
            + "config_node_key VARCHAR(255) DEFAULT NULL, "
            + "adm_column VARCHAR(255) DEFAULT NULL, "
            + "match_sample_num INT DEFAULT NULL, "
            + "repeat_in_sample INT DEFAULT NULL, "
            + "version VARCHAR(255) DEFAULT NULL, "
            + "parse_task_id INT DEFAULT NULL, "
            + "template_config_code VARCHAR(100) DEFAULT NULL, "
            + "org_name VARCHAR(255) DEFAULT NULL, "
            + "rectified_time DATETIME DEFAULT NULL, "
            + "gmt_created DATETIME DEFAULT NULL, "
            + "gmt_modified DATETIME DEFAULT NULL, "
            + "first_node VARCHAR(255) DEFAULT NULL, "
            + "is_regular_expression VARCHAR(255) DEFAULT NULL, "
            + "PRIMARY KEY (ID))"; // 关闭括号并定义主键

            // 执行创建表的SQL语句
            statement.execute(createTableSQL);
            System.out.println("Table 'New_config_node' is created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
    }
    public static void Create_New_emr_record(String table_name, String url, String user, String password) {        
        Connection connection = null;
        Statement statement = null;

        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);
            // 创建Statement对象
            statement = connection.createStatement();

             // 定义创建表的SQL语句
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
            + "ID BIGINT(11) NOT NULL AUTO_INCREMENT, "
            + "org_name VARCHAR(255) DEFAULT NULL, "
            + "content LONGTEXT DEFAULT NULL, "
            + "de_tagged INT DEFAULT NULL, "
            + "record_type VARCHAR(255) DEFAULT NULL, "
            + "norm_record_type VARCHAR(255) DEFAULT NULL, "
            + "record_source VARCHAR(255) DEFAULT NULL, "
            + "import_type VARCHAR(255) DEFAULT NULL, "
            + "gmt_created DATETIME DEFAULT NULL, "
            + "gmt_modified DATETIME DEFAULT NULL, "

            + "PRIMARY KEY (ID))"; // 关闭括号并定义主键

            // 执行创建表的SQL语句
            statement.execute(createTableSQL);
            System.out.println("Table 'New_emr_record' is created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
    }

    public static void Create_parse_task(String table_name, String url, String user, String password) { 
        Connection connection = null;
        Statement statement = null;

        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);
            // 创建Statement对象
            statement = connection.createStatement();

             // 定义创建表的SQL语句
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
            + "ID BIGINT(11) NOT NULL AUTO_INCREMENT, "
            + "name VARCHAR(255) DEFAULT NULL, "
            + "description VARCHAR(255) DEFAULT NULL, "
            + "sample_import_type INT(4) DEFAULT NULL, "
            + "sample_num INT(8) DEFAULT NULL, "
            + "record_types VARCHAR(255) DEFAULT NULL, "
            + "org_name VARCHAR(255) DEFAULT NULL, "
            + "status INT(4) DEFAULT NULL, "
            + "gmt_created DATETIME DEFAULT NULL, "
            + "gmt_modified DATETIME DEFAULT NULL, "
            + "sample_source VARCHAR(64) DEFAULT NULL, "
            + "PRIMARY KEY (ID))"; // 关闭括号并定义主键

            // 执行创建表的SQL语句
            statement.execute(createTableSQL);
            System.out.println("Table 'parse_task' is created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
    }

    public static void Create_template_config(String table_name, String url, String user, String password) { 
        Connection connection = null;
        Statement statement = null;

        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);
            // 创建Statement对象
            statement = connection.createStatement();

             // 定义创建表的SQL语句
             String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
             + "id BIGINT(20) NOT NULL AUTO_INCREMENT, "
             + "template_config_name VARCHAR(255) DEFAULT NULL, "
             + "template_config_code VARCHAR(255) NOT NULL, "
             + "org_name VARCHAR(255) DEFAULT NULL, "
             + "parse_task_id BIGINT(20) DEFAULT NULL, "
             + "config_type VARCHAR(255) DEFAULT NULL, "
             + "match_sample_id TEXT DEFAULT NULL, "
             + "match_sample_num BIGINT(20) DEFAULT NULL, "
             + "record_type VARCHAR(255) DEFAULT NULL, "
             + "rectified_time DATETIME DEFAULT NULL, "
             + "gmt_created DATETIME DEFAULT NULL, "
             + "gmt_modified DATETIME DEFAULT NULL, "
             + "PRIMARY KEY (id))"; // 关闭括号并定义主键

            // 执行创建表的SQL语句
            statement.execute(createTableSQL);
            System.out.println("Table 'template_config' is created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }     
    }
    public static void Modify_template_config(String table_name, String doc_type_en, int content_id, int parse_task_id, String url, String user, String password) {
        // Database credentials
        // String url = JDBC_URL;
        // String user = JDBC_USER;
        // String password = JDBC_PASSWORD;
    
        // SQL update query
        String content_id_2 = "," + String.valueOf(content_id);
        String sql = "UPDATE " + table_name + " SET match_sample_num = match_sample_num + 1, match_sample_id = CONCAT(match_sample_id, ?) WHERE template_config_name = ?";
    
        // Establishing a connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    
            // Set parameters for the prepared statement
            preparedStatement.setString(1, content_id_2);
            preparedStatement.setString(2, doc_type_en);
    
            // Executing the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void Insert_template_config(String table_name, String template_config_name, String template_config_code, String org_name, int match_sample_num, String doc_type_zh, int content_id, int parse_task_id,String url, String user, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);
                // 定义插入数据的SQL语句
                String insertSQL = "INSERT INTO " + table_name + " (template_config_name, template_config_code, org_name, parse_task_id, config_type, match_sample_id, match_sample_num, record_type, rectified_time, gmt_created, gmt_modified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                preparedStatement = connection.prepareStatement(insertSQL);

                // 设置参数值
                preparedStatement.setString(1, template_config_name);//doc_type_en
                preparedStatement.setString(2, template_config_code);//doc_type_en
                preparedStatement.setString(3, org_name);
                preparedStatement.setLong(4, parse_task_id);
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, String.valueOf(content_id));
                preparedStatement.setLong(7, match_sample_num);//这个后面做成累加的
                preparedStatement.setString(8, doc_type_zh);
                preparedStatement.setNull(9, java.sql.Types.TIMESTAMP); // rectified_time 设置为空
                preparedStatement.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis())); // gmt_created
                preparedStatement.setTimestamp(11, new java.sql.Timestamp(System.currentTimeMillis())); // gmt_created
                // preparedStatement.setNull(11, java.sql.Types.TIMESTAMP); // rectified_time 设置为空


                // 执行插入数据的SQL语句
                preparedStatement.executeUpdate();
                System.out.println("Data inserted into '" + table_name + "' successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void Insert_parse_task(String table_name, String name, String description, int sample_num, String org_name,String recordSource, String url, String user, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);


                // 定义插入数据的SQL语句
                String insertSQL = "INSERT INTO "+table_name+" (name, description, sample_import_type, sample_num, record_types, org_name, status, gmt_created, gmt_modified, sample_source) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                preparedStatement = connection.prepareStatement(insertSQL);

                // 设置参数值
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, 1); // sample_import_type 
                preparedStatement.setInt(4, sample_num);
                preparedStatement.setNull(5, java.sql.Types.VARCHAR); // record_types 设置为空
                preparedStatement.setString(6, org_name);
                preparedStatement.setInt(7, 2); // status 
                preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis())); // gmt_created
                preparedStatement.setNull(9, java.sql.Types.VARCHAR); // first_node
                // preparedStatement.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis())); // gmt_modified
                preparedStatement.setString(10, recordSource); // sample_source 
                // 执行插入数据的SQL语句
                preparedStatement.executeUpdate();
                System.out.println("Data inserted into 'Regular_Table' successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Insert_emr_record(String table_name, String doc_type_zh, String doc_type_en, String content, String org_name, String url, String user, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);


                // 定义插入数据的SQL语句
                String insertSQL = "INSERT INTO "+table_name+" (org_name, content,de_tagged,record_type, norm_record_type, record_source, import_type, gmt_created, gmt_modified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                preparedStatement = connection.prepareStatement(insertSQL);

                // 设置参数值
                preparedStatement.setString(1, org_name);
                preparedStatement.setString(2, content);
                preparedStatement.setInt(3, 0);
                preparedStatement.setString(4, doc_type_zh);
                preparedStatement.setString(5, doc_type_zh);
                preparedStatement.setNull(6, java.sql.Types.INTEGER); 
                preparedStatement.setInt(7, 1);
                preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setNull(9, java.sql.Types.TIMESTAMP); // gmt_modified
  
                // 执行插入数据的SQL语句
                preparedStatement.executeUpdate();

                System.out.println("Data inserted into "+table_name+" successfully!");
            


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int Get_content_id(String table_name, String doc_type_zh, String doc_type_en, String content, String org_name, String url, String user, String password){
       // SQL query to get the last row's id
        String sql = "SELECT id FROM "+ table_name +" ORDER BY id DESC LIMIT 1";

        // Variable to store the last id
        int lastId = 0;

        // Establishing a connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // If a result is found, get the id
            if (resultSet.next()) {
                lastId = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId;
    }



    public static void InsertDatabase(String table_name, String doc_type_zh, String doc_type_en, List<Map<String, String>>table, String org_name, int parse_task_id, String url, String user, String password){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (parse_task_id == 0){
            System.err.println("");
        }


        try {
            // 建立连接
            connection = DriverManager.getConnection(url, user, password);


                //插入doc_type_zh
                String insertSQL = "INSERT INTO "+table_name+" (config_node_key, adm_column, match_sample_num, repeat_in_sample, "
                        + "version, parse_task_id, template_config_code, org_name, rectified_time, gmt_created, gmt_modified, "
                        + "first_node, is_regular_expression) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                preparedStatement = connection.prepareStatement(insertSQL);

                // 设置参数值
                preparedStatement.setString(1, doc_type_zh); //文档辨别唯一标识符
                preparedStatement.setString(2, "type");

                preparedStatement.setNull(3, java.sql.Types.INTEGER); // match_sample_num
                preparedStatement.setNull(4, java.sql.Types.INTEGER); // repeat_in_sample
                preparedStatement.setNull(5, java.sql.Types.VARCHAR); // version
                preparedStatement.setInt(6, parse_task_id); // parse_task_id
                preparedStatement.setString(7, doc_type_en);
                preparedStatement.setString(8, org_name);

                preparedStatement.setNull(9, java.sql.Types.TIMESTAMP); // gmt_modified
                preparedStatement.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setNull(11, java.sql.Types.TIMESTAMP); // gmt_modified
                // preparedStatement.setTimestamp(11, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setNull(12, java.sql.Types.VARCHAR); // first_node
                preparedStatement.setNull(13, java.sql.Types.VARCHAR); // is_regular_expression
                preparedStatement.executeUpdate();


            for(Map<String, String> table_line:table){
                // 定义插入数据的SQL语句
                insertSQL = "INSERT INTO "+table_name+" (config_node_key, adm_column, match_sample_num, repeat_in_sample, "
                        + "version, parse_task_id, template_config_code, org_name, rectified_time, gmt_created, gmt_modified, "
                        + "first_node, is_regular_expression) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                preparedStatement = connection.prepareStatement(insertSQL);

                // 设置参数值
                preparedStatement.setString(1, table_line.get("regu"));
                preparedStatement.setString(2, table_line.get("schema"));

                preparedStatement.setNull(3, java.sql.Types.INTEGER); // match_sample_num
                preparedStatement.setNull(4, java.sql.Types.INTEGER); // repeat_in_sample
                preparedStatement.setNull(5, java.sql.Types.VARCHAR); // version
                preparedStatement.setInt(6, parse_task_id); // parse_task_id
                preparedStatement.setString(7, doc_type_en);
                preparedStatement.setString(8, org_name);
                preparedStatement.setNull(9, java.sql.Types.TIMESTAMP); // rectified_time
                preparedStatement.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis()));// gmt_created
                // preparedStatement.setNull(10, java.sql.Types.TIMESTAMP); // gmt_created
                preparedStatement.setNull(11, java.sql.Types.TIMESTAMP); // gmt_modified
                preparedStatement.setNull(12, java.sql.Types.VARCHAR); // first_node
                preparedStatement.setNull(13, java.sql.Types.VARCHAR); // is_regular_expression
                    preparedStatement.setString(12,  table_line.get("root_node"));
                preparedStatement.setString(13, table_line.get("is_regu"));

                // 执行插入数据的SQL语句
                preparedStatement.executeUpdate();

                System.out.println("Data inserted into 'Regular_Table' successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
  public static void main(String[] args) {
   
    }


}
