package com.jian.propertymanagesystem.util;

import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/11 9:48
 * @Version
 */
public class PathUtil {
    public static String seperator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = null;
        if (os.toLowerCase().startsWith("win")){
            basePath = "C:/Users/Mr.qin/Desktop/propertymanagesystem/pictures";
        } else{
            basePath = "/home/jian/picture";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }
}
