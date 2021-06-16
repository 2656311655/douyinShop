package com.auto.modal.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020/9/2 01:10
 */

public class WriteExcelUtils {
    private String pathname;
    private Workbook workbook;
    private Sheet sheet1;
    public static void main(String[] args) throws Exception {
        WriteExcelUtils obj = new WriteExcelUtils("E:\\writeExcel.xlsx");
        List<String> strings = new ArrayList<>();
        List<HashMap> list = new ArrayList<HashMap>();
        Map<Integer, String> dataMap = new HashMap<Integer, String>();
        dataMap.put(1, "BankName");
        dataMap.put(2, "Addr");
        dataMap.put(3, "Phone");
        list.add((HashMap) dataMap);
        HashMap<Integer, String> dataMap1 = new HashMap<Integer, String>();
        dataMap1.put(1, "BankName");
        dataMap1.put(2, "Addr");
        dataMap1.put(3, "Phone");
        list.add((HashMap) dataMap);
        obj.write(strings, 1);
        //obj.writeHashExcel(list);
    }

    public WriteExcelUtils(String excelPath) throws Exception {
        //在excelPath中需要指定具体的文件名(需要带上.xls或.xlsx的后缀)
        this.pathname = excelPath;
        String fileType = excelPath.substring(excelPath.lastIndexOf(".") + 1, excelPath.length());
        //创建文档对象
        if (fileType.equals("xls")) {
            //如果是.xls,就new HSSFWorkbook()
            workbook = new HSSFWorkbook();
        } else if (fileType.equals("xlsx")) {
            //如果是.xlsx,就new XSSFWorkbook()
            workbook = new XSSFWorkbook();
        } else {
            throw new Exception("文档格式后缀不正确!!！");
        }
        // 创建表sheet
        sheet1 = workbook.createSheet("sheet1");
    }

    public void write(List<String> writeStrings, int rowNumber) throws Exception {
        for (String area : writeStrings) {
            //获取最后一行的行号
            int lastRowNum = sheet1.getLastRowNum();
            Row dataRow = sheet1.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(area);
        }
        OutputStream stream = new FileOutputStream(pathname);
        workbook.write(stream);
        stream.close();
    }

    public boolean writeHashExcel(List<HashMap<Integer, String>> list) throws IOException {
        int j = 0;
        int sum = 0;
        for (Map<Integer, String> map : list) {
            Row row = sheet1.createRow(j++);
            for (int k = 0; k < map.size(); k++) {
                Cell cell = row.createCell(k);

                cell.setCellValue(map.get(k));
            }
        }
        OutputStream stream = new FileOutputStream(pathname);
        workbook.write(stream);
        stream.close();
        return false;
    }
}
