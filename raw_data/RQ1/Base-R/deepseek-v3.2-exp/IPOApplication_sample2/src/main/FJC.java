import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the IPO application system
 */
 class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private int failedAttempts;
    private boolean restricted;
    private List<IPOApplication> applications;

    /**
     * Default constructor
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.failedAttempts = 0;
        this.restricted = false;
    }

    /**
     * Checks if the customer is eligible to apply for IPOs
     * @return true if customer is retail and not restricted, false otherwise
     */
    public boolean isEligibleForIPO() {
        return !restricted;
    }

    /**
     * Increments failed attempts and restricts customer if threshold is reached
     */
    public void recordFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= 3) { // Assuming 3 failed attempts leads to restriction
            restricted = true;
        }
    }

    /**
     * Removes restriction from customer (e.g., after call center intervention)
     */
    public void removeRestriction() {
        restricted = false;
        failedAttempts = 0;
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

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }
}

/**
 * Represents an IPO application submitted by a customer
 */
 class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amountPaid;
    private ApplicationStatus status;
    private Customer customer;
    private Document document;

    /**
     * Default constructor
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Checks if the application is pending (neither approved nor rejected)
     * @return true if application is pending, false otherwise
     */
    public boolean isPending() {
        return status == ApplicationStatus.PENDING;
    }

    /**
     * Checks if the application has been reviewed (approved or rejected)
     * @return true if application has been reviewed, false otherwise
     */
    public boolean hasBeenReviewed() {
        return status == ApplicationStatus.APPROVED || status == ApplicationStatus.REJECTED;
    }

    // Getters and setters
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

/**
 * Represents a document uploaded during IPO application
 */
 class Document {
    private String fileName;
    private byte[] content;
    private String documentType;

    /**
     * Default constructor
     */
    public Document() {
    }

    // Getters and setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}

/**
 * Represents the status of an IPO application
 */
enum ApplicationStatus {
    PENDING, APPROVED, REJECTED
}

/**
 * Main system class that handles IPO application operations
 */
 class IPOApplicationSystem {
    private EmailService emailService;
    private List<Customer> customers;

    /**
     * Default constructor
     */
    public IPOApplicationSystem() {
        this.emailService = new EmailService();
        this.customers = new ArrayList<>();
    }

    /**
     * Creates an IPO application for a retail customer
     * @param customer The customer applying for the IPO
     * @param companyName The target company name
     * @param numberOfShares The number of shares to purchase (must be > 0)
     * @param amountPaid The amount of money paid
     * @param document The uploaded legal allowance document (must not be null)
     * @return true if application was created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, 
                                       int numberOfShares, double amountPaid, 
                                       Document document) {
        // Validate input parameters
        if (customer == null || companyName == null || companyName.trim().isEmpty() || 
            numberOfShares <= 0 || amountPaid <= 0 || document == null) {
            return false;
        }

        // Check customer eligibility
        if (!customer.isEligibleForIPO()) {
            customer.recordFailedAttempt();
            return false;
        }

        // Check if customer already has an approved application for the same company
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
                customer.recordFailedAttempt();
                return false;
            }
        }

        // Create new application
        IPOApplication application = new IPOApplication();
        application.setCompanyName(companyName);
        application.setNumberOfShares(numberOfShares);
        application.setAmountPaid(amountPaid);
        application.setDocument(document);
        application.setCustomer(customer);
        application.setStatus(ApplicationStatus.PENDING);

        // Add to customer's application list
        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves or rejects an IPO application
     * @param application The application to approve or reject
     * @param approved true to approve, false to reject
     * @return true if operation succeeded, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approved) {
        if (application == null || application.hasBeenReviewed()) {
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
            emailService.sendCustomerEmail(customer, application);
            emailService.sendCompanyEmail(customer, application);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            
            // Send rejection email to customer
            emailService.sendRejectionEmail(customer, application);
        }
        
        return true;
    }

    /**
     * Retrieves a customer's application count summary (approved and rejected applications only)
     * @param customer The customer to get summary for
     * @return The number of reviewed applications (approved + rejected), 0 if customer has none
     */
    public int getApplicationCountSummary(Customer customer) {
        if (customer == null) {
            return 0;
        }

        int count = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.hasBeenReviewed()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Queries total approved IPO applications amount for a customer
     * @param customer The customer to query
     * @return The sum of all approved application amounts, 0 if customer has no approved applications
     */
    public double getTotalApprovedAmount(Customer customer) {
        if (customer == null) {
            return 0;
        }

        double total = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmountPaid();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company
     * @param customer The customer requesting cancellation
     * @param companyName The company name of the application to cancel
     * @return true if cancellation succeeded, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        if (customer == null || companyName == null || companyName.trim().isEmpty()) {
            return false;
        }

        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.isPending()) {
                customer.getApplications().remove(app);
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}

/**
 * Service class for handling email notifications
 */
 class EmailService {
    
    /**
     * Sends email to customer with application information
     * @param customer The customer to send email to
     * @param application The application details
     */
    public void sendCustomerEmail(Customer customer, IPOApplication application) {
        // Implementation for sending email to customer
        String content = buildCustomerEmailContent(customer, application);
        // Actual email sending logic would go here
    }

    /**
     * Sends email to company with application information
     * @param customer The customer who applied
     * @param application The application details
     */
    public void sendCompanyEmail(Customer customer, IPOApplication application) {
        // Implementation for sending email to company
        String content = buildCompanyEmailContent(customer, application);
        // Actual email sending logic would go here
    }

    /**
     * Sends rejection email to customer
     * @param customer The customer to notify
     * @param application The rejected application
     */
    public void sendRejectionEmail(Customer customer, IPOApplication application) {
        // Implementation for sending rejection email
        String content = buildRejectionEmailContent(customer, application);
        // Actual email sending logic would go here
    }

    /**
     * Builds email content for customer notification
     * @param customer The customer
     * @param application The application
     * @return The formatted email content
     */
    private String buildCustomerEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "Your IPO application has been processed successfully.\n\n" +
            "Application Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Number of Shares: %d\n" +
            "Amount Paid: $%.2f\n\n" +
            "Thank you for using our services.",
            customer.getName(), customer.getSurname(),
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmountPaid()
        );
    }

    /**
     * Builds email content for company notification
     * @param customer The customer
     * @param application The application
     * @return The formatted email content
     */
    private String buildCompanyEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "IPO Application Notification\n\n" +
            "Customer Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Number of Shares: %d\n" +
            "Amount Paid: $%.2f",
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmountPaid()
        );
    }

    /**
     * Builds rejection email content for customer
     * @param customer The customer
     * @param application The application
     * @return The formatted email content
     */
    private String buildRejectionEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "We regret to inform you that your IPO application has been rejected.\n\n" +
            "Application Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Number of Shares: %d\n" +
            "Amount Paid: $%.2f\n\n" +
            "If you have any questions, please contact our call center.",
            customer.getName(), customer.getSurname(),
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmountPaid()
        );
    }
}