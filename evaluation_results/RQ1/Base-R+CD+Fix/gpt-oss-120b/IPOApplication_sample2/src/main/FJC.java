import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration representing possible states of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVAL,
    REJECTED
}

/**
 * Simple placeholder class for a legal allowance document.
 */
class Document {
    // No fields required for the current domain model.
}

/**
 * Represents a company that can receive IPO applications.
 */
class Company {
    private String name;
    private String email;

    /** No‑arg constructor required by the specification. */
    public Company() {
    }

    /** Full constructor for convenience. */
    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /** @return the company's name */
    public String getName() {
        return name;
    }

    /** @param name the company's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the company's email address */
    public String getEmail() {
        return email;
    }

    /** @param email the company's email address to set */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Represents an e‑mail that can be sent to a customer or a company.
 */
class Email {
    private String receiver;
    private String content;

    /** No‑arg constructor required by the specification. */
    public Email() {
    }

    /** Full constructor for convenience. */
    public Email(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    /** @return the e‑mail receiver */
    public String getReceiver() {
        return receiver;
    }

    /** @param receiver the e‑mail receiver to set */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /** @return the e‑mail content */
    public String getContent() {
        return content;
    }

    /** @param content the e‑mail content to set */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Creates the textual content for an informational e‑mail that is sent after an
     * application is approved.
     *
     * @param customer the customer who applied
     * @param company  the target company
     * @param shares   number of shares purchased
     * @param amount   amount of money paid
     * @return a formatted string containing the required information
     */
    public static String createEmailContent(Customer customer,
                                            Company company,
                                            int shares,
                                            double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n");
        sb.append("Your IPO application has been approved.\n");
        sb.append("Details:\n");
        sb.append("Customer Name: ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n');
        sb.append("Email: ").append(customer.getEmail()).append('\n');
        sb.append("Telephone: ").append(customer.getTelephone()).append('\n');
        sb.append("Company Applied: ").append(company.getName()).append('\n');
        sb.append("Shares Purchased: ").append(shares).append('\n');
        sb.append("Amount Paid: $").append(String.format("%.2f", amount)).append('\n');
        sb.append("\nThank you for using our services.");
        return sb.toString();
    }

    /**
     * Creates the textual content for a rejection e‑mail.
     *
     * @param customer the customer whose application was rejected
     * @param company  the target company
     * @return a formatted rejection message
     */
    public static String createRejectionContent(Customer customer, Company company) {
        return "Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n"
                + "We regret to inform you that your IPO application to " + company.getName()
                + " has been rejected.\n\n"
                + "Please contact our call centre if you wish to discuss this decision.\n\n"
                + "Best regards,\n"
                + "Your Bank";
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

    /** No‑arg constructor required by the specification. */
    public Customer() {
    }

    /** Full constructor for convenience. */
    public Customer(String name, String surname, String email, String telephone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
    }

    /** @return true if the customer is still allowed to apply for IPOs */
    public boolean isEligibleForIPO() {
        return canApplyForIPO;
    }

    /** @param canApplyForIPO flag indicating if the customer may apply for IPOs */
    public void setCanApplyForIPO(boolean canApplyForIPO) {
        this.canApplyForIPO = canApplyForIPO;
    }

    /** @return the customer's name */
    public String getName() {
        return name;
    }

    /** @param name the customer's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the customer's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the customer's surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return the customer's email address */
    public String getEmail() {
        return email;
    }

    /** @param email the customer's email address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the customer's telephone number */
    public String getTelephone() {
        return telephone;
    }

    /** @param telephone the customer's telephone number to set */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /** @return the list of applications belonging to this customer */
    public List<Application> getApplications() {
        return applications;
    }

    /** @param applications the list of applications to set */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    /**
     * Creates a new IPO application for the given company.
     *
     * @param company the target company
     * @param shares  number of shares requested (>0)
     * @param amount  amount of money to be paid
     * @param doc     non‑null legal allowance document
     * @return true if the application was successfully created; false otherwise
     */
    public boolean createApplication(Company company,
                                      int shares,
                                      double amount,
                                      Document doc) {
        // Validate basic pre‑conditions
        if (!isEligibleForIPO()
                || shares <= 0
                || doc == null
                || company == null) {
            return false;
        }

        // Ensure there is no already approved application for the same company
        for (Application a : applications) {
            if (a.getCompany() != null
                    && a.getCompany().getName().equals(company.getName())
                    && a.getStatus() == ApplicationStatus.APPROVAL) {
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
     * Returns the total number of reviewed applications (approved or rejected)
     * filed by this customer.
     *
     * @return count of reviewed applications; 0 if none
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
     * Returns the sum of all approved application amounts for this customer.
     *
     * @return total amount of approved IPO purchases; 0.0 if none
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
     * @param companyName the name of the company whose pending application should be cancelled
     * @return true if a pending application was found and cancelled; false otherwise
     */
    public boolean cancelApplication(String companyName) {
        for (Application a : applications) {
            if (a.getCompany() != null
                    && a.getCompany().getName().equals(companyName)
                    && a.getStatus() == ApplicationStatus.PENDING) {
                return a.cancel();
            }
        }
        return false;
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

    /** No‑arg constructor required by the specification. */
    public Application() {
    }

    /** @return number of shares requested */
    public int getShare() {
        return share;
    }

    /** @param share number of shares to set */
    public void setShare(int share) {
        this.share = share;
    }

    /** @return amount of money attached to the application */
    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    /** @param amountOfMoney amount of money to set */
    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /** @return current status of the application */
    public ApplicationStatus getStatus() {
        return status;
    }

    /** @param status status to set */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /** @return customer who submitted the application */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return target company */
    public Company getCompany() {
        return company;
    }

    /** @param company company to set */
    public void setCompany(Company company) {
        this.company = company;
    }

    /** @return the uploaded legal allowance document */
    public Document getAllowance() {
        return allowance;
    }

    /** @param allowance document to set */
    public void setAllowance(Document allowance) {
        this.allowance = allowance;
    }

    /** @return list of e‑mails generated for this application */
    public List<Email> getEmails() {
        return emails;
    }

    /** @param emails list of e‑mails to set */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    /**
     * Approves the application after verifying the customer is still eligible.
     *
     * @return true if approval succeeded; false otherwise
     */
    public boolean approve() {
        if (customer == null || !customer.isEligibleForIPO()
                || status != ApplicationStatus.PENDING) {
            return false;
        }
        status = ApplicationStatus.APPROVAL;
        sendEmailsToCustomerAndCompany();
        return true;
    }

    /**
     * Rejects the application and notifies the customer via e‑mail.
     *
     * @return true if rejection succeeded; false otherwise
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
     * Cancels a pending application. No e‑mail is sent.
     *
     * @return true if the application was pending and is now cancelled; false otherwise
     */
    public boolean cancel() {
        if (status != ApplicationStatus.PENDING) {
            return false;
        }
        // For cancellation we simply mark it as REJECTED (as there is no separate status)
        status = ApplicationStatus.REJECTED;
        return true;
    }

    /**
     * Sends two informational e‑mails: one to the customer and one to the company,
     * containing the details required by the domain description.
     */
    public void sendEmailsToCustomerAndCompany() {
        // Email to customer
        String customerContent = Email.createEmailContent(customer, company, share, amountOfMoney);
        Email customerEmail = new Email(customer.getEmail(), customerContent);
        emails.add(customerEmail);

        // Email to company
        String companyContent = "New IPO application received:\n"
                + "Customer: " + customer.getName() + " " + customer.getSurname() + "\n"
                + "Email: " + customer.getEmail() + "\n"
                + "Telephone: " + customer.getTelephone() + "\n"
                + "Shares: " + share + "\n"
                + "Amount: $" + String.format("%.2f", amountOfMoney) + "\n";
        Email companyEmail = new Email(company.getEmail(), companyContent);
        emails.add(companyEmail);
    }

    /**
     * Sends a rejection e‑mail to the customer informing them of the decision.
     */
    public void sendRejectionEmail() {
        String content = Email.createRejectionContent(customer, company);
        Email rejectionEmail = new Email(customer.getEmail(), content);
        emails.add(rejectionEmail);
    }
}