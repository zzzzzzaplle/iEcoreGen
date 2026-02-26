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
     *
     * @return true if the customer can apply for IPO, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     *
     * @param company The company for which the IPO application is being made
     * @param shares The number of shares to apply for (must be greater than 0)
     * @param amount The amount of money for the application
     * @param doc The legal allowance documentation
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        // Check if customer is eligible for IPO
        if (!isEligibleForIPO()) {
            return false;
        }

        // Check if shares is greater than 0
        if (shares <= 0) {
            return false;
        }

        // Check if document is provided
        if (doc == null) {
            return false;
        }

        // Check if customer already has an approved application for this company
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }

        // Create new application
        Application newApplication = new Application();
        newApplication.setShare(shares);
        newApplication.setAmountOfMoney(amount);
        newApplication.setStatus(ApplicationStatus.PENDING);
        newApplication.setCustomer(this);
        newApplication.setCompany(company);
        newApplication.setAllowance(doc);
        newApplication.setEmails(new ArrayList<>());

        applications.add(newApplication);
        return true;
    }

    /**
     * Retrieves the count of reviewed IPO applications (approved or rejected) for this customer.
     *
     * @return the number of reviewed applications, or 0 if none
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
     * Calculates the total amount of money from all approved IPO applications.
     *
     * @return the sum of approved application amounts
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
     * @param companyName The name of the company for which to cancel the application
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
     * Creates email content with customer, company, shares, and amount information.
     *
     * @param customer The customer making the application
     * @param company The company for which the application is made
     * @param shares The number of shares applied for
     * @param amount The amount of money for the application
     * @return The formatted email content
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Customer: ").append(customer.getName()).append(" ")
                      .append(customer.getSurname()).append("\n");
        contentBuilder.append("Email: ").append(customer.getEmail()).append("\n");
        contentBuilder.append("Telephone: ").append(customer.getTelephone()).append("\n");
        contentBuilder.append("Company: ").append(company.getName()).append("\n");
        contentBuilder.append("Shares: ").append(shares).append("\n");
        contentBuilder.append("Amount: ").append(amount).append("\n");
        return contentBuilder.toString();
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
     * Approves the application if the customer is still eligible for IPO applications.
     *
     * @return true if the application is approved successfully, false otherwise
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
     * Rejects the application and sends a rejection email to the customer.
     *
     * @return true if the application is rejected successfully, false otherwise
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application.
     *
     * @return true if cancellation is successful, false otherwise
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED; // Using REJECTED to indicate cancellation
        return true;
    }

    /**
     * Sends information emails to both the customer and the company.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        String content = new Email().createEmailContent(customer, company, share, amountOfMoney);
        customerEmail.setContent(content);
        emails.add(customerEmail);

        // Email to company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(content);
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        String content = new Email().createEmailContent(customer, company, share, amountOfMoney);
        rejectionEmail.setContent(content);
        emails.add(rejectionEmail);
    }
}