
















Feature: go to View Jobs page
	As a candidate, I want to search for a job, and to do it, I need to go to the View Jobs page.
	
	Scenario Outline: applying for a job
		Given a candidate has logged in
		When the candidate clicks "View Jobs"
		Then the candidate is redirected to the View Jobs page
		
	
	