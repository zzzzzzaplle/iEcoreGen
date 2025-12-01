import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration representing the possible statuses of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Simple placeholder class representing a legal allowance document that a customer must upload.
 */
class Document {
    // No fields are required for the current domain model.
    public Document() {
        // No‑arg constructor
    }
}

/**
 * Represents a company that receives IPO applications.
 */
class Company {
    private String name;
    private String email;

    public Company() {
        // No‑arg constructor
    }

    /** Getter / Setter **/
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
 * Represents an email that the system can generate and "send".
 */
class Email {
    private String receiver;
    private String content;

    public Email() {
        // No‑arg constructor
    }

    /** Getter / Setter **/
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
     * Creates the email body used for both the customer and the company when an
     * application is approved.
     *
     * @param customer the applicant
     * @param company  the target company
     * @param shares   number of shares applied for
     * @param amount   amount of money paid
     * @return a formatted string containing the required information
     */
    public String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application has been processed.\n");
        sb.append("Details:\n");
        sb.append("Customer Name: ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n');
        sb.append("Email: ").append(customer.getEmail()).append('\n');
        sb.append("Telephone: ").append(customer.getTelephone()).append('\n');
        sb.append("Target Company: ").append(company.getName()).append('\n');
        sb.append("Shares Purchased: ").append(shares).append('\n');
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append('\n');
        sb.append("\nThank you for using our service.\n");
        return sb.toString();
    }

    /**
     * Creates a rejection email content for a customer.
     *
     * @param customer the applicant
     * @param company  the target company
     * @return formatted rejection message
     */
    public String createRejectionContent(Customer customer, Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application to ").append(company.getName())
                .append(" has been rejected.\n");
        sb.append("Please contact our call centre if you wish to discuss this decision.\n");
        sb.append("\nBest regards,\nYour Bank");
        return sb.toString();
    }
}

/**
 * Represents an IPO application submitted by a customer.
 */
class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails = new ArrayList<>();

    public Application() {
        // No‑arg constructor
    }

    /** Getter / Setter **/
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
     *
     * @return {@code true} if the approval succeeds; {@code false} otherwise
     */
    public boolean approve() {
        if (customer == null || company == null) {
            return false;
        }
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        this.status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application and notifies the customer.
     *
     * @return {@code true} if the rejection succeeds; {@code false} otherwise
     */
    public boolean reject() {
        if (customer == null || company == null) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED;
        // Increment failed attempts for the customer
        customer.incrementFailedAttempts();
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application.
     *
     * @return {@code true} if the cancellation succeeds; {@code false} otherwise
     */
    public boolean cancel() {
        if (this.status != ApplicationStatus.PENDING) {
            return false;
        }
        // Removal from customer's list is handled in Customer.cancelApplication(...)
        this.status = ApplicationStatus.REJECTED; // Mark as rejected to indicate it will no longer be processed
        return true;
    }

    /**
     * Sends two information emails – one to the customer and one to the company –
     * after a successful approval.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to Customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        String content = customerEmail.createEmailContent(customer, company, share, amountOfMoney);
        customerEmail.setContent(content);
        emails.add(customerEmail);

        // Email to Company (using same content, could be customized)
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
        String content = rejectionEmail.createRejectionContent(customer, company);
        rejectionEmail.setContent(content);
        emails.add(rejectionEmail);
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
    private boolean canApplyForIPO = true;
    private List<Application> applications = new ArrayList<>();

    // Tracking failed attempts to enforce restriction logic
    private int failedAttempts = 0;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    public Customer() {
        // No‑arg constructor
    }

    /** Getter / Setter **/
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

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks whether the customer is currently eligible to apply for IPOs.
     *
     * @return {@code true} if the customer can apply; {@code false} otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Increments the count of failed attempts and disables further applications
     * if the maximum allowed attempts are exceeded.
     */
    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= MAX_FAILED_ATTEMPTS) {
            this.canApplyForIPO = false;
        }
    }

    /**
     * Creates a new IPO application for the given company.
     *
     * @param company the target company
     * @param shares  number of shares requested (>0)
     * @param amount  amount of money to be paid
     * @param doc     uploaded legal allowance document (must be non‑null)
     * @return {@code true} if the application is successfully created; {@code false} otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        // Validate input
        if (company == null || doc == null || shares <= 0 || amount <= 0.0) {
            return false;
        }
        // Eligibility check
        if (!isEligibleForIPO()) {
            return false;
        }
        // No already approved application for the same company
        for (Application a : applications) {
            if (a.getCompany() != null && a.getCompany().getName().equals(company.getName())
                    && a.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        // Create and store the new application
        Application app = new Application();
        app.setShare(shares);
        app.setAmountOfMoney(amount);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(this);
        app.setCompany(company);
        app.setAllowance(doc);
        applications.add(app);
        return true;
    }

    /**
     * Retrieves the total number of processed applications (approved or rejected)
     * for this customer.
     *
     * @return the count of applications that have been reviewed; {@code 0} if none
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.APPROVAL || a.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the aggregate amount of money for all approved applications of this
     * customer.
     *
     * @return sum of approved application amounts; {@code 0.0} if none
     */
    public double getApprovedTotalAmount() {
        double total = 0.0;
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.APPROVAL) {
                total += a.getAmountOfMoney();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application associated with the given company name.
     *
     * @param companyName the name of the company whose application should be cancelled
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application a : applications) {
            if (a.getCompany() != null && a.getCompany().getName().equals(companyName)
                    && a.getStatus() == ApplicationStatus.PENDING) {
                boolean cancelled = a.cancel();
                if (cancelled) {
                    applications.remove(a);
                }
                return cancelled;
            }
        }
        return false;
    }
}