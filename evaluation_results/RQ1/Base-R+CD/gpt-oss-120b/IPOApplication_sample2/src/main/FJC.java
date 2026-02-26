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
 * Represents a retail customer that can apply for IPOs.
 */
class Customer {

    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO = true;
    private List<Application> applications = new ArrayList<>();

    /** Number of rejected applications. Used to restrict further applications after a threshold. */
    private int failedAttempts = 0;

    /** Maximum allowed failed attempts before the customer is blocked from applying. */
    private static final int MAX_FAILED_ATTEMPTS = 3;

    /** No‑argument constructor required by the task. */
    public Customer() {
    }

    /* -------------------------- Getters & Setters -------------------------- */

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

    /* -------------------------- Business Methods -------------------------- */

    /**
     * Checks whether the customer is currently eligible to apply for an IPO.
     *
     * @return {@code true} if the customer can apply; {@code false} otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company.
     *
     * <p>The method validates:
     * <ul>
     *   <li>Customer eligibility.</li>
     *   <li>Number of shares is greater than zero.</li>
     *   <li>Document is not {@code null}.</li>
     *   <li>No already approved application exists for the same company.</li>
     * </ul>
     *
     * @param company the target company for the IPO application
     * @param shares  number of shares requested (must be &gt; 0)
     * @param amount  monetary amount for the application
     * @param doc     the legal allowance document (must not be {@code null})
     * @return {@code true} if the application was successfully created; {@code false} otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0 || doc == null) {
            return false;
        }
        // Ensure there is no already approved application for the same company
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
        applications.add(newApp);
        return true;
    }

    /**
     * Retrieves the total number of applications that have been reviewed
     * (either approved or rejected). Pending applications are excluded.
     *
     * @return the count of reviewed applications; {@code 0} if none exist
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
     * Calculates the aggregate amount of money for all approved applications.
     *
     * @return sum of the amounts of approved applications; {@code 0.0} if none
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
     * @return {@code true} if a pending application was found and cancelled; {@code false} otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (app.getCompany().getName().equals(companyName)
                    && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }

    /**
     * Records a failed (rejected) attempt. When the number of failed attempts
     * reaches the configured threshold, the customer is no longer allowed to
     * apply for IPOs.
     */
    protected void recordFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            canApplyForIPO = false;
        }
    }

    /**
     * Resets the failed attempts counter. This can be called after a successful
     * approval, allowing the customer to continue applying.
     */
    protected void resetFailedAttempts() {
        failedAttempts = 0;
        canApplyForIPO = true;
    }
}

/**
 * Represents a company that issues IPOs.
 */
class Company {

    private String name;
    private String email;

    /** No‑argument constructor. */
    public Company() {
    }

    /* -------------------------- Getters & Setters -------------------------- */

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
 * Placeholder class for the legal allowance document uploaded by a customer.
 */
class Document {
    /** No‑argument constructor. */
    public Document() {
    }
}

/**
 * Represents an email that can be sent to a customer or a company.
 */
class Email {

    private String receiver;
    private String content;

    /** No‑argument constructor. */
    public Email() {
    }

    /* -------------------------- Getters & Setters -------------------------- */

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
     * Creates the textual content of an informational email.
     *
     * @param customer the customer the email is about
     * @param company  the company involved in the IPO
     * @param shares   number of shares purchased
     * @param amount   amount of money paid
     * @return formatted email body as a {@code String}
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application details are as follows:\n");
        sb.append("Customer Name: ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n');
        sb.append("Email: ").append(customer.getEmail()).append('\n');
        sb.append("Telephone: ").append(customer.getTelephone()).append('\n');
        sb.append("Company: ").append(company.getName()).append('\n');
        sb.append("Shares Purchased: ").append(shares).append('\n');
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append('\n');
        sb.append("\nThank you for using our services.\n");
        return sb.toString();
    }

    /**
     * Creates the textual content of a rejection email.
     *
     * @param customer the customer being rejected
     * @param company  the company whose application was rejected
     * @return formatted rejection email body as a {@code String}
     */
    public static String createRejectionContent(Customer customer, Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application for company ")
                .append(company.getName()).append(" has been rejected.\n");
        sb.append("Please contact our call centre if you wish to discuss this decision.\n");
        sb.append("\nBest regards,\nYour Bank");
        return sb.toString();
    }
}

/**
 * Represents an individual IPO application.
 */
class Application {

    private int share;
    private double amountOfMoney;
    private ApplicationStatus status = ApplicationStatus.PENDING;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails = new ArrayList<>();

    /** No‑argument constructor. */
    public Application() {
    }

    /* -------------------------- Getters & Setters -------------------------- */

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

    /* -------------------------- Business Methods -------------------------- */

    /**
     * Approves the application if the customer is still eligible for IPOs.
     *
     * @return {@code true} if the approval succeeded; {@code false} otherwise
     */
    public boolean approve() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        status = ApplicationStatus.APPROVAL;
        // Reset failed attempts because the customer succeeded
        customer.resetFailedAttempts();
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application and notifies the customer via email.
     *
     * @return {@code true} if the rejection succeeded; {@code false} otherwise
     */
    public boolean reject() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED;
        // Record a failed attempt for the customer
        customer.recordFailedAttempt();
        sendRejectionEmail();
        return true;
    }

    /**
     * Cancels a pending application. No email is sent for a cancellation.
     *
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED; // Treat cancellation as a rejected state
        return true;
    }

    /**
     * Sends informational emails to both the customer and the company after a successful approval.
     * The email content includes customer details, company name, number of shares, and amount paid.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        String custContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        customerEmail.setContent(custContent);
        emails.add(customerEmail);
        // Simulated sending (could be replaced with a real mail service)
        System.out.println("Sending email to customer (" + customerEmail.getReceiver() + "):\n"
                + customerEmail.getContent());

        // Email to company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        String compContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        companyEmail.setContent(compContent);
        emails.add(companyEmail);
        System.out.println("Sending email to company (" + companyEmail.getReceiver() + "):\n"
                + companyEmail.getContent());
    }

    /**
     * Sends a rejection email to the customer informing them that their application was not approved.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        String content = Email.createRejectionContent(customer, company);
        rejectionEmail.setContent(content);
        emails.add(rejectionEmail);
        System.out.println("Sending rejection email to customer (" + rejectionEmail.getReceiver() + "):\n"
                + rejectionEmail.getContent());
    }
}