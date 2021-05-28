package com.mg.common.utils.excel;

import com.mg.common.utils.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ImportExcelUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static List<Map<String, Object>> importExcel(String path) throws FileNotFoundException, IOException, InvalidFormatException {

        return importExcel(path,0);
    }

    public static List<Map<String, Object>> importExcel(String path,int sheetIndex) throws FileNotFoundException, IOException, InvalidFormatException {

        Workbook wb = null;
        // 1. 打开excel
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            wb = WorkbookFactory.create(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileInputStream.close();
        }

        Sheet sheet = wb.getSheetAt(sheetIndex);


        Map<Integer, String> header = new HashMap<>();

        List<Map<String, Object>> datas = new ArrayList<>();

        Row row_ = sheet.getRow(0);
        for (int j = 0; j < row_.getPhysicalNumberOfCells(); j++) {
            Cell cell = row_.getCell(j);
            header.put(j, cell.getStringCellValue());
        }
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (i == 0) {
                continue;
            }

            Map<String, Object> data = new LinkedHashMap<>();

            for (int j = 0; j < row_.getPhysicalNumberOfCells(); j++) {

                data.put(header.get(j), null);

                Cell cell = row.getCell(j);

                if (cell == null) continue;

                cell.setCellType(Cell.CELL_TYPE_STRING);
                Object cellValue = ExcelUtil.getStringValue2007(cell);
                data.put(header.get(j), cellValue);
            }
            datas.add(data);
        }
        return datas;
    }
    /**
     * 将double类型的结果保留两位小数
     * @param score
     *        分数
     * @return
     *       保留两位小数的结果值
     */
    public static double round2Decimal(double score) {
        BigDecimal bg = new BigDecimal(score);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
