package com.ixigo.Util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.ixigo.BaseClass.BaseClass;

public class IXIGO_Util extends BaseClass {

	static JavascriptExecutor je = (JavascriptExecutor) driver;

	public static void JavaScriptClick(WebElement e) {
		je.executeScript("arguments[0].click();", e);
		// Reporter.log("Clicked on element "+e);
	}

	public static void Explicitwait(int timeout, String xpathkey) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathkey)));
	}

	public static void JavaScriptEnterValue(String value, WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + value + "';", e);
		Reporter.log("Value entered in element " + e);
	}

	public static void checkPageIsReady() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	public static void ScrollDownComplete() {
		Reporter.log("Scrolling down to load complete Page content..");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Scroll down completly to load all elements...");

		for (int i = 0; i < 10; i++) {

			je.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		}
	}

	public static String takeScreenShot() throws Exception {
		File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String Path = System.getProperty("user.dir") + "/ScreenShot/" + System.currentTimeMillis() + ".png";

		FileUtils.copyFile(screenshotAs, new File(Path));

		return Path;
	}
	
	
	public static List<Map<String, String>> getMapTestData(){
		List<Map<String,String>> testDataAllRows=null;
		
		Map<String,String> testdata =null;
		try{
			FileInputStream inputstream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/ixigo/testdata/Ixigo_Testdata.xlsx");
			
			Workbook workbook= new XSSFWorkbook(inputstream);
			Sheet sheet =workbook.getSheetAt(0);
			int lastRownum = sheet.getLastRowNum();
			
			int lastcolnumber=sheet.getRow(0).getLastCellNum();
			List<String> list = new ArrayList<String>();
			for(int i=0; i<lastcolnumber;i++){
				Row row=sheet.getRow(0);
				Cell cell=row.getCell(i);
				String rowheader=cell.getStringCellValue().trim();
				list.add(rowheader);
			}
			
			testDataAllRows=new ArrayList<Map<String,String>>();	
			for(int j=1; j<=lastRownum;j++){
				Row row = sheet.getRow(j);
				testdata=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				for(int k=0;k<lastcolnumber;k++){
					Cell cell =row.getCell(k);	
						if(cell==null){
							break;
						}
					switch (cell.getCellType()) {
		                
		                case Cell.CELL_TYPE_STRING:
		                    System.out.println(cell.getRichStringCellValue().getString());
		                    String colvalue=cell.getStringCellValue().trim();
							testdata.put((String) list.get(k), colvalue);
		                    break;
		                    
		                case Cell.CELL_TYPE_NUMERIC:
		                    if (DateUtil.isCellDateFormatted(cell)) {
		                        System.out.println(cell.getDateCellValue());
		                    } else {
		                        System.out.println(cell.getNumericCellValue());
		                        String colvaluenumeric=NumberToTextConverter.toText(cell.getNumericCellValue());
		    					testdata.put((String) list.get(k), colvaluenumeric);
		                    }
		                    break;
		                    
		                case Cell.CELL_TYPE_BOOLEAN:
		                    System.out.println(cell.getBooleanCellValue());
		                    break;
		                    
		                case Cell.CELL_TYPE_FORMULA:
		                    System.out.println(cell.getCellFormula());
		                    break;
		                    
		                default:
		                    System.out.println();
		            }
					
					
					
					//String colvalue=cell.getStringCellValue().trim();
					//testdata.put((String) list.get(k), colvalue);	
				}
				
				testDataAllRows.add(testdata);
			}						
		}
		catch(Exception e){
		  e.printStackTrace();	
		}
		
		return testDataAllRows;
		
	}
		
}
