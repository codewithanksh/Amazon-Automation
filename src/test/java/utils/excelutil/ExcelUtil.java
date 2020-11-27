package utils.excelutil;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import utils.FrameworkException;
import javax.swing.*;
import java.io.*;
import java.util.Hashtable;


public class ExcelUtil {

	private  FileOutputStream fileOut =null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	
	public ExcelUtil() {}

	private String excelFilePath = "";

	
	String filePath = System.getProperty("user.dir")+"/src/test/resources";
	String sheetName = "Data";

	private HSSFWorkbook getWorkBook(String sheetName) {
		
		FileInputStream fis = null;
		HSSFWorkbook workbook = null;
		boolean sheetFoundStatus= false;
				
	 	  File folder = new File(filePath);
	   	  File[] allFiles = folder.listFiles();
	   	  for (int i = 0; i < allFiles.length; i++) {
	   		  String fileType = FilenameUtils.getExtension(allFiles[i].getName());
	   		  if(fileType.equalsIgnoreCase("xls") | fileType.equalsIgnoreCase("xlsx")) {
	   			  
	   			if(allFiles[i].renameTo(allFiles[i])==false) {
                    JOptionPane.showMessageDialog(null, "Please close '"+allFiles[i].getName()+"' file and click OK", "Alert!", JOptionPane.ERROR_MESSAGE);
                }
	   			
	   			  try {
					fis = new FileInputStream(allFiles[i]);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	   			  try {
					workbook = new HSSFWorkbook(fis);
				} catch (IOException e) {
					e.printStackTrace();
				}
	   			  int allSheets = workbook.getNumberOfSheets();
	   			  for (int j = 0; j < allSheets; j++) {
	   				  if(workbook.getSheetAt(j).getSheetName().equals(sheetName)) {
	   					excelFilePath=allFiles[i].getAbsolutePath();
	   					sheetFoundStatus=true;
	   					break;
	   				  }
				}
	   			  if(sheetFoundStatus==true)
	   				  break;
	   			  
	   		  }
			
		  }
	   	  if(sheetFoundStatus==true)
	   	      return workbook;
	   	  else
	   		  throw new FrameworkException("Sheet <b>"+sheetName+"</b> not found in any workbook");
		
	}
	

    public String getData(String sheetName, String columnName, String currentTestCase, String iteration) {
		//checkPreRequisites();
    	boolean testCaseFoundStatus=false;
    	boolean iterationFoundStatus = false;
    	boolean columnFoundStatus = false;
    	DataFormatter format = new DataFormatter();
    	String data= new String();
    	HSSFWorkbook workbook = null;
    	try {
    		workbook = getWorkBook(sheetName);
    	}catch(Exception e)
    	{
    		throw new FrameworkException("Error while reading Excel sheet: "+sheetName);
    	}
		HSSFSheet currentSheet = workbook.getSheet(sheetName);
		int lastRow = currentSheet.getLastRowNum();
		int testCaseRow = 0;
		int columnnumber=0;
		int testHeadCloumnsRow = 0;
		// finding current test case row with respect to iteration number
		for (int i = 0; i <= lastRow; i++) {
			row = currentSheet.getRow(i);
			String testcaseName = format.formatCellValue(row.getCell(0));
			String testIteration = format.formatCellValue(row.getCell(1));
			if (testcaseName.equals(currentTestCase)) {
				testCaseFoundStatus = true;
				if (testIteration.equals(iteration)) {
					testCaseRow = i;
					iterationFoundStatus = true;
					break;
				}
			}
		}			
			
		
		if(!testCaseFoundStatus)
			throw new FrameworkException("<b>'"+currentTestCase+"'</b> is not found in <b>"+sheetName+"</b> excel sheet");
		
		if(!iterationFoundStatus)
			throw new FrameworkException("<b>'"+currentTestCase+"'</b> Iteration: <b>"+iteration+"</b> not found in "+sheetName+" excel sheet");
		
		
		// finding requested column number
		// row = currentSheet.getRow(testCaseRow);
		int totalColumns = currentSheet.getRow(testHeadCloumnsRow).getPhysicalNumberOfCells();
		for (int j = 0; j < totalColumns; j++) {
			String tempColumnName = format.formatCellValue(currentSheet.getRow(testHeadCloumnsRow).getCell(j));
			if(tempColumnName.equals(columnName)) {
				columnnumber=j;				
				columnFoundStatus=true;
				break;
			}
		}
		
		if(columnFoundStatus==false)
			throw new FrameworkException("<b>'"+columnName+"'</b> Column is not found in <b>"+sheetName+"</b> Excel Sheet");
		
		data = format.formatCellValue(currentSheet.getRow(testCaseRow).getCell(columnnumber));
		return data;
		
	}

 	// returns true if data is set successfully else false
	public boolean putData(String sheetName, String columnName,String currentTestCase, int iteration, String data) {
		boolean testCaseFoundStatus  = false;
    	boolean iterationFoundStatus = false;
    	boolean columnFoundStatus    = false;
    	boolean setCellValueStatus   = false;
    	HSSFWorkbook workbook        = null;
    	HSSFSheet currentSheet       = null;
    	HSSFCellStyle my_style       = null;
    	
     	try {
    		workbook = getWorkBook(sheetName);
    		currentSheet = workbook.getSheet(sheetName);
    	}catch(Exception e) {
    		throw new FrameworkException("Error while reading Excel sheet: "+sheetName);
    	}
    	
    	try {
    		my_style = workbook.createCellStyle();
    	} catch(IllegalStateException exp) {
    		my_style = workbook.getCellStyleAt(0);
    	}
    	
    	
		my_style.setAlignment(HorizontalAlignment.CENTER);
		int lastRow = currentSheet.getLastRowNum();
		int testCaseRow = 0;
		int columnnumber=0;
		int testHeadCloumnsRow = 1;
		DataFormatter format = new DataFormatter();
		// finding current test case row with respect to iteration number
		
		for (int i = 0; i <= lastRow; i++) {
			row = currentSheet.getRow(i);
			
			if(row != null) {
				String testcaseName = format.formatCellValue(row.getCell(0));
				String testIteration = format.formatCellValue(row.getCell(1));
				if (testcaseName.equals(currentTestCase)) {
					testCaseFoundStatus = true;
					testCaseRow = i;
					break;
				}
			}
		}

		if(!testCaseFoundStatus) { throw new FrameworkException("<b>'"+currentTestCase+"'</b> is not found in <b>"+sheetName+"</b> excel sheet"); }

		// finding requested column number
		row = currentSheet.getRow(testCaseRow);
		int totalColumns = currentSheet.getRow(testHeadCloumnsRow).getPhysicalNumberOfCells();
		
		for (int j = 0; j < totalColumns; j++) {
			String tempColumnName = format.formatCellValue(currentSheet.getRow(testHeadCloumnsRow).getCell(j));
			if(tempColumnName.equals(columnName)) {
				columnnumber=j;				
				columnFoundStatus=true;
				break;
			}
		}
		
		if(columnFoundStatus==false)
			throw new FrameworkException("<b>'"+columnName+"'</b> Column is not found in <b>"+sheetName+"</b> Excel Sheet");
		
			currentSheet.autoSizeColumn(columnnumber);
			row = currentSheet.getRow(testCaseRow);
			if (row == null)
				row = currentSheet.createRow(testCaseRow);

			cell = row.getCell(columnnumber);
			if (cell == null)
				cell = row.createCell(columnnumber);

			if (data.equalsIgnoreCase("Failed")) {
				my_style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
				my_style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
			} else if (data.equalsIgnoreCase("success")) {
				my_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				my_style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
			} else if (data.equalsIgnoreCase("skipped") ) {
				my_style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
				my_style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
			}

			cell.setCellValue(data);
			cell.setCellStyle(my_style);
			
			try {
				fileOut = new FileOutputStream(excelFilePath);
				workbook.write(fileOut);
				fileOut.close();
				setCellValueStatus=true;
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(setCellValueStatus==false)
				throw new FrameworkException("Error writing data in to <b>"+sheetName+"</b> Excel Sheet");
			else
				return setCellValueStatus;
	}

	
	

	
	
	 // To retrieve SuiteToRun and CaseToRun flag of test suite and test case.
    public String retrieveToRunFlag(String wsName, String colName, String rowName) {
    	HSSFWorkbook workbook = getWorkBook(wsName);
    	HSSFSheet sheet = workbook.getSheet(wsName);
        int sheetIndex = workbook.getSheetIndex(wsName);

        if(rowName==null || colName==null)
        	return "0";
        
        if (sheetIndex == -1) {
            return "0";
            
        } else {
            int     rowNum    = retrieveNoOfRows(wsName);
            int     colNum    = retrieveNoOfCols(wsName);
            int     colNumber = -1;
            int     rowNumber = -1;
            HSSFRow Suiterow  = sheet.getRow(1);

            for (int i = 0; i < colNum; i++) {
                if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;

                    break;

                    // System.out.println(colName+" index "+i);
                }
            }

            if (colNumber == -1) {
                return "0";
            }

            for (int j = 0; j < rowNum; j++) {
                HSSFRow Suitecol = sheet.getRow(j);
                HSSFCell cell = Suitecol.getCell(0);
                
                if(cell != null ) {
					if (cell.getStringCellValue().trim()
							.equals(rowName.trim())) {
						rowNumber = j;
						break;
					}
				}
            }

            if (rowNumber == -1) {
                return "0";
            }

            // System.out.println("COlumn No Is :" +colNumber);
            HSSFRow  row  = sheet.getRow(rowNumber);
            HSSFCell cell = row.getCell(colNumber);

            if (cell == null) {
                return "0";
            }
            DataFormatter format = new DataFormatter();
            String value = format.formatCellValue(cell);

            return value;
        }
    }

	// To Fetch data from given xls, Sheet, test name
	public  Hashtable<String, Hashtable<String,String>> getSheetData(String sheetName, ExcelUtil xls) {

		// find the row num from which test starts
		// number of cols in the test
		// number of rows
		// put the data in hastable and put hastable in array
		//Assuming that All Column headers are placed in 1st row of Excel Sheet
		int dataStartRowNum = 3;
		int totalCols      = xls.retrieveNoOfCols("Data");
		int totalRows       = xls.retrieveNoOfRows("Data");
		System.out.println("Total Cols in test " + sheetName + " are " + totalCols);
		System.out.println("Total Data Rows in test " + sheetName + " are " + (totalRows-2));
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// extract data
		//Object[][]                data  = new Object[totalRows][1];
		Hashtable<String,Hashtable<String,String>>   data  = new Hashtable<String,Hashtable<String,String>>();
		//int                       index = 0;
		Hashtable<String, String> table = null;
		String testCaseName="";

		for (int rNum = dataStartRowNum; rNum <= totalRows ; rNum++) {
			table = new Hashtable<String, String>();

			for (int cNum = 0; cNum < totalCols; cNum++) {
				//Read testCaseName
				if(cNum==0) {
					testCaseName=xls.getCellData(sheetName, 0, rNum).trim();
					table.put(xls.getCellData(sheetName, cNum,2).trim(), xls.getCellData(sheetName, cNum, rNum).trim());
				} else {
				  // Read all related columns for that testcase
					  table.put(xls.getCellData(sheetName, cNum, 2).trim(), xls.getCellData(sheetName, cNum, rNum).trim());
					  System.out.print(xls.getCellData(sheetName, cNum, rNum).trim() + " ---- ");
				}
			}

		data.put(testCaseName,table);
		//index++;
		System.out.println();
	}


		// System.out.println("done");
		return data;
	}

    // To retrieve No Of Rows from .xls file's sheets.
    public int retrieveNoOfRows(String wsName) {
    	HSSFWorkbook workbook = getWorkBook(wsName);
    	HSSFSheet sheet = workbook.getSheet(wsName);
        int sheetIndex = workbook.getSheetIndex(wsName);

        if (sheetIndex == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(sheetIndex);

            int rowCount = sheet.getLastRowNum() + 1;

            return rowCount;
        }
    }
    
    
    // To retrieve No Of Columns from .cls file's sheets.
    public int retrieveNoOfCols(String wsName) {
    	HSSFWorkbook workbook = getWorkBook(wsName);
    	HSSFSheet sheet = workbook.getSheet(wsName);
        int sheetIndex = workbook.getSheetIndex(wsName);

        if (sheetIndex == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(sheetIndex);

            int colCount = sheet.getRow(1).getLastCellNum();

            return colCount;
        }
    }

	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
			HSSFWorkbook workbook = getWorkBook(sheetName);
			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";
			//System.out.println("Index of sheet name is "+index);

			HSSFSheet sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
			if(cell==null)
				return "";

			switch(cell.getCellType()) {
				case NUMERIC:
					//System.out.println("Last evaluated as: --switch " + cell.getNumericCellValue());
					return String.valueOf(cell.getNumericCellValue());
				case STRING:
					//System.out.println("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
					return String.valueOf(cell.getRichStringCellValue());
				case BOOLEAN:
					return String.valueOf(cell.getBooleanCellValue());
				case BLANK:
					return "";
				case ERROR:
					return "";
				default:
					return "";
			}

		}
		catch(Exception e){
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist in xls";
		}
	}
}
