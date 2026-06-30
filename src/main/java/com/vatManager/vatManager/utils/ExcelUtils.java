package com.vatManager.vatManager.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.vatManager.vatManager.dto.ClientResponseDto;
import com.vatManager.vatManager.dto.KharidResponseDto;


public class ExcelUtils {
	
	
	public static byte[] createExcelBytes(List<ClientResponseDto> clientList) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TDS-Clients");
		
		//Header Row for the SpreadSheet/Excel.
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("FOOM-NAME");
		header.createCell(2).setCellValue("YEAR");
		header.createCell(3).setCellValue("MONTH");
		header.createCell(4).setCellValue("DAY");
		header.createCell(5).setCellValue("BILL-NUMBER");
		header.createCell(6).setCellValue("PAN-NUMBER");
		header.createCell(7).setCellValue("AMOUNT");
		header.createCell(8).setCellValue("VAT-TAX");
		header.createCell(9).setCellValue("TOTAL");
	
		//Data row for the spreadsheet/excel.
		
		int rowNumber = 1;
		
		for(ClientResponseDto client : clientList ) {
			
			Row row = sheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(client.getId());
			row.createCell(1).setCellValue(client.getFoomName());			
			row.createCell(2).setCellValue(client.getYearInBs());			
			row.createCell(3).setCellValue(client.getMonthInBs());			
			row.createCell(4).setCellValue(client.getDayInBs());
			row.createCell(4).setCellValue(client.getBillNumber());
			row.createCell(5).setCellValue(client.getPanNumber());
			row.createCell(6).setCellValue(client.getAmount().doubleValue());
			row.createCell(7).setCellValue(client.getVatTax().doubleValue());
			row.createCell(8).setCellValue(client.getTotal().doubleValue());
		}// ends for
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workbook.write(output);
		workbook.close();
		
		byte[] bytes = output.toByteArray();
		return bytes;
		
		
	}//ends method
	
	
	
	
	
	
	
public static byte[] createExcelBytesKharid(List<KharidResponseDto> kharidList) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TDS-Clients-Kharid");
		
		//Header Row for the SpreadSheet/Excel.
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("FOOM-NAME");
		header.createCell(2).setCellValue("YEAR");
		header.createCell(3).setCellValue("MONTH");
		header.createCell(4).setCellValue("DAY");
		header.createCell(5).setCellValue("BILL-NUMBER");
		header.createCell(6).setCellValue("PAN-NUMBER");
		header.createCell(7).setCellValue("AMOUNT");
		header.createCell(8).setCellValue("VAT-TAX");
		header.createCell(9).setCellValue("TOTAL");
	
		//Data row for the spreadsheet/excel.
		
		int rowNumber = 1;
		
		for(KharidResponseDto kharid : kharidList ) {
			
			Row row = sheet.createRow(rowNumber++);
			row.createCell(0).setCellValue(kharid.getId());
			row.createCell(1).setCellValue(kharid.getFoomName());
			row.createCell(2).setCellValue(kharid.getYearInBs());			
			row.createCell(3).setCellValue(kharid.getMonthInBs());			
			row.createCell(4).setCellValue(kharid.getDayInBs());
			row.createCell(5).setCellValue(kharid.getBillNumber());
			row.createCell(6).setCellValue(kharid.getPanNumber());
			row.createCell(7).setCellValue(kharid.getAmount().doubleValue());
			row.createCell(8).setCellValue(kharid.getVatTax().doubleValue());
			row.createCell(9).setCellValue(kharid.getTotal().doubleValue());
			
		}// ends for
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workbook.write(output);
		workbook.close();
		
		byte[] bytes = output.toByteArray();
		return bytes;
		
		
	}//ends method

	
	
	
}//ends class
