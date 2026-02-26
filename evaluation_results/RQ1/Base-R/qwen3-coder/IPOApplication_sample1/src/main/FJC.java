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
     * Checks if the customer has an approved application for a specific company.
     *
     * @param company The company to check for approved applications
     * @return true if there is an approved application for the company, false otherwise
     */
    public boolean hasApprovedApplicationForCompany(String company) {
        return applications.stream()
                .anyMatch(app -> company.equals(app.getCompany()) && app.isApproved());
    }

    /**
     * Increments the failed attempts counter for this customer.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    /**
     * Restricts the customer from IPO applications due to too many failed attempts.
     */
    public void restrictFromIPOApplication() {
        this.eligibleForIPO = false;
    }
}

/**
 * Represents a document required for IPO application.
 */
class Document {
    private String content;

    /**
     * Default constructor for Document.
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
    private Customer customer;
    private String company;
    private int numberOfShares;
    private double amount;
    private Document document;
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

    /**
     * Checks if the application is pending (not approved and not rejected).
     *
     * @return true if the application is pending, false otherwise
     */
    public boolean isPending() {
        return !approved && !rejected;
    }
}

/**
 * Represents the bank system that handles IPO applications.
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
     * @param company        The company for which the IPO is being applied
     * @param numberOfShares The number of shares to purchase (must be > 0)
     * @param amount         The amount of money for the purchase
     * @param document       The legal allowance documentation (must be non-null)
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String company, int numberOfShares, double amount, Document document) {
        // Check if customer is eligible
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        // Check if customer has approved application for the same company
        if (customer.hasApprovedApplicationForCompany(company)) {
            return false;
        }

        // Validate inputs
        if (numberOfShares <= 0 || amount <= 0 || document == null) {
            customer.incrementFailedAttempts();
            if (customer.getFailedAttempts() >= 3) {
                customer.restrictFromIPOApplication();
            }
            return false;
        }

        // Create application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);

        // Add to customer's applications and bank's records
        customer.getApplications().add(application);
        applications.add(application);

        return true;
    }

    /**
     * Approves or rejects an IPO application.
     *
     * @param application The application to process
     * @param approve     true to approve, false to reject
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve) {
        // Check if application exists in the system
        if (!applications.contains(application)) {
            return false;
        }

        // Check if customer is still eligible
        if (!application.getCustomer().isEligibleForIPO()) {
            return false;
        }

        if (approve) {
            // Approve the application
            application.setApproved(true);
            
            // Send emails to customer and company
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
     * Retrieves the count of reviewed (approved or rejected) IPO applications for a customer.
     *
     * @param customer The customer to check
     * @return The number of reviewed applications
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
     * @return The sum of all approved application amounts
     */
    public double getTotalApprovedIPOAmount(Customer customer) {
        return customer.getApplications().stream()
                .filter(IPOApplication::isApproved)
                .mapToDouble(IPOApplication::getAmount)
                .sum();
    }

    /**
     * Cancels a pending IPO application for a customer.
     *
     * @param customer    The customer who wants to cancel
     * @param companyName The company for which the application was made
     * @return true if cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        IPOApplication application = customer.getApplications().stream()
                .filter(app -> companyName.equals(app.getCompany()) && app.isPending())
                .findFirst()
                .orElse(null);

        if (application != null) {
            customer.getApplications().remove(application);
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
     * Sends an information email to the company about the customer's approved application.
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