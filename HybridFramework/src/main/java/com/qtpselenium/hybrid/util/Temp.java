package com.qtpselenium.hybrid.util;

import java.util.Hashtable;

public class Temp {

	public static void main(String[] args) {
		Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"//xls//TestCases.xlsx");
		int rows = xls.getRowCount("LoginTest");
		int cols = xls.getColumnCount("LoginTest");
		
		System.out.println("Total Rows "+ rows);
		System.out.println("Total Cols "+ cols);
		
		Object data[][] = new Object[rows-1][1];
		int r=0;
		Hashtable<String,String> table = null;
		for(int rNum=2;rNum<=rows;rNum++) {
			// new row
			table = new Hashtable<String, String>();
			for(int cNum=0;cNum<cols;cNum++) {
				String testData = xls.getCellData("LoginTest", cNum, rNum);
				String colName = xls.getCellData("LoginTest", cNum, 1);
				table.put(colName, testData);
			}
			System.out.println(table);
			data[r][0]=table;
			r++;
		}
		//
		
		
		
		

	}

}
