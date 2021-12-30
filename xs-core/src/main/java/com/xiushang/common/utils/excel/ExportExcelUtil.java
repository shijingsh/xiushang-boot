package com.xiushang.common.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ExportExcelUtil {


    private Logger logger = LoggerFactory.getLogger(this.getClass());



    public static void initExcel(List<Map<String,Object>> datas, String path){
        initExcel(datas, path, "Sheet1", null, false);
    }


    public static void initExcel(List<Map<String,Object>> datas,String path, String sheetName,Map<String,String> mess,boolean isAutoSizeColumn){
        Workbook wb = new HSSFWorkbook();
        //设置样式
        CellStyle titleStyle = wb.createCellStyle();//创建样式


        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);//居中

        Font font = wb.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(font);


        //设置样式
        CellStyle cellStyle = wb.createCellStyle();//创建样式

//        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//居中


        //创建一个新的表\并创建名称
        Sheet s = null==sheetName?s = wb.createSheet():wb.createSheet(sheetName);

        s.setDefaultColumnWidth(15);

        for(int i=0; i<datas.size(); i++){ //遍历数据

            Map<String,Object> map = datas.get(i);

            //定义列(列名第一行)
            Row titleColl = s.createRow(0);

            //定义行(内容第二行起)
            Row row = s.createRow(i+1);

            Set<Map.Entry<String, Object>> set = map.entrySet();

            Iterator<Map.Entry<String, Object>> it = set.iterator();
            int x = 0;
            while(it.hasNext()){
                //列名
                Map.Entry<String, Object> entry = it.next();
                Cell titleCell = titleColl.createCell(x);
                String titleName = entry.getKey();

                titleCell.setCellValue(null == mess || null == mess.get(titleName) ? titleName : mess.get(titleName));

                //设置列名样式
                titleCell.setCellStyle(titleStyle);
                //内容
                Cell cell = row.createCell(x);

                Object value = entry.getValue();


                cell.setCellValue(value + "");
//                if(value instanceof String){
//                    cell.setCellValue(value.toString());
//                }else if(value instanceof  Double){
//                    cell.setCellValue((Double)value);
//                }else if(value instanceof Integer){
//                    cell.setCellValue((Integer)value);
//                }else if(value instanceof Float){
//                    cell.setCellValue((Float)value);
//                }else if(value instanceof Boolean){
//                    cell.setCellValue((Boolean)value);
//                }else if(value instanceof java.util.Date|value instanceof java.sql.Date){
//                    cell.setCellValue((Date)value);
//                }
                //设置内容样式
                cell.setCellStyle(cellStyle);

                x++;

            }
            //自动调整列宽
            if(isAutoSizeColumn) s.autoSizeColumn(i);
//
        }
        writeToPan(wb, path);
    }


    /**
     * 将文件写入服务器临时文件中
     * @param wb
     * @param path
     */
    public static void writeToPan(Workbook wb, String path) {
        FileOutputStream out;
        try {
            File folder = new File(path.substring(0,path.lastIndexOf("/")));
            if (!folder.exists()) {
                folder.mkdirs();
            }
            out = new FileOutputStream(path);
            try {
                wb.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
