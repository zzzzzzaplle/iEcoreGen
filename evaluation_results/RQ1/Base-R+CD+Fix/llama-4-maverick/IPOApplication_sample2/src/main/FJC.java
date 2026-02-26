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
    // Add document details if necessary
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
     * Constructs an Email object with receiver and content.
     * @param receiver the email recipient
     * @param content the email content
     */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Creates email content based on customer, company, shares, and amount.
     * @param customer the customer who applied for IPO
     * @param company the company for which IPO is applied
     * @param shares the number of shares applied for
     * @param amount the amount of money involved
     * @return the generated email content
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Name: " + customer.getName() + " " + customer.getSurname() +
               "\nEmail: " + customer.getEmail() +
               "\nTelephone: " + customer.getTelephone() +
               "\nCompany: " + company.getName() +
               "\nShares: " + shares +
               "\nAmount: " + amount;
    }

    // Getters and setters
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a company involved in an IPO application.
 */
class Company {
    private String name;
    private String email;

    /**
     * Constructs a Company object.
     */
    public Company() {}

    /**
     * Constructs a Company object with name and email.
     * @param name the company name
     * @param email the company email
     */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

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
     * Constructs an Application object with details.
     * @param share the number of shares applied for
     * @param amountOfMoney the amount of money involved
     * @param customer the customer who applied
     * @param company the company for which IPO is applied
     * @param allowance the document uploaded by the customer
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
     * @return true if the application is approved, false otherwise
     */
    public boolean approve() {
        if (customer.isEligibleForIPO()) {
            this.status = ApplicationStatus.APPROVAL;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application and sends a rejection email to the customer.
     * @return true if the application is rejected, false otherwise
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels the IPO application if it is pending.
     * @return true if the application is cancelled, false otherwise
     */
    public boolean cancel() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.REJECTED; // Assuming cancellation is treated as rejection
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

    // Getters and setters
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Document getAllowance() {
        return allowance;
    }

    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    public List<Email> getEmails() {
        return emails;
    }

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
        this.canApplyForIPO = true; // Assuming customers are eligible by default
    }

    /**
     * Checks if the customer is eligible for IPO application.
     * @return true if eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company with specified details.
     * @param company the company for which IPO is applied
     * @param shares the number of shares applied for
     * @param amount the amount of money involved
     * @param doc the document uploaded by the customer
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO() || shares <= 0 || doc == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false; // Application already exists and is approved
            }
        }
        Application newApplication = new Application(shares, amount, this, company, doc);
        applications.add(newApplication);
        return true;
    }

    /**
     * Retrieves the count of IPO applications made by the customer.
     * @return the count of applications that are either approved or rejected
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
     * Calculates the total amount of approved IPO applications.
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
     * Cancels a pending IPO application for the specified company.
     * @param companyName the name of the company for which the application is to be cancelled
     * @return true if the application is cancelled, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}