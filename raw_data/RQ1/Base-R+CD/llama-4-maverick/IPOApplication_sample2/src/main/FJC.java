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
     * Unparameterized constructor for Company.
     */
    public Company() {}

    /**
     * Parameterized constructor for Company.
     * 
     * @param name  The name of the company.
     * @param email The email address of the company.
     */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the name of the company.
     * 
     * @return The name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * 
     * @param name The name of the company.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the company.
     * 
     * @return The email address of the company.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the company.
     * 
     * @param email The email address of the company.
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
     * Unparameterized constructor for Email.
     */
    public Email() {}

    /**
     * Parameterized constructor for Email.
     * 
     * @param receiver The receiver's email address.
     * @param content  The content of the email.
     */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Creates the content of an email based on the customer's application details.
     * 
     * @param customer The customer who applied for IPO.
     * @param company  The company the customer applied to.
     * @param shares   The number of shares applied for.
     * @param amount   The amount of money involved in the application.
     * @return The content of the email.
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Name: " + customer.getName() + " " + customer.getSurname() + "\n" +
               "Email: " + customer.getEmail() + "\n" +
               "Telephone: " + customer.getTelephone() + "\n" +
               "Company Name: " + company.getName() + "\n" +
               "Number of Shares: " + shares + "\n" +
               "Amount: " + amount;
    }

    /**
     * Gets the receiver's email address.
     * 
     * @return The receiver's email address.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver's email address.
     * 
     * @param receiver The receiver's email address.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the content of the email.
     * 
     * @return The content of the email.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the email.
     * 
     * @param content The content of the email.
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
     * Unparameterized constructor for Application.
     */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /**
     * Parameterized constructor for Application.
     * 
     * @param share         The number of shares applied for.
     * @param amountOfMoney The amount of money involved in the application.
     * @param customer      The customer who made the application.
     * @param company       The company the application is for.
     * @param allowance     The document uploaded by the customer.
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
     * @return True if the application is approved successfully, false otherwise.
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
     * @return True if the application is rejected successfully, false otherwise.
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
     * @return True if the application is cancelled successfully, false otherwise.
     */
    public boolean cancel() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.REJECTED; // Assuming cancellation is treated as a form of rejection
            return true;
        }
        return false;
    }

    /**
     * Sends emails to both the customer and the company upon approval of the application.
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
        Email email = new Email(customer.getEmail(), content);
        emails.add(email);
        // Logic to actually send the email
    }

    /**
     * Gets the number of shares applied for.
     * 
     * @return The number of shares.
     */
    public int getShare() {
        return share;
    }

    /**
     * Sets the number of shares applied for.
     * 
     * @param share The number of shares.
     */
    public void setShare(int share) {
        this.share = share;
    }

    /**
     * Gets the amount of money involved in the application.
     * 
     * @return The amount of money.
     */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * Sets the amount of money involved in the application.
     * 
     * @param amountOfMoney The amount of money.
     */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Gets the status of the application.
     * 
     * @return The status of the application.
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     * 
     * @param status The status of the application.
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Gets the customer who made the application.
     * 
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the application.
     * 
     * @param customer The customer.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the company the application is for.
     * 
     * @return The company.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company the application is for.
     * 
     * @param company The company.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the document uploaded by the customer.
     * 
     * @return The document.
     */
    public Document getAllowance() {
        return allowance;
    }

    /**
     * Sets the document uploaded by the customer.
     * 
     * @param allowance The document.
     */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /**
     * Gets the emails associated with the application.
     * 
     * @return The list of emails.
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * Sets the emails associated with the application.
     * 
     * @param emails The list of emails.
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
     * Unparameterized constructor for Customer.
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // Default to true
    }

    /**
     * Parameterized constructor for Customer.
     * 
     * @param name        The customer's name.
     * @param surname     The customer's surname.
     * @param email       The customer's email address.
     * @param telephone   The customer's telephone number.
     */
    public Customer(String name, String surname, String email, String telephone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.canApplyForIPO = true; // Default to true
        this.applications = new ArrayList<>();
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * 
     * @return True if the customer is eligible, false otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     * 
     * @param company  The company the customer is applying to.
     * @param shares   The number of shares the customer is applying for.
     * @param amount   The amount of money involved in the application.
     * @param document The document uploaded by the customer.
     * @return True if the application is created successfully, false otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document document) {
        if (!isEligibleForIPO()) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false; // Customer already has an approved application for the same company
            }
        }
        Application application = new Application(shares, amount, this, company, document);
        applications.add(application);
        return true;
    }

    /**
     * Gets the total count of the customer's applications that have been processed (approved or rejected).
     * 
     * @return The count of processed applications.
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
     * Gets the total amount of money involved in the customer's approved IPO applications.
     * 
     * @return The total amount.
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
     * @param companyName The name of the company.
     * @return True if the application is cancelled successfully, false otherwise.
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
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * 
     * @param name The customer's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * 
     * @return The customer's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * 
     * @param surname The customer's surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email address.
     * 
     * @return The customer's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * 
     * @param email The customer's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number.
     * 
     * @return The customer's telephone number.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the customer's telephone number.
     * 
     * @param telephone The customer's telephone number.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Checks if the customer can apply for IPO.
     * 
     * @return True if the customer can apply, false otherwise.
     */
    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    /**
     * Sets whether the customer can apply for IPO.
     * 
     * @param canApplyForIPO True if the customer can apply, false otherwise.
     */
    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    /**
     * Gets the customer's IPO applications.
     * 
     * @return The list of applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets the customer's IPO applications.
     * 
     * @param applications The list of applications.
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}