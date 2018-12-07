package com.shuben.zhixing.www.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kaifa on 2015/7/2.
 */
public class JSONUtil {
    public static String getStringValue(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null)
            return null;
        return jsonObject.optString(key);
    }
    public static int getIntValue(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null)
            return -1;
        return jsonObject.optInt(key);
    }

    public static JSONObject String2JSONObject(String json) {
        JSONObject jsonObject = null;
        if (!StringUtils.notNull(json)) {
            return null;
        }
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray String2JSONArray(String json) {
        JSONArray jsonArray = null;
        if (!StringUtils.notNull(json)) {
            return null;
        }
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null)
            return null;
        return jsonObject.optJSONArray(key);
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        if (jsonObject == null || key == null)
            return null;
        return jsonObject.optJSONObject(key);
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int i) {
        if (jsonArray == null )
            return null;
        return jsonArray.optJSONObject(i);
    }

    public static JSONObject Map2JSONObject(Map<String, String> map) {
        if (map == null)
            return null;
        return new JSONObject(map);
    }

    public static List<String> getValueForArray(JSONArray jsonArray, String key) {
        if (jsonArray == null || key == null)
            return null;
        List<String> list=new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add( jsonArray.getJSONObject(i).optString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static  JSONArray putObj2Arr(JSONArray jsonArray,JSONObject jsonObject){
        if (jsonArray == null || jsonObject == null)
            return jsonArray;
        jsonArray.put(jsonObject);
        return jsonArray;
    }


    public static JSONObject putJSONObject(JSONObject jsonObject,String key,String Value){
        if(jsonObject==null){
            jsonObject=new JSONObject();
        }
        try {
            jsonObject.put(key,Value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
