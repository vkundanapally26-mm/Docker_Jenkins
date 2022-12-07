package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitMethods {

	public static void WaitForPageToLoad(WebDriver driver, WebDriverWait wait) {

		ExpectedCondition<Boolean> PageLoadCondition = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}

		};
		wait.until(PageLoadCondition);
	}

	public static void waitForTextToBePresentInElementValue(WebDriverWait wait, By locator, String text) {
		try {
			
			wait.until(ExpectedConditions.textToBePresentInElementValue(locator, text));
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void waitForElementToBeClickable(WebDriverWait wait, By locator) {
		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void waitForFrameToBeAvailable(WebDriverWait wait,String frameLocator) {
		try {
			
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void waitForElementToBeClickable(WebDriverWait wait,WebElement element) {
		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(element));
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
