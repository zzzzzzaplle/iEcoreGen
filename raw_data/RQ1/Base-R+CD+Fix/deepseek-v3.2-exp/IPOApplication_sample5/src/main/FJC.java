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
    
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true;
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
    
    /**
     * Checks if the customer is eligible to apply for IPOs
     * @return true if customer can apply for IPOs, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }
    
    /**
     * Creates an IPO application for the customer
     * @param company The target company for the IPO application
     * @param shares The number of shares to purchase (must be > 0)
     * @param amount The amount of money to be paid
     * @param doc The legal allowance documentation (must not be null)
     * @return true if application was created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        
        if (shares <= 0) {
            return false;
        }
        
        if (doc == null) {
            return false;
        }
        
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
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
        return true;
    }
    
    /**
     * Retrieves the total count of IPO applications that have been reviewed by the bank
     * @return The number of approved and rejected applications
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
     * Calculates the total amount of money from all approved IPO applications
     * @return The sum of all approved application amounts
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
     * @param companyName The name of the company whose application should be canceled
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
}

class Company {
    private String name;
    private String email;
    
    public Company() {
    }
    
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
    public Document() {
    }
}

class Email {
    private String receiver;
    private String content;
    
    public Email() {
    }
    
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
    
    /**
     * Creates email content for IPO application notifications
     * @param customer The customer who applied for the IPO
     * @param company The company for which the IPO was applied
     * @param shares The number of shares purchased
     * @param amount The amount of money paid
     * @return The formatted email content string
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return String.format(
            "Customer Information:\n" +
            "Name: %s %s\n" +
            "Email: %s\n" +
            "Telephone: %s\n\n" +
            "Applied Company: %s\n" +
            "Number of Shares: %d\n" +
            "Amount Paid: %.2f",
            customer.getName(), customer.getSurname(), customer.getEmail(), customer.getTelephone(),
            company.getName(), shares, amount
        );
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
    }
    
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
     * Rejects the IPO application and sends rejection email to customer
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
        
        status = ApplicationStatus.REJECTED;
        return true;
    }
    
    /**
     * Sends information emails to both customer and company for approved applications
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(customerEmail);
        
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(companyEmail);
    }
    
    /**
     * Sends rejection email to the customer
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Dear " + customer.getName() + " " + customer.getSurname() + 
                                ",\n\nYour IPO application for " + company.getName() + 
                                " has been rejected by the company.\n\nBest regards,\nBank");
        emails.add(rejectionEmail);
    }
}