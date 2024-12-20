# Project 1 - ERS

This is a **Java Full Stack Employee Reimbursement System (ERS)**. The  ERS centers around Employees submitting Reimbursements that can either be accepted or denied by Managers. It is made using:
- ~~React-Based Front end (talks to backend via HTTP)~~ (It WILL be. Eventually. Not yet.)
- Spring-Based Back End
- Local Postgresql database

<hr>

**Employees using the application can:**

- Create a new Reimbursement

- See all reimbursement tickets (only their own)

- See only their pending reimbursement tickets

- Update an existing reimbursement's description or amount.


**Managers using the application can:**

- See all reimbursements

- See all reimbursements of a given status

- Resolve a reimbursement (update status from PENDING to APPROVED or DENIED)

- See all Users

- Delete a User and any related reimbursements

- Update existing user information, including role

*Managers can do anything Users can do. No need for role checks on User functionalities.\**

\**Managers cannot spoof reimbursements and submit them in other people's names as of the current implementation*


**Users who are not logged in to the application can ONLY:**

- Attempt to log in

- Register for a new account (create new User)