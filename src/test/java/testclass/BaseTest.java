package testclass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.cucumber.listener.Reporter;

import commonutils.CommonFunctions;
import commonutils.TestPropertyFileRead;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.selenide.AllureSelenide;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
        "html:target/cucumber/cucumber-html-report",
        "junit:target/cucumber/cucumber-junit-report.xml",
        "json:target/cucumber/cucumber.json",
        "io.qameta.allure.cucumberjvm.AllureCucumberJvm",
//        "com.epam.reportportal.cucumber.StepReporter",
        "com.cucumber.listener.ExtentCucumberFormatter:"        
        },
        glue = "stepdefinitions",
        features = "src/test/resources/features/dockerTest.feature",
        monochrome = true)
public class BaseTest extends AbstractTestNGCucumberTests
{
	
	@BeforeClass
	public static void setupAllureReports() throws IOException, InterruptedException {
		CommonFunctions.runTerminalCommand("docker-compose up", "Registered a node");
		System.out.println("Node Up");
		// Runtime.getRuntime().exec("cmd.exe /c start start_dockergrid.bat");
		//Thread.sleep(20000);
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

	}

	@AfterClass
	public static void extentReportGenerate() throws IOException, Exception {
		CommonFunctions.runTerminalCommand("docker-compose down" , "Removing selenium hub");
		// Runtime.getRuntime().exec("cmd /c start stop_dockergrid.bat");
		// Thread.sleep(10000);
	    // Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		Reporter.loadXMLConfig(new File("extentfile.xml"));
		Reporter.addStepLog("in the extent report");
	}

	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	

}
