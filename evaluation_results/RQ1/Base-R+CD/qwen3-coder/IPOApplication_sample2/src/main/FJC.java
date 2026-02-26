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
     * Checks if the customer is eligible to apply for IPO.
     *
     * @return true if the customer can apply for IPO, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application for the customer.
     *
     * @param company The company for which the IPO application is being made
     * @param shares The number of shares to apply for (must be greater than 0)
     * @param amount The amount of money for the application
     * @param doc The legal allowance documentation
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        // Check eligibility
        if (!isEligibleForIPO()) {
            return false;
        }

        // Validate inputs
        if (shares <= 0 || doc == null) {
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

        applications.add(newApplication);
        return true;
    }

    /**
     * Gets the count of reviewed IPO applications (approved or rejected) for this customer.
     *
     * @return the number of reviewed applications
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
     * @return true if the cancellation is successful, false otherwise
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

    public Company() {}

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
    public Document() {}
}

class Email {
    private String receiver;
    private String content;

    public Email() {}

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
     * Creates email content for IPO application notifications.
     *
     * @param customer The customer involved in the application
     * @param company The company associated with the application
     * @param shares The number of shares applied for
     * @param amount The amount of money for the application
     * @return A formatted string containing the email content
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder content = new StringBuilder();
        content.append("Customer Information:\n");
        content.append("Name: ").append(customer.getName()).append(" ").append(customer.getSurname()).append("\n");
        content.append("Email: ").append(customer.getEmail()).append("\n");
        content.append("Phone: ").append(customer.getTelephone()).append("\n");
        content.append("Applied Company: ").append(company.getName()).append("\n");
        content.append("Shares Purchased: ").append(shares).append("\n");
        content.append("Amount Paid: $").append(String.format("%.2f", amount)).append("\n");
        return content.toString();
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
     * Rejects the application and notifies the customer via email.
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
     * @return true if the application is cancelled successfully, false otherwise
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED; // Using REJECTED to indicate cancellation
        return true;
    }

    /**
     * Sends notification emails to both the customer and the company for approved applications.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(createEmailContent(true));
        emails.add(customerEmail);

        // Email to company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(createEmailContent(false));
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Your IPO application for " + company.getName() + " has been rejected.");
        emails.add(rejectionEmail);
    }

    private String createEmailContent(boolean isCustomer) {
        StringBuilder content = new StringBuilder();
        content.append("Customer Information:\n");
        content.append("Name: ").append(customer.getName()).append(" ").append(customer.getSurname()).append("\n");
        content.append("Email: ").append(customer.getEmail()).append("\n");
        content.append("Phone: ").append(customer.getTelephone()).append("\n");
        content.append("Applied Company: ").append(company.getName()).append("\n");
        content.append("Shares Purchased: ").append(share).append("\n");
        content.append("Amount Paid: $").append(String.format("%.2f", amountOfMoney)).append("\n");
        return content.toString();
    }
}