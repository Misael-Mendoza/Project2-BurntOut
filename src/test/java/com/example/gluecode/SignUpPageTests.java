package com.example.gluecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import com.example.page.SignUpPage;
import com.example.page.WelcomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpPageTests {
	
	public WelcomePage welcomePage;
	public SignUpPage signupPage;
	public WelcomePage welcomePage2;
	
	@Given("a nonuser is at the welcome page of BurntOut")
	public void a_nonuser_is_at_the_welcome_page_of_burnt_out() throws InterruptedException {
	    TimeUnit.SECONDS.sleep(1);
	    this.welcomePage = new WelcomePage(BurntOutDriverUtility.driver);
	    assertEquals(this.welcomePage.header.getText(), "Welcome to BurntOut!");
	}

	@When("a nonuser clicks the Sign Up button")
	public void a_nonuser_clicks_the_sign_up_button() {
	    this.welcomePage.signUpButton.click();
	}

	@Then("the nonuser is redirected to the Sign Up page")
	public void the_nonuser_is_redirected_to_the_sign_up_page() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
		this.signupPage = new SignUpPage(BurntOutDriverUtility.driver);
		assertEquals(this.signupPage.header.getText(), "Register for BurntOut!");
	    
	}

	@When("the nonuser is at the Sign Up page")
	public void the_nonuser_is_at_the_sign_up_page() {
	    assertEquals(this.signupPage.header.getText(), "Register for BurntOut!");
	}

	@When("the nonuser enters {string} and {string} and {string} and {string} and {string}")
	public void the_nonuser_enters_and_and_and_and(String string, String string2, String string3, String string4, String string5) {
	    this.signupPage.signUpCandidate(string, string2, string3, string4, string5);
	}

//	@When("the nonuser clicks {string} and {string}")
//	public void the_nonuser_clicks_and(String string, String string2) {
//	    this.
//	}

	@Then("the user is redirected to the welcome page")
	public void the_user_is_redirected_to_the_welcome_page() throws InterruptedException {
	    TimeUnit.SECONDS.sleep(2);
	    this.welcomePage2 = new WelcomePage(BurntOutDriverUtility.driver);
	    assertEquals(this.welcomePage2.header.getText(), "Register for BurntOut!");
	}
}
