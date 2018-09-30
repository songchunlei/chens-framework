package org.chens.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * FastJson工具类
 *
 * @author songchunlei
 * @since 2018/9/17
 */
public class FastJsonUtil {

    private static final String LEFT_BRACES = "{";

    private static final String LEFT_BRACKETS = "[";

    public static String toJSONString(Object obj) {
        return toJSONString(obj, SerializeConfig.getGlobalInstance(), SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toJSONString(Object obj, SerializeConfig serializeConfigs,
                                      SerializerFeature... serializerFeatures) {
        return toJSONString(obj, serializeConfigs, null, serializerFeatures);
    }

    /**
     * 把json解析为Java对象 AllowUnQuotedFieldNames 允许单引号来包住属性名称和字符串值
     * 
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        if (!json.trim().startsWith(LEFT_BRACES)) {
            return null;
        }
        return JSON.parseObject(json, clazz, Feature.AllowUnQuotedFieldNames);
    }

    /**
     * 把json数组解析为List
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        if (!json.trim().startsWith(LEFT_BRACKETS)) {
            return new ArrayList<>();
        }
        return JSON.parseArray(json, clazz);
    }

    /**
     *
     * @param jsonArray
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(JSONArray jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (null == jsonArray || jsonArray.isEmpty()) {
            return list;
        }
        for (int inc = 0; inc < jsonArray.size(); inc++) {
            list.add(jsonArray.getObject(inc, clazz));
        }
        return list;
    }

    /**
     * 把json解析为map AllowUnQuotedFieldNames 允许单引号来包住属性名称和字符串值
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> parseMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return new HashMap<>();
        }
        if (!json.trim().startsWith(LEFT_BRACES)) {
            return new HashMap<>();
        }
        return JSON.parseObject(json, Feature.AllowUnQuotedFieldNames);
    }

    /**
     * 把json数组解析为List<Map>
     *
     * @param json
     * @return
     */
    public static List<Map<String, Object>> parseListMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        List<JSONObject> list = parseList(json, JSONObject.class);
        List<Map<String, Object>> result = new ArrayList<>();
        for (JSONObject jsonObject : list) {
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * JSON转String,使用 DisableCircularReferenceDetect 避免$ref
     * 
     * @param obj
     * @param serializeConfigs
     * @param filters
     * @param serializerFeatures
     * @return
     */
    public static String toJSONString(Object obj, SerializeConfig serializeConfigs, SerializeFilter[] filters,
            SerializerFeature... serializerFeatures) {
        if (null != serializerFeatures && 0 != serializerFeatures.length) {
            boolean flg = false;
            for (SerializerFeature serializerFeature : serializerFeatures) {
                if (serializerFeature.getMask() == SerializerFeature.DisableCircularReferenceDetect.getMask()) {
                    // 已经包含SerializerFeature.DisableCircularReferenceDetect
                    flg = true;
                }
            }
            if (!flg) {
                // 所有操作必须加上SerializerFeature.DisableCircularReferenceDetect
                List<SerializerFeature> featureList = new ArrayList<>(Arrays.asList(serializerFeatures));
                featureList.add(SerializerFeature.DisableCircularReferenceDetect);
                serializerFeatures = featureList.toArray(serializerFeatures);
            }
        } else {
            serializerFeatures = new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect };
        }
        if (null == filters) {
            return JSON.toJSONString(obj, serializeConfigs, serializerFeatures);
        }
        return JSON.toJSONString(obj, serializeConfigs, filters, serializerFeatures);
    }
}
