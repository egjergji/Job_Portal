# 🏢 Job Portal Backend

## **📌 Project Overview**
The **Job Portal Backend** is a RESTful API built using **Spring Boot, Spring Data JPA, and MySQL**. It allows **Admins, Employers, and Job Seekers** to manage job postings, applications, and reviews.

- **Admin:** Can view and delete users.
- **Employer:** Can post jobs, manage applications, and add job reviews.
- **Job Seeker:** Can apply for jobs, upload resumes, and track applications.

---

## **🚀 Tech Stack**
- **Backend:** Spring Boot
- **Database:** MySQL (Spring Data JPA)
- **Security:** Spring Security (JWT Authentication)
- **API Testing:** Postman
- **Mapper:** MapStruct / Manual DTO Mapping

---

## **📌 Features**
### ✅ **User Roles & Access Control**
- **Admin:** Manage users.
- **Employer:** Post jobs, view applications, update status, add reviews.
- **Job Seeker:** Apply for jobs, upload resumes, view applications.

### ✅ **API Endpoints**
- **Admin APIs**: View & delete users.
- **Employer APIs**: Post jobs, manage applications, update statuses, add reviews.
- **Job Seeker APIs**: Apply for jobs, upload resumes, view job listings & applications.
- **Review APIs**: Get reviews for a job (with pagination & filtering by rating).

---

## **⚙️ Project Setup**
### On this repository you can find two filees , 
one contains the nacesery files to run the backend of the server and 
the other has postman collections for testing the funcionallity of the application.

###Application setup
- **Create a MySQL database .
- **Update src/main/resources/application.properties to make possible the conection to a database .
- **Run the application.

###Postman collection setup
- **Launch Postman.
- **On My Workspace section chose the option import.
- **Import postman collections folder.

###On these collections you can find Api tests 



##Here you can find the API examples explained

#Register/Login APIs
/api/auth/register     --Register (requires Jsonfile with username, password and role )
/api/auth/login     --Login (requires Jsonfile with username and password) generates JWT token

--------Before role specific actions make sure tu use the JWT token for authentication purpouses

#Admin APIs			
GET	/api/admin/users?page=0&role=EMPLOYER	       --ADMIN	Get all users (pagination & filtering)
DELETE	/api/admin/users/delete/{id}	     --ADMIN	Delete a user

#Employer APIs			
POST	/api/employer/{employerId}/jobs	     --EMPLOYER	Post a new job
GET	/api/employer/{employerId}/jobs	     --EMPLOYER	View employer’s jobs
GET	/api/employer/{employerId}/jobs/{jobId}/applications	   --EMPLOYER	View applications for a job
PUT	/api/employer/{employerId}/jobs/{jobId}/applications/{applicationId}/status?status=ACCEPTED	EMPLOYER	   --Update application status
POST	/api/employer/{employerId}/jobs/{jobId}/reviews	    --EMPLOYER	Add a review for a job

#Job Seeker APIs			
POST	/api/jobseekers/{jobSeekerId}/apply?jobId=5	      --JOB_SEEKER	Apply for a job
PUT	/api/jobseekers/{jobSeekerId}/resume?resumeLink=https://example.com/resume.pdf	    --JOB_SEEKER	Upload a resume
GET	/api/jobseekers/{jobSeekerId}/applications?page=0&jobTitle=Engineer&status=PENDING	    --JOB_SEEKER	View own applications
GET	/api/jobseekers/jobs?page=0&title=Engineer&employerId=3	   --PUBLIC	View all jobs

#Review APIs			
GET	/api/reviews/job/{jobId}?page=0&minRating=4	PUBLIC     --Get reviews for a job




📌 Author
👤 Emanuel Gjergji
📧 emanuel.gjergji.swe@gmail.com
🔗 github.com/egjergji/
🔗 linkedin.com/in/emanuel-gjergji/

