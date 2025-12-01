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
    private boolean isRestricted;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.ipoApplications = new ArrayList<>();
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
     * Checks if the customer is restricted from applying for IPO.
     * @return true if the customer is restricted, false otherwise
     */
    public boolean isRestricted() {
        return isRestricted;
    }

    /**
     * Sets whether the customer is restricted from applying for IPO.
     * @param restricted true if the customer is restricted, false otherwise
     */
    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }
}

/**
 * Represents an IPO application with its details and status.
 */
class IPOApplication {
    private String companyName;
    private int numberOfShares;
    private double amountOfMoney;
    private String document;
    private ApplicationStatus status;

    /**
     * Default constructor for IPOApplication.
     */
    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Gets the company name for the IPO application.
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name for the IPO application.
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the number of shares for the IPO application.
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares for the IPO application.
     * @param numberOfShares the number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the amount of money for the IPO application.
     * @return the amount of money
     */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * Sets the amount of money for the IPO application.
     * @param amountOfMoney the amount of money
     */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * Gets the document for the IPO application.
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the document for the IPO application.
     * @param document the document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Gets the status of the IPO application.
     * @return the status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the IPO application.
     * @param status the status
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Enum representing the status of an IPO application.
 */
enum ApplicationStatus {
    PENDING, APPROVED, REJECTED
}

/**
 * Represents the IPO application service with methods to manage IPO applications.
 */
class IPOApplicationService {
    private List<Customer> customers;

    /**
     * Default constructor for IPOApplicationService.
     */
    public IPOApplicationService() {
        this.customers = new ArrayList<>();
    }

    /**
     * Creates a new IPO application for a customer.
     * @param customer the customer applying for IPO
     * @param companyName the name of the company for the IPO application
     * @param numberOfShares the number of shares for the IPO application
     * @param amountOfMoney the amount of money for the IPO application
     * @param document the document for the IPO application
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amountOfMoney, String document) {
        if (customer.isRestricted() || numberOfShares <= 0 || document == null) {
            return false;
        }
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.APPROVED) {
                return false;
            }
        }
        IPOApplication ipoApplication = new IPOApplication();
        ipoApplication.setCompanyName(companyName);
        ipoApplication.setNumberOfShares(numberOfShares);
        ipoApplication.setAmountOfMoney(amountOfMoney);
        ipoApplication.setDocument(document);
        customer.getIpoApplications().add(ipoApplication);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     * @param customer the customer who applied for IPO
     * @param companyName the name of the company for the IPO application
     * @param approve true to approve, false to reject
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrRejectApplication(Customer customer, String companyName, boolean approve) {
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.PENDING) {
                if (approve) {
                    application.setStatus(ApplicationStatus.APPROVED);
                    // Send two information emails: one to the customer and one to the company
                    sendEmail(customer, companyName, application.getNumberOfShares(), application.getAmountOfMoney(), true);
                    sendEmailToCompany(customer, companyName, application.getNumberOfShares(), application.getAmountOfMoney());
                } else {
                    application.setStatus(ApplicationStatus.REJECTED);
                    // Send a rejection notice to the customer
                    sendEmail(customer, companyName, application.getNumberOfShares(), application.getAmountOfMoney(), false);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the number of IPO applications filed by a customer.
     * @param customer the customer
     * @return the number of IPO applications
     */
    public int getApplicationCountSummary(Customer customer) {
        int count = 0;
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Queries the total amount of approved IPO applications for a customer.
     * @param customer the customer
     * @return the total amount
     */
    public double queryTotalApprovalAmount(Customer customer) {
        double totalAmount = 0;
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getStatus() == ApplicationStatus.APPROVED) {
                totalAmount += application.getAmountOfMoney();
            }
        }
        return totalAmount;
    }

    /**
     * Cancels a pending IPO application for a customer.
     * @param customer the customer
     * @param companyName the name of the company for the IPO application
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication application : customer.getIpoApplications()) {
            if (application.getCompanyName().equals(companyName) && application.getStatus() == ApplicationStatus.PENDING) {
                customer.getIpoApplications().remove(application);
                return true;
            }
        }
        return false;
    }

    /**
     * Sends an email to the customer with the IPO application details.
     * @param customer the customer
     * @param companyName the name of the company
     * @param numberOfShares the number of shares
     * @param amountOfMoney the amount of money
     * @param isApproved true if the application is approved, false otherwise
     */
    private void sendEmail(Customer customer, String companyName, int numberOfShares, double amountOfMoney, boolean isApproved) {
        // Email content includes customer's name, surname, email, telephone number, company name, number of shares, and amount of money
        System.out.println("Sending email to customer: " + customer.getName() + " " + customer.getSurname());
        System.out.println("Email content: ");
        System.out.println("Name: " + customer.getName());
        System.out.println("Surname: " + customer.getSurname());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Telephone Number: " + customer.getTelephoneNumber());
        System.out.println("Company Name: " + companyName);
        System.out.println("Number of Shares: " + numberOfShares);
        System.out.println("Amount of Money: " + amountOfMoney);
        System.out.println("Application Status: " + (isApproved ? "Approved" : "Rejected"));
    }

    /**
     * Sends an email to the company with the IPO application details.
     * @param customer the customer
     * @param companyName the name of the company
     * @param numberOfShares the number of shares
     * @param amountOfMoney the amount of money
     */
    private void sendEmailToCompany(Customer customer, String companyName, int numberOfShares, double amountOfMoney) {
        // Email content includes customer's name, surname, email, telephone number, company name, number of shares, and amount of money
        System.out.println("Sending email to company: " + companyName);
        System.out.println("Email content: ");
        System.out.println("Name: " + customer.getName());
        System.out.println("Surname: " + customer.getSurname());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Telephone Number: " + customer.getTelephoneNumber());
        System.out.println("Company Name: " + companyName);
        System.out.println("Number of Shares: " + numberOfShares);
        System.out.println("Amount of Money: " + amountOfMoney);
    }
}