import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration representing the possible states of an IPO application.
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
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;

    /** Un‑parameterized constructor required for tests and frameworks. */
    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true; // default eligibility
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Checks whether this customer is currently eligible to apply for an IPO.
     *
     * @return {@code true} if the customer can apply, {@code false} otherwise.
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application for the specified company.
     *
     * <p>The method validates the following rules before creating the application:
     * <ul>
     *   <li>The customer must be eligible to apply for IPOs.</li>
     *   <li>The number of shares must be greater than zero.</li>
     *   <li>The uploaded document must not be {@code null}.</li>
     *   <li>The customer must not already have an approved application for the same company.</li>
     * </ul>
     *
     * @param company the target company for the IPO application
     * @param shares  the number of shares requested (must be {@code > 0})
     * @param amount  the monetary amount associated with the application
     * @param doc     the legal allowance document (must be non‑null)
     * @return {@code true} if the application was successfully created; {@code false} otherwise.
     */
    public boolean createApplication(Company company, int shares, double amount, Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0) {
            return false;
        }
        if (doc == null) {
            return false;
        }
        // No approved application for the same company should exist
        for (Application app : applications) {
            if (app.getCompany() != null && Objects.equals(app.getCompany().getName(), company.getName())
                    && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        Application newApp = new Application();
        newApp.setCustomer(this);
        newApp.setCompany(company);
        newApp.setShare(shares);
        newApp.setAmountOfMoney(amount);
        newApp.setAllowance(doc);
        newApp.setStatus(ApplicationStatus.PENDING);
        applications.add(newApp);
        return true;
    }

    /**
     * Returns the total number of reviewed applications (approved or rejected) the customer has submitted.
     *
     * @return count of applications whose status is {@link ApplicationStatus#APPROVAL} or {@link ApplicationStatus#REJECTED}.
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
     * Calculates the aggregate amount of money of all approved IPO applications for this customer.
     *
     * @return sum of {@code amountOfMoney} for applications with status {@link ApplicationStatus#APPROVAL}.
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
     * @param companyName the name of the company whose pending application should be cancelled
     * @return {@code true} if a pending application was found and cancelled; {@code false} otherwise.
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

/**
 * Represents a company that receives IPO applications.
 */
class Company {
    private String name;
    private String email;

    /** Un‑parameterized constructor. */
    public Company() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
 * Placeholder class for a legal allowance document uploaded by a customer.
 */
class Document {
    /** Un‑parameterized constructor. */
    public Document() {
    }
}

/**
 * Represents an email that can be sent to a customer or a company.
 */
class Email {
    private String receiver;
    private String content;

    /** Un‑parameterized constructor. */
    public Email() {
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
     * Creates the email body content for an approved IPO application.
     *
     * @param customer the customer who applied
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   amount of money paid
     * @return a formatted string containing all required information.
     */
    public static String createEmailContent(Customer customer, Company company, int shares, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application has been processed.\n");
        sb.append("Customer Details:\n");
        sb.append("  Name: ").append(customer.getName()).append("\n");
        sb.append("  Surname: ").append(customer.getSurname()).append("\n");
        sb.append("  Email: ").append(customer.getEmail()).append("\n");
        sb.append("  Telephone: ").append(customer.getTelephone()).append("\n\n");
        sb.append("Company: ").append(company.getName()).append("\n");
        sb.append("Shares Purchased: ").append(shares).append("\n");
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append("\n");
        sb.append("\nThank you for using our services.");
        return sb.toString();
    }
}

/**
 * Represents an IPO application made by a customer to a company.
 */
class Application {
    private int share;
    private double amountOfMoney;
    private ApplicationStatus status;
    private Customer customer;
    private Company company;
    private Document allowance;
    private List<Email> emails;

    /** Un‑parameterized constructor. */
    public Application() {
        this.emails = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Business methods
    // -------------------------------------------------------------------------

    /**
     * Approves the application if the customer is still eligible to apply.
     *
     * <p>When approved, the status is set to {@link ApplicationStatus#APPROVAL} and
     * two informational emails (one to the customer, one to the company) are sent.
     *
     * @return {@code true} if the approval succeeded; {@code false} otherwise.
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
     * Rejects the application and notifies the customer via email.
     *
     * @return {@code true} if the rejection succeeded; {@code false} otherwise.
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
     * Cancels a pending application. The status is changed to {@link ApplicationStatus#REJECTED}
     * (as a simple way to mark it as no longer active). No email is sent.
     *
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise.
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        this.status = ApplicationStatus.REJECTED;
        // No email is generated for a manual cancellation.
        return true;
    }

    /**
     * Sends two informational emails – one to the customer and one to the company –
     * after a successful approval.
     *
     * The content of each email is built using {@link Email#createEmailContent(Customer, Company, int, double)}.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to Customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(customerEmail);

        // Email to Company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection email to the customer informing them of the outcome.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application to ").append(company.getName())
          .append(" has been rejected.\n");
        sb.append("Please contact the company directly for further details.\n");
        sb.append("\nRegards,\nBank IPO Team");
        rejectionEmail.setContent(sb.toString());
        emails.add(rejectionEmail);
    }
}