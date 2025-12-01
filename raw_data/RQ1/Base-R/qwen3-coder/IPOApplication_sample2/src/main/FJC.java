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

    // Getters and Setters
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
     * Checks if the customer has an approved application for a specific company.
     *
     * @param company The company to check for approved applications
     * @return true if there is an approved application, false otherwise
     */
    public boolean hasApprovedApplicationForCompany(String company) {
        for (IPOApplication app : applications) {
            if (app.getCompanyName().equals(company) && app.isApproved()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increments the customer's failed attempts count.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    /**
     * Restricts the customer from applying for IPOs if failed attempts exceed a threshold.
     */
    public void checkAndRestrictIfNecessary() {
        if (this.failedAttempts >= 3) {
            this.eligibleForIPO = false;
        }
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

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an IPO application made by a customer.
 */
class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amountOfMoney;
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

    // Getters and Setters
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

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
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
 * Represents the IPO application system that manages applications and customer interactions.
 */
class IPOApplicationSystem {
    private List<IPOApplication> applications;

    /**
     * Default constructor for IPOApplicationSystem.
     */
    public IPOApplicationSystem() {
        this.applications = new ArrayList<>();
    }

    // Getters and Setters
    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Creates an IPO application for a customer.
     *
     * @param customer The customer applying for the IPO
     * @param company The company for which the IPO is being applied
     * @param numberOfShares The number of shares to purchase (>0)
     * @param amountOfMoney The amount of money for the purchase
     * @param document The legal allowance documentation
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String company, int numberOfShares, 
                                       double amountOfMoney, String document) {
        // Check if customer is eligible for IPO
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        // Check if customer has an approved application for the same company
        if (customer.hasApprovedApplicationForCompany(company)) {
            return false;
        }

        // Validate input parameters
        if (numberOfShares <= 0 || document == null) {
            customer.incrementFailedAttempts();
            customer.checkAndRestrictIfNecessary();
            return false;
        }

        // Create and add the application
        IPOApplication application = new IPOApplication();
        application.setCompanyName(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmountOfMoney(amountOfMoney);
        application.setDocument(document);
        
        customer.getApplications().add(application);
        this.applications.add(application);
        
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     *
     * @param application The IPO application to process
     * @param approve true to approve the application, false to reject
     * @param customer The customer who made the application
     * @param company The company related to the application
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve, 
                                             Customer customer, Company company) {
        // Check if customer is still eligible for IPO
        if (!customer.isEligibleForIPO()) {
            return false;
        }

        if (approve) {
            application.setApproved(true);
            // Send emails to customer and company
            sendInformationEmail(customer, company.getName(), application.getNumberOfShares(), 
                               application.getAmountOfMoney());
            sendInformationEmailToCompany(customer, company.getName(), application.getNumberOfShares(), 
                                        application.getAmountOfMoney());
        } else {
            application.setRejected(true);
            // Send rejection email to customer
            sendRejectionEmail(customer, company.getName());
        }
        
        return true;
    }

    /**
     * Retrieves the number of reviewed applications for a customer.
     *
     * @param customer The customer whose applications are to be counted
     * @return the number of reviewed applications (approved or rejected)
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
     * @param customer The customer whose approved application amounts are to be summed
     * @return the total amount of approved applications
     */
    public double getTotalApprovedAmount(Customer customer) {
        double total = 0.0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.isApproved()) {
                total += app.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company.
     *
     * @param customer The customer who made the application
     * @param companyName The name of the company for which the application was made
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && !app.isReviewed()) {
                app.setRejected(true); // Mark as rejected to indicate cancellation
                return true;
            }
        }
        return false;
    }

    /**
     * Sends an information email to a customer.
     *
     * @param customer The customer to whom the email is sent
     * @param companyName The name of the company related to the application
     * @param numberOfShares The number of shares purchased
     * @param amountOfMoney The amount of money paid
     */
    private void sendInformationEmail(Customer customer, String companyName, 
                                    int numberOfShares, double amountOfMoney) {
        // Implementation for sending email to customer
        // Email content would include customer details, company name, shares, and amount
    }

    /**
     * Sends an information email to a company.
     *
     * @param customer The customer who made the application
     * @param companyName The name of the company to which the email is sent
     * @param numberOfShares The number of shares purchased
     * @param amountOfMoney The amount of money paid
     */
    private void sendInformationEmailToCompany(Customer customer, String companyName, 
                                             int numberOfShares, double amountOfMoney) {
        // Implementation for sending email to company
        // Email content would include customer details, company name, shares, and amount
    }

    /**
     * Sends a rejection email to a customer.
     *
     * @param customer The customer to whom the rejection email is sent
     * @param companyName The name of the company related to the application
     */
    private void sendRejectionEmail(Customer customer, String companyName) {
        // Implementation for sending rejection email to customer
    }
}