Feature: Applying For A Job 
	As a Candidate, I want to apply for a job
	Scenario Outline: Applying for a job 
		Given a user is at the welcome page of BurntOut 
		When a user inputs clicks the Sign in button
		Then the user is redirected to the login page
		When the user is at the Log in page 
		And  the user enters their "<username>" and "<password>"
		Then the user is redirected to the welcome screen
		When the candidate clicks the View Jobs link"
		Then the candidate is redirected to the View Jobs page
	
	Examples: 
		|username        |password     |
		|olgamelnikoff	 |olgamelnikoff|