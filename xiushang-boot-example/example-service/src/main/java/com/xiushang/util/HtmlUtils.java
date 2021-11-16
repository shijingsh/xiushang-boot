package com.xiushang.util;

import org.apache.commons.lang3.StringUtils;

public class HtmlUtils {

     public static String buildHtml(String content){
         if(StringUtils.isNotBlank(content) && content.contains("</html>")){
             //如果存在body则不添加
             return content;
         }
         String html  = "<!DOCTYPE html>\n" +
                 "<html lang=\"en\">\n" +
                 "<head>\n" +
                 "    <meta charset=\"UTF-8\">\n" +
                 "    <title>秀上</title>\n" +
                 "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no\">\n" +
                 "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                 "    <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                 "    <style>\n" +
                 "        *{word-wrap:break-word}\n" +
                 "        body{\n" +
                 "            font-size: 14px;\n" +
                 "        }\n" +
                 "        img{\n" +
                 "            width: 100%;\n" +
                 "        }"+
                 "    </style>\n" +
                 "</head>\n" +
                 "<body><div>\n" +
                    content +"\n" +
                 "</div></body>\n" +
                 "</html>\n";

         return html;
     }
}
