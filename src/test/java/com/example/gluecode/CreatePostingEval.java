package com.example.gluecode;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import com.example.page.CreatePostingPage;
import com.example.page.LogIn;
import com.example.page.ViewSelfPostings;
import com.example.page.WelcomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreatePostingEval {
	private CreatePostingPage createPostingPage;
	private LogIn loginPage;
	private ViewSelfPostings postingsPage;
	private WelcomePage welcomePage;
	private String postingTitle;
	
	@Given("A user is logged in as a company")
	public void a_user_is_logged_in_as_a_company() {
		
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.welcomePage = new WelcomePage(BurntOutDriverUtility.driver);
		
	}

	@When("a user clicks the create job posting button")
	public void a_user_clicks_the_create_job_posting_button() {
		this.welcomePage.createPostingButton.click();
	}
	@When("a user enters a job title {string}")
	public void a_user_enters_a_job_title(String string) {
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    this.createPostingPage = new CreatePostingPage(BurntOutDriverUtility.driver);
	    this.postingTitle = string;
	    this.createPostingPage.setTitle(string);
	}
	@When("a user enters a location {string}")
	public void a_user_enters_a_location(String string) {
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    this.createPostingPage.setLocation(string);
	}
	@When("a user enters an industry {string}")
	public void a_user_enters_an_industry(String string) {
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    this.createPostingPage.setIndustry(string);
	}
	@When("a user enters a description {string}")
	public void a_user_enters_a_description(String string) {
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    this.createPostingPage.setDescription(string);
	}
	@When("submits the job posting information")
	public void submits_the_job_posting_information() {
		BurntOutDriverUtility.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    this.createPostingPage.submitButton.click();
	}
	
	@Then("the user is redirected to the view self-job postings page as confirmation.")
	public void the_user_is_redirected_to_the_view_self_job_postings_page_as_confirmation() throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		this.postingsPage = new ViewSelfPostings(BurntOutDriverUtility.driver);
		//Make sure it was added to the table of job postings
		assertEquals(this.postingTitle, this.postingsPage.checkLastTitleAdded());
		assertEquals("http://localhost:4200/company/view-postings", BurntOutDriverUtility.driver.getCurrentUrl());
	}

}
