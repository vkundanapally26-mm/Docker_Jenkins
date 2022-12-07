package stepdefinitions;

import static com.codeborne.selenide.Selenide.$;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageclass.SelfTest;

public class SelfHealSteps {
	
	SelfTest selfTest ;
	@Given("^I want to launch the test with url$")
	public void i_want_to_launch_the_test_with_url() throws Throwable {
	 System.out.println("i_want_to_launch_the_test_with_url");
	 selfTest = new SelfTest();
	 selfTest.launchTheTestSteps1();
	}

	@And("^perform the steps$")
	public void perform_the_steps() throws Throwable {
		System.out.println("perform_the_steps");
	}

	@When("^I complete action$")
	public void i_complete_action() throws Throwable {
	  System.out.println("i_complete_action");
	}

	@Then("^I validate the outcomes of self heal pages$")
	public void i_validate_the_outcomes_of_self_heal_pages() throws Throwable {
	 System.out.println("i_validate_the_outcomes_of_self_heal_pages");
	}

}
