import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer who can apply for IPOs.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephoneNumber;
    private List<IPOApplication> applications;
    private int failedAttempts;
    private boolean isRestricted;

    /**
     * Constructs a new Customer object.
     */
    public Customer() {
        this.applications = new ArrayList<>();
        this.failedAttempts = 0;
        this.isRestricted = false;
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
     * Gets the customer's email address.
     * @return the customer's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * @param email the customer's email address
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
    public List<IPOApplication> getApplications() {
        return applications;
    }

    /**
     * Sets the customer's IPO applications.
     * @param applications the customer's IPO applications
     */
    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Gets the number of failed attempts.
     * @return the number of failed attempts
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * Sets the number of failed attempts.
     * @param failedAttempts the number of failed attempts
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is restricted from applying for IPOs.
     * @return true if the customer is restricted, false otherwise
     */
    public boolean isRestricted() {
        return isRestricted;
    }

    /**
     * Sets whether the customer is restricted from applying for IPOs.
     * @param restricted true if the customer is restricted, false otherwise
     */
    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }
}

/**
 * Represents an IPO application.
 */
class IPOApplication {
    private Customer customer;
    private String companyName;
    private int numberOfShares;
    private double amount;
    private String document;
    private ApplicationStatus status;

    /**
     * Constructs a new IPOApplication object.
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Gets the customer who made the application.
     * @return the customer who made the application
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the application.
     * @param customer the customer who made the application
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the name of the company for which the application was made.
     * @return the name of the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company for which the application was made.
     * @param companyName the name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares applied for.
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares applied for.
     * @param numberOfShares the number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount applied for.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount applied for.
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the document uploaded with the application.
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the document uploaded with the application.
     * @param document the document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the status of the application.
     * @return the status of the application
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     * @param status the status of the application
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Enum representing the status of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents the IPO application service.
 */
class IPOApplicationService {
    private List<Customer> customers;

    /**
     * Constructs a new IPOApplicationService object.
     */
    public IPOApplicationService() {
        this.customers = new ArrayList<>();
    }

    /**
     * Creates a new IPO application for a customer.
     * @param customer the customer making the application
     * @param companyName the name of the company for which the application is being made
     * @param numberOfShares the number of shares being applied for
     * @param amount the amount being applied for
     * @param document the document uploaded with the application
     * @return true if the application is successful, false otherwise
     */
    public boolean createApplication(Customer customer, String companyName, int numberOfShares, double amount, String document) {
        if (customer.isRestricted() || numberOfShares <= 0 || document == null) {
            return false;
        }
        for (IPOApplication application : customer.getApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.APPROVED) {
                return false;
            }
        }
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName(companyName);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);
        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     * @param application the application to be approved or rejected
     * @param isApproved true if the application is to be approved, false if it is to be rejected
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean isApproved) {
        if (application.getCustomer().isRestricted()) {
            return false;
        }
        if (isApproved) {
            application.setStatus(ApplicationStatus.APPROVED);
            // Send emails to customer and company
            sendEmails(application);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            // Send rejection email to customer
            sendRejectionEmail(application);
        }
        return true;
    }

    /**
     * Retrieves a customer's application count summary.
     * @param customer the customer for whom to retrieve the application count summary
     * @return the number of applications made by the customer
     */
    public int getApplicationCountSummary(Customer customer) {
        int count = 0;
        for (IPOApplication application : customer.getApplications()) {
            if (application.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Queries the total approval IPO applications amount for a customer.
     * @param customer the customer for whom to query the total approval amount
     * @return the total approval amount
     */
    public double queryTotalApprovalAmount(Customer customer) {
        double totalAmount = 0;
        for (IPOApplication application : customer.getApplications()) {
            if (application.getStatus() == ApplicationStatus.APPROVED) {
                totalAmount += application.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Cancels a pending IPO application.
     * @param customer the customer who made the application
     * @param companyName the name of the company for which the application was made
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication application : customer.getApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.PENDING) {
                application.setStatus(ApplicationStatus.REJECTED); // or remove the application from the list
                return true;
            }
        }
        return false;
    }

    /**
     * Sends emails to the customer and company after an application is approved.
     * @param application the approved application
     */
    private void sendEmails(IPOApplication application) {
        // Implement email sending logic here
        System.out.println("Sending emails to customer and company...");
    }

    /**
     * Sends a rejection email to the customer after an application is rejected.
     * @param application the rejected application
     */
    private void sendRejectionEmail(IPOApplication application) {
        // Implement email sending logic here
        System.out.println("Sending rejection email to customer...");
    }
}