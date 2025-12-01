import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration representing the possible statuses of an IPO application.
 */
enum ApplicationStatus {
    /** The application has been submitted but not yet reviewed. */
    PENDING,
    /** The application has been approved by the bank. */
    APPROVAL,
    /** The application has been rejected by the bank. */
    REJECTED
}

/**
 * Represents a retail customer who can apply for IPOs.
 */
class Customer {

    /** Customer's first name. */
    private String name;
    /** Customer's last name. */
    private String surname;
    /** Customer's e‑mail address. */
    private String email;
    /** Customer's telephone number. */
    private String telephone;
    /** Flag indicating whether the customer is allowed to apply for IPOs. */
    private boolean canApplyForIPO;
    /** All applications created by this customer. */
    private List<Application> applications;

    /** Default constructor required for object creation without arguments. */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // by default a new customer can apply
    }

    /* -------------------------------------------------
     * Getters and Setters
     * ------------------------------------------------- */

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

    /* -------------------------------------------------
     * Business methods
     * ------------------------------------------------- */

    /**
     * Checks whether the customer is currently eligible to apply for an IPO.
     *
     * @return {@code true} if the customer can apply, {@code false} otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the specified company.
     *
     * @param company the target company for the IPO application (must not be {@code null})
     * @param shares  number of shares the customer wants to purchase (must be &gt; 0)
     * @param amount  monetary amount for the purchase (must be positive)
     * @param doc     legal allowance document (must not be {@code null})
     * @return {@code true} if the application was successfully created; {@code false} otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (company == null || doc == null || shares <= 0 || amount <= 0) {
            return false;
        }
        // ensure no already approved application for the same company
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        Application newApp = new Application();
        newApp.setShare(shares);
        newApp.setAmountOfMoney(amount);
        newApp.setStatus(ApplicationStatus.PENDING);
        newApp.setCustomer(this);
        newApp.setCompany(company);
        newApp.setAllowance(doc);
        newApp.setEmails(new ArrayList<>());
        applications.add(newApp);
        return true;
    }

    /**
     * Retrieves the total number of reviewed applications (approved or rejected) filed by the customer.
     *
     * @return the count of reviewed applications; {@code 0} if none exist.
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
     * Calculates the aggregate amount of money of all approved IPO applications for this customer.
     *
     * @return the sum of approved application amounts; {@code 0.0} if none are approved.
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
     * Cancels a pending application for the given company name.
     *
     * @param companyName the name of the company whose pending application should be cancelled.
     * @return {@code true} if a pending application was found and cancelled; {@code false} otherwise.
     */
    public boolean cancelApplication(String companyName) {
        if (companyName == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.getCompany() != null &&
                companyName.equals(app.getCompany().getName()) &&
                app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }
}

/**
 * Represents a company that can receive IPO applications.
 */
class Company {

    /** Company name. */
    private String name;
    /** Company e‑mail address. */
    private String email;

    /** Default constructor. */
    public Company() {
    }

    /* -------------------------------------------------
     * Getters and Setters
     * ------------------------------------------------- */

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

/**
 * Placeholder class representing a legal allowance document that must be uploaded with an application.
 */
class Document {
    /** Default constructor. */
    public Document() {
    }

    // In a real implementation this class would contain fields such as file name,
    // binary content, MIME type, etc. For this exercise it remains empty.
}

/**
 * Represents an e‑mail that can be sent either to a customer or a company.
 */
class Email {

    /** Receiver e‑mail address. */
    private String receiver;
    /** Body content of the e‑mail. */
    private String content;

    /** Default constructor. */
    public Email() {
    }

    /* -------------------------------------------------
     * Getters and Setters
     * ------------------------------------------------- */

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
     * Creates the e‑mail body content according to the specification.
     *
     * @param customer the customer to whom the e‑mail refers
     * @param company  the company involved in the IPO
     * @param shares   number of shares purchased
     * @param amount   monetary amount paid
     * @return a formatted string that contains all required information.
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application details are as follows:\n");
        sb.append("Customer Email: ").append(customer.getEmail()).append("\n");
        sb.append("Telephone: ").append(customer.getTelephone()).append("\n");
        sb.append("Company: ").append(company.getName()).append("\n");
        sb.append("Number of Shares: ").append(shares).append("\n");
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append("\n\n");
        sb.append("Thank you for using our service.\n");
        return sb.toString();
    }
}

/**
 * Represents an IPO application created by a customer for a specific company.
 */
class Application {

    /** Number of shares requested. */
    private int share;
    /** Amount of money attached to the request. */
    private double amountOfMoney;
    /** Current status of the application. */
    private ApplicationStatus status;
    /** Customer who created the application. */
    private Customer customer;
    /** Target company of the application. */
    private Company company;
    /** Uploaded legal allowance document. */
    private Document allowance;
    /** List of e‑mails generated for this application. */
    private List<Email> emails;

    /** Default constructor. */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /* -------------------------------------------------
     * Getters and Setters
     * ------------------------------------------------- */

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

    /* -------------------------------------------------
     * Business methods
     * ------------------------------------------------- */

    /**
     * Approves the application if the customer is still eligible to apply.
     *
     * @return {@code true} if the approval succeeds; {@code false} otherwise.
     */
    public boolean approve() {
        if (customer == null || !customer.isEligibleForIPO()) {
            return false;
        }
        this.status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application and notifies the customer via e‑mail.
     *
     * @return {@code true} if the rejection succeeds; {@code false} otherwise.
     */
    public boolean reject() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application.
     *
     * @return {@code true} if the cancellation succeeds; {@code false} otherwise.
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        // Cancellation is treated as a rejection without notifying the company.
        this.status = ApplicationStatus.REJECTED;
        // Optionally we could send a cancellation email; for now we just change the status.
        return true;
    }

    /**
     * Sends informational e‑mails to both the customer and the company after a successful approval.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to Customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        String customerContent = new Email().createEmailContent(customer, company, share, amountOfMoney);
        customerEmail.setContent(customerContent);
        emails.add(customerEmail);

        // Email to Company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        String companyContent = new Email().createEmailContent(customer, company, share, amountOfMoney);
        companyEmail.setContent(companyContent);
        emails.add(companyEmail);

        // In a real system we would now dispatch these e‑mails via an SMTP server.
    }

    /**
     * Sends a rejection e‑mail to the customer informing them that their application was not approved.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application for company ")
          .append(company.getName()).append(" has been rejected.\n");
        sb.append("If you have any questions, please contact our call centre.\n\n");
        sb.append("Regards,\nIPO Processing Team");
        rejectionEmail.setContent(sb.toString());
        emails.add(rejectionEmail);

        // Real e‑mail dispatch would occur here.
    }
}