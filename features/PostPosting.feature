Feature: Post Job Posting
	As a company user, I wish to post job posting applications
	
	Scenario Outline: Posting a Job Posting
	Given a user is at the welcome page of BurntOut 
	When a user inputs clicks the Sign in button
	Then the user is redirected to the login page
	When the user is at the Log in page 
	And  the user enters their "<username>" and "<password>"
	Then the user is redirected to the welcome screen
	Given A user is logged in as a company
	When a user clicks the create job posting button
	And a user enters a job title "<jobtitle>"
	And a user enters a location "<location>"
	And a user enters an industry "<industry>"
	And a user enters a description "<description>"
	And submits the job posting information
	Then the user is redirected to the view self-job postings page as confirmation.
	
	Examples:
	
	|jobtitle|location|industry|description|username      |password|
	|Selenium| Sel,IM |Selenium|Description|spacexdragon  |password|
	
	
	