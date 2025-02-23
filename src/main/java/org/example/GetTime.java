package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetTime {
    public static String getCurrentTimeFormatted() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        
        // 格式化时间并返回字符串
        return now.format(formatter);
    }   
}
