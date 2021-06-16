package com.auto.modal.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020/9/2 01:12
 */
public class ReadExcelUtils {
    public static List<Map<String, String>> readSHash(String path) throws IOException {
        List<HashMap<Integer, String>> list = new ArrayList<>();
        ExcelReaderUtil excel = null;
        try {
            excel = new ExcelReaderUtil();
            excel.readOneSheet(path, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excel.getDataList();
    }
    public static List<Map<String, String>>readSXHash(String path) throws IOException{
        List<Map<String, String>> list = new ArrayList<>();
        InputStream fileIn = null;
        HSSFWorkbook workbook = null;
        try {
            // 1.创建文件输入流
            fileIn = new FileInputStream(path);
            // 2.创建Excel工作簿对象
            workbook = new HSSFWorkbook(fileIn);
            // 校验excel中的工作表是否存在
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets <= 0) {
                return null;
            }
            // 3.获取Excel工作表对象
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            // 工作表中至少要有一行数据
            if (sheetAt.getLastRowNum() < 0) {
                return null;
            }

            // 4.循环读取表格数据
            for (Row row : sheetAt) {
                // 首行（即表头）不读取,后面可以校验表头
                if (row.getRowNum() == 0) {
                    //continue;
                }
                Map<String, String> map=new HashMap<>();
                for(int j=0;j<row.getLastCellNum();j++){
                    String key=CellReference.convertNumToColString(j);
                    String value=row.getCell(j).getStringCellValue();
                    map.put(key,value);
                }
                list.add(map);
            }
        } finally {
            // 5.关闭流
            if (fileIn != null) {
                fileIn.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
        return list;
    }
    public static List<HashMap<Integer, String>> readHash(String path) throws IOException {
        List<HashMap<Integer, String>> list = new ArrayList<>();
        try {
            ExcelReaderUtil excel = new ExcelReaderUtil();
            excel.readOneSheet(path, 1);
            for (Map<String, String> map : excel.getDataList()) {
                HashMap<Integer, String> tMap = new HashMap<>();
                int i = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String s = entry.getValue();
                    if (s == null) {
                        s = "";
                    }
                    tMap.put(i++, s);
                }
                list.add(tMap);
            }
            //System.out.println(excel.getDataList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
