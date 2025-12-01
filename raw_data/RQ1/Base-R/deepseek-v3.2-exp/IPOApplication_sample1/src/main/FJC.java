import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the IPO application system.
 */
 class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean eligibleForIPO;
    private int failedAttempts;
    private boolean restricted;
    private List<IPOApplication> applications;

    /**
     * Default constructor initializing a customer with empty fields and default values.
     */
    public Customer() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.telephone = "";
        this.eligibleForIPO = true;
        this.failedAttempts = 0;
        this.restricted = false;
        this.applications = new ArrayList<>();
    }

    /**
     * Gets the customer's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number.
     * @return the telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the customer's telephone number.
     * @param telephone the telephone number to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Checks if the customer is eligible for IPO applications.
     * @return true if eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return eligibleForIPO;
    }

    /**
     * Sets the customer's eligibility for IPO applications.
     * @param eligibleForIPO the eligibility status to set
     */
    public void setEligibleForIPO(boolean eligibleForIPO) {
        this.eligibleForIPO = eligibleForIPO;
    }

    /**
     * Gets the number of failed IPO application attempts.
     * @return the number of failed attempts
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * Sets the number of failed IPO application attempts.
     * @param failedAttempts the number of failed attempts to set
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is restricted from IPO applications.
     * @return true if restricted, false otherwise
     */
    public boolean isRestricted() {
        return restricted;
    }

    /**
     * Sets the customer's restriction status for IPO applications.
     * @param restricted the restriction status to set
     */
    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * Gets the list of IPO applications submitted by the customer.
     * @return the list of IPO applications
     */
    public List<IPOApplication> getApplications() {
        return applications;
    }

    /**
     * Sets the list of IPO applications for the customer.
     * @param applications the list of IPO applications to set
     */
    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Increments the failed attempts counter and restricts the customer if threshold is reached.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) {
            this.restricted = true;
        }
    }

    /**
     * Removes restriction from the customer and resets failed attempts.
     */
    public void removeRestriction() {
        this.restricted = false;
        this.failedAttempts = 0;
    }

    /**
     * Creates an IPO application for this customer.
     * @param companyName the target company name
     * @param numberOfShares the number of shares to purchase (must be > 0)
     * @param amount the amount of money to pay (must be > 0)
     * @param document the legal allowance document (must not be null)
     * @return true if the application was created successfully, false otherwise
     */
    public boolean createIPOApplication(String companyName, int numberOfShares, double amount, Document document) {
        if (companyName == null || companyName.trim().isEmpty() || numberOfShares <= 0 || amount <= 0 || document == null) {
            return false;
        }

        if (this.restricted || !this.eligibleForIPO) {
            return false;
        }

        for (IPOApplication app : applications) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
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
        return true;
    }

    /**
     * Retrieves the total count of reviewed IPO applications (approved or rejected).
     * @return the number of reviewed applications
     */
    public int getApplicationCountSummary() {
        int count = 0;
        for (IPOApplication app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of all approved IPO applications.
     * @return the sum of approved application amounts
     */
    public double getTotalApprovedAmount() {
        double total = 0.0;
        for (IPOApplication app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending IPO application for a specific company.
     * @param companyName the company name to cancel the application for
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelPendingApplication(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            return false;
        }

        for (IPOApplication app : applications) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                app.setStatus(ApplicationStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }
}

/**
 * Represents an IPO application submitted by a customer.
 */
 class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amount;
    private Document document;
    private ApplicationStatus status;

    /**
     * Default constructor initializing an IPO application with default values.
     */
    public IPOApplication() {
        this.companyName = "";
        this.numberOfShares = 0;
        this.amount = 0.0;
        this.document = null;
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Gets the company name for this IPO application.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name for this IPO application.
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares requested in this IPO application.
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares for this IPO application.
     * @param numberOfShares the number of shares to set
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount of money for this IPO application.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money for this IPO application.
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the document associated with this IPO application.
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the document for this IPO application.
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Gets the status of this IPO application.
     * @return the application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of this IPO application.
     * @param status the status to set
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Represents a legal allowance document required for IPO applications.
 */
 class Document {
    private String documentId;
    private String documentType;
    private String content;

    /**
     * Default constructor initializing a document with empty fields.
     */
    public Document() {
        this.documentId = "";
        this.documentType = "";
        this.content = "";
    }

    /**
     * Gets the document ID.
     * @return the document ID
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document ID.
     * @param documentId the document ID to set
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the document type.
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     * @param documentType the document type to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the document content.
     * @return the document content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the document content.
     * @param content the document content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents the status of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}

/**
 * Represents the banking system that manages IPO applications.
 */
 class BankSystem {
    private EmailService emailService;

    /**
     * Default constructor initializing the bank system with an email service.
     */
    public BankSystem() {
        this.emailService = new EmailService();
    }

    /**
     * Gets the email service used by the bank system.
     * @return the email service
     */
    public EmailService getEmailService() {
        return emailService;
    }

    /**
     * Sets the email service for the bank system.
     * @param emailService the email service to set
     */
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Approves or rejects an IPO application.
     * @param customer the customer who submitted the application
     * @param application the IPO application to process
     * @param approved true to approve, false to reject
     * @return true if the operation was successful, false otherwise
     */
    public boolean processApplication(Customer customer, IPOApplication application, boolean approved) {
        if (customer == null || application == null || application.getStatus() != ApplicationStatus.PENDING) {
            return false;
        }

        if (approved) {
            if (!customer.isEligibleForIPO() || customer.isRestricted()) {
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
}

/**
 * Service responsible for sending emails related to IPO applications.
 */
 class EmailService {
    
    /**
     * Default constructor for the email service.
     */
    public EmailService() {
    }

    /**
     * Sends an information email to the customer after successful IPO application approval.
     * @param customer the customer to send the email to
     * @param application the approved IPO application
     */
    public void sendCustomerEmail(Customer customer, IPOApplication application) {
        // Implementation would send an actual email
        String emailContent = generateCustomerEmailContent(customer, application);
        System.out.println("Sending email to customer: " + customer.getEmail());
        System.out.println("Content: " + emailContent);
    }

    /**
     * Sends an information email to the company after successful IPO application approval.
     * @param customer the customer who applied
     * @param application the approved IPO application
     */
    public void sendCompanyEmail(Customer customer, IPOApplication application) {
        // Implementation would send an actual email
        String emailContent = generateCompanyEmailContent(customer, application);
        System.out.println("Sending email to company: " + application.getCompanyName());
        System.out.println("Content: " + emailContent);
    }

    /**
     * Sends a rejection email to the customer when their IPO application is rejected.
     * @param customer the customer to send the email to
     * @param application the rejected IPO application
     */
    public void sendRejectionEmail(Customer customer, IPOApplication application) {
        // Implementation would send an actual email
        String emailContent = generateRejectionEmailContent(customer, application);
        System.out.println("Sending rejection email to customer: " + customer.getEmail());
        System.out.println("Content: " + emailContent);
    }

    /**
     * Generates email content for customer notification.
     * @param customer the customer
     * @param application the IPO application
     * @return the generated email content
     */
    private String generateCustomerEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\nYour IPO application for %s has been approved.\n" +
            "Number of shares: %d\n" +
            "Amount paid: $%.2f\n\n" +
            "Contact Information:\nEmail: %s\nTelephone: %s",
            customer.getName(), customer.getSurname(), application.getCompanyName(),
            application.getNumberOfShares(), application.getAmount(),
            customer.getEmail(), customer.getTelephone()
        );
    }

    /**
     * Generates email content for company notification.
     * @param customer the customer
     * @param application the IPO application
     * @return the generated email content
     */
    private String generateCompanyEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "IPO Application Details:\n" +
            "Customer: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Number of shares: %d\n" +
            "Amount: $%.2f",
            customer.getName(), customer.getSurname(), customer.getEmail(),
            customer.getTelephone(), application.getCompanyName(),
            application.getNumberOfShares(), application.getAmount()
        );
    }

    /**
     * Generates rejection email content for customer notification.
     * @param customer the customer
     * @param application the IPO application
     * @return the generated rejection email content
     */
    private String generateRejectionEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\nWe regret to inform you that your IPO application for %s has been rejected.\n\n" +
            "Contact Information:\nEmail: %s\nTelephone: %s",
            customer.getName(), customer.getSurname(), application.getCompanyName(),
            customer.getEmail(), customer.getTelephone()
        );
    }
}