package com.xiushang.common.utils.excel;

import com.xiushang.common.exception.ServiceException;
import com.xiushang.framework.sys.PropertyConfigurer;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理Excel导出
 */
public class ExcelExport<T> {

    private Logger logger = LoggerFactory.getLogger(ExcelExport.class);
    private String title;

    public ExcelExport() {
    }

    public ExcelExport(String title) {
        this.title = title;
    }

    public void setParams( String title) {
        this.title = title;
    }

    /**
     * 导出Excel数据
     *
     * @param headers 表头
     * @param data    表数据
     * @param fields  导出区域和字段
     */
    public String expExcel(String[] headers, List<T> data, String fields[]) {

        // 创建一个工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 创建一个工作表，设定sheet名字
        HSSFSheet sheet = workBook.createSheet(title);

        //创建Excel表头
        createHeader(workBook, sheet, headers);
        //从第二行开始写数据
        createData(sheet, data, fields);
        //保存excel到服务器
        return saveExcel(workBook);
    }

    /**
     * 创建Excel表头
     *
     * @param sheet   当前Excel的Sheet
     * @param headers 表头参数
     */
    public void createHeader(HSSFWorkbook wb, HSSFSheet sheet, String[] headers) {
        // 创建一个单元格，从0开始
        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellStyle(createRedFont(wb));
            cell.setCellValue(text);
        }
    }

    /**
     * 保存Excel文件到服务器里面
     *
     * @param wb 欲导出的Excel文件
     */
    public String saveExcel(HSSFWorkbook wb) {
        OutputStream os = null;
        File file = null;
        try {
            file = createFile();
            os = new FileOutputStream(file);
            wb.write(os);
            os.close();
        } catch (Exception e) {
            logger.debug("export Excel file error :" + e.getMessage());
        }

        return file.getName();
    }

    /**
     * 从第二行开始写数据
     *
     * @param sheet  当前Sheet
     * @param data   查询出来的List数据
     * @param fields 欲显示出来的字段
     */
    private void createData(HSSFSheet sheet, List<T> data, String fields[]) {
        int index = 1;
        Row row = null;

        for (T t : data) {
            row = sheet.createRow(index++);
            for (int i = 0; i < fields.length; i++) {
                PropertyDescriptor pd = null;
                try {
                    pd = new PropertyDescriptor(fields[i], t.getClass());
                } catch (Exception e) {
                    throw new ServiceException("bean中没有属性：" + fields[i]);
                }

                // 得到bean的属性值
                Object attrValue = null;
                try {
                    attrValue = pd.getReadMethod().invoke(t);
                } catch (Exception e) {
                    throw new ServiceException("无法获取bean的属性值：" + pd.getName());
                }
                //设置Row/Cell值
                Cell cell = row.createCell(i);
                // 转成字符串
                if (attrValue==null){
                    attrValue = "";
                }
                String cellValue = "";
                if (attrValue instanceof Date) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = df.format(attrValue);
                    cell.setCellValue(cellValue);
                }else if (Double.class.isAssignableFrom(attrValue.getClass())) {
                    Double num = (Double)attrValue;
                    cell.setCellValue(num);
                }else if (Integer.class.isAssignableFrom(attrValue.getClass())) {
                    Integer num = (Integer)attrValue;
                    cell.setCellValue(num);
                }else if (BigDecimal.class.isAssignableFrom(attrValue.getClass())) {
                    BigDecimal num = (BigDecimal)attrValue;
                    cell.setCellValue(num.doubleValue());
                } else {
                    if (attrValue != null) {
                        cellValue = attrValue.toString();
                        cell.setCellValue(cellValue);
                    }
                }


            }
        }
    }

    /**
     * 设置Excel表头字体颜色
     *
     * @param wb Excel文件
     * @return: 字体对象
     */
    private HSSFCellStyle createRedFont(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return style;
    }


    /**
     * 创建Excel文件
     *
     * @return: File  Excel文件
     */
    private File createFile() {
        String fileName = title;
        String path = PropertyConfigurer.getContextProperty("temppath") + fileName;
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("create file: {}", path);
            }
        }
        return file;
    }

}
