import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private int failedAttempts;
    private List<IPOApplication> applications;
    private boolean restricted;

    public Customer() {
        this.failedAttempts = 0;
        this.applications = new ArrayList<>();
        this.restricted = false;
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

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * Checks if the customer is eligible to apply for IPO.
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleToApply() {
        return !restricted && failedAttempts < 3;
    }

    /**
     * Increments the failed attempts counter and checks if the customer should be restricted.
     */
    public void incrementFailedAttempts() {
        failedAttempts++;
        if (failedAttempts >= 3) {
            restricted = true;
        }
    }

    /**
     * Adds an IPO application to the customer's list of applications.
     * @param application the IPO application to add
     */
    public void addApplication(IPOApplication application) {
        applications.add(application);
    }

    /**
     * Retrieves the total number of approved and rejected IPO applications.
     * @return the total number of approved and rejected applications
     */
    public int getApplicationCountSummary() {
        int count = 0;
        for (IPOApplication application : applications) {
            if (application.isApproved() || application.isRejected()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total amount of approved IPO applications.
     * @return the total amount of approved applications
     */
    public double getTotalApprovalAmount() {
        double total = 0;
        for (IPOApplication application : applications) {
            if (application.isApproved()) {
                total += application.getAmount();
            }
        }
        return total;
    }
}

class IPOApplication {
    private String company;
    private int shares;
    private double amount;
    private String document;
    private boolean approved;
    private boolean rejected;

    public IPOApplication() {
        this.approved = false;
        this.rejected = false;
    }

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
     * Creates an IPO application if the customer is eligible and has no approved application for the same company.
     * @param customer the customer applying for the IPO
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Customer customer) {
        if (!customer.isEligibleToApply()) {
            customer.incrementFailedAttempts();
            return false;
        }

        for (IPOApplication application : customer.getApplications()) {
            if (application.getCompany().equals(this.company) && application.isApproved()) {
                customer.incrementFailedAttempts();
                return false;
            }
        }

        customer.addApplication(this);
        return true;
    }

    /**
     * Approves or rejects the IPO application.
     * @param customer the customer who applied for the IPO
     * @param company the company reviewing the application
     * @param approve true to approve the application, false to reject it
     * @return true if the operation is successful, false otherwise
     */
    public boolean approveOrReject(Customer customer, Company company, boolean approve) {
        if (this.approved || this.rejected) {
            return false;
        }

        if (approve) {
            this.approved = true;
            sendEmails(customer, company, true);
        } else {
            this.rejected = true;
            sendEmails(customer, company, false);
        }

        return true;
    }

    /**
     * Sends emails to the customer and the company based on the application status.
     * @param customer the customer who applied for the IPO
     * @param company the company reviewing the application
     * @param approved true if the application is approved, false if rejected
     */
    private void sendEmails(Customer customer, Company company, boolean approved) {
        String emailContent = generateEmailContent(customer, company, approved);
        if (approved) {
            company.sendEmail(customer.getEmail(), emailContent);
        }
        customer.sendEmail(emailContent);
    }

    /**
     * Generates the email content based on the application status.
     * @param customer the customer who applied for the IPO
     * @param company the company reviewing the application
     * @param approved true if the application is approved, false if rejected
     * @return the email content
     */
    private String generateEmailContent(Customer customer, Company company, boolean approved) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Name: ").append(customer.getName()).append("\n");
        emailContent.append("Surname: ").append(customer.getSurname()).append("\n");
        emailContent.append("Email: ").append(customer.getEmail()).append("\n");
        emailContent.append("Telephone: ").append(customer.getTelephone()).append("\n");
        emailContent.append("Company: ").append(company.getName()).append("\n");
        emailContent.append("Shares: ").append(this.shares).append("\n");
        emailContent.append("Amount: ").append(this.amount).append("\n");
        if (approved) {
            emailContent.append("Status: Approved");
        } else {
            emailContent.append("Status: Rejected");
        }
        return emailContent.toString();
    }

    /**
     * Cancels a pending IPO application.
     * @param customer the customer who applied for the IPO
     * @param companyName the name of the company for which the application is being canceled
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelApplication(Customer customer, String companyName) {
        if (!this.company.equals(companyName) || (this.approved || this.rejected)) {
            return false;
        }

        customer.getApplications().remove(this);
        return true;
    }
}

class Company {
    private String name;

    public Company() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sends an email to the specified recipient.
     * @param email the recipient's email address
     * @param content the content of the email
     */
    public void sendEmail(String email, String content) {
        // Implementation of sending an email
    }
}