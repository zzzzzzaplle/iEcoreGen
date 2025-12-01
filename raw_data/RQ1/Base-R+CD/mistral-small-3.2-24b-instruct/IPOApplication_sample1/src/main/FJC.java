import java.util.ArrayList;
import java.util.List;

enum ApplicationStatus {
    PENDING,
    APPROVED,
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

    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application for the customer.
     *
     * @param company The target company for the IPO application.
     * @param shares The number of shares to apply for.
     * @param amount The amount of money to invest.
     * @param doc The legal allowance documentation.
     * @return true if the application is created successfully, false otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO() || hasApprovedApplicationForCompany(company.getName())) {
            return false;
        }
        Application application = new Application(company, shares, amount, doc, this);
        applications.add(application);
        return true;
    }

    /**
     * Checks if the customer has an approved application for the given company.
     *
     * @param companyName The name of the company to check.
     * @return true if the customer has an approved application, false otherwise.
     */
    private boolean hasApprovedApplicationForCompany(String companyName) {
        for (Application application : applications) {
            if (application.getCompany().getName().equals(companyName) &&
                application.getStatus() == ApplicationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the total number of IPO applications filed by the customer.
     *
     * @return The total number of applications, including approved and rejected ones.
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application application : applications) {
            if (application.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total approved IPO application amount for the customer.
     *
     * @return The sum of all approved application amounts.
     */
    public double getApprovedTotalAmount() {
        double total = 0;
        for (Application application : applications) {
            if (application.getStatus() == ApplicationStatus.APPROVED) {
                total += application.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels an application for the given company.
     *
     * @param companyName The name of the company for which to cancel the application.
     * @return true if the cancellation is successful, false otherwise.
     */
    public boolean cancelApplication(String companyName) {
        for (Application application : applications) {
            if (application.getCompany().getName().equals(companyName) &&
                application.getStatus() == ApplicationStatus.PENDING) {
                return application.cancel();
            }
        }
        return false;
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
}

class Company {
    private String name;
    private String email;

    public Company() {
    }

    // Getters and Setters
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

    /**
     * Creates the content of the email.
     *
     * @param customer The customer who applied for the IPO.
     * @param company The company for which the IPO was applied.
     * @param shares The number of shares applied for.
     * @param amount The amount of money invested.
     * @return The content of the email.
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return String.format("Customer: %s %s\nEmail: %s\nTelephone: %s\nCompany: %s\nShares: %d\nAmount: %.2f",
                customer.getName(), customer.getSurname(), customer.getEmail(), customer.getTelephone(),
                company.getName(), shares, amount);
    }

    // Getters and Setters
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

class Application {
    private int shares;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    public Application(Company company, int shares, double amount, Document doc, Customer customer) {
        this.company = company;
        this.shares = shares;
        this.amountOfMoney = amount;
        this.allowance = doc;
        this.customer = customer;
        this.status = ApplicationStatus.PENDING;
        this.emails = new ArrayList<>();
    }

    /**
     * Approves the IPO application.
     *
     * @return true if the approval is successful, false otherwise.
     */
    public boolean approve() {
        if (customer.isEligibleForIPO()) {
            status = ApplicationStatus.APPROVED;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the IPO application.
     *
     * @return true if the rejection is successful, false otherwise.
     */
    public boolean reject() {
        status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels the IPO application.
     *
     * @return true if the cancellation is successful, false otherwise.
     */
    public boolean cancel() {
        if (status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.REJECTED;
            return true;
        }
        return false;
    }

    /**
     * Sends emails to the customer and the company.
     */
    private void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(customerEmail.createEmailContent(customer, company, shares, amountOfMoney));
        emails.add(customerEmail);

        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(companyEmail.createEmailContent(customer, company, shares, amountOfMoney));
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer.
     */
    private void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Your application for IPO with " + company.getName() + " has been rejected.");
        emails.add(rejectionEmail);
    }

    // Getters and Setters
    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
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