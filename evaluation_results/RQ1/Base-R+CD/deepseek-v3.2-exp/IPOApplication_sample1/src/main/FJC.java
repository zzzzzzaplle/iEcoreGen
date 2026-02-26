import java.util.ArrayList;
import java.util.List;

enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;
    private int failedAttempts;

    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true;
        this.failedAttempts = 0;
    }

    /**
     * Checks if the customer is eligible to apply for IPOs
     * @return true if customer can apply for IPOs, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO && failedAttempts < 3;
    }

    /**
     * Creates an IPO application for the customer
     * @param company The target company for the IPO application
     * @param shares Number of shares to purchase (must be > 0)
     * @param amount Amount of money for the purchase
     * @param doc Legal allowance documentation (must not be null)
     * @return true if application was created successfully, false otherwise
     * @throws IllegalArgumentException if shares <= 0 or doc is null
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (shares <= 0) {
            throw new IllegalArgumentException("Number of shares must be greater than 0");
        }
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        
        if (!isEligibleForIPO()) {
            failedAttempts++;
            if (failedAttempts >= 3) {
                canApplyForIPO = false;
            }
            return false;
        }
        
        // Check if customer has existing approved application for same company
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    canApplyForIPO = false;
                }
                return false;
            }
        }
        
        Application application = new Application();
        application.setCustomer(this);
        application.setCompany(company);
        application.setShare(shares);
        application.setAmountOfMoney(amount);
        application.setAllowance(doc);
        application.setStatus(ApplicationStatus.PENDING);
        
        applications.add(application);
        failedAttempts = 0; // Reset failed attempts on successful application creation
        return true;
    }

    /**
     * Retrieves the total number of processed IPO applications (approved or rejected)
     * @return Count of applications that have been approved or rejected
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVAL || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of money from approved IPO applications
     * @return Sum of all approved application amounts
     */
    public double getApprovedTotalAmount() {
        double total = 0.0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVAL) {
                total += app.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company
     * @param companyName Name of the company whose application should be cancelled
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName) && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public boolean isCanApplyForIPO() { return canApplyForIPO; }
    public void setCanApplyForIPO(boolean canApplyForIPO) { this.canApplyForIPO = canApplyForIPO; }
    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }
}

class Company {
    private String name;
    private String email;

    public Company() {}

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Company company = (Company) obj;
        return name != null ? name.equals(company.name) : company.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

class Document {
    public Document() {}
}

class Email {
    private String receiver;
    private String content;

    public Email() {}

    /**
     * Creates email content for IPO application notifications
     * @param customer Customer who applied for IPO
     * @param company Company that issued the IPO
     * @param shares Number of shares purchased
     * @param amount Amount of money paid
     * @return Formatted email content string
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return String.format(
            "IPO Application Details:\n" +
            "Customer: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n" +
            "Company: %s\n" +
            "Shares Purchased: %d\n" +
            "Amount Paid: %.2f",
            customer.getName(), customer.getSurname(), customer.getEmail(), 
            customer.getTelephone(), company.getName(), shares, amount
        );
    }

    // Getters and setters
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    public Application() {
        this.emails = new ArrayList<>();
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Approves the IPO application and sends notification emails
     * @return true if approval was successful, false otherwise
     */
    public boolean approve() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        
        status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the IPO application and sends rejection email
     * @return true if rejection was successful, false otherwise
     */
    public boolean reject() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        
        status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        
        // In a real system, you might want to add additional cleanup logic here
        return true;
    }

    /**
     * Sends information emails to both customer and company for approved applications
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(createEmailContent(customer, company, share, amountOfMoney));
        emails.add(customerEmail);
        
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(createEmailContent(customer, company, share, amountOfMoney));
        emails.add(companyEmail);
    }

    /**
     * Sends rejection email to the customer
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent(String.format(
            "Dear %s %s,\n\n" +
            "Your IPO application for %s has been rejected.\n" +
            "If you have any questions, please contact our call center.",
            customer.getName(), customer.getSurname(), company.getName()
        ));
        emails.add(rejectionEmail);
    }

    private String createEmailContent(Customer customer, Company company, int shares, double amount) {
        Email email = new Email();
        return email.createEmailContent(customer, company, shares, amount);
    }

    // Getters and setters
    public int getShare() { return share; }
    public void setShare(int share) { this.share = share; }
    public double getAmountOfMoney() { return amountOfMoney; }
    public void setAmountOfMoney(double amountOfMoney) { this.amountOfMoney = amountOfMoney; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public Document getAllowance() { return allowance; }
    public void setAllowance(Document allowance) { this.allowance = allowance; }
    public List<Email> getEmails() { return emails; }
    public void setEmails(List<Email> emails) { this.emails = emails; }
}