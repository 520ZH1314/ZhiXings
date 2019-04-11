package com.zhixing.netlib.base;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
/**
 *
 *@author zjq
 *create at 2019/1/16 下午4:37
 */
public class BaseHttpUtil<T> {

    /**
     * vo转换为Map
     *
     * @param
     * @return
     */
    public RequestBody convertVo2Json(T t) {
        Map maps = convertVo2Params(t);
        JSONObject object = new JSONObject(maps);
        return RequestBody.create(MediaType.parse("Content-Type, application/json"),
                object.toString());
    }

    //特殊情况


    public RequestBody toJson(String s)  {

        try {
            JSONArray jsonArray =new JSONArray(s);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("Content-Type, application/json"),
                s);
    }




    /**
     * vo转换为Map
     *
     * @param vo
     * @return
     */
    public Map<String, String> convertVo2Params(T vo) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(gson.toJson(vo), type);
    }

    //先转换为map
    public Map convertVo2Map(T t) {
        Map maps = convertVo2Params(t);
        return  maps;
    }
    //将map组合之后转成json
    public RequestBody convertVo2Json(Map map){
        JSONObject object = new JSONObject(map);
        return RequestBody.create(MediaType.parse("Content-Type, application/json"),
                object.toString());
    }



    /**
     * 多文件转换为 Map<String, RequestBody> bodyMap
     *
     * @param files
     * @param mediaType 文件类型
     * @return
     */
    public static Map<String, RequestBody> convertVo2BodyMap(List<File> files, String mediaType) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            bodyMap.put("file" + i + "\"; filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse(mediaType), files.get(i)));
        }
        return bodyMap;
    }



    /**
     *
     *@author zjq
     *create at 2019/4/9 下午1:57
     * 单文件
     */


    public static MultipartBody.Part SingleFlie(File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        return  body;

    }



}
