import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of possible states of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Simple placeholder class for a legal‑allowance document that a customer
 * must upload when creating an IPO application.
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

    /** No‑argument constructor. */
    public Company() {
        this.name = "";
        this.email = "";
    }

    /** Getters and setters */
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
 * Simple email model used by the system to send notifications.
 */
class Email {
    private String receiver;
    private String content;

    /** No‑argument constructor. */
    public Email() {
        this.receiver = "";
        this.content = "";
    }

    /** Getters and setters */
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
     * Builds the body of an informational email that contains the details of an
     * approved IPO purchase.
     *
     * @param customer the customer that applied
     * @param company  the company to which the application was made
     * @param shares   number of shares purchased
     * @param amount   total amount of money paid
     * @return a formatted string that can be used as the email content
     */
    public String createEmailContent(Customer customer,
                                    Company company,
                                    int shares,
                                    double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application has been processed.\n");
        sb.append("Company: ").append(company.getName()).append('\n');
        sb.append("Shares purchased: ").append(shares).append('\n');
        sb.append("Amount paid: $").append(String.format("%.2f", amount)).append('\n');
        sb.append("\nCustomer details:\n");
        sb.append("Name: ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n');
        sb.append("Email: ").append(customer.getEmail()).append('\n');
        sb.append("Telephone: ").append(customer.getTelephone()).append('\n');
        sb.append("\nThank you for using our service.\n");
        return sb.toString();
    }

    /**
     * Builds the body of a rejection email.
     *
     * @param customer the customer whose application was rejected
     * @param company  the company to which the application was made
     * @return a formatted rejection message
     */
    public String createRejectionContent(Customer customer, Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application to ")
          .append(company.getName())
          .append(" has been rejected.\n\n");
        sb.append("If you have any questions, please contact our support centre.\n");
        sb.append("\nRegards,\nBank IPO Team");
        return sb.toString();
    }
}

/**
 * Represents a single IPO application submitted by a customer.
 */
class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    /** No‑argument constructor. */
    public Application() {
        this.share = 0;
        this.amountOfMoney = 0.0;
        this.status = ApplicationStatus.PENDING;
        this.emails = new ArrayList<>();
    }

    /** Getters and setters */
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
     * Approves this application if it is still pending. After approval the system
     * sends informational emails to both the customer and the company.
     *
     * @return true if the approval succeeded, false otherwise
     */
    public boolean approve() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects this application if it is still pending. Sends a rejection email
     * to the customer.
     *
     * @return true if the rejection succeeded, false otherwise
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
     * Cancels a pending application. The status is set to REJECTED to indicate
     * that the request will no longer be processed.
     *
     * @return true if the cancellation succeeded, false otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED;
        // No email is sent for a manual cancellation.
        return true;
    }

    /**
     * Sends two informational emails – one to the customer and one to the
     * company – after a successful approval.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to the customer
        Email custEmail = new Email();
        custEmail.setReceiver(customer.getEmail());
        custEmail.setContent(custEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(custEmail);

        // Email to the company (using company's email address)
        Email compEmail = new Email();
        compEmail.setReceiver(company.getEmail());
        compEmail.setContent(compEmail.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(compEmail);
    }

    /**
     * Sends a rejection email to the customer when the application is rejected.
     */
    public void sendRejectionEmail() {
        Email rejection = new Email();
        rejection.setReceiver(customer.getEmail());
        rejection.setContent(rejection.createRejectionContent(customer, company));
        emails.add(rejection);
    }
}

/**
 * Represents a retail customer that can submit IPO applications.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;

    /** No‑argument constructor. */
    public Customer() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.telephone = "";
        this.canApplyForIPO = true;
        this.applications = new ArrayList<>();
    }

    /** Getters and setters */
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
     * Checks whether this customer is currently eligible to submit IPO
     * applications.
     *
     * @return true if the customer can apply, false otherwise
     */
    public boolean isEligibleForIPO() {
        // Additional business logic (e.g., number of rejected attempts) could be added here.
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company.
     *
     * @param company the target company
     * @param shares  number of shares requested (must be > 0)
     * @param amount  monetary amount of the request
     * @param doc     the uploaded legal‑allowance document (must be non‑null)
     * @return true if the application was successfully created, false otherwise
     */
    public boolean createApplication(Company company,
                                      int shares,
                                      double amount,
                                      Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0 || doc == null) {
            return false;
        }
        // Ensure no already approved application exists for the same company.
        for (Application a : applications) {
            if (a.getCompany() != null &&
                a.getCompany().getName().equals(company.getName()) &&
                a.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        Application app = new Application();
        app.setShare(shares);
        app.setAmountOfMoney(amount);
        app.setCompany(company);
        app.setCustomer(this);
        app.setAllowance(doc);
        app.setStatus(ApplicationStatus.PENDING);
        applications.add(app);
        return true;
    }

    /**
     * Returns the total number of reviewed (approved or rejected) applications
     * submitted by this customer.
     *
     * @return count of reviewed applications, or 0 if none exist
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.APPROVAL ||
                a.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the aggregate amount of money for all approved applications.
     *
     * @return sum of amounts of approved applications, or 0.0 if none
     */
    public double getApprovedTotalAmount() {
        double sum = 0.0;
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.APPROVAL) {
                sum += a.getAmountOfMoney();
            }
        }
        return sum;
    }

    /**
     * Cancels a pending application for the specified company.
     *
     * @param companyName name of the company whose pending application should be cancelled
     * @return true if a pending application was found and cancelled, false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application a : applications) {
            if (a.getCompany() != null &&
                a.getCompany().getName().equals(companyName) &&
                a.getStatus() == ApplicationStatus.PENDING) {
                return a.cancel();
            }
        }
        return false;
    }
}