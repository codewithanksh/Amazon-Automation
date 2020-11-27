package utils;

import utils.excelutil.ExcelUtil;

import java.util.Hashtable;

public class TestUtil {
//    public static long PAGE_LOAD_TIMEOUT = 100;
//    public static long IMPLICIT_WAIT     = 7;
//
//
//
//    // To Fetch data from given xls, Sheet, test name
//    public static Object[][] getData(String testName, Xls_Reader xls) {
//
//        // find the row num from which test starts
//        // number of cols in the test
//        // number of rows
//        // put the data in hastable and put hastable in array
//        int testStartRowNum = 0;
//
//        // find the row num from which test starts
//        for (int rNum = 1; rNum <= xls.getRowCount(testName); rNum++) {
//            if (xls.getCellData(testName, 0, rNum).equals(testName)) {
//                testStartRowNum = rNum;
//
//                break;
//            }
//        }
//
//        // System.out.println("Test "+ testName +" starts from "+ testStartRowNum);
//        int colStartRowNum = testStartRowNum + 2;
//        int totalCols      = 0;
//
//        while (!xls.getCellData(testName, totalCols, colStartRowNum).equals("")) {
//            totalCols++;
//        }
//
//        System.out.println("Total Cols in test " + testName + " are " + totalCols);
//
//        // rows
//        int dataStartRowNum = testStartRowNum + 3;
//        int totalRows       = 0;
//
//        while (!xls.getCellData(testName, 0, dataStartRowNum + totalRows).equals("")) {
//            totalRows++;
//        }
//
//        System.out.println("Total Rows in test " + testName + " are " + totalRows);
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//        // extract data
//        Object[][]                data  = new Object[totalRows][1];
//        int                       index = 0;
//        Hashtable<String, String> table = null;
//
//        for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + totalRows); rNum++) {
//            table = new Hashtable<String, String>();
//
//            for (int cNum = 0; cNum < totalCols; cNum++) {
//                table.put(xls.getCellData(testName, cNum, colStartRowNum).trim(),
//                          xls.getCellData(testName, cNum, rNum).trim());
//                System.out.print(xls.getCellData(testName, cNum, rNum).trim() + " -- ");
//            }
//
//            data[index][0] = table;
//            index++;
//            System.out.println();
//        }
//
//        // System.out.println("done");
//        return data;
//    }
//
//    /** ****************************************************************************************************************************** */
//    public static Object[][] getData(String testName, String identifier, ExcelUtil xls) {
//
//        // find the row num from which test starts
//        // number of cols in the test
//        // number of rows
//        // put the data in hastable and put hastable in array
//        int testStartRowNum = 0;
//
//        // find the row num from which test starts
//        for (int rNum = 1; rNum <= xls.getRowCount(testName); rNum++) {
//            if (xls.getCellData(testName, 0, rNum).equals(identifier)) {
//                testStartRowNum = rNum;
//
//                break;
//            }
//        }
//
//        // System.out.println("Test "+ testName +" starts from "+ testStartRowNum);
//        int colStartRowNum = testStartRowNum + 2;
//        int totalCols      = 0;
//
//        while (!xls.getCellData(testName, totalCols, colStartRowNum).equals("")) {
//            totalCols++;
//        }
//
//        System.out.println("Total Cols in Test Data Group " + identifier + " are " + totalCols);
//
//        // rows
//        int dataStartRowNum = testStartRowNum + 3;
//        int totalRows       = 0;
//
//        while (!xls.getCellData(testName, 0, dataStartRowNum + totalRows).equals("")) {
//            totalRows++;
//        }
//
//        System.out.println("Total Rows in Test Data Group " + identifier + " are " + totalRows);
//
//        // System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        // extract data
//        Object[][]                data  = new Object[totalRows][1];
//        int                       index = 0;
//        Hashtable<String, String> table = null;
//
//        for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + totalRows); rNum++) {
//            table = new Hashtable<String, String>();
//
//            for (int cNum = 0; cNum < totalCols; cNum++) {
//                table.put(xls.getCellData(testName, cNum, colStartRowNum), xls.getCellData(testName, cNum, rNum));
//                System.out.print(xls.getCellData(testName, cNum, rNum) + " -- ");
//            }
//
//            data[index][0] = table;
//            index++;
//            System.out.println();
//        }
//
//        // System.out.println("done");
//        return data;
//    }
//
//    /** ****************************************************************************************************************************** */
//
//    // Checking the Run mode for TestCase and skipping the test case, if Run mode set to "N"
//    // true- test has to be executed
//    // false- test has to be skipped
//    public static boolean isExecutable(String testName, Xls_Reader xls) {
//        for (int rowNum = 2; rowNum <= xls.getRowCount("TestCases"); rowNum++) {
//            if (xls.getCellData("TestCases", "TCID", rowNum).equals(testName)) {
//                if (xls.getCellData("TestCases", "Runmode", rowNum).equals("Y")) {
//
//                    // System.out.println("Run mode for "+testName+" is :Y");
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//
//            // print the test cases with RUnmode Y
//        }
//
//        return false;
//    }
//
//    public static String getTestInstances(String currentTestCase, ExcelUtil xls) {
//    	return xls.retrieveToRunFlag("Data", "Instances", currentTestCase);
//    }
//
//
// // To Fetch data from given xls, Sheet, test name
// static Hashtable<String,Hashtable<String,String>> testSuiteManager(String testName, Xls_Reader xls) {
//
//     // find the row num from which test starts
//     // number of cols in the test
//     // number of rows
//     // put the data in hastable and put hastable in array
// 	//Assuming that All Column headers are placed in 1st row of Excel Sheet
//     int testStartRowNum = 1;
//     int colStartRowNum = testStartRowNum;
//     int totalCols      = 0;
//
//     //Get count of total columns
//     while (!xls.getCellData(testName, totalCols, colStartRowNum).equals("")) {
//         totalCols++;
//     }
//
//
//
//     System.out.println("Total Cols in test " + testName + " are " + totalCols);
//
//     //Get count of total rows
//     int dataStartRowNum = testStartRowNum + 1;
//     int totalRows       = 0;
//
//     while (!xls.getCellData(testName, 0, dataStartRowNum + totalRows).equals("")) {
//         totalRows++;
//     }
//
//     System.out.println("Total Rows in test " + testName + " are " + totalRows);
//     System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//     // extract data
//     //Object[][]                data  = new Object[totalRows][1];
//     Hashtable<String,Hashtable<String,String>>   data  = new Hashtable<String,Hashtable<String,String>>();
//     //int                       index = 0;
//     Hashtable<String, String> table = null;
//     String testCaseName="";
//
//     for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + totalRows); rNum++) {
//         table = new Hashtable<String, String>();
//
//         for (int cNum = 0; cNum < totalCols; cNum++) {
//         	//Read testCaseName
//         	if(cNum==0)
//         		testCaseName=xls.getCellData(testName, cNum, rNum).trim();
//         	//Read all related columns for that testcase
//             table.put(xls.getCellData(testName, cNum, colStartRowNum).trim(),
//                       xls.getCellData(testName, cNum, rNum).trim());
//             System.out.print(xls.getCellData(testName, cNum, rNum).trim() + " ---- ");
//         }
//
//         data.put(testCaseName,table);
//         //index++;
//         System.out.println();
//     }
//
//
//     // System.out.println("done");
//     return data;
// }
//
//
 public static String getTestRunMode(String currentTestCase, ExcelUtil xls) {
 	return xls.retrieveToRunFlag("Data", "Runnable", currentTestCase);
 }
 
}


//~ Formatted by Jindent --- http://www.jindent.com
