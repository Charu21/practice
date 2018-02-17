
/**
This is a javadoc comment. 

@version 2.0
@author  Charusmita Shah
@see     "http://www.seleniumhq.org/"
@since    2018-02-14
	
	
*/

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Making a generic callBackPage class which can be called many times
public class callBacPage{

    private final WebDriver driver;
    private WebElement phoneNo;
    private WebElement submit;
    
    @FindBy(xpath = "//*[contains(text(), 'Call Back')]") 
    private WebElement callBack;

    public callBacPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void submit() {
    	//WebElement parentElement = driver.findElement(By.className("button"));
    	//WebElement childElement = parentElement.findElement(By.id("submit"));
    	//childElement.submit();
    	this.submit = driver.findElement(By.xpath("//*[@id='cal-reg']/div/input"));
    	this.submit.click();
    }

    public static callBacPage using(WebDriver driver) {
        return new callBacPage(driver);
    }

    public callBacPage launch() {
    	driver.get("http://techno-geek.co.uk/callmycab/");
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/nav/div/div[2]/ul/li[2]/a/span[2]")).click();
        return this;
    }


    
    public callBacPage setPhoneNo(String no) {
    	this.phoneNo = driver.findElement(By.xpath("//*[@id='Call']"));
    	this.phoneNo.sendKeys(no);
        return this;
    }
    public void tearDownDriver(){
     this.driver.close();
    }
    
}
    
