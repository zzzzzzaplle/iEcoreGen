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
     * Unparameterized constructor for Customer.
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
     * Unparameterized constructor for IPOApplication.
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Gets the customer who made the IPO application.
     * @return the customer who made the IPO application
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the IPO application.
     * @param customer the customer who made the IPO application
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the name of the company for which the IPO application was made.
     * @return the name of the company for which the IPO application was made
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the company for which the IPO application was made.
     * @param companyName the name of the company for which the IPO application was made
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares applied for in the IPO application.
     * @return the number of shares applied for in the IPO application
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares applied for in the IPO application.
     * @param numberOfShares the number of shares applied for in the IPO application
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount applied for in the IPO application.
     * @return the amount applied for in the IPO application
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount applied for in the IPO application.
     * @param amount the amount applied for in the IPO application
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the document uploaded with the IPO application.
     * @return the document uploaded with the IPO application
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the document uploaded with the IPO application.
     * @param document the document uploaded with the IPO application
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the status of the IPO application.
     * @return the status of the IPO application
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the IPO application.
     * @param status the status of the IPO application
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
 * Represents a bank that manages IPO applications.
 */
class Bank {
    private List<Customer> customers;

    /**
     * Unparameterized constructor for Bank.
     */
    public Bank() {
        this.customers = new ArrayList<>();
    }

    /**
     * Creates a new IPO application for a customer.
     * @param customer the customer making the IPO application
     * @param companyName the name of the company for which the IPO application is being made
     * @param numberOfShares the number of shares being applied for
     * @param amount the amount being applied for
     * @param document the document being uploaded with the IPO application
     * @return true if the IPO application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amount, String document) {
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
     * @param application the IPO application to be approved or rejected
     * @param isApproved true if the application is to be approved, false if it is to be rejected
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean isApproved) {
        if (application.getStatus() != ApplicationStatus.PENDING) {
            return false;
        }
        if (isApproved) {
            application.setStatus(ApplicationStatus.APPROVED);
            // Send two information emails: one to the customer and one to the company
            sendEmail(application.getCustomer(), application.getCompanyName(), application.getNumberOfShares(), application.getAmount(), true);
            sendEmailToCompany(application.getCompanyName(), application.getCustomer().getName(), application.getCustomer().getSurname(), application.getNumberOfShares(), application.getAmount());
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            // Send a rejection notice to the customer
            sendEmail(application.getCustomer(), application.getCompanyName(), application.getNumberOfShares(), application.getAmount(), false);
        }
        return true;
    }

    /**
     * Retrieves a customer's application count summary.
     * @param customer the customer for whom to retrieve the application count summary
     * @return the number of IPO applications made by the customer, including approved and rejected applications
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
     * @param customer the customer for whom to query the total approval IPO applications amount
     * @return the sum of all approved IPO application amounts for the customer
     */
    public double queryTotalApprovalIPOApplicationsAmount(Customer customer) {
        double totalAmount = 0;
        for (IPOApplication application : customer.getApplications()) {
            if (application.getStatus() == ApplicationStatus.APPROVED) {
                totalAmount += application.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Cancels a pending IPO application for a customer.
     * @param customer the customer who made the IPO application
     * @param companyName the name of the company for which the IPO application was made
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication application : customer.getApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.PENDING) {
                application.setStatus(ApplicationStatus.REJECTED);
                return true;
            }
        }
        return false;
    }

    /**
     * Sends an email to a customer.
     * @param customer the customer to whom to send the email
     * @param companyName the name of the company for which the IPO application was made
     * @param numberOfShares the number of shares applied for
     * @param amount the amount applied for
     * @param isApproved true if the application was approved, false if it was rejected
     */
    private void sendEmail(Customer customer, String companyName, int numberOfShares, double amount, boolean isApproved) {
        // Implement email sending logic here
    }

    /**
     * Sends an email to a company.
     * @param companyName the name of the company to whom to send the email
     * @param customerName the name of the customer who made the IPO application
     * @param customerSurname the surname of the customer who made the IPO application
     * @param numberOfShares the number of shares applied for
     * @param amount the amount applied for
     */
    private void sendEmailToCompany(String companyName, String customerName, String customerSurname, int numberOfShares, double amount) {
        // Implement email sending logic here
    }
}