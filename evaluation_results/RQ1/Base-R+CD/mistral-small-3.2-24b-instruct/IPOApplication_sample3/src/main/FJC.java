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
     * @return true if the customer can apply for IPO, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the customer.
     * @param company the target company
     * @param shares the number of shares (>0)
     * @param amount the amount of money
     * @param doc the uploaded document (non-null)
     * @return true if the application is created successfully, false otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO() || shares <= 0 || doc == null) {
            return false;
        }

        for (Application app : applications) {
            if (app.getCompany().getName().equals(company.getName()) && app.getStatus() == ApplicationStatus.APPROVED) {
                return false;
            }
        }

        Application application = new Application();
        application.setShare(shares);
        application.setAmountOfMoney(amount);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(this);
        application.setCompany(company);
        application.setAllowance(doc);

        applications.add(application);
        return true;
    }

    /**
     * Retrieves the total number of IPO applications filed by the customer.
     * @return the total number of applications
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() != ApplicationStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the total amount of approved IPO applications for the customer.
     * @return the total amount of approved applications
     */
    public double getApprovedTotalAmount() {
        double total = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels an application for the specified company.
     * @param companyName the name of the company
     * @return true if the cancellation succeeds, false otherwise
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
     * Creates the content of the email.
     * @param customer the customer
     * @param company the company
     * @param shares the number of shares
     * @param amount the amount of money
     * @return the email content
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        return String.format(
            "Customer: %s %s\nEmail: %s\nTelephone: %s\nCompany: %s\nShares: %d\nAmount: %.2f",
            customer.getName(),
            customer.getSurname(),
            customer.getEmail(),
            customer.getTelephone(),
            company.getName(),
            shares,
            amount
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
     * Approves the application.
     * @return true if the application is approved successfully, false otherwise
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
     * Rejects the application.
     * @return true if the application is rejected successfully, false otherwise
     */
    public boolean reject() {
        status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels the application.
     * @return true if the application is canceled successfully, false otherwise
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
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent("Your application for IPO has been rejected.");
        emails.add(rejectionEmail);
    }
}