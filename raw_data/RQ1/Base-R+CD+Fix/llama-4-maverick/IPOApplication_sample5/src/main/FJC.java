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
    // Add properties and methods as needed for the Document class
}

/**
 * Represents a company that a customer can apply for IPO.
 */
class Company {
    private String name;
    private String email;

    /**
     * Default constructor for Company.
     */
    public Company() {}

    /**
     * Constructs a Company with the given name and email.
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
     * @return the company name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * 
     * @param name the company name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the company.
     * 
     * @return the company email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the company.
     * 
     * @param email the company email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Represents an email sent to a customer or company.
 */
class Email {
    private String receiver;
    private String content;

    /**
     * Default constructor for Email.
     */
    public Email() {}

    /**
     * Constructs an Email with the given receiver and content.
     * 
     * @param receiver the recipient's email address
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
     * @param company  the company the customer applied to
     * @param shares   the number of shares applied for
     * @param amount   the amount of money involved
     * @return a string representing the email content
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
     * Gets the recipient's email address.
     * 
     * @return the receiver's email
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the recipient's email address.
     * 
     * @param receiver the receiver's email to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the content of the email.
     * 
     * @return the email content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the email.
     * 
     * @param content the email content to set
     */
    public void setContent(String content) {
        this.content = content;
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
     * Default constructor for Application.
     */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /**
     * Constructs an Application with the given details.
     * 
     * @param share         the number of shares applied for
     * @param amountOfMoney the amount of money involved
     * @param customer      the customer who made the application
     * @param company       the company the application is for
     * @param allowance     the document uploaded by the customer
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
     * Approves the IPO application if the customer is eligible.
     * 
     * @return true if the application is approved successfully, false otherwise
     */
    public boolean approve() {
        if (customer.isEligibleForIPO() && status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.APPROVAL;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application and sends a rejection email to the customer.
     * 
     * @return true if the application is rejected successfully, false otherwise
     */
    public boolean reject() {
        if (status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.REJECTED;
            sendRejectionEmail();
            return true;
        }
        return false;
    }

    /**
     * Cancels the IPO application if it is pending.
     * 
     * @return true if the application is cancelled successfully, false otherwise
     */
    public boolean cancel() {
        if (status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.REJECTED; // Assuming cancellation is treated as rejection
            return true;
        }
        return false;
    }

    /**
     * Sends information emails to both the customer and the company upon approval.
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
     * @param share the number of shares to set
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
     * @param amountOfMoney the amount of money to set
     */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Gets the status of the application.
     * 
     * @return the application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     * 
     * @param status the application status to set
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
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the company the application is for.
     * 
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company the application is for.
     * 
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the document uploaded by the customer.
     * 
     * @return the allowance document
     */
    public Document getAllowance() {
        return allowance;
    }

    /**
     * Sets the document uploaded by the customer.
     * 
     * @param allowance the allowance document to set
     */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /**
     * Gets the list of emails related to this application.
     * 
     * @return the list of emails
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * Sets the list of emails related to this application.
     * 
     * @param emails the list of emails to set
     */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}

/**
 * Represents a retail customer who can apply for IPO.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // Assuming customers are eligible by default
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * 
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company.
     * 
     * @param company  the company to apply for IPO
     * @param shares   the number of shares to apply for
     * @param amount   the amount of money involved
     * @param document the document to be uploaded
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document document) {
        if (!isEligibleForIPO() || shares <= 0 || document == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false; // Customer already has an approved application for the same company
            }
        }
        Application newApplication = new Application(shares, amount, this, company, document);
        applications.add(newApplication);
        return true;
    }

    /**
     * Gets the total count of IPO applications made by the customer that have been processed.
     * 
     * @return the count of processed applications
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
     * Gets the total amount of all approved IPO applications.
     * 
     * @return the total approved amount
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
     * Cancels a pending IPO application for the specified company.
     * 
     * @param companyName the name of the company
     * @return true if the application is cancelled successfully, false otherwise
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
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * 
     * @return the customer's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email address.
     * 
     * @return the customer's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number.
     * 
     * @return the customer's telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the customer's telephone number.
     * 
     * @param telephone the telephone number to set
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
     * @param applications the list of applications to set
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}