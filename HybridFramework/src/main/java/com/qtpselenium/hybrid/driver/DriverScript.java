// implementing 1 test
// Complete Login test
// Validations
// identify objects with id,name,css,xpath
// logs in keywords, screenshots
// reporting +  putting screenshots in the reports + logs in reports
// data reading from xls file

// implement other test cases
// Implement ANT
// Implement GRID
// Implement Jenkins,GIT
// batch run the test cases

package com.qtpselenium.hybrid.driver;

import java.util.Hashtable;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.keywords.ApplicationKeywords;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class DriverScript {
	ExtentTest test;
	
	public DriverScript(ExtentTest t) {
		test=t;
	}

	//public static void main(String[] args) {
	  public void executeTest(String testName , Xls_Reader xls,Hashtable<String,String> table) {
		//GenericKeywords app = new GenericKeywords();// constructor will be called
		  // prop file in init
		ApplicationKeywords app = new ApplicationKeywords(test);// constructor will be called
		
		int rows = xls.getRowCount("TestCases");
		for(int rNum=2;rNum<=rows;rNum++) {
			String tcid = xls.getCellData("TestCases", "TCID", rNum);
			if(tcid.equals(testName)) {
				String keyword = xls.getCellData("TestCases", "Keyword", rNum);
				String objectKey = xls.getCellData("TestCases", "Object", rNum);
				String dataKey = xls.getCellData("TestCases", "Data", rNum);
				String data = table.get(dataKey);
				
			//	System.out.println(tcid +" --- "+ keyword+" ----- "+ objectKey+" --- "+ data);
				//test.log(Status.INFO, tcid +" --- "+ keyword+" ----- "+ objectKey+" --- "+ data);
				
					if(keyword.equals("openBrowser"))
						app.openBrowser(data);
					else if(keyword.equals("navigate"))
						app.navigate(objectKey);
					else if(keyword.equals("click"))
						app.click(objectKey);
					else if(keyword.equals("type"))
						app.type(objectKey,data);
					else if(keyword.equals("validateLogin"))
						app.validateLogin(data);
					else if(keyword.equals("validateElementPresent"))
						app.validateElementPresent(objectKey);
					else if(keyword.equals("acceptAlertIfPresent"))
						app.acceptAlertIfPresent();
					else if(keyword.equals("defaultLogin"))
						app.defaultLogin();
					else if(keyword.equals("clear"))
						app.clear(objectKey);
					else if(keyword.equals("select"))
						app.select(objectKey,data);
					
				}
				
				
		}

	}

}
