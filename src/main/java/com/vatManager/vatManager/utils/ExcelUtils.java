package com.vatManager.vatManager.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.vatManager.vatManager.dto.ClientResponseDto;


public class ExcelUtils {
	
	
	public static byte[] createExcelBytes(List<ClientResponseDto> clientList) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TDS-Clients");
		
		//Header Row for the SpreadSheet/Excel.
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(0).setCellValue("FOOM-NAME");
		header.createCell(0).setCellValue("DATE");
		header.createCell(0).setCellValue("BILL-NUMBER");
		header.createCell(0).setCellValue("PAN-NUMBER");
		header.createCell(0).setCellValue("AMOUNT");
		header.createCell(0).setCellValue("VAT-TAX");
		header.createCell(0).setCellValue("TOTAL");
	
		//Data row for the spreadsheet/excel.
		
		int rowNumber = 1;
		
		for(ClientResponseDto client : clientList ) {
			
			Row row = sheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(client.getId());
			row.createCell(0).setCellValue(client.getFoomName());
			row.createCell(0).setCellValue(client.getDate());
			row.createCell(0).setCellValue(client.getBillNumber());
			row.createCell(0).setCellValue(client.getPanNumber());
			row.createCell(0).setCellValue(client.getAmount());
			row.createCell(0).setCellValue(client.getVatTax());
			row.createCell(0).setCellValue(client.getTotal());
			
		}// ends for
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workbook.write(output);
		workbook.close();
		
		byte[] bytes = output.toByteArray();
		return bytes;
		
		
	}//ends method
	
	

	
	
	
}//ends class
