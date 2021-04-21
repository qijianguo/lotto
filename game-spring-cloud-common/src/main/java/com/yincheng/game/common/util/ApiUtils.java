package com.yincheng.game.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class ApiUtils {

    public static String concatSignString(Map<String, String> map) throws UnsupportedEncodingException {
        Map<String, String> paramterMap = new HashMap<>(12);
        map.forEach((key, value) -> {
            if (isJSONValid(value)) {
                // 排序后 拼接成json重新put
                Map<String, String> map1 = jsonToMap(value);
            } else {
                paramterMap.put(key, value);
            }
        });
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            String k = keyArray[i];
            if (paramterMap.get(k).length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(URLEncoder.encode(paramterMap.get(k), "UTF-8"));
                if (i < keyArray.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    /**
     * JSON转顺序排序的Map(为了方便后期获取数据,应答就不返回JSON字符串了,可自行去转换)
     *
     * @param jsonStr 原始json
     * @return 响应的map
     */
    public static Map<String, String> jsonToMap(String jsonStr) {
        if (!isJSONValid(jsonStr)) {
            return null;
        }
        Map<String, String> treeMap = new TreeMap();
        //Feature.OrderedField实现解析后保存不乱序
        JSONObject json = JSONObject.parseObject(jsonStr, Feature.OrderedField);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            Object value = json.get(key);
            //判断传入kay-value中是否含有""或null
            if (json.get(key) == null || value == null || value.toString().length() == 0) {
                //当JSON字符串存在null时,不将该kay-value放入Map中,即显示的结果不包括该kay-value
                continue;
            }
            // 判断是否为JSONArray(json数组)
            if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                List<Object> arrayList = new ArrayList<>();
                for (Object object : jsonArray) {
                    // 判断是否为JSONObject，如果是 转化成TreeMap
                    if (object instanceof JSONObject) {
                        object = jsonToMap(object.toString());
                    }
                    arrayList.add(object);
                }
                treeMap.put(key, JSONObject.toJSONString(arrayList));
            } else {
                //判断该JSON中是否嵌套JSON
                boolean flag = isJSONValid(value.toString());
                if (flag) {
                    //若嵌套json了,通过递归再对嵌套的json(即子json)进行排序
                    value = jsonToMap(value.toString());
                    treeMap.put(key, JSONObject.toJSONString(value));
                } else {
                    treeMap.put(key, value.toString());
                }
            }
        }
        return treeMap;
    }

    /**
     * 校验是否是JSON字符串
     *
     * @param json 传入数据
     * @return 是JSON返回true, 否则false
     */
    public final static boolean isJSONValid(String json) {
        try {
            JSONObject.parseObject(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

}