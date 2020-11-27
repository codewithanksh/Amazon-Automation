package utils.excelutil;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestManagerUtil {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;

	private static final String TESTCASES_ID = "TCID";
	private static final String TOTAL_TESTCASES = "TotalTestcases";
	private static final String PASSED_CASES = "NumberOfPass";
	private static final String FAILED_CASES = "NumberOfFail";
	private static final String SKIPPED_CASES = "NumberOfSkip";
	private static final String TEST_SUMMARY = "Test Summary";

	private void initTestManager() {

		this.path = System.getProperty("user.dir")+ "src/test/resources/Account_Data.xlsx";
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet("TestManager");
			fis.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	/**
	 * @throws IOException
	 ********************************************************************************************************************************/

//	public void updateTestSummary()  {
//
//		initTestManager();
//
//		try {
//			int totalNumberOfRows = sheet.getPhysicalNumberOfRows();
//			Xls_Reader reader = new Xls_Reader(this.path);
//			int executedTestCases = 0, total_passed = 0, total_failed = 0, total_skipped = 0;
//
//			int total_TestCases_headerColumnIndex = reader.getColumnIndex(sheet.getSheetName(), TOTAL_TESTCASES);
//			int total_PassedCases_headerColumnIndex = reader.getColumnIndex(sheet.getSheetName(), PASSED_CASES);
//			int total_FailedCases_headerColumnIndex = reader.getColumnIndex(sheet.getSheetName(), FAILED_CASES);
//			int total_SkippedCases_headerColumnIndex = reader.getColumnIndex(sheet.getSheetName(), SKIPPED_CASES);
//
//			int testCases_StartRowNo = reader.getCellRowNum(sheet.getSheetName(), TESTCASES_ID);
//
//			// Perform Addition on the respective Columns
//			for (int i = (testCases_StartRowNo +
//					1); i < totalNumberOfRows; i++) {
//				if (sheet.getRow(i) != null) {
//					if (!getCellValueAsString(sheet.getRow(i).getCell(0)).isEmpty()) {
//						if (!getCellValueAsString(sheet.getRow(i).getCell(total_TestCases_headerColumnIndex)).isEmpty())
//							executedTestCases = executedTestCases + Integer.parseInt(
//									getCellValueAsString(sheet.getRow(i).getCell(total_TestCases_headerColumnIndex)));
//
//						if (!getCellValueAsString(sheet.getRow(i).getCell(total_PassedCases_headerColumnIndex))
//								.isEmpty())
//							total_passed = total_passed + Integer.parseInt(
//									getCellValueAsString(sheet.getRow(i).getCell(total_PassedCases_headerColumnIndex)));
//
//						if (!getCellValueAsString(sheet.getRow(i).getCell(total_FailedCases_headerColumnIndex))
//								.isEmpty())
//							total_failed = total_failed + Integer.parseInt(
//									getCellValueAsString(sheet.getRow(i).getCell(total_FailedCases_headerColumnIndex)));
//
//						if (!getCellValueAsString(sheet.getRow(i).getCell(total_SkippedCases_headerColumnIndex))
//								.isEmpty())
//							total_skipped = total_skipped + Integer.parseInt(getCellValueAsString(
//									sheet.getRow(i).getCell(total_SkippedCases_headerColumnIndex)));
//
//
//				}
//			 }
//		  }
//
//			String message = "\n********** EXECUTION RESULTS SUMMARY *************\n"
//					+ " Total Executed Cases : " + executedTestCases + "\nTotal Passed Cases : " + total_passed
//					+ "\nTotal Failed Cases : " + total_failed + "\nTotal Skipped : " + total_skipped;
//			System.out.println(message);
//
////			Report.logger(LogStatus.INFO, "<b> <i> "+message+" </b> </i>");
//
//			// Setting Cell Font
//			HSSFCellStyle cell_style = null;
//			HSSFFont cell_font = null;
//			try {
//				cell_style = workbook.createCellStyle();
//				cell_font  = workbook.createFont();
//			}catch(IllegalStateException exp) {
//				cell_style = workbook.getCellStyleAt(0);
//				cell_font = workbook.getFontAt((short) 0);
//			}
//			cell_style.setFont(cell_font);
//			cell_style.setBorderTop(BorderStyle.THIN);
//			cell_style.setBorderBottom(BorderStyle.THIN);
//			cell_style.setBorderRight(BorderStyle.THIN);
//			cell_style.setBorderLeft(BorderStyle.THIN);
//			cell_style.setAlignment(HorizontalAlignment.CENTER);
//
//			// Update Details Inside Test Summary
//			int testSummary_ColumnHeader_Index = reader.getColumnIndex(sheet.getSheetName(), TEST_SUMMARY);
//			int testSummary_Row_Index = reader.getCellRowNum(sheet.getSheetName(), TEST_SUMMARY);
//			HSSFCell cell;
//			cell = (sheet.getRow(testSummary_Row_Index + 1).getCell(testSummary_ColumnHeader_Index + 1) != null )?
//					sheet.getRow(testSummary_Row_Index + 1).getCell(testSummary_ColumnHeader_Index + 1) : sheet.getRow(testSummary_Row_Index + 1).createCell(testSummary_ColumnHeader_Index + 1);
//			cell.setCellStyle(cell_style);
//			cell.setCellValue(executedTestCases);
//
//			cell = (sheet.getRow(testSummary_Row_Index + 2).getCell(testSummary_ColumnHeader_Index + 1) != null )?
//					sheet.getRow(testSummary_Row_Index + 2).getCell(testSummary_ColumnHeader_Index + 1) : sheet.getRow(testSummary_Row_Index + 1).createCell(testSummary_ColumnHeader_Index + 1);
//			cell.setCellStyle(cell_style);
//			cell.setCellValue(total_passed);
//
//			cell = (sheet.getRow(testSummary_Row_Index + 3).getCell(testSummary_ColumnHeader_Index + 1) != null )?
//					sheet.getRow(testSummary_Row_Index + 3).getCell(testSummary_ColumnHeader_Index + 1) : sheet.getRow(testSummary_Row_Index + 1).createCell(testSummary_ColumnHeader_Index + 1);
//			cell.setCellStyle(cell_style);
//			cell.setCellValue(total_skipped);
//
//			cell = (sheet.getRow(testSummary_Row_Index + 4).getCell(testSummary_ColumnHeader_Index + 1) != null )?
//					sheet.getRow(testSummary_Row_Index + 4).getCell(testSummary_ColumnHeader_Index + 1) : sheet.getRow(testSummary_Row_Index + 1).createCell(testSummary_ColumnHeader_Index + 1);
//			cell.setCellStyle(cell_style);
//			cell.setCellValue(total_failed);
//
//		} catch (Exception exp) {
//			exp.printStackTrace(System.out);
//		} finally {
//			try {
//				fileOut = new FileOutputStream(path);
//				workbook.write(fileOut);
//				workbook.close();
//				fileOut.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	public String getCellValueAsString(HSSFCell cell) {
//
//		DataFormatter format = new DataFormatter();
//		return format.formatCellValue(cell);
//
//	}
	

}
