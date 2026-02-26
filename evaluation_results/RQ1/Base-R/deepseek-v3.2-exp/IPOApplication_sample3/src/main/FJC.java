import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer who can apply for IPO applications
 */
 class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean isEligible;
    private int failedAttempts;
    private List<IPOApplication> applications;

    /**
     * Default constructor
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.isEligible = true; // Customers are eligible by default
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

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
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
     * Creates an IPO application for this customer
     * @param companyName the target company name
     * @param numberOfShares the number of shares to purchase (must be > 0)
     * @param amount the amount of money to be paid
     * @param document the legal allowance documentation (must not be null)
     * @return true if the application was created successfully, false otherwise
     */
    public boolean createIPOApplication(String companyName, int numberOfShares, double amount, Object document) {
        if (!isEligible) {
            failedAttempts++;
            return false;
        }

        if (numberOfShares <= 0) {
            failedAttempts++;
            return false;
        }

        if (document == null) {
            failedAttempts++;
            return false;
        }

        // Check if customer already has an approved application for the same company
        for (IPOApplication app : applications) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
                failedAttempts++;
                return false;
            }
        }

        IPOApplication newApplication = new IPOApplication();
        newApplication.setCompanyName(companyName);
        newApplication.setNumberOfShares(numberOfShares);
        newApplication.setAmount(amount);
        newApplication.setDocument(document);
        newApplication.setStatus(ApplicationStatus.PENDING);
        
        applications.add(newApplication);
        failedAttempts = 0; // Reset failed attempts on successful application
        return true;
    }

    /**
     * Retrieves the total count of reviewed IPO applications (approved or rejected)
     * @return the number of reviewed applications
     */
    public int getIPOApplicationCountSummary() {
        int count = 0;
        for (IPOApplication app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of all approved IPO applications
     * @return the sum of approved application amounts
     */
    public double getTotalApprovedIPOAmount() {
        double total = 0.0;
        for (IPOApplication app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company
     * @param companyName the company name for which to cancel the application
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelPendingApplication(String companyName) {
        for (IPOApplication app : applications) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                applications.remove(app);
                return true;
            }
        }
        return false;
    }

    /**
     * Restricts the customer from IPO applications due to too many failed attempts
     */
    public void restrictFromIPOApplications() {
        if (failedAttempts >= 3) { // Assuming 3 failed attempts trigger restriction
            isEligible = false;
        }
    }

    /**
     * Requests IPO application access through call center
     */
    public void requestIPOAccess() {
        isEligible = true;
        failedAttempts = 0;
    }
}

/**
 * Represents an IPO application submitted by a customer
 */
 class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amount;
    private Object document;
    private ApplicationStatus status;

    /**
     * Default constructor
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
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
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents the bank system that manages IPO applications
 */
 class BankSystem {
    private EmailService emailService;

    /**
     * Default constructor
     */
    public BankSystem() {
        this.emailService = new EmailService();
    }

    /**
     * Approves or rejects an IPO application
     * @param customer the customer who submitted the application
     * @param application the IPO application to process
     * @param isApproved true to approve, false to reject
     * @return true if the operation was successful, false otherwise
     */
    public boolean approveOrRejectApplication(Customer customer, IPOApplication application, boolean isApproved) {
        if (application == null || customer == null) {
            return false;
        }

        if (!customer.isEligible()) {
            return false;
        }

        if (isApproved) {
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

    // Getters and setters
    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}

/**
 * Service responsible for sending emails related to IPO applications
 */
 class EmailService {
    /**
     * Sends an information email to the customer after approval
     * @param customer the customer to send the email to
     * @param application the approved IPO application
     */
    public void sendCustomerEmail(Customer customer, IPOApplication application) {
        // Implementation would send actual email
        String emailContent = createCustomerEmailContent(customer, application);
        // Email sending logic would go here
    }

    /**
     * Sends an information email to the company after approval
     * @param customer the customer who applied
     * @param application the approved IPO application
     */
    public void sendCompanyEmail(Customer customer, IPOApplication application) {
        // Implementation would send actual email
        String emailContent = createCompanyEmailContent(customer, application);
        // Email sending logic would go here
    }

    /**
     * Sends a rejection email to the customer
     * @param customer the customer to notify
     * @param application the rejected IPO application
     */
    public void sendRejectionEmail(Customer customer, IPOApplication application) {
        // Implementation would send actual email
        String emailContent = createRejectionEmailContent(customer, application);
        // Email sending logic would go here
    }

    /**
     * Creates the email content for customer notification
     * @param customer the customer information
     * @param application the IPO application details
     * @return the formatted email content
     */
    private String createCustomerEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "Your IPO application has been approved.\n\n" +
            "Application Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Shares: %d\n" +
            "Amount: $%.2f\n\n" +
            "Thank you for your application.",
            customer.getName(), customer.getSurname(),
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmount()
        );
    }

    /**
     * Creates the email content for company notification
     * @param customer the customer information
     * @param application the IPO application details
     * @return the formatted email content
     */
    private String createCompanyEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "IPO Application Approval Notification\n\n" +
            "Customer Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Shares: %d\n" +
            "Amount: $%.2f",
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmount()
        );
    }

    /**
     * Creates the rejection email content for customer notification
     * @param customer the customer information
     * @param application the rejected IPO application
     * @return the formatted email content
     */
    private String createRejectionEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "We regret to inform you that your IPO application has been rejected.\n\n" +
            "Application Details:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Shares: %d\n" +
            "Amount: $%.2f\n\n" +
            "Please contact our call center if you have any questions.",
            customer.getName(), customer.getSurname(),
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmount()
        );
    }
}