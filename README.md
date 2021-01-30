# BurntOut

## Project Overview
BurntOut is an application used by companies to post job openings and candidates to apply to them. Candidates can view job openings and search them by location, industry, company or job title. They can also apply for jobs, and view their applications. Company representatives can log in and post job openings, view a list of their applicants' information, such as resumes and applicants' profiles, and approve or deny applications. Also, users can create blog posts and see other users' posts in their feed.


## Technologies Used
- Java
- Spring Boot
- Spring Data
- Angular 2+
- HTML
- CSS
- JUnit
- Spring Test
- Selenium
- Karma
- Jasmine
- Log4J
- Maven
- Amazon Web Services
- AWS EC2
- AWS S3
- Jenkins
- Git
- Github
- Trello
- Agile

## List of Features Implemented:
- A user can sign up (create an account). There are two roles available: candidate and recruiter.
- The user can log in if correct combination of username and password is used.
- If the user forgot their password, they can click the "Forgot Password?" link and indicate their email address. After that, a recovery code is sent to their email address that allows them to change their password.
- The user can log out.
- If the user role is Recruiter, then the following features are available: 
  * The recruiter can create a job posting.
  * The recruiter can see their own job postings.
  * The recruiter can see all the applications submitted for their job postings and download the applicants' resumes.
  * The recruiter can approve or reject applications.
  * The recruiter can create a blog post and see blog posts of other users.
  * The recruiter can see all the job postings.
  * The recruiter can search for job postings by job title, by location, by company name or by industry.
  * The recruiter can see other users' profiles (including their applicants' profiles).
  * The recruiter can see and edit their own profile.
- If the user role is Candidate, then the following features are available:
  * The candidate can see all the job postings.
  * The candidate can search for job postings by job title, by location, by company name or by industry.
  * The candidate can apply for any job in the Job list.
  * The candidate can see their own applications and their status.
  * The candidate can create a blog post and see blog posts of other users.
  * The candidate can see other users' profiles.
  * The candidate can see and edit their own profile.
- Even if the user is not logged in, they can still see all the job postings and search for job postings by job title, by location, by company name or by industry.
  
## To-do List:
- Log out functionality
- Registration functionality
- Forgot Password feature
- Sending an email with a temporary password upon registration
- Password encryption
- Add more security to the project, to prevent unauthorized access

## How to set up / get started using it
- git clone the project using the project URL
- update the URL, username and password for the Database connection in the following file: com.example.dao.DAOConnection.java
- start Main Driver as Java Application, it will start Javalin
- Go to start page - http://localhost:7001/html/login.html

## Usage of the project
1. On the start page, enter "spacexdragon" as username and "password" as password

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/01_LoginPage.png "Optional title")

2. You will see the Employee screen:

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/02_Employee_Dashboard.png "Optional title")

3. Hit "View Past Tickets" button:

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/03_Past_Tickets.png "Optional title")

4. Go back and hit "Add Reimbursement Request" button, then fill out the form and hit "Submit Request":

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/04_Submit_New_Request.png "Optional title")

5. You can reload the login page and enter "nectarine" as username and "password" as password. Then hit "View All Tickets" button:

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/05_Finance_Manager_Dashboard.png "Optional title")

6. Then select "Pending" from the dropdown list:

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/06_View_All_Tickets.png "Optional title")

7. It will direct you to the Pending Tickets page. You can then hit the "Approve" or "Reject" button.

![Alt text](https://github.com/olgamelnikoff/project-1-revature/blob/master/src/main/resources/frontend/screenshots/07_Pending_Tickets.png "Optional title")

## Contributors and License information

