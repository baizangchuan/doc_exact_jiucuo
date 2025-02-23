package org.example;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class Template {

    public static void Template_modify(String doc_type, JSONObject normResult,List<List<String>> rawSeg, String content){
        switch (doc_type) {
            case "admit_info":
                normResult=Modify_admit_info(doc_type, normResult, rawSeg, content);
                break;

            case "discharge_info":
                normResult=Modify_discharge_info(doc_type, normResult, rawSeg, content);
                break;

            case "first_case_info":
                normResult=Modify_first_case_info(doc_type, normResult, rawSeg, content);
                break;

            case "operation_info":
                normResult=Modify_operation_info(doc_type, normResult, rawSeg, content);
                break;

            case "case_info":
                normResult=Modify_case_info(doc_type, normResult, rawSeg, content);
                break;
                
            case "inform_info":
                normResult=Modify_inform_info(doc_type, normResult, rawSeg, content);
                break;
            
            case "postoperative_first_case_info":
                normResult=Modify_postoperative_first_case_info(doc_type, normResult, rawSeg, content);
                break;

            case "new_template":
                normResult=Modify_new_template(doc_type, normResult, rawSeg, content);
                break;

            default:
                break;
        }
    }

    public static JSONObject Modify_admit_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){
        List<String> keyList = new ArrayList<>(List.of("T","P","R","BP"));
        String endWord = "生命征";
        List<JSONObject> keyMetrics = InfoExtracter.subExtract(rawSeg, "体格检查", keyList, endWord);
        JSONObject physical_exam = new JSONObject();
        
        //获取physical_exam原来的内容
        JSONArray physical_exam_org=normResult.getJSONArray("physical_exam");
        for (int i = 0; i < physical_exam_org.length(); i++) {
            physical_exam.put("key",physical_exam_org.getJSONObject(i).getString("key"));
            physical_exam.put("value",physical_exam_org.getJSONObject(i).getString("value"));
        }

        //加上二级解析的内容
        physical_exam.put("physical_exam", keyMetrics);

        //少了个parse_result，这里之后补上
        physical_exam.put("parse_result", new JSONObject());


        //把新增的东西放回normResult里
        JSONArray array_temp = new JSONArray();
        array_temp.put(physical_exam);
        normResult.put("physical_exam", array_temp);

        return normResult;
    }

    public static JSONObject Modify_discharge_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){

        return normResult;
    }

    public static JSONObject Modify_first_case_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){
        List<String> keyList = new ArrayList<>(List.of("T","P","R","BP"));
        String endWord = "mmHg";
        List<JSONObject> keyMetrics = subExtract(rawSeg, "体格检查", keyList, endWord);
        normResult.put("key_metrics", keyMetrics);//这里的第四个结果缺个单位，要补一下
        return normResult;
    }

    public static JSONObject Modify_operation_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){

        return normResult;
    }

    public static JSONObject Modify_case_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){
        //在normResult中增加一个subjective
        String subjective_value = InfoExtracter.extractContent(content, "姓名：", "；").trim().replaceAll("\t", "");
        JSONObject subjective = new JSONObject();
        
        subjective.put("key", "nonkey");
        subjective.put("value", subjective_value);
        JSONArray array_temp = new JSONArray();
        array_temp.put(subjective);
        normResult.put("subjective", array_temp);


        JSONObject assessment = new JSONObject();
        assessment.put("key", "nonkey");
        assessment.put("value", "");
        array_temp = new JSONArray();
        array_temp.put(assessment);
        normResult.put("assessment", array_temp);


        JSONObject plan = new JSONObject();
        plan.put("key", "nonkey");
        plan.put("value", "");
        array_temp = new JSONArray();
        array_temp.put(plan);
        normResult.put("plan", array_temp);

        JSONObject roles = new JSONObject();
        roles.put("key", "nonkey");
        roles.put("value", "");
        array_temp = new JSONArray();
        array_temp.put(roles);
        normResult.put("roles", array_temp);

        //二级解析
        List<String> keyList = new ArrayList<>(List.of("T","P","R","BP"));
        String endWord = "mmHg";
        List<JSONObject> keyMetrics = subExtract(rawSeg, "体格检查", keyList, endWord);
        normResult.put("key_metrics", keyMetrics);//这里的第四个结果缺个单位，要补一下
        System.out.println(keyMetrics);
        System.err.println("---sadasdasdasdasdas");

        return normResult;
    }

    public static JSONObject Modify_inform_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){

        return normResult;
    }

    public static JSONObject Modify_postoperative_first_case_info(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){
        //二级解析
        List<String> keyList = new ArrayList<>(List.of("T","P","R","BP"));
        String endWord = "mmHg";
        List<JSONObject> keyMetrics = subExtract(rawSeg, "体格检查", keyList, endWord);
        normResult.put("key_metrics", keyMetrics);//这里的第四个结果缺个单位，要补一下
        return normResult;
    }

    public static JSONObject Modify_new_template(String doc_type, JSONObject normResult, List<List<String>> rawSeg, String content){
        //写上要加上的解析规则

        return normResult;
    }
    

        //确立最终文件的格式
        public static JSONObject genFinalData(JSONObject normResult, List<List<String>> rawSeg, String doc_type) {

            String record_title="";
            switch (doc_type) {
                case "admit_info":
                    record_title="入院记录";
                    break;
                case "discharge_info":
                    record_title="出院记录";
                    break;
                case "first_case_info":
                    record_title="首次病程记录";
                    break;
                case "operation_info":
                    record_title="手术记录";
                    break;
                case "case_info":
                    record_title="病程记录";
                    break;
                case "inform_info":
                    record_title="手术知情同意书"; //这里可能会变，需要的话做个参数传进去
                    break;
                case "postoperative_first_case_info":
                    record_title="术后首次病程记录";
                    break;
                case "new_template":
                    record_title="术后首次病程记录";
                    break;
                default:
                    record_title="";
                    break;
            }
            JSONObject finalData = genFinalData_Template(normResult, rawSeg, doc_type, record_title);

            return finalData;
        }

        public static JSONObject genFinalData_Template(JSONObject normResult, List<List<String>> rawSeg, String doc_type, String record_title){
            JSONObject finalData = new JSONObject();
            JSONObject result = new JSONObject();
            JSONObject data = new JSONObject();
            result = new JSONObject();
            result.put("recordType", doc_type);
            result.put("record_title", record_title);
            result.put("record_date", JSONObject.NULL);
            result.put("doctor_titles", "");
            result.put("norm_result", normResult);
            result.put("normResSubSeg", new JSONObject());
            result.put("raw_seg", rawSeg);
            result.put("msg", "ok");
            result.put("code", "0");

            data = new JSONObject();
            data.put("result", result);
            data.put("sample_rate", JSONObject.NULL);
            data.put("sample_count", JSONObject.NULL);

            finalData.put("data", data);
            return finalData;

        }

        //二级解析
        public static List<JSONObject> subExtract(List<List<String>> rawSeg, String rawSegItemName, List<String> keyList, String endWord) {
            String content = "";
            for (List<String> item : rawSeg) {
                if (item.get(0).contains(rawSegItemName)) {
                    content = item.get(1);
                    break;
                }
            }
            keyList.add(endWord);
            List<JSONObject> extractList = new ArrayList<>();
            for (int i = 0; i < keyList.size() - 1; i++) {
                String startMarker = keyList.get(i);
                String endMarker = keyList.get(i + 1);
                String value = InfoExtracter.extractContent(content, startMarker, endMarker).trim().replaceAll("\t", "");
                JSONObject extractItem = new JSONObject();
                extractItem.put("key", keyList.get(i));
                extractItem.put("value", value);
                extractList.add(extractItem);
            }
            return extractList;
        }

        // 在normResult里指定schema，添加内容
        public static JSONObject Add_schema(JSONObject normResult,JSONObject new_content,  String schema_name){

            JSONObject schema = new JSONObject();
            
            //获取schema原来的内容
            JSONArray schema_org=normResult.getJSONArray(schema_name);
            for (int i = 0; i < schema_org.length(); i++) {
                schema.put("key",schema_org.getJSONObject(i).getString("key"));
                schema.put("value",schema_org.getJSONObject(i).getString("value"));
            }
    
            //加上二级解析的内容
            schema.put(schema_name, new_content);
    
    
            //把新增的东西放回normResult里
            JSONArray array_temp = new JSONArray();
            array_temp.put(schema);
            normResult.put(schema_name, array_temp);

            return normResult;
        }
}
