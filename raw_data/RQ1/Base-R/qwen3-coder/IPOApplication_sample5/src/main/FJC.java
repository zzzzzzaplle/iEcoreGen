import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer who can apply for IPOs.
 */
class Customer {
    private String name;
    private String surname;
    private String emailAddress;
    private String telephoneNumber;
    private boolean eligibleForIPO;
    private int failedAttempts;
    private List<IPOApplication> applications;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.eligibleForIPO = true;
        this.failedAttempts = 0;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public boolean isEligibleForIPO() {
        return eligibleForIPO;
    }

    public void setEligibleForIPO(boolean eligibleForIPO) {
        this.eligibleForIPO = eligibleForIPO;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Increases the failed attempts counter and restricts the customer from IPO application
     * if the failed attempts exceed a threshold (e.g., 3).
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) {
            this.eligibleForIPO = false;
        }
    }

    /**
     * Resets the failed attempts counter and makes the customer eligible for IPO again.
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
        this.eligibleForIPO = true;
    }
}

/**
 * Represents a company that offers IPOs.
 */
class Company {
    private String name;
    private String emailAddress;

    /**
     * Default constructor for Company.
     */
    public Company() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

/**
 * Represents an IPO application submitted by a customer.
 */
class IPOApplication {
    private Customer customer;
    private Company company;
    private int numberOfShares;
    private double amount;
    private String document;
    private boolean approved;
    private boolean rejected;
    private boolean reviewed; // Indicates if the application has been reviewed by the bank

    /**
     * Default constructor for IPOApplication.
     */
    public IPOApplication() {
        this.approved = false;
        this.rejected = false;
        this.reviewed = false;
    }

    // Getters and setters
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

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
        this.reviewed = true;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
        this.reviewed = true;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}

/**
 * Represents the email system for sending notifications.
 */
class EmailService {
    /**
     * Sends an email with the specified content.
     *
     * @param recipient The email recipient
     * @param content   The content of the email
     */
    public void sendEmail(String recipient, String content) {
        // Implementation for sending email
        System.out.println("Email sent to: " + recipient);
        System.out.println("Content: " + content);
    }

    /**
     * Sends a rejection email to the customer.
     *
     * @param application The IPO application that was rejected
     */
    public void sendRejectionEmail(IPOApplication application) {
        String content = "Dear " + application.getCustomer().getName() + " " + 
                        application.getCustomer().getSurname() + ",\n" +
                        "Your IPO application for " + application.getCompany().getName() + 
                        " has been rejected.\n" +
                        "Number of shares: " + application.getNumberOfShares() + "\n" +
                        "Amount paid: $" + application.getAmount();
        sendEmail(application.getCustomer().getEmailAddress(), content);
    }

    /**
     * Sends information emails to both the customer and the company for an approved application.
     *
     * @param application The approved IPO application
     */
    public void sendApprovalEmails(IPOApplication application) {
        // Email to customer
        String customerContent = "Dear " + application.getCustomer().getName() + " " + 
                                application.getCustomer().getSurname() + ",\n" +
                                "Your IPO application for " + application.getCompany().getName() + 
                                " has been approved.\n" +
                                "Number of shares: " + application.getNumberOfShares() + "\n" +
                                "Amount paid: $" + application.getAmount();
        sendEmail(application.getCustomer().getEmailAddress(), customerContent);

        // Email to company
        String companyContent = "IPO Application Approved\n" +
                                "Customer: " + application.getCustomer().getName() + " " + 
                                application.getCustomer().getSurname() + "\n" +
                                "Email: " + application.getCustomer().getEmailAddress() + "\n" +
                                "Phone: " + application.getCustomer().getTelephoneNumber() + "\n" +
                                "Company: " + application.getCompany().getName() + "\n" +
                                "Number of shares: " + application.getNumberOfShares() + "\n" +
                                "Amount paid: $" + application.getAmount();
        sendEmail(application.getCompany().getEmailAddress(), companyContent);
    }
}

/**
 * Represents the main IPO application system.
 */
class IPOApplicationSystem {
    private EmailService emailService;

    /**
     * Default constructor for IPOApplicationSystem.
     */
    public IPOApplicationSystem() {
        this.emailService = new EmailService();
    }

    // Getters and setters
    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Creates an IPO application for a customer.
     *
     * @param customer        The customer applying for the IPO
     * @param company         The company offering the IPO
     * @param numberOfShares  The number of shares to purchase (>0)
     * @param amount          The amount of money for the purchase
     * @param document        The legal allowance documentation (non-null)
     * @return true if the application is successfully created, false otherwise
     */
    public boolean createIPOApplication(Customer customer, Company company, int numberOfShares, 
                                       double amount, String document) {
        // Check if customer is eligible for IPO
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        // Check if shares is greater than 0
        if (numberOfShares <= 0) {
            return false;
        }

        // Check if document is provided
        if (document == null || document.isEmpty()) {
            return false;
        }

        // Check if customer already has an approved application for this company
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompany().getName().equals(company.getName()) && app.isApproved()) {
                return false;
            }
        }

        // Create and add the application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);
        
        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     *
     * @param application The IPO application to process
     * @param approve     true to approve, false to reject
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve) {
        // Check if customer is still eligible for IPO
        if (!application.getCustomer().isEligibleForIPO()) {
            return false;
        }

        if (approve) {
            // Check if the application is not already approved or rejected
            if (application.isApproved() || application.isRejected()) {
                return false;
            }
            
            application.setApproved(true);
            emailService.sendApprovalEmails(application);
        } else {
            // Check if the application is not already approved or rejected
            if (application.isApproved() || application.isRejected()) {
                return false;
            }
            
            application.setRejected(true);
            emailService.sendRejectionEmail(application);
        }
        
        return true;
    }

    /**
     * Retrieves the number of reviewed (approved or rejected) IPO applications for a customer.
     *
     * @param customer The customer to check
     * @return The count of reviewed applications, or 0 if none
     */
    public int getCustomerApplicationCount(Customer customer) {
        int count = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.isReviewed()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of approved IPO applications for a customer.
     *
     * @param customer The customer to check
     * @return The sum of all approved application amounts
     */
    public double getTotalApprovedAmount(Customer customer) {
        double total = 0.0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.isApproved()) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending IPO application for a specific company.
     *
     * @param customer    The customer who wants to cancel
     * @param companyName The name of the company for which the application was made
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication app : customer.getApplications()) {
            // Check if application is for the specified company and is neither approved nor rejected
            if (app.getCompany().getName().equals(companyName) && 
                !app.isApproved() && !app.isRejected()) {
                customer.getApplications().remove(app);
                return true;
            }
        }
        return false;
    }
}