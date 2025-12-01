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
     * @return true if the customer is eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates an IPO application for the customer.
     * @param company The company for which the IPO application is being made
     * @param shares The number of shares to be purchased (>0)
     * @param amount The amount of money for the purchase
     * @param doc The legal allowance documentation
     * @return true if the application is successfully created, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO() || doc == null || shares <= 0) {
            return false;
        }

        // Check if customer already has an approved application for this company
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
     * Retrieves the count of applications filed by the customer (excluding pending).
     * @return the number of reviewed applications (approved or rejected)
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
     * Calculates the total amount of approved IPO applications for the customer.
     * @return the sum of all approved application amounts
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
     * @param companyName The name of the company for which the application should be canceled
     * @return true if the cancellation is successful, false otherwise
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
     * Creates email content with customer, company, shares, and amount information.
     * @param customer The customer for whom the email is generated
     * @param company The company related to the IPO application
     * @param shares The number of shares purchased
     * @param amount The amount of money paid
     * @return formatted email content string
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return "Customer: " + customer.getName() + " " + customer.getSurname() +
               "\nEmail: " + customer.getEmail() +
               "\nPhone: " + customer.getTelephone() +
               "\nCompany: " + company.getName() +
               "\nShares: " + shares +
               "\nAmount: " + amount;
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
     * Approves the application if the customer is still eligible.
     * Sends confirmation emails to customer and company.
     * @return true if approved successfully, false otherwise
     */
    public boolean approve() {
        if (customer.isEligibleForIPO() && status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.APPROVAL;
            sendEmailsToCustomerAndCompany();
            return true;
        }
        return false;
    }

    /**
     * Rejects the application and sends a rejection email to the customer.
     * @return true if rejected successfully, false otherwise
     */
    public boolean reject() {
        if (status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.REJECTED;
            sendRejectionEmail();
            return true;
        }
        return false;
    }

    /**
     * Cancels a pending application.
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancel() {
        if (status == ApplicationStatus.PENDING) {
            status = ApplicationStatus.REJECTED;
            return true;
        }
        return false;
    }

    /**
     * Sends information emails to both customer and company.
     */
    public void sendEmailsToCustomerAndCompany() {
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(new Email().createEmailContent(
            customer, company, share, amountOfMoney));
        emails.add(customerEmail);

        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(new Email().createEmailContent(
            customer, company, share, amountOfMoney));
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
}