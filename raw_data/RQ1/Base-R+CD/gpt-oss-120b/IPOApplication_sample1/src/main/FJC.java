import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enum representing the possible statuses of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Represents a retail customer who can apply for IPOs.
 */
class Customer {

    /* ==================== Fields ==================== */
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;

    /* ==================== Constructors ==================== */

    /** Default constructor required for frameworks and testing. */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // By default a customer is eligible
    }

    /* ==================== Getters / Setters ==================== */

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

    /* ==================== Business Methods ==================== */

    /**
     * Determines whether the customer is currently eligible to apply for an IPO.
     *
     * @return {@code true} if the customer can apply; {@code false} otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the given company.
     * <p>
     * The method checks:
     * <ul>
     *   <li>the customer is eligible to apply,</li>
     *   <li>the number of shares is greater than zero,</li>
     *   <li>the provided document is not {@code null},</li>
     *   <li>the customer does not already have an approved application for the same company.</li>
     * </ul>
     * If all checks pass, a new {@link Application} with status {@link ApplicationStatus#PENDING}
     * is created, stored in the customer's application list and {@code true} is returned.
     *
     * @param company the target company for the IPO application
     * @param shares  number of shares requested (must be > 0)
     * @param amount  monetary amount of the application
     * @param doc     the legal allowance document (must be non‑null)
     * @return {@code true} if the application was successfully created; {@code false} otherwise
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0 || doc == null || company == null) {
            return false;
        }
        // ensure no already approved application for the same company
        for (Application a : applications) {
            if (a.getCompany() != null && a.getCompany().getName().equals(company.getName())
                    && a.getStatus() == ApplicationStatus.APPROVAL) {
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
     * Returns the total number of reviewed applications (approved or rejected) filed by this customer.
     *
     * @return count of reviewed applications; {@code 0} if none exist
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
     * Returns the aggregated monetary amount of all approved applications for this customer.
     *
     * @return sum of amounts of approved applications; {@code 0.0} if none are approved
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
     * Cancels a pending application for the specified company name.
     *
     * @param companyName name of the company whose pending application should be cancelled
     * @return {@code true} if a pending application was found and cancelled; {@code false} otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application a : applications) {
            if (a.getCompany() != null && a.getCompany().getName().equals(companyName)
                    && a.getStatus() == ApplicationStatus.PENDING) {
                return a.cancel();
            }
        }
        return false;
    }
}

/**
 * Represents a company that can receive IPO applications.
 */
class Company {

    private String name;
    private String email;

    /** Default constructor. */
    public Company() {
    }

    /* Getters / Setters */

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
 * Placeholder class for a legal allowance document.
 */
class Document {

    /** Default constructor. */
    public Document() {
    }

    // In a real system this would contain file data, metadata, etc.
}

/**
 * Represents an email that will be sent to a receiver.
 */
class Email {

    private String receiver;
    private String content;

    /** Default constructor. */
    public Email() {
    }

    /* Getters / Setters */

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
     * Generates the standard email content for a successful IPO application.
     *
     * @param customer the applicant
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   amount of money paid
     * @return a formatted email body containing the required information
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application has been approved.\n");
        sb.append("Details:\n");
        sb.append("Customer Name: ").append(customer.getName()).append("\n");
        sb.append("Surname: ").append(customer.getSurname()).append("\n");
        sb.append("Email: ").append(customer.getEmail()).append("\n");
        sb.append("Telephone: ").append(customer.getTelephone()).append("\n");
        sb.append("Company: ").append(company.getName()).append("\n");
        sb.append("Shares Purchased: ").append(shares).append("\n");
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append("\n\n");
        sb.append("Thank you for using our services.\n");
        return sb.toString();
    }

    /**
     * Generates the email content for a rejection notice.
     *
     * @param customer the applicant
     * @param company  the target company
     * @return a formatted rejection email body
     */
    public static String createRejectionContent(Customer customer, Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application to ").append(company.getName())
                .append(" has been rejected.\n");
        sb.append("Please contact the company for further details.\n\n");
        sb.append("Best regards,\n");
        sb.append("Your Bank");
        return sb.toString();
    }
}

/**
 * Represents an IPO application submitted by a customer for a particular company.
 */
class Application {

    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    /** Default constructor required for testing and framework usage. */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /* Getters / Setters */

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

    /* ==================== Business Methods ==================== */

    /**
     * Approves this application if the customer is still eligible to apply and the application is pending.
     * Sends the required information emails to both the customer and the company.
     *
     * @return {@code true} if the approval succeeded; {@code false} otherwise
     */
    public boolean approve() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        if (customer == null || !customer.isEligibleForIPO()) {
            return false;
        }
        status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects this application if it is currently pending.
     * Sends a rejection email to the customer.
     *
     * @return {@code true} if the rejection succeeded; {@code false} otherwise
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
     * Cancels a pending application. The application status will be set to {@link ApplicationStatus#REJECTED}
     * without sending any email notifications.
     *
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED; // considered cancelled
        // No emails are sent on a manual cancellation.
        return true;
    }

    /**
     * Sends two informational emails: one to the customer and one to the company,
     * containing details of the approved IPO application.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to Customer
        Email emailToCustomer = new Email();
        emailToCustomer.setReceiver(customer.getEmail());
        emailToCustomer.setContent(
                Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(emailToCustomer);

        // Email to Company
        Email emailToCompany = new Email();
        emailToCompany.setReceiver(company.getEmail());
        emailToCompany.setContent(
                Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(emailToCompany);

        // In a real system, here we would invoke an email service to actually send the messages.
    }

    /**
     * Sends a rejection notification email to the customer.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent(
                Email.createRejectionContent(customer, company));
        emails.add(rejectionEmail);

        // In a real system, the email would be dispatched via an email service.
    }
}