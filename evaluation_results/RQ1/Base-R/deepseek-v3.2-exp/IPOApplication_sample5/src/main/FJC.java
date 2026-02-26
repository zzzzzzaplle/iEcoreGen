import java.util.ArrayList;
import java.util.List;

/**
 * Represents a retail customer who can apply for IPOs
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
     * Gets the customer's name
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname
     * @return the customer's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname
     * @param surname the customer's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email address
     * @return the customer's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address
     * @param email the customer's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number
     * @return the customer's telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the customer's telephone number
     * @param telephone the customer's telephone number
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets the number of failed application attempts
     * @return the number of failed attempts
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * Sets the number of failed application attempts
     * @param failedAttempts the number of failed attempts
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is restricted from applying
     * @return true if the customer is restricted, false otherwise
     */
    public boolean isRestricted() {
        return restricted;
    }

    /**
     * Sets the customer's restriction status
     * @param restricted true to restrict the customer, false to remove restriction
     */
    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * Gets the list of IPO applications
     * @return the list of IPO applications
     */
    public List<IPOApplication> getApplications() {
        return applications;
    }

    /**
     * Sets the list of IPO applications
     * @param applications the list of IPO applications
     */
    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Increments the failed attempt counter and restricts the customer if too many failures
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) {
            this.restricted = true;
        }
    }

    /**
     * Removes restrictions from the customer
     */
    public void removeRestriction() {
        this.restricted = false;
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

    /**
     * Gets the customer who submitted the application
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who submitted the application
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the target company name
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the target company name
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares requested
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares requested
     * @param numberOfShares the number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount of money paid
     * @return the amount paid
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets the amount of money paid
     * @param amountPaid the amount paid
     */
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * Gets the uploaded document
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the uploaded document
     * @param document the document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the application status
     * @return the application status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the application status
     * @param status the application status
     */
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
 * Represents the bank system that manages IPO applications
 */
 class BankSystem {
    private List<Customer> customers;
    private EmailService emailService;

    /**
     * Default constructor
     */
    public BankSystem() {
        this.customers = new ArrayList<>();
        this.emailService = new EmailService();
    }

    /**
     * Gets the list of customers
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers
     * @param customers the list of customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the email service
     * @return the email service
     */
    public EmailService getEmailService() {
        return emailService;
    }

    /**
     * Sets the email service
     * @param emailService the email service
     */
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Creates an IPO application for a retail customer
     * 
     * @param customer the customer applying for the IPO
     * @param companyName the target company name
     * @param numberOfShares the number of shares to purchase (must be > 0)
     * @param amountPaid the amount of money to be paid
     * @param document the uploaded legal allowance documentation (must not be null)
     * @return true if the application was created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amountPaid, String document) {
        // Check if customer is eligible
        if (!isCustomerEligible(customer)) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Check if customer already has an approved application for the same company
        if (hasApprovedApplicationForCompany(customer, companyName)) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Validate input parameters
        if (numberOfShares <= 0 || document == null || document.trim().isEmpty()) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Create new application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName(companyName);
        application.setNumberOfShares(numberOfShares);
        application.setAmountPaid(amountPaid);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);

        // Add to customer's application list
        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves or rejects an IPO application
     * 
     * @param application the IPO application to process
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
            if (!isCustomerEligible(customer)) {
                return false;
            }
            application.setStatus(ApplicationStatus.APPROVED);
            
            // Send emails to customer and company
            emailService.sendCustomerConfirmationEmail(application);
            emailService.sendCompanyNotificationEmail(application);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            
            // Send rejection email to customer
            emailService.sendRejectionEmail(application);
        }
        
        return true;
    }

    /**
     * Retrieves a customer's application count summary (approved and rejected only)
     * 
     * @param customer the customer to query
     * @return the total number of approved and rejected applications, 0 if no applications
     */
    public int getCustomerApplicationCount(Customer customer) {
        if (customer == null || customer.getApplications() == null) {
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
     * Queries the total amount of approved IPO applications for a customer
     * 
     * @param customer the customer to query
     * @return the sum of all approved application amounts, 0 if no approved applications
     */
    public double getTotalApprovedAmount(Customer customer) {
        if (customer == null || customer.getApplications() == null) {
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
     * Cancels a pending application for a specific company
     * 
     * @param customer the customer requesting cancellation
     * @param companyName the company name of the application to cancel
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        if (customer == null || customer.getApplications() == null || companyName == null) {
            return false;
        }

        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                customer.getApplications().remove(app);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a customer is eligible to apply for IPOs
     * 
     * @param customer the customer to check
     * @return true if the customer is eligible, false otherwise
     */
    private boolean isCustomerEligible(Customer customer) {
        return customer != null && !customer.isRestricted();
    }

    /**
     * Checks if a customer has an approved application for a specific company
     * 
     * @param customer the customer to check
     * @param companyName the company name to check
     * @return true if the customer has an approved application for the company, false otherwise
     */
    private boolean hasApprovedApplicationForCompany(Customer customer, String companyName) {
        if (customer == null || customer.getApplications() == null || companyName == null) {
            return false;
        }

        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Service for sending email notifications
 */
 class EmailService {
    
    /**
     * Default constructor
     */
    public EmailService() {
    }

    /**
     * Sends a confirmation email to the customer for an approved application
     * 
     * @param application the approved IPO application
     */
    public void sendCustomerConfirmationEmail(IPOApplication application) {
        if (application == null || application.getCustomer() == null) {
            return;
        }

        Customer customer = application.getCustomer();
        String emailContent = generateCustomerEmailContent(customer, application);
        // Implementation to actually send email would go here
        System.out.println("Sending confirmation email to customer: " + customer.getEmail());
        System.out.println("Email content: " + emailContent);
    }

    /**
     * Sends a notification email to the company for an approved application
     * 
     * @param application the approved IPO application
     */
    public void sendCompanyNotificationEmail(IPOApplication application) {
        if (application == null) {
            return;
        }

        String emailContent = generateCompanyEmailContent(application);
        // Implementation to actually send email would go here
        System.out.println("Sending notification email to company: " + application.getCompanyName());
        System.out.println("Email content: " + emailContent);
    }

    /**
     * Sends a rejection email to the customer
     * 
     * @param application the rejected IPO application
     */
    public void sendRejectionEmail(IPOApplication application) {
        if (application == null || application.getCustomer() == null) {
            return;
        }

        Customer customer = application.getCustomer();
        String emailContent = generateRejectionEmailContent(customer, application);
        // Implementation to actually send email would go here
        System.out.println("Sending rejection email to customer: " + customer.getEmail());
        System.out.println("Email content: " + emailContent);
    }

    /**
     * Generates email content for customer confirmation
     * 
     * @param customer the customer
     * @param application the IPO application
     * @return the formatted email content
     */
    private String generateCustomerEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "Your IPO application for %s has been approved.\n" +
            "Number of shares: %d\n" +
            "Amount paid: $%.2f\n\n" +
            "Thank you for your business.\n" +
            "Best regards,\nBank System",
            customer.getName(), customer.getSurname(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmountPaid()
        );
    }

    /**
     * Generates email content for company notification
     * 
     * @param application the IPO application
     * @return the formatted email content
     */
    private String generateCompanyEmailContent(IPOApplication application) {
        Customer customer = application.getCustomer();
        return String.format(
            "IPO Application Notification:\n\n" +
            "Customer: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Number of shares: %d\n" +
            "Amount paid: $%.2f",
            customer.getName(), customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            application.getCompanyName(),
            application.getNumberOfShares(),
            application.getAmountPaid()
        );
    }

    /**
     * Generates email content for rejection notice
     * 
     * @param customer the customer
     * @param application the IPO application
     * @return the formatted email content
     */
    private String generateRejectionEmailContent(Customer customer, IPOApplication application) {
        return String.format(
            "Dear %s %s,\n\n" +
            "We regret to inform you that your IPO application for %s has been rejected.\n\n" +
            "If you have any questions, please contact our call center.\n" +
            "Best regards,\nBank System",
            customer.getName(), customer.getSurname(),
            application.getCompanyName()
        );
    }
}