package com.hj.util;

import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtils {

	public static WritableWorkbook createTemplate(OutputStream output) throws IOException, WriteException {
        WritableWorkbook writableWorkbook= Workbook.createWorkbook(output);
        WritableSheet wsheet = writableWorkbook.createSheet("测试title", 0);


        CellFormat cf = writableWorkbook.getSheet(0).getCell(1, 0).getCellFormat();
        WritableCellFormat wc = new WritableCellFormat();
        // 设置居中
        wc.setAlignment(Alignment.CENTRE);
        // 设置边框线
//    wc.setBorder(Border.ALL, BorderLineStyle.THIN);
        wc.setBackground(jxl.format.Colour.GREEN);

        Label nc0 = new Label(0, 0, "标题1",wc);//Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
        Label nc1 = new Label(1, 0, "标题2",wc);
        Label nc2 = new Label(2, 0, "标题3",wc);
        Label nc3 = new Label(0, 1, "dddd");
        Label nc4 = new Label(1, 1, "ffff");


        wsheet.addCell(nc0);
        wsheet.addCell(nc1);
        wsheet.addCell(nc2);
        wsheet.addCell(nc3);
        wsheet.addCell(nc4);

        return writableWorkbook;
    }
}
