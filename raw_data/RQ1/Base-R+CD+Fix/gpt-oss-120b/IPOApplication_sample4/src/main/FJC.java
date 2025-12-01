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
 * Simple placeholder class for a document that the customer uploads.
 */
class Document {
    /** No‑arg constructor required by the specification. */
    public Document() {
    }
}

/**
 * Represents a company that receives IPO applications.
 */
class Company {
    private String name;
    private String email;

    /** No‑arg constructor required by the specification. */
    public Company() {
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for email. */
    public String getEmail() {
        return email;
    }

    /** Setter for email. */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Represents an e‑mail message.
 */
class Email {
    private String receiver;
    private String content;

    /** No‑arg constructor required by the specification. */
    public Email() {
    }

    /** Getter for receiver. */
    public String getReceiver() {
        return receiver;
    }

    /** Setter for receiver. */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /** Getter for content. */
    public String getContent() {
        return content;
    }

    /** Setter for content. */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Creates the body of an informational e‑mail that is sent after a successful
     * application approval.
     *
     * @param customer the applicant
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   total amount of money paid
     * @return a formatted e‑mail body
     */
    public static String createEmailContent(Customer customer,
                                            Company company,
                                            int shares,
                                            double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(" ").append(customer.getSurname()).append(",\n\n")
          .append("Your IPO application has been processed.\n")
          .append("Details:\n")
          .append("Customer Name: ").append(customer.getName()).append(" ").append(customer.getSurname()).append('\n')
          .append("Email: ").append(customer.getEmail()).append('\n')
          .append("Telephone: ").append(customer.getTelephone()).append('\n')
          .append("Company: ").append(company.getName()).append('\n')
          .append("Shares Purchased: ").append(shares).append('\n')
          .append("Amount Paid: $").append(String.format("%.2f", amount)).append('\n')
          .append("\nThank you for using our services.");
        return sb.toString();
    }

    /**
     * Creates a simple rejection e‑mail content.
     *
     * @param customer the applicant
     * @param company  the target company
     * @return a formatted rejection e‑mail body
     */
    public static String createRejectionContent(Customer customer, Company company) {
        return "Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n"
                + "We regret to inform you that your IPO application to " + company.getName()
                + " has been rejected.\n"
                + "Please contact the bank for further details.\n\n"
                + "Best regards,\n"
                + "Bank IPO Department";
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
    private List<Email> emails;

    /** No‑arg constructor required by the specification. */
    public Application() {
        this.emails = new ArrayList<>();
    }

    /** Getter for share. */
    public int getShare() {
        return share;
    }

    /** Setter for share. */
    public void setShare(int share) {
        this.share = share;
    }

    /** Getter for amountOfMoney. */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /** Setter for amountOfMoney. */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /** Getter for status. */
    public ApplicationStatus getStatus() {
        return status;
    }

    /** Setter for status. */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /** Getter for customer. */
    public Customer getCustomer() {
        return customer;
    }

    /** Setter for customer. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** Getter for company. */
    public Company getCompany() {
        return company;
    }

    /** Setter for company. */
    public void setCompany(Company company) {
        this.company = company;
    }

    /** Getter for allowance. */
    public Document getAllowance() {
        return allowance;
    }

    /** Setter for allowance. */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /** Getter for emails. */
    public List<Email> getEmails() {
        return emails;
    }

    /** Setter for emails. */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    /**
     * Approves the application if it is pending and the customer is still eligible.
     *
     * @return {@code true} if the approval succeeds, {@code false} otherwise
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
     * Rejects the application if it is pending and notifies the customer.
     *
     * @return {@code true} if the rejection succeeds, {@code false} otherwise
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
     * Cancels a pending application. The cancellation simply changes the status to
     * {@code REJECTED} without sending any e‑mail.
     *
     * @return {@code true} if the cancellation succeeds, {@code false} otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.REJECTED;
        return true;
    }

    /**
     * Sends two informational e‑mails: one to the customer and one to the company.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        Email customerEmail = new Email();
        customerEmail.setReceiver(customer.getEmail());
        customerEmail.setContent(Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(customerEmail);

        // Email to company
        Email companyEmail = new Email();
        companyEmail.setReceiver(company.getEmail());
        companyEmail.setContent(Email.createEmailContent(customer, company, share, amountOfMoney));
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection e‑mail to the customer.
     */
    public void sendRejectionEmail() {
        Email rejectionEmail = new Email();
        rejectionEmail.setReceiver(customer.getEmail());
        rejectionEmail.setContent(Email.createRejectionContent(customer, company));
        emails.add(rejectionEmail);
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
    private boolean canApplyForIPO;
    private List<Application> applications;

    /** No‑arg constructor required by the specification. */
    public Customer() {
        this.applications = new ArrayList<>();
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for surname. */
    public String getSurname() {
        return surname;
    }

    /** Setter for surname. */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** Getter for email. */
    public String getEmail() {
        return email;
    }

    /** Setter for email. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Getter for telephone. */
    public String getTelephone() {
        return telephone;
    }

    /** Setter for telephone. */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /** Getter for canApplyForIPO. */
    public boolean isCanApplyForIPO() {
        return canApplyForIPO;
    }

    /** Setter for canApplyForIPO. */
    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    /** Getter for applications. */
    public List<Application> getApplications() {
        return applications;
    }

    /** Setter for applications. */
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
     * Creates a new IPO application if the customer satisfies all constraints.
     *
     * @param company the target company
     * @param shares  number of shares requested (must be &gt; 0)
     * @param amount  amount of money to be paid
     * @param doc     uploaded legal allowance document (must be non‑null)
     * @return {@code true} if the application was successfully created,
     *         {@code false} otherwise
     */
    public boolean createApplication(Company company,
                                      int shares,
                                      double amount,
                                      Document doc) {
        if (!isEligibleForIPO()) {
            return false;
        }
        if (shares <= 0) {
            return false;
        }
        if (doc == null) {
            return false;
        }
        // Ensure there is no already approved application for the same company
        for (Application a : applications) {
            if (a.getCompany() != null
                    && Objects.equals(a.getCompany().getName(), company.getName())
                    && a.getStatus() == ApplicationStatus.APPROVAL) {
                return false;
            }
        }
        Application app = new Application();
        app.setCompany(company);
        app.setCustomer(this);
        app.setShare(shares);
        app.setAmountOfMoney(amount);
        app.setAllowance(doc);
        app.setStatus(ApplicationStatus.PENDING);
        applications.add(app);
        return true;
    }

    /**
     * Returns the total number of processed applications (approved or rejected)
     * for this customer.
     *
     * @return count of processed applications, or 0 if none exist
     */
    public int getApplicationCount() {
        int count = 0;
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.APPROVAL
                    || a.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the sum of the monetary amounts of all approved applications.
     *
     * @return total amount of approved purchases, 0.0 if none are approved
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
     * Cancels a pending application for the given company name.
     *
     * @param companyName name of the company whose application should be cancelled
     * @return {@code true} if a pending application was found and cancelled,
     *         {@code false} otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application a : applications) {
            if (a.getStatus() == ApplicationStatus.PENDING
                    && a.getCompany() != null
                    && Objects.equals(a.getCompany().getName(), companyName)) {
                return a.cancel();
            }
        }
        return false;
    }
}