package stepdefinitions;

import org.junit.runners.Parameterized.BeforeParam;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideWait;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.commands.WaitUntil;
import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

import commonutils.TestPropertyFileRead;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.cucumber.java.BeforeAll;

import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.open;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class HooksTest{

	public static WebDriver idriver;
	TestPropertyFileRead testPropertyFileRead = new TestPropertyFileRead();
	String url = "";
	String prop = "";

	
	@Before
	public void InitialTest() throws Exception
	{
		 System.out.println("InitialTest");
	        Configuration.startMaximized = true;
	      //  System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
	        //Configuration.browser=testPropertyFileRead.getThePropertyValue("Browser");
	        crossBrowsing();
//	        ChromeOptions options = new ChromeOptions();
//	        //options.addArguments("--incognito");
//	        options.addArguments("ignore-certificate-errors", "ignore-urlfetcher-cert-requests");
//	        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			prop = System.getProperty("env");
			beforeTests(prop);
			
			url = testPropertyFileRead.getThePropertyValue("Url");
			open(url);
//	        open(testPropertyFileRead.getThePropertyValue("Url"));
	        idriver = WebDriverRunner.getWebDriver();
		
	} 

	@After
	public void CloseTest(Scenario scenario) throws Exception
	{
		addScreenshot(scenario);
		System.out.println("CloseTest");
	}
	
	public void beforeTests(String env) throws IOException
	{
		try
		{
			if(env.equals(null) || env.equals(""))
				System.out.println("environment condition not added is:  "+ env);
			
		}catch (NullPointerException a) {
			env = "qa";
			System.out.println("environment considered as:  "+ env);
		}
		
		testPropertyFileRead.envTest(env);
	}
	
	public void addScreenshot(Scenario scenario) throws Exception{
		
		String filepath = System.getProperty("user.dir") + "/TestResults/screenshots";
		File theDir = new File(filepath);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		if (scenario.isFailed()) {
			 String screenshotName = scenario.getName().replaceAll(" ", "_");
			 try {
			 File sourcePath = ((TakesScreenshot)idriver).getScreenshotAs(OutputType.FILE);
			 File destinationPath = new File(filepath+ "/"+ screenshotName + ".png");
			 Files.copy(sourcePath, destinationPath);   
			 Reporter.addScreenCaptureFromPath(destinationPath.toString());
			 } catch (Exception e) {
				 System.out.println("The screenshot exceptions"+e.toString());
			 } 
			 }
		

	}
	
	public void crossBrowsing() throws MalformedURLException {
		String urlToRenoteWD = "http://localhost:4444/wd/hub";
		RemoteWebDriver chrome = new RemoteWebDriver(new URL(urlToRenoteWD), DesiredCapabilities.chrome());
//		RemoteWebDriver firefox = new RemoteWebDriver(new URL(urlToRenoteWD), DesiredCapabilities.firefox());
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		// capabilities.setCapability("se:recordVideo", true);
//		capabilities.setCapability("browserName", "MicrosoftEdge");
//		RemoteWebDriver edge = new RemoteWebDriver(new URL(urlToRenoteWD), capabilities);
		WebDriverRunner.setWebDriver(chrome);
//		WebDriverRunner.setWebDriver(firefox);
//		WebDriverRunner.setWebDriver(edge);

	}

}













//Configuration.browser = "firefox";
//Configuration.headless = false;
//Configuration.timeout = 4000;
//Configuration.screenshots = false;
//Configuration.startMaximized = true;
//Configuration.browserSize = "800x600";
//Configuration.browserVersion =  "89.0.4389.23";
/*Configuration.proxyEnabled = true;
Configuration.proxyHost = "0.0.0.0";
Configuration.fileDownload = PROXY;*/