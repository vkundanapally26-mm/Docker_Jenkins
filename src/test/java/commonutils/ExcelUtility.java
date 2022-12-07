package commonutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public static FileInputStream inputstream = null;
	public static XSSFWorkbook workbook = null;
	
	//get row count of a sheet
	public static int getRowCount(String filePath, String sheetName) throws IOException
	{
		int rowCount;
		File file = new File(filePath);
		inputstream = new FileInputStream(file);
		workbook = new XSSFWorkbook(inputstream);
		XSSFSheet worksheet = workbook.getSheet(sheetName);
		rowCount = worksheet.getLastRowNum()+1;		
		return rowCount;
	}	
	//----------------------------------------
	//get column count of a row
	public static int getColumnCount(String filePath, String sheetName, int rowNumber) throws IOException
	{
		int colCount;
		File file = new File(filePath);
		inputstream = new FileInputStream(file);
		workbook = new XSSFWorkbook(inputstream);
		XSSFSheet worksheet = workbook.getSheet(sheetName);
		Row row = worksheet.getRow(rowNumber-1);
		colCount = row.getLastCellNum();		
		return colCount;
	}
	//---------------------------------
	//get cell data
	public static String getCellData(String filePath, String sheetName, int rowNumber, int colNumber)
	{
		String cellText = null;
		try {			
			File file = new File(filePath);
			inputstream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputstream);
			XSSFSheet worksheet = workbook.getSheet(sheetName);
			Row row = worksheet.getRow(rowNumber-1);
			cellText = row.getCell(colNumber).getStringCellValue();			
		}
		
		catch(Exception e) {
			
		}
		
		return cellText;
	}	
	//---------------------------------
	//get row data	
	public static String[] getRowData(String filePath, String sheetName, int rowNumber) throws IOException
	{		
		String[] rowData;
		File file = new File(filePath);
		inputstream = new FileInputStream(file);
		workbook = new XSSFWorkbook(inputstream);
		XSSFSheet worksheet = workbook.getSheet(sheetName);
		Row row = worksheet.getRow(rowNumber-1);
		int colCount = row.getLastCellNum();
		rowData = new String[colCount];
		for(int iCol=0; iCol<colCount; iCol++)
		{
			rowData[iCol] = row.getCell(iCol).getStringCellValue();	
			//System.out.println(row.getCell(iCol).getStringCellValue());
		}				
		return rowData;
	}
	//---------------------------------		
	public static String[] getRowData(String filePath, String sheetName, String tcName)
	{		
		String[] rowData = new String[10];
		boolean bTag = false;
		try {
			File file = new File(filePath);
			inputstream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputstream);
			XSSFSheet worksheet = workbook.getSheet(sheetName);
			int rowcount = worksheet.getLastRowNum()+1;
			//Search for test case name in each row at column 2
			for(int i=1; i<rowcount; i++) 
			{
				Row row = worksheet.getRow(i);
				String exlTCName = row.getCell(1).getStringCellValue();
				//verify test case name matched
				if(tcName.equals(exlTCName.trim()))						
				{
					int colLastIndexCount = row.getLastCellNum();
					//read test case row data to array
					for(int j=2; j<=colLastIndexCount; j++)
					{
						rowData[j-2]  = row.getCell(j).getStringCellValue();
					}												
					bTag = true;
					break;
				}						
			}//outer for loop end
			
			if(bTag==false)
			{
				System.out.println(tcName +" - Test case not found in test data sheet - "+ sheetName);
			}		
		}//try end			
	
		catch(IOException e)	{
			System.out.println(filePath +" - File not found or unable to read/write data");
		}
		
		catch(Exception e)	{
			System.out.println(e.getMessage());
		}
		
		return rowData;
	}
	//------------------------------------
	public static HashMap<String, String> getRowData2(String filePath, String sheetName, String tcName)
	{		
		HashMap<String, String> rowData = new HashMap<String, String>();
		boolean bTag = false;
		try {
			File file = new File(filePath);
			inputstream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputstream);
			XSSFSheet worksheet = workbook.getSheet(sheetName);
			int rowcount = worksheet.getLastRowNum()+1;
			//Search for test case name in each row at column 2
			for(int i=1; i<rowcount; i++) 
			{
				Row row = worksheet.getRow(i);
				String exlTCName = row.getCell(1).getStringCellValue();
				//verify test case name matched
				if(tcName.equals(exlTCName.trim()))						
				{
					int colLastIndexCount = row.getLastCellNum();
					//read test case row data to array
					String cellText;
					for(int j=2; j<=colLastIndexCount; j++)
					{
						cellText = row.getCell(j).getStringCellValue();
						String[] arr  = cellText.split(":=");
						rowData.put(arr[0], arr[1]);
					}												
					bTag = true;
					break;
				}						
			}//outer for loop end
			
			if(bTag==false)
			{
				System.out.println(tcName +" - Test case not found in test data sheet - "+ sheetName);
			}		
		}//try end			
	
		catch(IOException e)	{
			System.out.println(filePath +" - File not found or unable to read/write data");
		}
		
		catch(Exception e)	{
			System.out.println(e.getMessage());
		}
		
		return rowData;
	}
	
}
