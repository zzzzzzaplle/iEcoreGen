import java.util.ArrayList;
import java.util.List;

/**
 * Enum representing the possible statuses of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents a customer who can apply for IPOs.
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
        this.canApplyForIPO = true;
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

    /**
     * Checks if the customer is eligible to apply for IPOs.
     *
     * @return true if the customer can apply for IPOs, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     *
     * @param company The company for which the IPO application is made
     * @param shares  The number of shares to apply for (> 0)
     * @param amount  The amount of money for the application
     * @param doc     The legal allowance documentation
     * @return true if the application was created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        // Check eligibility
        if (!isEligibleForIPO()) {
            return false;
        }

        // Validate input parameters
        if (shares <= 0 || amount <= 0 || doc == null) {
            return false;
        }

        // Check if there's already an approved application for the same company
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVED) {
                return false;
            }
        }

        // Create and add the new application
        Application newApplication = new Application();
        newApplication.setShare(shares);
        newApplication.setAmountOfMoney(amount);
        newApplication.setStatus(ApplicationStatus.PENDING);
        newApplication.setCustomer(this);
        newApplication.setCompany(company);
        newApplication.setAllowance(doc);

        applications.add(newApplication);
        return true;
    }

    /**
     * Retrieves the count of reviewed IPO applications (approved or rejected).
     *
     * @return the number of reviewed applications
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of money from approved IPO applications.
     *
     * @return the sum of all approved application amounts
     */
    public double getApprovedTotalAmount() {
        double total = 0.0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company.
     *
     * @param companyName the name of the company for which to cancel the application
     * @return true if the cancellation was successful, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName) &&
                app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }
}

/**
 * Represents a company that offers IPOs.
 */
class Company {
    private String name;
    private String email;

    /**
     * Default constructor for Company.
     */
    public Company() {}

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
 * Represents a legal allowance document required for IPO applications.
 */
class Document {
    /**
     * Default constructor for Document.
     */
    public Document() {}
}

/**
 * Represents an email notification sent during the IPO application process.
 */
class Email {
    private String receiver;
    private String content;

    /**
     * Default constructor for Email.
     */
    public Email() {}

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

    /**
     * Creates the content for an email notification.
     *
     * @param customer The customer involved in the application
     * @param company  The company related to the application
     * @param shares   The number of shares applied for
     * @param amount   The amount of money for the application
     * @return the formatted email content
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Customer Information:\n");
        contentBuilder.append("- Name: ").append(customer.getName()).append("\n");
        contentBuilder.append("- Surname: ").append(customer.getSurname()).append("\n");
        contentBuilder.append("- Email: ").append(customer.getEmail()).append("\n");
        contentBuilder.append("- Telephone: ").append(customer.getTelephone()).append("\n");
        contentBuilder.append("- Applied Company: ").append(company.getName()).append("\n");
        contentBuilder.append("- Number of Shares: ").append(shares).append("\n");
        contentBuilder.append("- Amount Paid: $").append(amount).append("\n");
        
        return contentBuilder.toString();
    }
}

/**
 * Represents an IPO application made by a customer for a company.
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

    /**
     * Approves the application if the customer is still eligible for IPO applications.
     *
     * @return true if the application was approved successfully, false otherwise
     */
    public boolean approve() {
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        this.status = ApplicationStatus.APPROVED;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application and sends a rejection email to the customer.
     *
     * @return true if the application was rejected successfully, false otherwise
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application.
     *
     * @return true if the application was cancelled successfully, false otherwise
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }

        this.status = ApplicationStatus.REJECTED;
        return true;
    }

    /**
     * Sends information emails to both the customer and the company.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        String customerContent = customerEmail.createEmailContent(customer, company, share, amountOfMoney);
        customerEmail.setContent(customerContent);
        emails.add(customerEmail);

        // Email to company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        String companyContent = companyEmail.createEmailContent(customer, company, share, amountOfMoney);
        companyEmail.setContent(companyContent);
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        String content = "Your IPO application for " + company.getName() + " has been rejected.";
        rejectionEmail.setContent(content);
        emails.add(rejectionEmail);
    }
}