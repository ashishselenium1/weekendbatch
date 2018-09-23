package com.qtpselenium.hybrid.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.util.ExtentManager;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class CreatePortfolioTest {
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"//xls//TestCases.xlsx");
	String testName="CreatePortfolioTest";
	ExtentReports rep;
	ExtentTest test;
	
	@BeforeMethod
	public void init() {
		 rep = ExtentManager.getInstance("D:\\Whizdom-Trainings\\Online Training Workspace\\reports\\");
		 test = rep.createTest(testName);
	}
	
	@AfterMethod
	public void quit() {
		rep.flush();
	}
	
	@Test(dataProvider="getData")
	public void createPortFolioTest(Hashtable<String,String> data) {
		if(data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Skipping the test as Runmode is NO");
			throw new SkipException("Skipping the test as Runmode is NO");
		}
			
		test.log(Status.INFO, "Starting test "+ testName);
		test.log(Status.INFO, data.toString());
		DriverScript ds = new DriverScript(test);
		test.log(Status.INFO, "Executing Keywords");
		ds.executeTest(testName,xls,data);
		test.log(Status.PASS, testName+ "Passed");
		
	}
	
	
	@DataProvider
	public Object[][] getData(){
		int rows = xls.getRowCount(testName);
		int cols = xls.getColumnCount(testName);
		
		
		Object data[][] = new Object[rows-1][1];
		int r=0;
		Hashtable<String,String> table = null;
		for(int rNum=2;rNum<=rows;rNum++) {
			// new row
			table = new Hashtable<String, String>();
			for(int cNum=0;cNum<cols;cNum++) {
				String testData = xls.getCellData(testName, cNum, rNum);
				String colName = xls.getCellData(testName, cNum, 1);
				table.put(colName, testData);
			}
			System.out.println(table);
			data[r][0]=table;
			r++;
		}
		return data;
	}
	
}
