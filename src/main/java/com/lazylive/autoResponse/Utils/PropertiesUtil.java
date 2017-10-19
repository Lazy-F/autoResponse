package com.lazylive.autoResponse.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lazy
 * @create 2017/10/10-17:07
 */
public class PropertiesUtil {

    /**
     * 根据 filepath 读取properties文件，获取 key 所对应的值
     * @param filepath
     * @param key
     * @return
     */
    public static String getValue(String filepath,String key){
        if ("".equals(filepath.trim()) || key == null) return key;

        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filepath), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(key) == null ? key : prop.getProperty(key) ;
    }

    /**
     * 根据 filepath 读取properties文件，转换为 HashMap
     * @param filepath
     * @return
     */
    public static HashMap<String, String> toHashMap(String filepath){
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filepath), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<String, String>((Map)prop);
    }
}
