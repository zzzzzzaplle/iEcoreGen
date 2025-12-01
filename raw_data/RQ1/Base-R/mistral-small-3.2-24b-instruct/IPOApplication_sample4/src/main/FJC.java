import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private List<IPOApplication> applications;
    private boolean isRestricted;
    private int failedAttempts;

    public Customer() {
        this.applications = new ArrayList<>();
        this.isRestricted = false;
        this.failedAttempts = 0;
    }

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

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return !isRestricted;
    }

    /**
     * Increments the failed attempts counter and restricts the customer if the threshold is reached.
     */
    public void incrementFailedAttempts() {
        failedAttempts++;
        if (failedAttempts >= 3) {
            isRestricted = true;
        }
    }

    /**
     * Resets the failed attempts counter and un-restricts the customer.
     */
    public void resetFailedAttempts() {
        failedAttempts = 0;
        isRestricted = false;
    }
}

class IPOApplication {
    private Customer customer;
    private String companyName;
    private int numberOfShares;
    private double amount;
    private String document;
    private ApplicationStatus status;

    public IPOApplication() {
        this.status = ApplicationStatus.PENDING;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Creates an IPO application if the customer is eligible and has no approved application for the same company.
     * @param customer the customer applying for IPO
     * @param companyName the target company
     * @param numberOfShares the number of shares (>0)
     * @param amount the amount of money
     * @param document the uploaded document (non-null)
     * @return true on success, false otherwise
     */
    public static boolean createIPOApplication(Customer customer, String companyName, int numberOfShares, double amount, String document) {
        if (!customer.isEligibleForIPO() || numberOfShares <= 0 || document == null) {
            customer.incrementFailedAttempts();
            return false;
        }

        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompanyName().equals(companyName) && app.getStatus() == ApplicationStatus.APPROVED) {
                customer.incrementFailedAttempts();
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
     * Approves or rejects an application.
     * @param application the application to approve or reject
     * @param isApproved true if the application is approved, false otherwise
     * @return true if the operation is successful, false otherwise
     */
    public static boolean approveOrRejectApplication(IPOApplication application, boolean isApproved) {
        if (application == null || application.getStatus() != ApplicationStatus.PENDING) {
            return false;
        }

        application.setStatus(isApproved ? ApplicationStatus.APPROVED : ApplicationStatus.REJECTED);

        if (isApproved) {
            EmailSender.sendCustomerEmail(application);
            EmailSender.sendCompanyEmail(application);
        } else {
            EmailSender.sendRejectionEmail(application);
        }

        return true;
    }

    /**
     * Retrieves the total number of approved and rejected applications for a customer.
     * @param customer the customer
     * @return the total number of approved and rejected applications
     */
    public static int getApplicationCountSummary(Customer customer) {
        if (customer == null) {
            return 0;
        }

        int count = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total amount of approved IPO applications for a customer.
     * @param customer the customer
     * @return the total amount of approved IPO applications
     */
    public static double getTotalApprovedAmount(Customer customer) {
        if (customer == null) {
            return 0.0;
        }

        double total = 0.0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company.
     * @param customer the customer
     * @param companyName the company name
     * @return true if the cancellation is successful, false otherwise
     */
    public static boolean cancelPendingApplication(Customer customer, String companyName) {
        if (customer == null || companyName == null) {
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
}

enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class EmailSender {
    /**
     * Sends an email to the customer with application details.
     * @param application the application
     */
    public static void sendCustomerEmail(IPOApplication application) {
        // Implementation to send email to the customer
        System.out.println("Sending email to customer: " + application.getCustomer().getEmail());
    }

    /**
     * Sends an email to the company with application details.
     * @param application the application
     */
    public static void sendCompanyEmail(IPOApplication application) {
        // Implementation to send email to the company
        System.out.println("Sending email to company: " + application.getCompanyName());
    }

    /**
     * Sends a rejection email to the customer.
     * @param application the application
     */
    public static void sendRejectionEmail(IPOApplication application) {
        // Implementation to send rejection email to the customer
        System.out.println("Sending rejection email to customer: " + application.getCustomer().getEmail());
    }
}