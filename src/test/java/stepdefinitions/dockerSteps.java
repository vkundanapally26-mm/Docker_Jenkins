package stepdefinitions;

import com.codeborne.selenide.WebDriverRunner;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageclass.dockerStepsCheck;

public class dockerSteps {
	
	
	@Given("^Goto the google page$")
	public void goto_the_google_page() throws Throwable {
		System.out.println("Opened URL");
	}

	@Given("^Get the title of the page$")
	public void get_the_title_of_the_page() throws Throwable {
		System.out.println(dockerStepsCheck.getTitle(WebDriverRunner.getWebDriver()));
	}

	@Then("^Print in console$")
	public void print_in_console() throws Throwable {
		System.out.println("Done");
	}

}
