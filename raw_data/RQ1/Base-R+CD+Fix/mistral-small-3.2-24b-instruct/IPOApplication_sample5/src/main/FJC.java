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

    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application for the customer.
     * @param company The target company for the IPO application.
     * @param shares The number of shares to purchase (>0).
     * @param amount The amount of money to pay.
     * @param doc The legal allowance documentation (non-null).
     * @return true if the application is created successfully, false otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO() || hasApprovedApplicationForCompany(company)) {
            return false;
        }

        Application application = new Application(company, shares, amount, doc, this);
        applications.add(application);
        return true;
    }

    /**
     * Checks if the customer has an approved application for the same company.
     * @param company The company to check.
     * @return true if the customer has an approved application for the company, false otherwise.
     */
    private boolean hasApprovedApplicationForCompany(Company company) {
        for (Application application : applications) {
            if (application.getCompany().equals(company) && application.getStatus() == ApplicationStatus.APPROVAL) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the total number of IPO applications filed by the customer, including approved and rejected applications.
     * @return The total number of applications.
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application application : applications) {
            if (application.getStatus() == ApplicationStatus.APPROVAL || application.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total approved IPO applications amount for the customer.
     * @return The sum of all approved application amounts.
     */
    public double getApprovedTotalAmount() {
        double total = 0;
        for (Application application : applications) {
            if (application.getStatus() == ApplicationStatus.APPROVAL) {
                total += application.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for the specified company.
     * @param companyName The name of the company for which the application is to be canceled.
     * @return true if the cancellation succeeds, false otherwise.
     */
    public boolean cancelApplication(String companyName) {
        for (Application application : applications) {
            if (application.getCompany().getName().equals(companyName) &&
                (application.getStatus() == ApplicationStatus.PENDING)) {
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

    public boolean canApplyForIPO() {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Company company = (Company) obj;
        return name.equals(company.name) && email.equals(company.email);
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
     * @param customer The customer who applied for the IPO.
     * @param company The target company for the IPO application.
     * @param shares The number of shares to purchase.
     * @param amount The amount of money to pay.
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

    public Application(Company company, int shares, double amount, Document doc, Customer customer) {
        this();
        this.company = company;
        this.share = shares;
        this.amountOfMoney = amount;
        this.allowance = doc;
        this.customer = customer;
    }

    /**
     * Approves the application.
     * @return true if the application is approved successfully, false otherwise.
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
     * Rejects the application.
     * @return true if the application is rejected successfully, false otherwise.
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels the application.
     * @return true if the application is canceled successfully, false otherwise.
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }

        this.status = ApplicationStatus.REJECTED;
        return true;
    }

    /**
     * Sends emails to the customer and the company.
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(customerEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(customerEmail);

        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(companyEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer.
     */
    public void sendRejectionEmail() {
        Email email = new Email();
        email.setReceiver(customer.getEmail());
        email.setContent("Your application for IPO with " + company.getName() + " has been rejected.");
        emails.add(email);
    }

    // Getters and Setters
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