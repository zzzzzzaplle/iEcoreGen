import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private List<IpoApplication> applications;
    private int failedAttempts;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    public Customer() {
        this.applications = new ArrayList<>();
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

    public List<IpoApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IpoApplication> applications) {
        this.applications = applications;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligible() {
        return failedAttempts < MAX_FAILED_ATTEMPTS;
    }

    /**
     * Increments the failed attempts counter.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    /**
     * Resets the failed attempts counter.
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    /**
     * Retrieves the total number of approved and rejected applications.
     * @return the total number of approved and rejected applications
     */
    public int getApplicationCountSummary() {
        return (int) applications.stream().filter(app -> app.isApproved() || app.isRejected()).count();
    }

    /**
     * Retrieves the total amount of approved IPO applications.
     * @return the total amount of approved IPO applications
     */
    public double getTotalApprovedAmount() {
        return applications.stream()
                .filter(IpoApplication::isApproved)
                .mapToDouble(IpoApplication::getAmount)
                .sum();
    }

    /**
     * Cancels a pending application for the specified company.
     * @param companyName the name of the company
     * @return true if the cancellation succeeds, false otherwise
     */
    public boolean cancelPendingApplication(String companyName) {
        return applications.removeIf(app ->
                app.getCompanyName().equals(companyName) && !app.isApproved() && !app.isRejected());
    }
}

class IpoApplication {
    private String companyName;
    private int shares;
    private double amount;
    private String document;
    private boolean approved;
    private boolean rejected;

    public IpoApplication() {
        this.approved = false;
        this.rejected = false;
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
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
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    /**
     * Creates an IPO application.
     * @param customer the customer applying for IPO
     * @param companyName the name of the target company
     * @param shares the number of shares (>0)
     * @param amount the amount of money
     * @param document the uploaded document (non-null)
     * @return true on success, false otherwise
     */
    public static boolean createApplication(Customer customer, String companyName, int shares, double amount, String document) {
        if (!customer.isEligible() || shares <= 0 || document == null) {
            customer.incrementFailedAttempts();
            return false;
        }

        IpoApplication application = new IpoApplication();
        application.setCompanyName(companyName);
        application.setShares(shares);
        application.setAmount(amount);
        application.setDocument(document);

        customer.getApplications().add(application);
        customer.resetFailedAttempts();
        return true;
    }

    /**
     * Approves or rejects an application.
     * @param application the application to approve or reject
     * @param approve true to approve, false to reject
     * @return true on success, false otherwise
     */
    public static boolean approveOrReject(IpoApplication application, boolean approve) {
        if (application == null) {
            return false;
        }

        if (approve) {
            application.setApproved(true);
            EmailService.sendCustomerApprovalEmail(application);
            EmailService.sendCompanyApprovalEmail(application);
        } else {
            application.setRejected(true);
            EmailService.sendRejectionEmail(application);
        }
        return true;
    }
}

class EmailService {
    /**
     * Sends an email to the customer with approval information.
     * @param application the approved application
     */
    public static void sendCustomerApprovalEmail(IpoApplication application) {
        // Implementation to send email to the customer
    }

    /**
     * Sends an email to the company with approval information.
     * @param application the approved application
     */
    public static void sendCompanyApprovalEmail(IpoApplication application) {
        // Implementation to send email to the company
    }

    /**
     * Sends an email to the customer with rejection information.
     * @param application the rejected application
     */
    public static void sendRejectionEmail(IpoApplication application) {
        // Implementation to send email to the customer
    }
}