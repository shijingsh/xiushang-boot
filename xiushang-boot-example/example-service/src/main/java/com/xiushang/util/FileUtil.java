package com.xiushang.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class FileUtil
{

    /**
     * 文件类型
     *       const fileType = {
     *         'img': ['bmp', 'gif', 'jpg', 'png', 'jpeg', 'psd', 'svg', 'webp'],
     *         'doc': ['docx', 'doc'],
     *         'txt': ['txt'],
     *         'xml': ['xml'],
     *         'xls': ['xls', 'xlsx'],
     *         'ppt': ['pptx', 'ppt'],
     *         'zip': ['rar', 'zip', 'tar', 'gz', '7z', 'bz2', 'arj', 'z'],
     *         'pdf': ['pdf'],
     *         'video': ['flv', 'swf', 'mkv', 'avi', 'rm', 'rmvb', 'mpeg', 'mpg', 'ogv', 'mov', 'wmv', 'mp4', 'webm', 'swf'],
     *         'audio': ['mp3', 'wav', 'ogg', 'aif', 'au', 'ram', 'wma', 'mmf', 'amr', 'aac', 'flac']
     *       };
     */
    private static Map<String,String> map = new HashMap<>();
    static {
        {
            map.put("bmp","img");
            map.put("gif","img");
            map.put("jpg","img");
            map.put("png","img");
            map.put("jpeg","img");
            map.put("psd","img");
            map.put("svg","img");
            map.put("webp","img");
        }

        {
            map.put("docx","doc");
            map.put("doc","doc");

            map.put("txt","txt");
            map.put("xml","xml");
            map.put("pdf","pdf");

            map.put("xls","xls");
            map.put("xlsx","xls");

            map.put("pptx","ppt");
            map.put("ppt","ppt");
        }

        {
            map.put("rar","zip");
            map.put("zip","zip");
            map.put("tar","zip");
            map.put("gz","zip");
            map.put("7z","zip");
            map.put("bz2","zip");
            map.put("arj","zip");
            map.put("z","zip");
        }

        {
            map.put("flv","video");
            map.put("swf","video");
            map.put("mkv","video");
            map.put("avi","video");
            map.put("rm","video");
            map.put("rmvb","video");
            map.put("mpeg","video");
            map.put("mpg","video");
            map.put("ogv","video");
            map.put("mov","video");
            map.put("wmv","video");
            map.put("mp4","video");
            map.put("webm","video");
        }

        {
            map.put("mp3","audio");
            map.put("wav","audio");
            map.put("ogg","audio");
            map.put("aif","audio");
            map.put("au","audio");
            map.put("ram","audio");
            map.put("wma","audio");
            map.put("mmf","audio");
            map.put("amr","audio");
            map.put("aac","audio");
            map.put("flac","audio");
        }
    }

    public static String getFileType(String path) {
        String[] strArray = path.split("\\.");
        int suffixIndex = strArray.length -1;
        //System.out.println(strArray[suffixIndex]);

        String suffix = strArray[suffixIndex];

        String type = map.get(suffix);
        if(StringUtils.isNotBlank(type)){
            return type;
        }
        return suffix;
    }

    public static String getFileName(String path) {
        if (StringUtils.isBlank(path)){
            return null;
        }
        int index = path.lastIndexOf("\\");
        if(index==-1){
            index = path.lastIndexOf("/");
        }

        String fileNameNow = path.substring(index+1);
        //System.out.println(fileNameNow);

        return fileNameNow;
    }

    public static void main(String args[]){
        String path = "/null/20201127/mg1606446231666761011.xlsx";
        System.out.println(getFileName(path));
        System.out.println(getFileType(path));

        String originalFilename = "IMG_161423913198239_compressed.jpg";
        if(StringUtils.isNotBlank(originalFilename)){
            originalFilename = originalFilename.replaceAll("_compressed","");
        }
        System.out.println(originalFilename);
    }
}
