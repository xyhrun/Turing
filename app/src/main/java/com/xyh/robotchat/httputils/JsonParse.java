package com.xyh.robotchat.httputils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 向阳湖 on 2016/6/27.
 */
public class JsonParse {
    private static final String TAG = "JsonParse";
    //解析出机器人的消息
    public static String jsonParse(String result) {
        try {
            JSONObject root = new JSONObject(result);
            String content = root.getString("text");
            return content;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Map<String, String>> jsonParseWeather(String result) {
        try {
            List<Map<String, String>> datas = new ArrayList<>();
            JSONObject root = new JSONObject(result);
            JSONArray array_results = root.getJSONArray("results");
            for (int i = 0; i < array_results.length(); i++) {
                JSONObject resultObject = array_results.getJSONObject(i);
                JSONArray array_weather_data = resultObject.getJSONArray("weather_data");
                for (int j = 0; j < array_weather_data.length(); j++) {
                    Map<String, String> map = new HashMap<>();
                    JSONObject object = array_weather_data.getJSONObject(i);
                    String date = object.getString("date");
                    String weather = object.getString("weather");
                    String wind = object.getString("wind");
                    String temperature = object.getString("temperature");
                    map.put("date", date);
                    map.put("weather", weather);
                    map.put("wind", wind);
                    map.put("temperature", temperature);
                    datas.add(map);
                }
            }
            return datas;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
