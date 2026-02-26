import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer with their details and IPO applications.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephoneNumber;
    private List<IPOApplication> ipoApplications;
    private int failedAttempts;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.ipoApplications = new ArrayList<>();
        this.failedAttempts = 0;
    }

    /**
     * Gets the customer's name.
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name.
     * @param name the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * @return the customer's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * @param surname the customer's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's email.
     * @return the customer's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email.
     * @param email the customer's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's telephone number.
     * @return the customer's telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Sets the customer's telephone number.
     * @param telephoneNumber the customer's telephone number
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Gets the customer's IPO applications.
     * @return the customer's IPO applications
     */
    public List<IPOApplication> getIpoApplications() {
        return ipoApplications;
    }

    /**
     * Sets the customer's IPO applications.
     * @param ipoApplications the customer's IPO applications
     */
    public void setIpoApplications(List<IPOApplication> ipoApplications) {
        this.ipoApplications = ipoApplications;
    }

    /**
     * Gets the customer's failed attempts.
     * @return the customer's failed attempts
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * Sets the customer's failed attempts.
     * @param failedAttempts the customer's failed attempts
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        // For simplicity, let's assume a customer is eligible if they have less than 3 failed attempts.
        return failedAttempts < 3;
    }

    /**
     * Increments the customer's failed attempts.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    /**
     * Retrieves the count of the customer's IPO applications.
     * @return the count of IPO applications
     */
    public int getApplicationCount() {
        int count = 0;
        for (IPOApplication application : ipoApplications) {
            if (application.getStatus() != IPOApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total amount of approved IPO applications.
     * @return the total amount
     */
    public double getTotalApprovedAmount() {
        double totalAmount = 0;
        for (IPOApplication application : ipoApplications) {
            if (application.getStatus() == IPOApplicationStatus.APPROVED) {
                totalAmount += application.getAmount();
            }
        }
        return totalAmount;
    }
}

/**
 * Represents an IPO application with its details and status.
 */
class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amount;
    private String document;
    private IPOApplicationStatus status;

    /**
     * Default constructor for IPOApplication.
     */
    public IPOApplication() {
        this.status = IPOApplicationStatus.PENDING;
    }

    /**
     * Gets the company name of the IPO application.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name of the IPO application.
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares of the IPO application.
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares of the IPO application.
     * @param numberOfShares the number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount of the IPO application.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the IPO application.
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the document of the IPO application.
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the document of the IPO application.
     * @param document the document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the status of the IPO application.
     * @return the status
     */
    public IPOApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the IPO application.
     * @param status the status
     */
    public void setStatus(IPOApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Enum representing the status of an IPO application.
 */
enum IPOApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents the IPO service with methods to manage IPO applications.
 */
class IPOService {
    private List<Customer> customers;

    /**
     * Default constructor for IPOService.
     */
    public IPOService() {
        this.customers = new ArrayList<>();
    }

    /**
     * Creates an IPO application for a customer.
     * @param customer the customer
     * @param companyName the company name
     * @param numberOfShares the number of shares
     * @param amount the amount
     * @param document the document
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amount, String document) {
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == IPOApplicationStatus.APPROVED) {
                return false;
            }
        }
        IPOApplication ipoApplication = new IPOApplication();
        ipoApplication.setCompanyName(companyName);
        ipoApplication.setNumberOfShares(numberOfShares);
        ipoApplication.setAmount(amount);
        ipoApplication.setDocument(document);
        customer.getIpoApplications().add(ipoApplication);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     * @param customer the customer
     * @param companyName the company name
     * @param approve true to approve, false to reject
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(Customer customer, String companyName, boolean approve) {
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == IPOApplicationStatus.PENDING) {
                if (approve) {
                    application.setStatus(IPOApplicationStatus.APPROVED);
                    // Send emails to customer and company
                    sendEmails(customer, application);
                } else {
                    application.setStatus(IPOApplicationStatus.REJECTED);
                    // Send rejection email to customer
                    sendRejectionEmail(customer, application);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Sends emails to the customer and company after approving an IPO application.
     * @param customer the customer
     * @param application the IPO application
     */
    private void sendEmails(Customer customer, IPOApplication application) {
        // Implement email sending logic here
        System.out.println("Sending emails to customer and company...");
    }

    /**
     * Sends a rejection email to the customer after rejecting an IPO application.
     * @param customer the customer
     * @param application the IPO application
     */
    private void sendRejectionEmail(Customer customer, IPOApplication application) {
        // Implement email sending logic here
        System.out.println("Sending rejection email to customer...");
    }

    /**
     * Retrieves the count of IPO applications for a customer.
     * @param customer the customer
     * @return the count of IPO applications
     */
    public int getApplicationCount(Customer customer) {
        return customer.getApplicationCount();
    }

    /**
     * Retrieves the total amount of approved IPO applications for a customer.
     * @param customer the customer
     * @return the total amount
     */
    public double getTotalApprovedAmount(Customer customer) {
        return customer.getTotalApprovedAmount();
    }

    /**
     * Cancels a pending IPO application for a customer.
     * @param customer the customer
     * @param companyName the company name
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelApplication(Customer customer, String companyName) {
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == IPOApplicationStatus.PENDING) {
                customer.getIpoApplications().remove(application);
                return true;
            }
        }
        return false;
    }
}