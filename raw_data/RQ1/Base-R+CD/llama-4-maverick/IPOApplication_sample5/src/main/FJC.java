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
    // Document details can be added here if needed
}

/**
 * Represents a company involved in an IPO application.
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
     * @param name  Name of the company.
     * @param email Email address of the company.
     */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the name of the company.
     * @return Name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name Name of the company.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the company.
     * @return Email address of the company.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the company.
     * @param email Email address of the company.
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
     * @param receiver Email address of the receiver.
     * @param content  Content of the email.
     */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Creates the content of an email based on customer and application details.
     * @param customer Customer details.
     * @param company  Company details.
     * @param shares   Number of shares applied for.
     * @param amount   Amount of money involved.
     * @return Content of the email.
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Name: " + customer.getName() + " " + customer.getSurname() +
               "\nCustomer Email: " + customer.getEmail() +
               "\nCustomer Telephone: " + customer.getTelephone() +
               "\nCompany Name: " + company.getName() +
               "\nNumber of Shares: " + shares +
               "\nAmount: " + amount;
    }

    /**
     * Gets the receiver's email address.
     * @return Receiver's email address.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver's email address.
     * @param receiver Receiver's email address.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the content of the email.
     * @return Content of the email.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the email.
     * @param content Content of the email.
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
     * @param share         Number of shares applied for.
     * @param amountOfMoney Amount of money involved.
     * @param customer      Customer making the application.
     * @param company       Company involved in the application.
     * @param allowance     Document uploaded by the customer.
     */
    public Application(int share, double amountOfMoney, Customer customer, Company company, Document allowance) {
        this.share = share;
        this.amountOfMoney = amountOfMoney;
        this.status = ApplicationStatus.PENDING;
        this.customer = customer;
        this.company = company;
        this.allowance = allowance;
        this.emails = new ArrayList<>();
    }

    /**
     * Approves the IPO application.
     * @return True if the application is approved successfully, false otherwise.
     */
    public boolean approve() {
        if (this.status == ApplicationStatus.PENDING && customer.isEligibleForIPO()) {
            this.status = ApplicationStatus.APPROVAL;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application.
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
     * @return True if the application is cancelled successfully, false otherwise.
     */
    public boolean cancel() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.REJECTED; // Assuming cancellation is treated as rejection
            return true;
        }
        return false;
    }

    /**
     * Sends emails to the customer and company upon approval of the application.
     */
    public void sendEmailsToCustomerAndCompany() {
        String content = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email customerEmail = new Email(customer.getEmail(), content);
        Email companyEmail = new Email(company.getEmail(), content);
        emails.add(customerEmail);
        emails.add(companyEmail);
        // Logic to send emails can be added here
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        String content = "Your IPO application for " + company.getName() + " has been rejected.";
        Email rejectionEmail = new Email(customer.getEmail(), content);
        emails.add(rejectionEmail);
        // Logic to send email can be added here
    }

    /**
     * Gets the number of shares applied for.
     * @return Number of shares.
     */
    public int getShare() {
        return share;
    }

    /**
     * Sets the number of shares applied for.
     * @param share Number of shares.
     */
    public void setShare(int share) {
        this.share = share;
    }

    /**
     * Gets the amount of money involved.
     * @return Amount of money.
     */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * Sets the amount of money involved.
     * @param amountOfMoney Amount of money.
     */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Gets the status of the application.
     * @return Status of the application.
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     * @param status Status of the application.
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Gets the customer who made the application.
     * @return Customer details.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the application.
     * @param customer Customer details.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the company involved in the application.
     * @return Company details.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company involved in the application.
     * @param company Company details.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the document uploaded by the customer.
     * @return Document details.
     */
    public Document getAllowance() {
        return allowance;
    }

    /**
     * Sets the document uploaded by the customer.
     * @param allowance Document details.
     */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /**
     * Gets the list of emails related to the application.
     * @return List of emails.
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * Sets the list of emails related to the application.
     * @param emails List of emails.
     */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}

/**
 * Represents a customer who can apply for an IPO.
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
    }

    /**
     * Parameterized constructor for Customer.
     * @param name        Name of the customer.
     * @param surname     Surname of the customer.
     * @param email       Email address of the customer.
     * @param telephone   Telephone number of the customer.
     * @param canApplyForIPO Eligibility to apply for IPO.
     */
    public Customer(String name, String surname, String email, String telephone, boolean canApplyForIPO) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.canApplyForIPO = canApplyForIPO;
        this.applications = new ArrayList<>();
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * @return True if eligible, false otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     * @param company Company involved in the application.
     * @param shares  Number of shares applied for.
     * @param amount  Amount of money involved.
     * @param doc     Document uploaded by the customer.
     * @return True if the application is created successfully, false otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!canApplyForIPO || shares <= 0 || doc == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false; // Application already exists and is approved
            }
        }
        Application newApp = new Application(shares, amount, this, company, doc);
        applications.add(newApp);
        return true;
    }

    /**
     * Gets the count of IPO applications made by the customer.
     * @return Count of applications.
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
     * @return Total amount.
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
     * @param companyName Name of the company.
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
     * Gets the name of the customer.
     * @return Name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name Name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the customer.
     * @return Surname of the customer.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the customer.
     * @param surname Surname of the customer.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the email address of the customer.
     * @return Email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     * @param email Email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the telephone number of the customer.
     * @return Telephone number of the customer.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the telephone number of the customer.
     * @param telephone Telephone number of the customer.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Checks if the customer can apply for IPO.
     * @return True if eligible, false otherwise.
     */
    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    /**
     * Sets the eligibility of the customer to apply for IPO.
     * @param canApplyForIPO Eligibility status.
     */
    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    /**
     * Gets the list of IPO applications made by the customer.
     * @return List of applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Sets the list of IPO applications made by the customer.
     * @param applications List of applications.
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}