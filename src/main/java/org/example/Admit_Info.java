package org.example;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Admit_Info {
    public static List<String> get_rawSegKeySet(){
        List<String> rawSegKeySet=new ArrayList<>();
        rawSegKeySet.add("nonkey_contents:");
        rawSegKeySet.add("入院记录");
        rawSegKeySet.add("姓名：");
        rawSegKeySet.add("性别：");
        rawSegKeySet.add("年龄：");
        rawSegKeySet.add("科别：");
        rawSegKeySet.add("床号：");
        rawSegKeySet.add("病案号：");
        rawSegKeySet.add("姓名：");
        rawSegKeySet.add("性别：");
        rawSegKeySet.add("现住址：");
        rawSegKeySet.add("年龄：");
        rawSegKeySet.add("工作单位：");
        rawSegKeySet.add("婚姻：");
        rawSegKeySet.add("入院时间：");
        rawSegKeySet.add("民族：");
        rawSegKeySet.add("记录时间：");
        rawSegKeySet.add("职业：");
        rawSegKeySet.add("病史描述者：");
        rawSegKeySet.add("出生日期：");
        rawSegKeySet.add("联系人：");
        rawSegKeySet.add("联系人电话：");
        rawSegKeySet.add("联系方式：");
        rawSegKeySet.add("主诉：");
        rawSegKeySet.add("现病史：");
        rawSegKeySet.add("既往史：");
        rawSegKeySet.add("预防接种史");
        rawSegKeySet.add("个人史：");
        rawSegKeySet.add("婚姻史：");
        rawSegKeySet.add("家族史：");
        rawSegKeySet.add("体格检查");
        rawSegKeySet.add("专科情况");
        rawSegKeySet.add("辅助检查");
        rawSegKeySet.add("出院诊断：");
        rawSegKeySet.add("初步诊断：");
        return rawSegKeySet;
    }

    public static ArrayList<ArrayList<String>> get_normResultGuide(){
        ArrayList<ArrayList<String>> normResultGuide = new ArrayList<>();
        normResultGuide.add(new ArrayList<>(Arrays.asList("patient_name", "姓名：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("age", "年龄：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("gender_name", "性别：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("occupation_type_name", "职业：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("company_address", "工作单位：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("residence_name", "现住址：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("contact_tel_no", "联系人电话：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("tel_no", "联系方式：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("id_no")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("emr_id")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("contact_name", "联系人：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("contact_relation_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("contact_address")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("education_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("birth_date", "出生日期：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("birth_address_full", "出生地：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("household_address")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("native_address")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("marriage_status_name", "婚姻 ：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("medical_history_narrator_name", "病史描述者：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("narrator_reliable_flag")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("narrator_relation_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("hist_reliability")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("admit_date", "入院时间：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("discharge_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("record_date", "记录时间：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("keeper_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("nation_name", "民族：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("religion")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("nationality_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("school_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("chief_complaints", "主 诉：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("present_illness_history", "现病史：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("past_history", "既往史：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("personal_history", "个人史：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("marital_history", "婚姻史：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("last_menstrual_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("birth_times")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("due_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("husband_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("menstrual_history")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("family_history", "家族史：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("physical_exam", "体 格 检 查")));  // 内含value key_metrics parse_result
        normResultGuide.add(new ArrayList<>(Arrays.asList("specialty_situation", "专 科 情 况")));    // 需要二级解析
        normResultGuide.add(new ArrayList<>(Arrays.asList("assistant_exam_item", "辅 助 检 查")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("system_review")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("primary_diag", "初步诊断：")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("admit_diag_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("primary_diagnosis")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("datreat_plan")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("correction_diag")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("correction_diag_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("ascertain_wm_diag_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("ascertain_diag_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("supplement_wm_diag_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("supplement_diag_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("sign_date")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("recorder_name")));
        normResultGuide.add(new ArrayList<>(Arrays.asList("other", "入院记录", "科别：", "床号：", "病案号：", "出院诊断：")));     // 此处需要一个other_list
        normResultGuide.add(new ArrayList<>(Arrays.asList("sign_date")));
        return normResultGuide;
    }

    public static JSONObject genFinalData(JSONObject normResult, List<List<String>> rawSeg, String doc_type) {

        JSONObject finalData = new JSONObject();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        result = new JSONObject();
        result.put(Main.normResult_Mark, normResult);
        result.put("recordType", doc_type);
        result.put("record_title", JSONObject.NULL);
        result.put("record_date", JSONObject.NULL);
        result.put("norm_res_sub_seg", new JSONObject());
        result.put(Main.rawSeg_Mark, rawSeg);
        result.put("msg", "ok");
        result.put("code", 0);
        finalData.put("code", 0);
        data = new JSONObject();
        data.put("sample_rate", JSONObject.NULL);
        data.put("sample_count", JSONObject.NULL);
        data.put("result", result);
        finalData.put("data", data);
        return finalData;
    }

}
