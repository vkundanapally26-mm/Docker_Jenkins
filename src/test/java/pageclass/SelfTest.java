package pageclass;

import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;
import org.openqa.selenium.By;
import com.codeborne.selenide.conditions.Visible;
import utilities.RecoverTestItems;

public class SelfTest {

	By userInputField = By.xpath("//input[@id='email']");
	By userPswdField = By.xpath("//input[@id='passwd']");
	By loginsubmit = By.xpath("//button[@id='SubmitLogin']");
	By orderHistoryBtn = By.xpath("//span[contains(text(),'Order history and details')]");
	By myPersonalBtn = By.xpath("//span[contains(text(),'My personal information')]");
	By backHomeBtn = By.xpath("//li/a[@href='http://automationpractice.com/index.php?controller=my-account']");
	By selectabout = By.xpath("//a[contains(text(),'About us')]");
	By selectContact = By.xpath("//div[@id='contact-link']");
	By selectSignIn = By.xpath("//a[contains(text(),'Sign in')]");
	
	//========================
	By userInputField1 = By.xpath("//input[@id='user-name']");
	By userInputField2 = By.xpath("//*[@id='popupModal']/div/div[1]/button");
	By userPswdField1 = By.xpath("//input[@id='password']");
	By loginsubmit1 = By.xpath("//input[@id='login-button']");
	By dashboardTitle = By.xpath("//span[contains(text(),'Products')]");
	By dashboardProduct1 = By.xpath("//div[@class='inventory_item_name'][contains(text(),'Sauce Labs Backpack')]");
	By dashboardProductBack = By.xpath("//button[@id='back-to-products']");
	By dashboardMenu = By.xpath("//button[@id='react-burger-menu-btn']");
	By dashboarCartSelect = By.xpath("//a[@class='shopping_cart_link']");
	By dashboarLogoutSelect = By.xpath("//a[@id='logout_sidebar_link']");
	
	//RecoverTestItems recoverTestItems ;

	public void launchTheTestSteps() throws Exception {
		RecoverTestItems recoverTestItems = new RecoverTestItems();
		recoverTestItems.TestSelfHealmethod();
		
		
		$(userInputField).setValue("testexecutionhelper@testhelper.com");
		$(userPswdField).setValue("test@123");
		$(loginsubmit).click();
		
		System.out.println("");
		System.out.println("the second page which is after logged in page:");
		System.out.println("the second page which is after logged in page:");
		System.out.println("the second page which is after logged in page:");
		System.out.println("");
		recoverTestItems.TestSelfHealmethod();
		
		$(orderHistoryBtn).click();
		/*recoverTestItems.TestSelfHealmethod();
		
		$(backHomeBtn).click();
		$(myPersonalBtn).click();
		recoverTestItems.TestSelfHealmethod();
		$(backHomeBtn).click();*/
	
	}
	
	public void launchTheTestSteps1() throws Exception {
		
		try
		{
		$(userInputField1).shouldBe(Visible.exist, Duration.ofSeconds(2));
		RecoverTestItems recoverTestItems = new RecoverTestItems();
		recoverTestItems.TestSelfHealmethod();
		
		$(userInputField1).setValue("standard_user");
		$(userPswdField1).setValue("secret_sauce");
		$(loginsubmit1).click();
		
		$(dashboardTitle).shouldBe(Visible.exist, Duration.ofSeconds(2));
		recoverTestItems.TestSelfHealmethod();
		Thread.sleep(1000);
		
		$(dashboardProduct1).click();
		recoverTestItems.TestSelfHealmethod();
		Thread.sleep(1000);
		
		$(dashboardProductBack).click();
		Thread.sleep(1000);
		
		$(dashboarCartSelect).click();
		recoverTestItems.TestSelfHealmethod();
		Thread.sleep(1000);
		
		$(dashboardMenu).click();
		Thread.sleep(1000);
		$(dashboarLogoutSelect).click();
		$(userInputField1).shouldBe(Visible.exist, Duration.ofSeconds(2));
		
		System.out.println("");
		System.out.println("The End of Self Healing Script:");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
