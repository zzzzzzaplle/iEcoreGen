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
    private List<IPOApplication> applications;
    private int failedAttempts;
    private boolean isRestricted;

    /**
     * Default constructor for Customer.
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

    /**
     * Creates an IPO application for the customer.
     * @param companyName the name of the company
     * @param numberOfShares the number of shares
     * @param amount the amount of money
     * @param document the uploaded document
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(String companyName, int numberOfShares, double amount, String document) {
        if (isRestricted || numberOfShares <= 0 || document == null) {
            return false;
        }
        for (IPOApplication application : applications) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == IPOApplication.Status.APPROVED) {
                return false;
            }
        }
        IPOApplication application = new IPOApplication(companyName, numberOfShares, amount, document);
        applications.add(application);
        return true;
    }

    /**
     * Retrieves the customer's application count summary.
     * @return the number of IPO applications filed by the customer
     */
    public int getApplicationCountSummary() {
        int count = 0;
        for (IPOApplication application : applications) {
            if (application.getStatus() == IPOApplication.Status.APPROVED || application.getStatus() == IPOApplication.Status.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Queries the total approval IPO applications amount for the customer.
     * @return the sum of all approved application amounts
     */
    public double queryTotalApprovalAmount() {
        double totalAmount = 0;
        for (IPOApplication application : applications) {
            if (application.getStatus() == IPOApplication.Status.APPROVED) {
                totalAmount += application.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Cancels a pending IPO application for the customer.
     * @param companyName the name of the company
     * @return true if the cancellation succeeds, false otherwise
     */
    public boolean cancelPendingApplication(String companyName) {
        for (IPOApplication application : applications) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == IPOApplication.Status.PENDING) {
                application.setStatus(IPOApplication.Status.CANCELLED);
                return true;
            }
        }
        return false;
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
    private Status status;

    /**
     * Enum for the status of an IPO application.
     */
 enum Status {
        PENDING, APPROVED, REJECTED, CANCELLED
    }

    /**
     * Default constructor for IPOApplication.
     */
    public IPOApplication() {
        this.status = Status.PENDING;
    }

    /**
     * Constructor for IPOApplication with parameters.
     * @param companyName the name of the company
     * @param numberOfShares the number of shares
     * @param amount the amount of money
     * @param document the uploaded document
     */
    public IPOApplication(String companyName, int numberOfShares, double amount, String document) {
        this.companyName = companyName;
        this.numberOfShares = numberOfShares;
        this.amount = amount;
        this.document = document;
        this.status = Status.PENDING;
    }

    /**
     * Gets the company name.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name.
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares.
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares.
     * @param numberOfShares the number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount of money.
     * @return the amount of money
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money.
     * @param amount the amount of money
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the uploaded document.
     * @return the uploaded document
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the uploaded document.
     * @param document the uploaded document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the status of the IPO application.
     * @return the status of the IPO application
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the IPO application.
     * @param status the status of the IPO application
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}

/**
 * Represents a bank that manages IPO applications and sends emails.
 */
class Bank {
    /**
     * Approves or rejects an IPO application.
     * @param application the IPO application
     * @param approve true to approve, false to reject
     * @return true if the operation succeeds, false otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve) {
        if (application.getStatus() != IPOApplication.Status.PENDING) {
            return false;
        }
        if (approve) {
            application.setStatus(IPOApplication.Status.APPROVED);
            // Send two information emails: one to the customer and one to the company
            sendEmail(application, true);
            sendEmailToCompany(application);
        } else {
            application.setStatus(IPOApplication.Status.REJECTED);
            // Send a rejection notice to the customer
            sendEmail(application, false);
        }
        return true;
    }

    /**
     * Sends an email to the customer.
     * @param application the IPO application
     * @param approved true if the application is approved, false if rejected
     */
    private void sendEmail(IPOApplication application, boolean approved) {
        // Email content includes customer's name, surname, email address, telephone number,
        // applied company name, number of shares, and amount of money
        // For simplicity, assume customer details are available
        Customer customer = new Customer(); // Replace with actual customer object
        String emailContent = "Customer Name: " + customer.getName() + "\n" +
                "Customer Surname: " + customer.getSurname() + "\n" +
                "Customer Email: " + customer.getEmail() + "\n" +
                "Customer Telephone Number: " + customer.getTelephoneNumber() + "\n" +
                "Company Name: " + application.getCompanyName() + "\n" +
                "Number of Shares: " + application.getNumberOfShares() + "\n" +
                "Amount: " + application.getAmount();
        if (approved) {
            emailContent = "Approved: " + emailContent;
        } else {
            emailContent = "Rejected: " + emailContent;
        }
        // Send email using emailContent
        System.out.println("Sending email to customer: " + emailContent);
    }

    /**
     * Sends an email to the company.
     * @param application the IPO application
     */
    private void sendEmailToCompany(IPOApplication application) {
        // Email content includes customer's name, surname, email address, telephone number,
        // applied company name, number of shares, and amount of money
        // For simplicity, assume customer details are available
        Customer customer = new Customer(); // Replace with actual customer object
        String emailContent = "Customer Name: " + customer.getName() + "\n" +
                "Customer Surname: " + customer.getSurname() + "\n" +
                "Customer Email: " + customer.getEmail() + "\n" +
                "Customer Telephone Number: " + customer.getTelephoneNumber() + "\n" +
                "Company Name: " + application.getCompanyName() + "\n" +
                "Number of Shares: " + application.getNumberOfShares() + "\n" +
                "Amount: " + application.getAmount();
        emailContent = "Application Details: " + emailContent;
        // Send email using emailContent
        System.out.println("Sending email to company: " + emailContent);
    }
}