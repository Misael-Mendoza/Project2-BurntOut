Feature: Applying For A Job 
	As a Candidate, I want to apply for a job
	Scenario Outline: Applying for a job 
		Given a user is at the welcome page of BurntOut 
		When a user inputs clicks the Sign in button
		Then the user is redirected to the login page
		When the user is at the Log in page 
		And  the user enters their "<username>" and "<password>"
		Then the user is redirected to the candidate screen
		When the candidate clicks the View Jobs link"
		Then the candidate is redirected to the View Jobs page
		When the candidate inputs the "<jobTitle>" to the Search bar
		When the candidate inputs "<anotherJobTitle>" to the Search bar
		Then the job postings corresponding to the search show up
		And the candidate clears the Search Bar
		When the candidate clicks the Apply button for the chosen posting
		Then the candidate is redirected to the Submit Application screen
		But the only information the candidate provides is the resume
		When the candidate clicks Submit Application
		Then the application is submitted
		
	Examples: 
		|username        |password     |	jobTitle  | 	anotherJobTitle  |
		|olgamelnikoff	 |olgamelnikoff|	J         |		A				 |
		