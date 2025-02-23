package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class test {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> normResultGuide = new ArrayList<>();
        normResultGuide.add(new ArrayList<>(Arrays.asList("patient_name", "姓名：")));
        System.out.println(normResultGuide);

    }
}
