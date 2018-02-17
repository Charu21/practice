/**
This is a javadoc comment. 

@version 2.0
@author  Charusmita Shah
@see     "http://www.seleniumhq.org/"
@since    2018-02-14
	
	
*/

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.sql.Date; 
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import java.util.*;


// This is a program to check with test data from excel file for validation of phoneNo for CallBackPage
// If valid phone number then Pass
// If invalid phone number then Fail
// This dummy test webpage also accepts Case 4.  i.e phone number with length greater than 10 which is wrong 
/*
 * Sr.No.	PhoneNo		ExpectedResult		ActualResults		Bug
 *  1		fgfsga			Fail				Fail	
 *  2		234				Fail				Fail	
 *  3		4234443254		Pass				Pass
 *  4		34222222111		Fail				Pass			Webpage has a bug that it accepts phoneno greater than 10 digits
 *  5		#$$@#adgd		Fail				Fail
 * 
 */
public class phoneCheck {
	
	
	// Function to get the phone number data,i.e, second column from excel sheet
	public static Map<Integer,String> getPhoneDataFromExcel(){
		//FileOutputStream os = null;
				FileInputStream fis = null;
				XSSFWorkbook myWorkBook=null;	
				Map<Integer,String> dataMap = new HashMap<Integer,String>();	
				try{
			 File myFile= new File("C://temp/testdata.xlsx"); 
			fis = new FileInputStream(myFile); 
			// Finds the workbook instance for XLSX file 
			 myWorkBook = new XSSFWorkbook (fis); 
			// Return first sheet from the XLSX workbook
			XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
			// Get iterator to all the rows in current sheet 
			Iterator<Row> rowIterator = mySheet.iterator();
			//Map<String, Object[]> newData = new HashMap<String, Object[]>();
		
			// Traversing over each row of XLSX file
			int i=0;
			while (rowIterator.hasNext()) 
			{ 
				Row row = rowIterator.next(); 
	 
				Cell cell = row.getCell(1); 
				if (cell.getCellType()== Cell.CELL_TYPE_STRING) 
				{
					dataMap.put(i, cell.getStringCellValue());
					i++;
					} 
				} System.out.println(""); 
			
			for(Map.Entry<Integer,String> m: dataMap.entrySet())
			{
				System.out.println(m.getValue());
			}
			fis.close();
			}
				catch (FileNotFoundException fe) { fe.printStackTrace(); } 
				catch (IOException ie) { ie.printStackTrace(); }
				finally{
			
			// Close workbook, OutputStream and Excel file to prevent leak 
					if(myWorkBook!=null)
						try {
							myWorkBook.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					
					if(fis!=null){
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
					}
			

			
		}


			
	return dataMap;
}


	// Putting results back to Excel sheet
	public static void putResultToExcel(Map<Integer,String> result){
	
		
		
			FileOutputStream os = null;
		//	FileInputStream fis=null;	
			XSSFWorkbook myWorkBook=null;	
				
				try{
					 File myFile= new File("C://temp/testdata.xlsx"); 
						os = new FileOutputStream(myFile); 
						 myWorkBook = new XSSFWorkbook (); 
			
						// Return first sheet from the XLSX workbook
						XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
						// Get iterator to all the rows in current sheet 
						// writing data into XLSX file 
					
				
					// writing data into XLSX file 
					int rowCount=1;
					for (Map.Entry<Integer, String> m : result.entrySet()) 
					{ 
						Row row = mySheet.getRow(rowCount);
						if(row==null)
						{
							row=mySheet.createRow(rowCount);
						}
						
						
						Cell cell =row.getCell(3);
						if (cell==null)
						{
								row.createCell(3).setCellValue(m.getValue());
							}
						rowCount++;
					}
					myWorkBook.write(os); 
					System.out.println("Writing on Excel file Finished ..."); 
					}
						catch (FileNotFoundException fe) { fe.printStackTrace(); } 
						catch (IOException ie) { ie.printStackTrace(); }
						finally{
					
					// Close workbook, OutputStream and Excel file to prevent leak 
							if(os!=null)
								try {
									os.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							if(myWorkBook!=null)
								try {
									myWorkBook.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
						}
							
						
					
}
	
	public static void main(String[] args){
		
		// setting the driver to be used as FireFox (this can be changed)
		System.setProperty("webdriver.gecko.driver","C:/SeleniumGecko/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		
		// Loading driver and calling the callBackPage
		callBacPage callPage =new callBacPage(driver);

		// taking test data to be passed
		Map<Integer,String> data =  getPhoneDataFromExcel();
		Map<Integer,String> output = new HashMap<Integer,String> ();
			for(Map.Entry<Integer, String> m: data.entrySet())
			{
				callPage.launch();
				if(m.getValue().toString().equals("PhoneNo"))
				{
					continue;
				}
				callPage= callPage.setPhoneNo(m.getValue());
				driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				callPage.submit();
				WebElement wb=null;
				try{
					 wb =driver.findElement(By.xpath("//*[@id='parsley-id-37']/li"));
						if ( wb!= null)
						{
							output.put(m.getKey(),"Fail");
						}
					}
				catch(org.openqa.selenium.NoSuchElementException s)
				{
					output.put(m.getKey(),"Pass");
					Actions action = new Actions(driver);
					action.sendKeys(Keys.ESCAPE).perform();
					

						
					
				}

			}
			//putResultToExcel(output);
			for(Map.Entry<Integer,String> m:output.entrySet())
			{
				System.out.println("TestData Sr.No. "+m.getKey()+" & Result ="+m.getValue());
			}
			callPage.tearDownDriver();
		
			// Storing the automated test results in Excel Test execution File
			
	}
		
}