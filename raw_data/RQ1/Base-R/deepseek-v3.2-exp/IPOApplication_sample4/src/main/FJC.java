import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer who can apply for IPOs
 */
 class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean eligibleForIPO;
    private int failedAttempts;
    private List<IPOApplication> applications;

    /**
     * Default constructor
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.eligibleForIPO = true; // Customers are eligible by default
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
     * Increments the failed attempts counter and restricts the customer if too many failures
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) { // Assuming 3 failed attempts trigger restriction
            this.eligibleForIPO = false;
        }
    }

    /**
     * Reactivates IPO eligibility for customers who were restricted
     */
    public void reactivateIPOEligibility() {
        this.eligibleForIPO = true;
        this.failedAttempts = 0;
    }
}

/**
 * Represents an IPO application submitted by a customer
 */
 class IPOApplication {
    private Customer customer;
    private String companyName;
    private int numberOfShares;
    private double amountPaid;
    private String document;
    private ApplicationStatus status;

    /**
     * Default constructor
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    // Getters and setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Represents the status of an IPO application
 */
enum ApplicationStatus {
    PENDING, APPROVED, REJECTED
}

/**
 * Represents the banking system that manages IPO applications
 */
 class BankSystem {
    private EmailService emailService;
    private List<IPOApplication> allApplications;

    /**
     * Default constructor
     */
    public BankSystem() {
        this.emailService = new EmailService();
        this.allApplications = new ArrayList<>();
    }

    // Getters and setters
    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public List<IPOApplication> getAllApplications() {
        return allApplications;
    }

    public void setAllApplications(List<IPOApplication> allApplications) {
        this.allApplications = allApplications;
    }

    /**
     * Creates an IPO application for a customer
     * @param customer The customer applying for the IPO
     * @param companyName The name of the company for which IPO is being applied
     * @param numberOfShares The number of shares to purchase (must be > 0)
     * @param amountPaid The amount of money paid for the shares
     * @param document The legal allowance documentation (must not be null)
     * @return true if the application was created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amountPaid, String document) {
        // Validate input parameters
        if (customer == null || companyName == null || companyName.trim().isEmpty() || 
            numberOfShares <= 0 || amountPaid <= 0 || document == null) {
            return false;
        }

        // Check customer eligibility
        if (!customer.isEligibleForIPO()) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Check if customer already has an approved application for the same company
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
                customer.incrementFailedAttempts();
                return false;
            }
        }

        // Create new application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName(companyName);
        application.setNumberOfShares(numberOfShares);
        application.setAmountPaid(amountPaid);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);

        // Add to customer's applications and bank's record
        customer.getApplications().add(application);
        allApplications.add(application);

        return true;
    }

    /**
     * Approves or rejects an IPO application
     * @param application The application to be approved or rejected
     * @param approved true to approve, false to reject
     * @return true if the operation was successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approved) {
        if (application == null || application.getStatus() != ApplicationStatus.PENDING) {
            return false;
        }

        Customer customer = application.getCustomer();
        
        if (approved) {
            // Verify customer can still apply for IPO
            if (!customer.isEligibleForIPO()) {
                return false;
            }
            
            application.setStatus(ApplicationStatus.APPROVED);
            
            // Send emails to customer and company
            emailService.sendCustomerApprovalEmail(application);
            emailService.sendCompanyNotificationEmail(application);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            
            // Send rejection email to customer
            emailService.sendRejectionEmail(application);
        }
        
        return true;
    }

    /**
     * Retrieves the count of reviewed IPO applications for a customer
     * @param customer The customer whose applications are being counted
     * @return The number of approved and rejected applications (pending applications are not included)
     */
    public int getCustomerApplicationCountSummary(Customer customer) {
        if (customer == null) {
            return 0;
        }

        int count = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of approved IPO applications for a customer
     * @param customer The customer whose approved application amounts are being summed
     * @return The sum of all approved application amounts
     */
    public double getTotalApprovedIPOAmount(Customer customer) {
        if (customer == null) {
            return 0.0;
        }

        double total = 0.0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmountPaid();
            }
        }
        return total;
    }

    /**
     * Cancels a pending IPO application for a specific company
     * @param customer The customer requesting the cancellation
     * @param companyName The company name for which the application should be cancelled
     * @return true if the cancellation was successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        if (customer == null || companyName == null || companyName.trim().isEmpty()) {
            return false;
        }

        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                app.setStatus(ApplicationStatus.REJECTED); // Mark as rejected to indicate cancellation
                return true;
            }
        }
        return false;
    }
}

/**
 * Service responsible for sending emails related to IPO applications
 */
 class EmailService {
    
    /**
     * Sends approval email to customer
     * @param application The approved IPO application
     */
    public void sendCustomerApprovalEmail(IPOApplication application) {
        // Implementation for sending email to customer
        // Email content includes: customer name, surname, email, telephone, company name, shares, amount
        String content = createCustomerEmailContent(application);
        // Actual email sending implementation would go here
        System.out.println("Sending approval email to customer: " + content);
    }

    /**
     * Sends notification email to company
     * @param application The approved IPO application
     */
    public void sendCompanyNotificationEmail(IPOApplication application) {
        // Implementation for sending email to company
        String content = createCompanyEmailContent(application);
        // Actual email sending implementation would go here
        System.out.println("Sending notification email to company: " + content);
    }

    /**
     * Sends rejection email to customer
     * @param application The rejected IPO application
     */
    public void sendRejectionEmail(IPOApplication application) {
        // Implementation for sending rejection email
        String content = createRejectionEmailContent(application);
        // Actual email sending implementation would go here
        System.out.println("Sending rejection email to customer: " + content);
    }

    /**
     * Creates email content for customer approval notification
     * @param application The IPO application
     * @return Formatted email content
     */
    private String createCustomerEmailContent(IPOApplication application) {
        Customer customer = application.getCustomer();
        return String.format(
            "Dear %s %s,%n%n" +
            "Your IPO application for %s has been approved.%n" +
            "Number of shares: %d%n" +
            "Amount paid: $%.2f%n%n" +
            "Contact information:%n" +
            "Email: %s%n" +
            "Telephone: %s%n%n" +
            "Thank you for using our IPO services.",
            customer.getName(), customer.getSurname(), application.getCompanyName(),
            application.getNumberOfShares(), application.getAmountPaid(),
            customer.getEmail(), customer.getTelephone()
        );
    }

    /**
     * Creates email content for company notification
     * @param application The IPO application
     * @return Formatted email content
     */
    private String createCompanyEmailContent(IPOApplication application) {
        Customer customer = application.getCustomer();
        return String.format(
            "New IPO Application Approved:%n%n" +
            "Customer: %s %s%n" +
            "Email: %s%n" +
            "Telephone: %s%n" +
            "Company: %s%n" +
            "Shares: %d%n" +
            "Amount: $%.2f",
            customer.getName(), customer.getSurname(), customer.getEmail(),
            customer.getTelephone(), application.getCompanyName(),
            application.getNumberOfShares(), application.getAmountPaid()
        );
    }

    /**
     * Creates email content for rejection notification
     * @param application The rejected IPO application
     * @return Formatted email content
     */
    private String createRejectionEmailContent(IPOApplication application) {
        Customer customer = application.getCustomer();
        return String.format(
            "Dear %s %s,%n%n" +
            "We regret to inform you that your IPO application for %s has been rejected.%n%n" +
            "If you have any questions, please contact our call center.%n%n" +
            "Contact information:%n" +
            "Email: %s%n" +
            "Telephone: %s",
            customer.getName(), customer.getSurname(), application.getCompanyName(),
            customer.getEmail(), customer.getTelephone()
        );
    }
}