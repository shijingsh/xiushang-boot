package com.xiushang.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by liukefu on 2015/10/21.
 */
public class ExcelUtil {

    public static String getStringValue2007(XSSFCell cell) {
        if (cell == null) {
            return null;
        }

        String value = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }
        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(cell.getDateCellValue());
                } else {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                break;
        }
        if (StringUtils.isNotBlank(value)) {
            value = value.trim();
        }

        return value;
    }


    public static String getStringValue2007(Cell cell) {
        if (cell == null) {
            return null;
        }

        String value = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }

        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:
                value = null;
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(cell.getDateCellValue());
                } else {
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                break;
        }
        if (StringUtils.isNotBlank(value)) {
            value = value.trim();
        }

        return value;
    }

    public static Object getValue2007(XSSFCell cell) {
        if (cell == null) {
            return null;
        }

        Object value = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }

        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:
                value = null;
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }

        return value;
    }

    public static Object getValue2007(Cell cell) {
        if (cell == null) {
            return null;
        }

        Object value = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }

        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:
                value = null;
                break;
            case Cell.CELL_TYPE_STRING:
                value = StringUtils.trim(cell.getRichStringCellValue().getString());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return value;
    }


    /**
     * 创建文件夹
     */
    public static void makeDirs(String path) {
        File file = new File(path);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }


    public static void setCellValue(Cell cell,Object value){

        if(String.class.isAssignableFrom(value.getClass())){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(String.valueOf(value));
        }else if(Integer.class.isAssignableFrom(value.getClass())){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Integer)value);
        }else if(Double.class.isAssignableFrom(value.getClass())){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Double)value);
        }else if(Long.class.isAssignableFrom(value.getClass())){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Long)value);
        }else if(Float.class.isAssignableFrom(value.getClass())){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Float)value);
        }else{
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(String.valueOf(value));
        }
    }
}
