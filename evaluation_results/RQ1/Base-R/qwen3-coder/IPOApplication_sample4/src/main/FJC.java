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
     * Checks if the customer has an approved application for the given company.
     *
     * @param company The company to check for approved applications
     * @return true if there is an approved application, false otherwise
     */
    public boolean hasApprovedApplicationForCompany(String company) {
        return applications.stream()
                .anyMatch(app -> company.equals(app.getCompany()) && app.isApproved());
    }

    /**
     * Increases the count of failed attempts for this customer.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    /**
     * Resets the failed attempts counter to zero.
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }
}

/**
 * Represents a company that offers IPOs.
 */
class Company {
    private String name;

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
}

/**
 * Represents an IPO application submitted by a customer.
 */
class IPOApplication {
    private Customer customer;
    private String company;
    private int numberOfShares;
    private double amount;
    private String document;
    private boolean approved;
    private boolean rejected;
    private boolean reviewed;

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
 * Represents the banking system that processes IPO applications.
 */
class BankSystem {
    private List<IPOApplication> applications;

    /**
     * Default constructor for BankSystem.
     */
    public BankSystem() {
        this.applications = new ArrayList<>();
    }

    // Getters and setters
    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Creates an IPO application for a customer.
     *
     * @param customer       The customer applying for the IPO
     * @param company        The company offering the IPO
     * @param numberOfShares The number of shares to purchase (>0)
     * @param amount         The amount of money for the purchase
     * @param document       The legal allowance documentation (non-null)
     * @return true if the application is successfully created, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String company, int numberOfShares, double amount, String document) {
        // Check if customer is eligible
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        // Check if customer has too many failed attempts
        if (customer.getFailedAttempts() >= 3) {
            return false;
        }

        // Validate input parameters
        if (numberOfShares <= 0 || document == null || document.isEmpty()) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Check if customer already has an approved application for this company
        if (customer.hasApprovedApplicationForCompany(company)) {
            customer.incrementFailedAttempts();
            return false;
        }

        // Create and save the application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);

        applications.add(application);
        customer.getApplications().add(application);
        customer.resetFailedAttempts();

        return true;
    }

    /**
     * Approves or rejects an IPO application.
     *
     * @param application The IPO application to process
     * @param approve     true to approve, false to reject
     * @return true if the operation is successful, false otherwise
     */
    public boolean processApplication(IPOApplication application, boolean approve) {
        // Check if application exists in the system
        if (!applications.contains(application)) {
            return false;
        }

        // Check if already processed
        if (application.isReviewed()) {
            return false;
        }

        // Verify customer is still eligible
        if (!application.getCustomer().isEligibleForIPO()) {
            return false;
        }

        if (approve) {
            // Approve the application
            application.setApproved(true);
            
            // Send two information emails
            sendInformationEmailToCustomer(application);
            sendInformationEmailToCompany(application);
        } else {
            // Reject the application
            application.setRejected(true);
            
            // Send rejection email to customer
            sendRejectionEmailToCustomer(application);
        }

        return true;
    }

    /**
     * Retrieves the number of reviewed (approved or rejected) IPO applications for a customer.
     *
     * @param customer The customer to check
     * @return the count of reviewed applications, or 0 if none
     */
    public int getCustomerApplicationCount(Customer customer) {
        return (int) customer.getApplications().stream()
                .filter(IPOApplication::isReviewed)
                .count();
    }

    /**
     * Calculates the total amount of approved IPO applications for a customer.
     *
     * @param customer The customer to check
     * @return the sum of all approved application amounts
     */
    public double getTotalApprovedAmount(Customer customer) {
        return customer.getApplications().stream()
                .filter(IPOApplication::isApproved)
                .mapToDouble(IPOApplication::getAmount)
                .sum();
    }

    /**
     * Cancels a pending IPO application for a customer.
     *
     * @param customer    The customer requesting cancellation
     * @param companyName The company for which the application was made
     * @return true if cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        IPOApplication application = customer.getApplications().stream()
                .filter(app -> companyName.equals(app.getCompany()) && !app.isReviewed())
                .findFirst()
                .orElse(null);

        if (application != null) {
            application.setRejected(true); // Mark as rejected to indicate cancellation
            applications.remove(application);
            return true;
        }

        return false;
    }

    /**
     * Sends an information email to the customer about their approved application.
     *
     * @param application The approved application
     */
    private void sendInformationEmailToCustomer(IPOApplication application) {
        // In a real system, this would send an actual email
        System.out.println("Sending information email to customer: " + 
                application.getCustomer().getName() + " " + 
                application.getCustomer().getSurname());
        System.out.println("Company: " + application.getCompany());
        System.out.println("Shares: " + application.getNumberOfShares());
        System.out.println("Amount: " + application.getAmount());
    }

    /**
     * Sends an information email to the company about the approved application.
     *
     * @param application The approved application
     */
    private void sendInformationEmailToCompany(IPOApplication application) {
        // In a real system, this would send an actual email
        System.out.println("Sending information email to company: " + application.getCompany());
        System.out.println("Customer: " + application.getCustomer().getName() + " " + 
                application.getCustomer().getSurname());
        System.out.println("Shares: " + application.getNumberOfShares());
        System.out.println("Amount: " + application.getAmount());
    }

    /**
     * Sends a rejection email to the customer.
     *
     * @param application The rejected application
     */
    private void sendRejectionEmailToCustomer(IPOApplication application) {
        // In a real system, this would send an actual email
        System.out.println("Sending rejection email to customer: " + 
                application.getCustomer().getName() + " " + 
                application.getCustomer().getSurname());
        System.out.println("Company: " + application.getCompany());
    }
}