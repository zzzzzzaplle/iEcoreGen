// ==version1==
```
enum ApplicationStatus {
    PENDING
    APPROVAL
    REJECTED
}

class Customer {
    - name            : String
    - surname         : String
    - email           : String
    - telephone       : String
    - canApplyForIPO  : boolean
    - applications : List<Application>

    + isEligibleForIPO() : boolean
    
    + createApplication(company : Company, shares : int, amount : double, doc : Document) : boolean
    + getApplicationCount() : int
    + getApprovedTotalAmount() : double
    + cancelApplication(companyName: string) : boolean
}

class Company {
  - name  : String
  - email : String
}

class Document

class Email {
  - receiver : String
  - content  : String

  + createEmailContent( customer : Customer, company : Company, shares : int,  amount : double): String
}

class Application {
    - share         : int
    - amountOfMoney : double
    - status        : ApplicationStatus
    - customer      : Customer
    - company       : Company
    - allowance     : Document
    - emails        : List<Email>


    + approve() : boolean
    + reject(): boolean
    + cancel() : boolean
    + sendEmailsToCustomerAndCompany() : void
    + sendRejectionEmail() : void
}



Application --> "1" Document : allowance
Application --> "1" Customer : customer
Customer *-- "*" Application : applications
Application --> "1" Company : company
Application *-- "*" Email : emails
```
// ==end==