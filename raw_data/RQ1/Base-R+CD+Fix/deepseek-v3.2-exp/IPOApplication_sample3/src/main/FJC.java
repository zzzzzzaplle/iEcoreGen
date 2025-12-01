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
     * Checks if the customer is eligible for IPO application.
     * Retail customers should be eligible for applying IPO.
     * Customers who have been restricted due to failed attempts cannot apply.
     *
     * @return true if customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application for the customer.
     * The system first checks that the customer is still eligible to apply for IPOs
     * and has no approved application for the same company; only then is an application record created.
     *
     * @param company the target company for the IPO application
     * @param shares the number of shares to purchase (must be > 0)
     * @param amount the amount of money to pay
     * @param doc the legal allowance documentation (must be non-null)
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
            return false;
        }
        
        // Check if customer has any approved application for the same company
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        
        Application application = new Application();
        application.setShare(shares);
        application.setAmountOfMoney(amount);
        application.setCustomer(this);
        application.setCompany(company);
        application.setAllowance(doc);
        application.setStatus(ApplicationStatus.PENDING);
        
        applications.add(application);
        return true;
    }

    /**
     * Retrieves the total count of IPO applications that have been reviewed by the bank
     * (either approved or rejected). Applications that are still pending are not included.
     *
     * @return the number of reviewed applications, 0 if none
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
     * Calculates the total amount of all approved IPO applications for this customer.
     *
     * @return the sum of all approved application amounts, 0 if none
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
     * Cancels a pending application for a specific company.
     *
     * @param companyName the name of the company whose application should be cancelled
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.PENDING && 
                app.getCompany().getName().equals(companyName)) {
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
     * Creates email content with the required information for IPO applications.
     *
     * @param customer the customer who applied for the IPO
     * @param company the company the IPO was applied to
     * @param shares the number of shares purchased
     * @param amount the amount of money paid
     * @return the formatted email content string
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer Information:\n" +
               "Name: " + customer.getName() + "\n" +
               "Surname: " + customer.getSurname() + "\n" +
               "Email: " + customer.getEmail() + "\n" +
               "Telephone: " + customer.getTelephone() + "\n\n" +
               "Applied Company: " + company.getName() + "\n" +
               "Number of Shares: " + shares + "\n" +
               "Amount Paid: $" + amount;
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
     * Approves the IPO application after verifying customer eligibility.
     * If approved, sends two information emails: one to the customer and one to the company.
     *
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
     * Rejects the IPO application and sends a rejection email to the customer.
     *
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
     * Cancels a pending application.
     *
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
     * Sends two information emails: one to the customer and one to the company.
     * Email content includes customer information, company name, shares, and amount.
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(companyEmail.createEmailContent(customer, company, share, amountOfMoney));
        
        emails.add(customerEmail);
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer informing them that their application was rejected.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n" +
                               "We regret to inform you that your IPO application for " + company.getName() + 
                               " has been rejected.\n\n" +
                               "Best regards,\nBank IPO Team");
        
        emails.add(rejectionEmail);
    }
}