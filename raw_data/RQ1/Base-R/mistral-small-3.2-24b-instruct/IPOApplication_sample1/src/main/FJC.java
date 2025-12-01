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

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is eligible to apply for IPOs.
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleToApply() {
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
     * Resets the failed attempts counter.
     */
    public void resetFailedAttempts() {
        failedAttempts = 0;
    }

    /**
     * Retrieves the total number of approved and rejected IPO applications.
     * @return the count of approved and rejected applications
     */
    public int getApplicationCountSummary() {
        int count = 0;
        for (IPOApplication application : applications) {
            if (application.getStatus() != IPOApplication.Status.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total amount of approved IPO applications.
     * @return the sum of all approved application amounts
     */
    public double getTotalApprovedAmount() {
        double total = 0.0;
        for (IPOApplication application : applications) {
            if (application.getStatus() == IPOApplication.Status.APPROVED) {
                total += application.getAmount();
            }
        }
        return total;
    }
}

class IPOApplication {
 enum Status {
        PENDING, APPROVED, REJECTED
    }

    private String companyName;
    private int shares;
    private double amount;
    private String document;
    private Status status;
    private Customer customer;

    public IPOApplication() {
        this.status = Status.PENDING;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Checks if the customer has an approved application for the same company.
     * @param customer the customer to check
     * @param companyName the name of the company
     * @return true if the customer has an approved application, false otherwise
     */
    public boolean hasApprovedApplication(Customer customer, String companyName) {
        for (IPOApplication application : customer.getApplications()) {
            if (application.getStatus() == Status.APPROVED && application.getCompanyName().equals(companyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Approves the IPO application and sends notification emails.
     * @return true if the application is approved, false otherwise
     */
    public boolean approve() {
        if (customer.isEligibleToApply()) {
            status = Status.APPROVED;
            customer.resetFailedAttempts();
            sendEmails();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application and sends a rejection email.
     * @return true if the application is rejected, false otherwise
     */
    public boolean reject() {
        status = Status.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels the pending IPO application.
     * @return true if the application is canceled, false otherwise
     */
    public boolean cancel() {
        if (status == Status.PENDING) {
            status = Status.REJECTED;
            return true;
        }
        return false;
    }

    /**
     * Sends notification emails to the customer and the company.
     */
    private void sendEmails() {
        String customerEmailContent = generateEmailContent(customer, true);
        String companyEmailContent = generateEmailContent(customer, false);
        // Logic to send emails
    }

    /**
     * Sends a rejection email to the customer.
     */
    private void sendRejectionEmail() {
        String emailContent = generateRejectionEmailContent();
        // Logic to send email
    }

    /**
     * Generates the email content for the customer or the company.
     * @param customer the customer
     * @param forCustomer true if the email is for the customer, false for the company
     * @return the email content
     */
    private String generateEmailContent(Customer customer, boolean forCustomer) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Name: ").append(customer.getName()).append(" ").append(customer.getSurname()).append("\n");
        emailContent.append("Email: ").append(customer.getEmail()).append("\n");
        emailContent.append("Telephone: ").append(customer.getTelephone()).append("\n");
        emailContent.append("Company: ").append(companyName).append("\n");
        emailContent.append("Shares: ").append(shares).append("\n");
        emailContent.append("Amount: ").append(amount).append("\n");

        if (forCustomer) {
            emailContent.append("Your IPO application has been approved.");
        } else {
            emailContent.append("The customer's IPO application has been approved.");
        }

        return emailContent.toString();
    }

    /**
     * Generates the rejection email content.
     * @return the rejection email content
     */
    private String generateRejectionEmailContent() {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Name: ").append(customer.getName()).append(" ").append(customer.getSurname()).append("\n");
        emailContent.append("Email: ").append(customer.getEmail()).append("\n");
        emailContent.append("Telephone: ").append(customer.getTelephone()).append("\n");
        emailContent.append("Company: ").append(companyName).append("\n");
        emailContent.append("Shares: ").append(shares).append("\n");
        emailContent.append("Amount: ").append(amount).append("\n");
        emailContent.append("Your IPO application has been rejected.");

        return emailContent.toString();
    }
}

class Bank {
    /**
     * Creates an IPO application for a customer.
     * @param customer the customer
     * @param companyName the name of the company
     * @param shares the number of shares
     * @param amount the amount of money
     * @param document the uploaded document
     * @return true if the application is created, false otherwise
     */
    public boolean createIPOApplication(Customer customer, String companyName, int shares, double amount, String document) {
        if (!customer.isEligibleToApply() || shares <= 0 || document == null) {
            customer.incrementFailedAttempts();
            return false;
        }

        IPOApplication application = new IPOApplication();
        application.setCompanyName(companyName);
        application.setShares(shares);
        application.setAmount(amount);
        application.setDocument(document);
        application.setCustomer(customer);

        customer.getApplications().add(application);
        return true;
    }
}