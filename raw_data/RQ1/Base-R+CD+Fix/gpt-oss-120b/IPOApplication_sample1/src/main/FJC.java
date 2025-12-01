import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of possible statuses for an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Simple placeholder for a document that a customer uploads when applying for an IPO.
 */
class Document {
    // In a real system this would contain file data, metadata, etc.
}

/**
 * Represents a company that can receive IPO applications.
 */
class Company {
    private String name;
    private String email;

    /** No‑arg constructor */
    public Company() {
    }

    /** Full constructor for convenience */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // ---------- Getters & Setters ----------
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
 * Represents an e‑mail that will be sent either to a customer or a company.
 */
class Email {
    private String receiver;
    private String content;

    /** No‑arg constructor */
    public Email() {
    }

    /** Constructor with fields */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    // ---------- Getters & Setters ----------
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
     * Creates the text body of an informational e‑mail that contains the
     * details required by the domain description.
     *
     * @param customer the customer that applied
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   total amount of money paid
     * @return a formatted string that can be used as email content
     */
    public static String createEmailContent(Customer customer,
                                            Company company,
                                            int shares,
                                            double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application details are as follows:\n");
        sb.append("Customer Name   : ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n');
        sb.append("Email           : ").append(customer.getEmail()).append('\n');
        sb.append("Telephone       : ").append(customer.getTelephone()).append('\n');
        sb.append("Target Company  : ").append(company.getName()).append('\n');
        sb.append("Shares Purchased: ").append(shares).append('\n');
        sb.append("Amount Paid     : $").append(String.format("%.2f", amount)).append('\n');
        sb.append("\nThank you for using our service.");
        return sb.toString();
    }
}

/**
 * Represents an IPO application made by a customer for a specific company.
 */
class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails = new ArrayList<>();

    /** No‑arg constructor */
    public Application() {
        this.status = ApplicationStatus.PENDING;
    }

    /** Full constructor used internally by Customer.createApplication */
    public Application(int share,
                       double amountOfMoney,
                       Customer customer,
                       Company company,
                       Document allowance) {
        this.share = share;
        this.amountOfMoney = amountOfMoney;
        this.customer = customer;
        this.company = company;
        this.allowance = allowance;
        this.status = ApplicationStatus.PENDING;
    }

    // ---------- Getters & Setters ----------
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
     * Approves this application if the customer is still allowed to apply.
     *
     * @return true if the approval succeeds, false otherwise
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
     * Rejects this application and notifies the customer via e‑mail.
     *
     * @return true if the rejection succeeds, false otherwise
     */
    public boolean reject() {
        this.status = ApplicationStatus.REJECTED;
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application. The status is changed to REJECTED to
     * indicate that it will no longer be processed.
     *
     * @return true if the cancellation succeeds, false otherwise
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED;
        // No email is sent for a voluntary cancellation.
        return true;
    }

    /**
     * Sends two informational e‑mails: one to the customer and one to the
     * company, containing the details required by the domain description.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to Customer
        String custContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email custEmail = new Email(customer.getEmail(), custContent);
        emails.add(custEmail);

        // Email to Company
        String compContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email compEmail = new Email(company.getEmail(), compContent);
        emails.add(compEmail);
    }

    /**
     * Sends a rejection notice to the customer.
     */
    public void sendRejectionEmail() {
        String content = "Dear " + customer.getName() + " " + customer.getSurname()
                + ",\n\nWe regret to inform you that your IPO application for "
                + company.getName() + " has been rejected.\n\nRegards,\nBank";
        Email rejectionEmail = new Email(customer.getEmail(), content);
        emails.add(rejectionEmail);
    }
}

/**
 * Represents a retail customer who may apply for IPOs.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO = true;
    private List<Application> applications = new ArrayList<>();

    /** No‑arg constructor */
    public Customer() {
    }

    /** Full constructor for convenience */
    public Customer(String name, String surname, String email, String telephone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.canApplyForIPO = true;
    }

    // ---------- Getters & Setters ----------
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
     * Checks if the customer is currently eligible to apply for IPOs.
     *
     * @return true if eligible, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company.
     *
     * @param company the target company
     * @param shares  number of shares requested (must be > 0)
     * @param amount  amount of money to be paid (must be > 0)
     * @param doc     non‑null document proving legal allowance
     * @return true if the application is successfully created, false otherwise
     */
    public boolean createApplication(Company company,
                                     int shares,
                                     double amount,
                                     Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (company == null || doc == null || shares <= 0 || amount <= 0) {
            return false;
        }
        // Ensure no already approved application exists for the same company
        for (Application app : applications) {
            if (app.getCompany() != null
                    && Objects.equals(app.getCompany().getName(), company.getName())
                    && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        Application newApp = new Application(shares, amount, this, company, doc);
        applications.add(newApp);
        return true;
    }

    /**
     * Returns the total number of reviewed applications (approved or rejected).
     *
     * @return count of applications with status APPROVAL or REJECTED
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVAL
                    || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the aggregate amount of money for all approved applications.
     *
     * @return sum of amountOfMoney for applications with status APPROVAL
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
     * Cancels a pending application for the specified company.
     *
     * @param companyName name of the company whose application should be cancelled
     * @return true if the cancellation succeeds, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany() != null
                    && Objects.equals(app.getCompany().getName(), companyName)
                    && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }
}