package com.zjmzxfzhl.common.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson工具类
 * 
 * @author 庄金明
 * 
 */
public class JacksonUtil {

    /**
     * 对象转str
     * 
     * @param obj
     * @param include
     * @return
     */
    public static String objToStr(Object obj, Include include) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (include != null) {
                objectMapper.setSerializationInclusion(include);
            }
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转str
     * 
     * @param obj
     * @return
     */
    public static String objToStr(Object obj) {
        return objToStr(obj, null);
    }

    /**
     * str转对象(简单对象)
     * 
     * @param str
     * @param clazz
     * @return
     */
    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(str, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * str转对象(复杂对象，List，Map等)
     * 
     * @param str
     * @param typeReference
     *            【例1：单个对象】SysConfig sysConfig2 = strToObj(str, new TypeReference<SysConfig>() { });
     * 
     *            【例2：list对象】List<SysConfig> list = strToObj(str, new TypeReference<List<SysConfig>>() { });
     * 
     *            【例3：map对象】Map<String, SysConfig> map = strToObj(str, new TypeReference<Map<String, SysConfig>>() { });
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T strToObj(String str, TypeReference<T> typeReference) {
        try {
            // 空值返回null
            if (StringUtils.isEmpty(str)) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return (T) objectMapper.readValue(str, typeReference);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
