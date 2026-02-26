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
 * Simple placeholder class for a document that a customer uploads when applying for an IPO.
 */
class Document {
    // In a real system this class would contain the document's data, type, size, etc.
    public Document() {
        // no‑arg constructor
    }
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

    /** Full constructor */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /* ---------- Getters & Setters ---------- */
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
 * Simple class modelling an e‑mail. In this prototype the e‑mail is only stored
 * in memory; sending is simulated by printing to the console.
 */
class Email {
    private String receiver;
    private String content;

    /** No‑arg constructor */
    public Email() {
    }

    /** Full constructor */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /* ---------- Getters & Setters ---------- */
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
     * Generates the e‑mail body used for the two informational e‑mails that are
     * sent when an application is approved.
     *
     * @param customer the applicant
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   total amount of money paid
     * @return a formatted string containing all required information
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
        sb.append("\nThank you for using our services.\n");
        return sb.toString();
    }
}

/**
 * Represents a single IPO application.
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
    }

    /** Full constructor */
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

    /* ---------- Getters & Setters ---------- */
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
     * Approves the application if it is still pending and the customer is still
     * eligible to apply for IPOs.
     *
     * @return {@code true} if the approval succeeded, {@code false} otherwise
     */
    public boolean approve() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        if (!customer.isEligibleForIPO()) {
            return false;
        }
        status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application if it is still pending and notifies the customer.
     *
     * @return {@code true} if the rejection succeeded, {@code false} otherwise
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
     * Cancels a pending application. The cancellation does not generate any e‑mail.
     *
     * @return {@code true} if the cancellation succeeded, {@code false} otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED; // treated as a rejected state
        return true;
    }

    /**
     * Sends two informational e‑mails: one to the customer and one to the target
     * company. The e‑mail content includes the customer's data, the company name,
     * the number of shares purchased and the amount paid.
     */
    public void sendEmailsToCustomerAndCompany() {
        // email to customer
        String customerContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email toCustomer = new Email(customer.getEmail(), customerContent);
        emails.add(toCustomer);
        System.out.println("Sending email to customer (" + toCustomer.getReceiver() + "):");
        System.out.println(toCustomer.getContent());

        // email to company
        String companyContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email toCompany = new Email(company.getEmail(), companyContent);
        emails.add(toCompany);
        System.out.println("Sending email to company (" + toCompany.getReceiver() + "):");
        System.out.println(toCompany.getContent());
    }

    /**
     * Sends a rejection e‑mail to the customer informing him/her that the
     * application has been rejected.
     */
    public void sendRejectionEmail() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("We regret to inform you that your IPO application for company ")
          .append(company.getName()).append(" has been rejected.\n");
        sb.append("If you have any questions, please contact our support centre.\n\n");
        sb.append("Best regards,\nThe IPO Team");
        Email rejection = new Email(customer.getEmail(), sb.toString());
        emails.add(rejection);
        System.out.println("Sending rejection email to customer (" + rejection.getReceiver() + "):");
        System.out.println(rejection.getContent());
    }
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

    /** No‑arg constructor */
    public Customer() {
    }

    /** Full constructor */
    public Customer(String name,
                    String surname,
                    String email,
                    String telephone,
                    boolean canApplyForIPO) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.canApplyForIPO = canApplyForIPO;
    }

    /* ---------- Getters & Setters ---------- */
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
     * Checks whether the customer is currently eligible to apply for an IPO.
     *
     * @return {@code true} if the customer can apply, {@code false} otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /**
     * Creates a new IPO application if the customer satisfies all business
     * constraints.
     *
     * @param company the target company
     * @param shares  number of shares requested (must be &gt; 0)
     * @param amount  total amount of money for the purchase
     * @param doc     the uploaded legal allowance document (must be non‑null)
     * @return {@code true} if the application was successfully created,
     *         {@code false} otherwise
     */
    public boolean createApplication(Company company,
                                      int shares,
                                      double amount,
                                      Document doc) {
        // basic validation
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0 || doc == null || company == null) {
            return false;
        }
        // ensure there is no already approved application for the same company
        for (Application app : applications) {
            if (Objects.equals(app.getCompany().getName(), company.getName())
                    && app.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        // create and store the new application
        Application newApp = new Application(shares, amount, this, company, doc);
        applications.add(newApp);
        return true;
    }

    /**
     * Returns the total number of applications that have been reviewed
     * (approved or rejected). Pending applications are not counted.
     *
     * @return the count of reviewed applications, or 0 if none exist
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
     * @return sum of {@code amountOfMoney} for approved applications,
     *         or 0.0 if there are none
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
     * @param companyName the name of the company whose application should be cancelled
     * @return {@code true} if a pending application was found and cancelled,
     *         {@code false} otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application app : applications) {
            if (Objects.equals(app.getCompany().getName(), companyName)
                    && app.getStatus() == ApplicationStatus.PENDING) {
                return app.cancel();
            }
        }
        return false;
    }
}