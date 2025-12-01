import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer who can apply for IPOs.
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
     * Creates a new customer with default values.
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
     * Checks if the customer has an approved application for a specific company.
     *
     * @param company The company to check for approved applications
     * @return true if there is an approved application for the company, false otherwise
     */
    public boolean hasApprovedApplicationForCompany(String company) {
        for (IPOApplication application : applications) {
            if (company.equals(application.getCompany()) && application.isApproved()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the customer has a pending application for a specific company.
     *
     * @param company The company to check for pending applications
     * @return true if there is a pending application for the company, false otherwise
     */
    public boolean hasPendingApplicationForCompany(String company) {
        for (IPOApplication application : applications) {
            if (company.equals(application.getCompany()) && 
                !application.isApproved() && !application.isRejected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a pending application for a specific company.
     *
     * @param company The company to find the pending application for
     * @return The pending IPOApplication if found, null otherwise
     */
    public IPOApplication findPendingApplicationForCompany(String company) {
        for (IPOApplication application : applications) {
            if (company.equals(application.getCompany()) && 
                !application.isApproved() && !application.isRejected()) {
                return application;
            }
        }
        return null;
    }
}

/**
 * Represents a document required for IPO application.
 */
class Document {
    private String content;

    /**
     * Creates a new document with default values.
     */
    public Document() {
    }

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents an IPO application made by a customer.
 */
class IPOApplication {
    private String company;
    private int numberOfShares;
    private double amount;
    private Document document;
    private boolean approved;
    private boolean rejected;
    private Customer customer;

    /**
     * Creates a new IPO application with default values.
     */
    public IPOApplication() {
        this.approved = false;
        this.rejected = false;
    }

    // Getters and setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Represents the software system that handles IPO applications.
 */
class SoftwareSystem {
    private static final int MAX_FAILED_ATTEMPTS = 3;

    /**
     * Creates a new software system with default values.
     */
    public SoftwareSystem() {
    }

    /**
     * Creates an IPO application for a customer.
     * The customer must be eligible for IPOs and not have an approved application for the same company.
     * Also checks that shares > 0, amount > 0, and document is not null.
     *
     * @param customer The customer applying for the IPO
     * @param company The company for which the IPO is being applied
     * @param numberOfShares The number of shares to purchase (must be > 0)
     * @param amount The amount of money for the purchase (must be > 0)
     * @param document The legal allowance documentation (must not be null)
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String company, int numberOfShares, 
                                       double amount, Document document) {
        // Check customer eligibility
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        // Check failed attempts
        if (customer.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
            return false;
        }

        // Check application parameters
        if (numberOfShares <= 0 || amount <= 0 || document == null) {
            customer.setFailedAttempts(customer.getFailedAttempts() + 1);
            return false;
        }

        // Check if customer already has an approved application for this company
        if (customer.hasApprovedApplicationForCompany(company)) {
            customer.setFailedAttempts(customer.getFailedAttempts() + 1);
            return false;
        }

        // Check if customer already has a pending application for this company
        if (customer.hasPendingApplicationForCompany(company)) {
            customer.setFailedAttempts(customer.getFailedAttempts() + 1);
            return false;
        }

        // Create and add the application
        IPOApplication application = new IPOApplication();
        application.setCompany(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     * If approved and the customer is still eligible, sends information emails to customer and company.
     * If rejected, sends a rejection notice to the customer.
     *
     * @param application The IPO application to process
     * @param approve true to approve the application, false to reject it
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve) {
        Customer customer = application.getCustomer();
        
        // Check if customer is still eligible
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        if (approve) {
            // Check if application is neither approved nor rejected already
            if (application.isApproved() || application.isRejected()) {
                return false;
            }
            
            // Approve the application
            application.setApproved(true);
            
            // Send emails
            sendInformationEmailToCustomer(application);
            sendInformationEmailToCompany(application);
        } else {
            // Check if application is neither approved nor rejected already
            if (application.isApproved() || application.isRejected()) {
                return false;
            }
            
            // Reject the application
            application.setRejected(true);
            
            // Send rejection email
            sendRejectionEmailToCustomer(application);
        }
        
        return true;
    }

    /**
     * Retrieves the count of reviewed IPO applications (approved or rejected) for a customer.
     *
     * @param customer The customer whose applications are to be counted
     * @return The number of reviewed applications
     */
    public int getCustomerApplicationCount(Customer customer) {
        int count = 0;
        for (IPOApplication application : customer.getApplications()) {
            if (application.isApproved() || application.isRejected()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of approved IPO applications for a customer.
     *
     * @param customer The customer whose approved application amounts are to be summed
     * @return The sum of all approved application amounts
     */
    public double getTotalApprovedAmount(Customer customer) {
        double total = 0;
        for (IPOApplication application : customer.getApplications()) {
            if (application.isApproved()) {
                total += application.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending IPO application for a customer.
     *
     * @param customer The customer who wants to cancel the application
     * @param company The company for which the application was made
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String company) {
        IPOApplication application = customer.findPendingApplicationForCompany(company);
        
        if (application != null) {
            customer.getApplications().remove(application);
            return true;
        }
        
        return false;
    }

    /**
     * Sends an information email to the customer about their approved IPO application.
     *
     * @param application The approved IPO application
     */
    private void sendInformationEmailToCustomer(IPOApplication application) {
        // Implementation would send an email with:
        // - Customer's name, surname, email, telephone
        // - Applied company name
        // - Number of shares purchased
        // - Amount of money paid
        System.out.println("Sending information email to customer: " + 
                          application.getCustomer().getName() + " " + 
                          application.getCustomer().getSurname());
    }

    /**
     * Sends an information email to the company about the approved IPO application.
     *
     * @param application The approved IPO application
     */
    private void sendInformationEmailToCompany(IPOApplication application) {
        // Implementation would send an email with:
        // - Customer's name, surname, email, telephone
        // - Applied company name
        // - Number of shares purchased
        // - Amount of money paid
        System.out.println("Sending information email to company: " + application.getCompany());
    }

    /**
     * Sends a rejection email to the customer about their rejected IPO application.
     *
     * @param application The rejected IPO application
     */
    private void sendRejectionEmailToCustomer(IPOApplication application) {
        // Implementation would send a rejection email to the customer
        System.out.println("Sending rejection email to customer: " + 
                          application.getCustomer().getName() + " " + 
                          application.getCustomer().getSurname());
    }
}