package com.xiushang.common.utils.excel;

import com.xiushang.common.utils.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ImportExcelUtil {


    public static List<Map<String, Object>> importExcel(String path) throws FileNotFoundException, IOException, InvalidFormatException {

        return importExcel(path,0);
    }


    public static List<Map<String, Object>> importExcel(String path,int sheetIndex) throws FileNotFoundException, IOException, InvalidFormatException {

        Workbook wb = createWorkBook(path);

        if(wb!=null){
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

        return new ArrayList<>();
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

    /**
     * filePath是文件的网络路径
     * @param filePath 网络路径，比如：https://www.aime.cc/null/shop_import.xlsx
     * @return
     * @throws IOException
     */
    protected static Workbook createWorkBook(String filePath) throws IOException {

        Workbook wb = null;
        // 1. 打开excel
        InputStream inputStream = null;
        try {
             inputStream = getInputStream(filePath);
            wb = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return wb ;
    }

    public static InputStream getInputStream(String url) throws IOException {
        // url = url.replace(" ", "%20");
        /*String[] split = fileName.split("\\.");
        if (split.length > 0) {
            fileName = split[0];
        }*/
        // URLEncoder主要解决文件名称是中文的问题
        //String newFileName = URLEncoder.encode(fileName, "UTF-8");
        url = url.replace(" ", "%20");
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            // new BufferedInputStream(inputStream)主要解决HttpInputStream不支持.mark(int)的问题
            return new BufferedInputStream(inputStream);
        }
        return null;
    }


}
