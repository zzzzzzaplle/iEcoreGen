import java.util.ArrayList;
import java.util.List;

enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

class Document {
    // Placeholder class for legal allowance documentation
    // Could be extended with actual document properties if needed
}

class Email {
    private String receiver;
    private String content;

    public Email() {
    }

    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Creates email content with customer information, company details, shares, and amount
     * @param customer The customer associated with the IPO application
     * @param company The company for which the IPO is being applied
     * @param shares The number of shares being purchased
     * @param amount The amount of money paid for the shares
     * @return Formatted email content string
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Information:\n" +
               "Name: " + customer.getName() + " " + customer.getSurname() + "\n" +
               "Email: " + customer.getEmail() + "\n" +
               "Telephone: " + customer.getTelephone() + "\n" +
               "Company: " + company.getName() + "\n" +
               "Shares Purchased: " + shares + "\n" +
               "Amount Paid: " + amount;
    }

    // Getters and setters
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class Company {
    private String name;
    private String email;

    public Company() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
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
     * Approves the IPO application if customer is still eligible
     * Sends confirmation emails to customer and company upon approval
     * @return true if approval is successful, false otherwise
     */
    public boolean approve() {
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        
        this.status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the IPO application and sends rejection email to customer
     * @return true if rejection is successful
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application
     * @return true if cancellation is successful, false if application is not in PENDING status
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED; // Mark as rejected for cancellation
        return true;
    }

    /**
     * Sends confirmation emails to both customer and company
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        
        emails.add(customerEmail);
        emails.add(companyEmail);
        
        // In a real implementation, this would actually send the emails
    }

    /**
     * Sends rejection email to the customer
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Dear " + customer.getName() + " " + customer.getSurname() + 
                                ",\n\nYour IPO application for " + company.getName() + 
                                " has been rejected.\n\nBest regards,\nThe Bank");
        
        emails.add(rejectionEmail);
        
        // In a real implementation, this would actually send the email
    }

    // Getters and setters
    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Document getAllowance() {
        return allowance;
    }

    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
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
     * Checks if customer is eligible for IPO applications
     * @return true if customer can apply for IPOs, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application after validating eligibility and checking for duplicate applications
     * @param company The target company for the IPO application
     * @param shares The number of shares to purchase (must be > 0)
     * @param amount The amount of money for the purchase
     * @param doc The legal allowance documentation (must not be null)
     * @return true if application creation is successful, false otherwise
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
        
        // Check for existing approved application for the same company
        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && 
                app.getStatus() == ApplicationStatus.APPROVAL) {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    canApplyForIPO = false;
                }
                return false;
            }
        }
        
        Application newApplication = new Application();
        newApplication.setCustomer(this);
        newApplication.setCompany(company);
        newApplication.setShare(shares);
        newApplication.setAmountOfMoney(amount);
        newApplication.setAllowance(doc);
        
        applications.add(newApplication);
        failedAttempts = 0; // Reset failed attempts on successful application
        return true;
    }

    /**
     * Retrieves the total count of reviewed applications (approved or rejected)
     * @return The number of applications that have been reviewed
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVAL || 
                app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total amount of money from all approved IPO applications
     * @return The sum of approved application amounts
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
     * @param companyName The name of the company whose application should be cancelled
     * @return true if cancellation is successful, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName) && 
                app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }

    // Getters and setters
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

    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}