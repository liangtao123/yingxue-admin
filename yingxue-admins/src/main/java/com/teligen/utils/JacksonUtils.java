package com.teligen.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author liangtao
 * @date 2023-03-12 22:43
 * @desc: 封装JackSon工具类
 */
public class JacksonUtils {

    public static String writeValue(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        }catch (JsonProcessingException e){
           e.printStackTrace();
        }
        return null;
    }
}
