import java.util.ArrayList;
import java.util.List;

/**
 * Enum representing the status of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Represents a document uploaded by a customer.
 */
class Document {
    // Add document-related fields and methods as needed
}

/**
 * Represents an email sent to a customer or company.
 */
class Email {
    private String receiver;
    private String content;

    /**
     * Constructs an Email object.
     */
    public Email() {}

    /**
     * Constructs an Email object with the given receiver and content.
     * 
     * @param receiver the email address of the receiver
     * @param content  the content of the email
     */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Creates the content of an email based on the customer's application details.
     * 
     * @param customer the customer who applied for IPO
     * @param company  the company to which the customer applied
     * @param shares   the number of shares applied for
     * @param amount   the amount of money involved in the application
     * @return the content of the email
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Name: " + customer.getName() + " " + customer.getSurname() +
               "\nEmail: " + customer.getEmail() +
               "\nTelephone: " + customer.getTelephone() +
               "\nCompany: " + company.getName() +
               "\nNumber of Shares: " + shares +
               "\nAmount: " + amount;
    }

    /**
     * Gets the receiver's email address.
     * 
     * @return the receiver's email address
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver's email address.
     * 
     * @param receiver the receiver's email address
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the content of the email.
     * 
     * @return the content of the email
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the email.
     * 
     * @param content the content of the email
     */
    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a company that issues IPO.
 */
class Company {
    private String name;
    private String email;

    /**
     * Constructs a Company object.
     */
    public Company() {}

    /**
     * Constructs a Company object with the given name and email.
     * 
     * @param name  the name of the company
     * @param email the email address of the company
     */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the name of the company.
     * 
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * 
     * @param name the name of the company
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the company.
     * 
     * @return the email address of the company
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the company.
     * 
     * @param email the email address of the company
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Represents an IPO application made by a customer.
 */
class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    /**
     * Constructs an Application object.
     */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /**
     * Constructs an Application object with the given details.
     * 
     * @param share        the number of shares applied for
     * @param amountOfMoney the amount of money involved in the application
     * @param customer     the customer who made the application
     * @param company      the company to which the application was made
     * @param allowance    the document uploaded by the customer
     */
    public Application(int share, double amountOfMoney, Customer customer, Company company, Document allowance) {
        this.share = share;
        this.amountOfMoney = amountOfMoney;
        this.customer = customer;
        this.company = company;
        this.allowance = allowance;
        this.status = ApplicationStatus.PENDING;
        this.emails = new ArrayList<>();
    }

    /**
     * Approves the IPO application.
     * 
     * @return true if the application is approved successfully, false otherwise
     */
    public boolean approve() {
        if (this.status == ApplicationStatus.PENDING && this.customer.isEligibleForIPO()) {
            this.status = ApplicationStatus.APPROVAL;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application.
     * 
     * @return true if the application is rejected successfully, false otherwise
     */
    public boolean reject() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.REJECTED;
            sendRejectionEmail();
            return true;
        }
        return false;
    }

    /**
     * Cancels the IPO application.
     * 
     * @return true if the application is canceled successfully, false otherwise
     */
    public boolean cancel() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.REJECTED; // Assuming cancel means reject
            return true;
        }
        return false;
    }

    /**
     * Sends emails to the customer and the company upon approval of the application.
     */
    public void sendEmailsToCustomerAndCompany() {
        String content = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email customerEmail = new Email(customer.getEmail(), content);
        Email companyEmail = new Email(company.getEmail(), content);
        emails.add(customerEmail);
        emails.add(companyEmail);
        // Logic to actually send the emails
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        String content = "Your IPO application for " + company.getName() + " has been rejected.";
        Email rejectionEmail = new Email(customer.getEmail(), content);
        emails.add(rejectionEmail);
        // Logic to actually send the email
    }

    /**
     * Gets the number of shares applied for.
     * 
     * @return the number of shares
     */
    public int getShare() {
        return share;
    }

    /**
     * Sets the number of shares applied for.
     * 
     * @param share the number of shares
     */
    public void setShare(int share) {
        this.share = share;
    }

    /**
     * Gets the amount of money involved in the application.
     * 
     * @return the amount of money
     */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * Sets the amount of money involved in the application.
     * 
     * @param amountOfMoney the amount of money
     */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Gets the status of the application.
     * 
     * @return the status of the application
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     * 
     * @param status the status of the application
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Gets the customer who made the application.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the application.
     * 
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the company to which the application was made.
     * 
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company to which the application was made.
     * 
     * @param company the company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the document uploaded by the customer.
     * 
     * @return the document
     */
    public Document getAllowance() {
        return allowance;
    }

    /**
     * Sets the document uploaded by the customer.
     * 
     * @param allowance the document
     */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /**
     * Gets the emails related to the application.
     * 
     * @return the list of emails
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * Sets the emails related to the application.
     * 
     * @param emails the list of emails
     */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}

/**
 * Represents a customer who can apply for IPO.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;

    /**
     * Constructs a Customer object.
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // Default to true
    }

    /**
     * Checks if the customer is eligible for IPO application.
     * 
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     * 
     * @param company  the company to which the application is made
     * @param shares   the number of shares applied for
     * @param amount   the amount of money involved in the application
     * @param document the document uploaded by the customer
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document document) {
        if (!isEligibleForIPO() || shares <= 0 || document == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false; // Customer already has an approved application for the same company
            }
        }
        Application newApplication = new Application(shares, amount, this, company, document);
        applications.add(newApplication);
        return true;
    }

    /**
     * Gets the total count of IPO applications made by the customer.
     * 
     * @return the count of applications
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the total amount of approved IPO applications.
     * 
     * @return the total amount
     */
    public double getApprovedTotalAmount() {
        double totalAmount = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVAL) {
                totalAmount += app.getAmountOfMoney();
            }
        }
        return totalAmount;
    }

    /**
     * Cancels a pending IPO application for a specific company.
     * 
     * @param companyName the name of the company
     * @return true if the application is canceled successfully, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }

    /**
     * Gets the customer's name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * 
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email address.
     * 
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * 
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number.
     * 
     * @return the telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the customer's telephone number.
     * 
     * @param telephone the telephone number
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Checks if the customer can apply for IPO.
     * 
     * @return true if the customer can apply, false otherwise
     */
    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    /**
     * Sets whether the customer can apply for IPO.
     * 
     * @param canApplyForIPO true if the customer can apply, false otherwise
     */
    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    /**
     * Gets the list of IPO applications made by the customer.
     * 
     * @return the list of applications
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets the list of IPO applications made by the customer.
     * 
     * @param applications the list of applications
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}