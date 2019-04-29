package com.table.info.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

@Component("excelDownload")
public class ExcelDownload extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filename = (String) model.get("filename");
		System.out.println("파일명 =================== " + filename);
		response.setHeader("Content-Disposition", "attachment; filename=\""+filename+".xls\"");
		String[] headers = (String[]) model.get("headers");
		String[] bodyCols = (String[]) model.get("bodyCols");
		int[] colSize = (int[]) model.get("colSize");
		List<HashMap> list = (List<HashMap>) model.get("list");
		
		Sheet sheet = workbook.createSheet("sheet");
		
		HSSFCellStyle headerStyle = (HSSFCellStyle) workbook.createCellStyle();

		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		
//		headerStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
//
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 14);
//		font.setColor(HSSFColor.WHITE.index);
		headerStyle.setFont(font);
		
		HSSFCellStyle bodyStyle = (HSSFCellStyle) workbook.createCellStyle();
		bodyStyle.setAlignment(HorizontalAlignment.CENTER);
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyle.setBorderTop(BorderStyle.MEDIUM);
		bodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		bodyStyle.setBorderRight(BorderStyle.MEDIUM);
		bodyStyle.setBorderLeft(BorderStyle.MEDIUM);
		bodyStyle.setWrapText(true);

		// header
		int r = 0;
		Row row = sheet.createRow(r++);
		for (int i=0; i<headers.length; i++) {
			sheet.setColumnWidth(i, colSize[i]);
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headers[i]);
		}
		
		// body
		for(int i=0; i<list.size(); i++){
			row = sheet.createRow(r++);
			HashMap hm = list.get(i);
			for(int j=0; j<bodyCols.length; j++) {
				String value = String.valueOf(hm.get(bodyCols[j]));
				if("null".equals(value)) {
					value = "";
				}
				Cell cell = row.createCell(j);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(value);
			}
		}
		


	}


}
