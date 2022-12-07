package commonutils;

import static com.codeborne.selenide.Selenide.$;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codeborne.selenide.WebDriverRunner;

import stepdefinitions.HooksTest;

public class CommonFunctions {
	WebDriver driver = HooksTest.idriver;
	//WebDriver driver = WebDriverRunner.getWebDriver();

	public static boolean isAlertPresent(WebDriver driver) {
		// try
		try { 
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println(" The Alert is handled successfully");
			return true;
			
		} // catch
		catch (NoAlertPresentException Ex) {
			System.out.println("There was no alert to handle");
			return false;
			
		} 
	}
	
	public static void runTerminalCommand(String command, String shortText) {
		try {
			String path = System.getProperty("user.dir");
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \""+path+"\"&&"+command);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			//BufferedReader r= new BufferedReader(new FileReader(shortText));
		    BufferedReader r= new BufferedReader(new InputStreamReader(p.getInputStream()));
		    System.out.println(r);
			String line;
			while(true) {
				line = r.readLine();
				if (line.contains(shortText)) {
					Thread.sleep(4000);
					System.out.println(line);
					break;
				}
			}
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
//	
//	public boolean isAlertPresentt(){
//	    boolean foundAlert = false;
//	    WebDriverWait wait = new WebDriverWait(driver, 0 /*timeout in seconds*/);
//	    try {
//	        wait.until(ExpectedConditions.alertIsPresent());
//	        foundAlert = true;
//	    } catch (TimeoutException eTO) {
//	        foundAlert = false;
//	    }
//	    return foundAlert;
//	}
	public void jseScrollIntoView(By ele ){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", $(ele));
	}
	public String getDateOfBirth(int age, int days){
		
		Calendar calendar = new GregorianCalendar();
		String pattern = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        calendar.add(Calendar.YEAR, -age);
        date = sdf.format(calendar.getTime());
		return date;
	}
	

	public String getCurrentURL(WebDriver driver)  {
		boolean flag = true;
		String text = driver.getCurrentUrl();
		if (flag) {
			System.out.println("Current URL is: \""+text+"\"");
		}
		return text;
	}
	
	public static String getTitle(WebDriver driver) {
		boolean flag = true;
		String text = driver.getTitle();
		if (flag) {
			System.out.println("Title of the page is: \""+text+"\"");
		}
		return text;
	}
	
	public static boolean switchWindowByTitle(WebDriver driver,String windowTitle, int count) {
		boolean flag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();

			String[] array = windowList.toArray(new String[0]);

			driver.switchTo().window(array[count-1]);

			if (driver.getTitle().contains(windowTitle)){
				flag = true;
			}else{
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			//flag = true;
			return false;
		} finally {
			if (flag) {
				System.out.println("Navigated to the window with title");
			} else {
				System.out.println("The Window with title is not Selected");
			}
		}
	}
	
	public static boolean switchToOldWindow(WebDriver driver) {
		boolean flag = false;
		try {

			Set<String> s=driver.getWindowHandles();
			Object popup[]=s.toArray();
			driver.switchTo().window(popup[0].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				System.out.println("Focus navigated to the window with title");			
			} else {
				System.out.println("The Window with title: is not Selected");
			}
		}
	}
	
	public static boolean moveToElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			Actions actions = new Actions(driver);
			// actions.moveToElement(driver.findElement(locator)).build().perform();
			actions.moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean JSClick(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			// driver.executeAsyncScript("arguments[0].click();", element);

			flag = true;

		}

		catch (Exception e) {
			throw e;

		} finally {
			if (flag) {
				System.out.println("Click Action is performed");
			} else if (!flag) {
				System.out.println("Click Action is not performed");
			}
		}
		return flag;
	}


	

}
