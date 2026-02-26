import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company for which customers can apply IPO.
 */
class Company {
    private String name;

    /** Unparameterized constructor */
    public Company() {
    }

    /** Parameterized constructor for convenience */
    public Company(String name) {
        this.name = name;
    }

    /** @return the company name */
    public String getName() {
        return name;
    }

    /** @param name the company name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Possible states of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents a retail customer.
 * <p>
 * A customer is eligible for IPO applications unless restricted due to many failed attempts.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean eligible = true;               // true if the customer may apply
    private int failedAttempts = 0;                // count of failed attempts
    private boolean restricted = false;            // true when failedAttempts exceed a limit
    private List<IPOApplication> applications = new ArrayList<>();

    /** Unparameterized constructor */
    public Customer() {
    }

    /** @return customer's first name */
    public String getName() {
        return name;
    }

    /** @param name the customer's first name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return customer's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the customer's surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return customer's e‑mail address */
    public String getEmail() {
        return email;
    }

    /** @param email the customer's e‑mail address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return customer's telephone number */
    public String getTelephone() {
        return telephone;
    }

    /** @param telephone the customer's telephone number to set */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /** @return true if the customer is still eligible for IPOs */
    public boolean isEligible() {
        return eligible;
    }

    /** @param eligible set eligibility flag */
    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    /** @return number of failed attempts */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /** @param failedAttempts number of failed attempts to set */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
        evaluateRestriction();
    }

    /** @return true if the customer is restricted from applying */
    public boolean isRestricted() {
        return restricted;
    }

    /** @param restricted set restricted flag */
    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    /** @return list of all applications made by the customer */
    public List<IPOApplication> getApplications() {
        return applications;
    }

    /** @param applications the list of applications to set */
    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Increments the failed attempt counter and updates the restricted flag if needed.
     * The system restricts a customer after 3 failed attempts.
     */
    public void registerFailedAttempt() {
        this.failedAttempts++;
        evaluateRestriction();
    }

    /** Helper method to evaluate whether the customer should be restricted */
    private void evaluateRestriction() {
        this.restricted = this.failedAttempts >= 3;
    }
}

/**
 * Represents an IPO application made by a customer for a particular company.
 */
class IPOApplication {
    private Customer customer;
    private Company company;
    private int numberOfShares;
    private double amount;
    private String document;                 // representation of legal allowance documentation
    private ApplicationStatus status = ApplicationStatus.PENDING;

    /** Unparameterized constructor */
    public IPOApplication() {
    }

    /** @return the customer who created the application */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the customer to set */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return the target company */
    public Company getCompany() {
        return company;
    }

    /** @param company the target company to set */
    public void setCompany(Company company) {
        this.company = company;
    }

    /** @return number of shares requested */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /** @param numberOfShares number of shares to set */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /** @return monetary amount associated with the request */
    public double getAmount() {
        return amount;
    }

    /** @param amount monetary amount to set */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /** @return the uploaded legal document (non‑null) */
    public String getDocument() {
        return document;
    }

    /** @param document the legal document to set */
    public void setDocument(String document) {
        this.document = document;
    }

    /** @return current status of the application */
    public ApplicationStatus getStatus() {
        return status;
    }

    /** @param status the status to set */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Simple service that simulates sending e‑mail messages.
 * In a real system this would integrate with an e‑mail provider.
 */
class EmailService {

    /**
     * Sends an e‑mail.
     *
     * @param to      recipient address
     * @param subject e‑mail subject
     * @param body    e‑mail body
     */
    public void sendEmail(String to, String subject, String body) {
        // For demonstration, we simply print the e‑mail.
        System.out.println("=== Sending Email ===");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("=====================\n");
    }
}

/**
 * Core class that implements the business logic described in the domain.
 */
class Bank {

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private EmailService emailService = new EmailService();

    /** Unparameterized constructor */
    public Bank() {
    }

    /**
     * Creates a new IPO application for a retail customer.
     *
     * @param customer      the retail customer initiating the application
     * @param company       target company for the IPO
     * @param shares        number of shares requested (must be &gt; 0)
     * @param amount        monetary amount of the request
     * @param document      uploaded legal allowance document (non‑null)
     * @return {@code true} if the application was successfully created; {@code false} otherwise
     */
    public boolean createApplication(Customer customer, Company company, int shares,
                                     double amount, String document) {

        // Eligibility check
        if (!customer.isEligible() || customer.isRestricted()) {
            customer.registerFailedAttempt();
            return false;
        }

        // Basic validation
        if (shares <= 0 || document == null) {
            customer.registerFailedAttempt();
            return false;
        }

        // Ensure there is no already approved application for the same company
        boolean alreadyApproved = customer.getApplications().stream()
                .anyMatch(app -> app.getCompany().getName().equalsIgnoreCase(company.getName())
                        && app.getStatus() == ApplicationStatus.APPROVED);
        if (alreadyApproved) {
            customer.registerFailedAttempt();
            return false;
        }

        // Create and store the new application
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(shares);
        application.setAmount(amount);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);

        customer.getApplications().add(application);
        return true;
    }

    /**
     * Approves a pending IPO application for the given company.
     *
     * @param customer    the customer owning the application
     * @param companyName name of the target company
     * @return {@code true} if the application was approved and e‑mails sent; {@code false} otherwise
     */
    public boolean approveApplication(Customer customer, String companyName) {
        // Verify eligibility at moment of approval
        if (!customer.isEligible() || customer.isRestricted()) {
            return false;
        }

        IPOApplication pending = findPendingApplication(customer, companyName);
        if (pending == null) {
            return false;
        }

        pending.setStatus(ApplicationStatus.APPROVED);
        sendApprovalEmails(customer, pending);
        return true;
    }

    /**
     * Rejects a pending IPO application for the given company.
     *
     * @param customer    the customer owning the application
     * @param companyName name of the target company
     * @return {@code true} if the application was rejected and a notice e‑mailed; {@code false} otherwise
     */
    public boolean rejectApplication(Customer customer, String companyName) {
        IPOApplication pending = findPendingApplication(customer, companyName);
        if (pending == null) {
            return false;
        }

        pending.setStatus(ApplicationStatus.REJECTED);
        sendRejectionEmail(customer, pending);
        return true;
    }

    /**
     * Retrieves a summary of how many IPO applications a customer has filed (approved or rejected).
     *
     * @param customer the customer whose applications are counted
     * @return total number of reviewed applications (approved + rejected); {@code 0} if none
     */
    public int getApplicationCountSummary(Customer customer) {
        return (int) customer.getApplications().stream()
                .filter(app -> app.getStatus() != ApplicationStatus.PENDING)
                .count();
    }

    /**
     * Computes the aggregate monetary amount of all approved IPO applications for a customer.
     *
     * @param customer the customer whose approved amounts are summed
     * @return sum of the {@code amount} fields of approved applications; {@code 0.0} if none
     */
    public double getTotalApprovedAmount(Customer customer) {
        return customer.getApplications().stream()
                .filter(app -> app.getStatus() == ApplicationStatus.APPROVED)
                .mapToDouble(IPOApplication::getAmount)
                .sum();
    }

    /**
     * Cancels a pending IPO application for a specific company.
     *
     * @param customer    the customer owning the application
     * @param companyName name of the company whose pending application should be cancelled
     * @return {@code true} if the pending application was found and removed; {@code false} otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        IPOApplication pending = findPendingApplication(customer, companyName);
        if (pending == null) {
            return false;
        }
        return customer.getApplications().remove(pending);
    }

    /** Helper to locate a pending application for a given company */
    private IPOApplication findPendingApplication(Customer customer, String companyName) {
        return customer.getApplications().stream()
                .filter(app -> app.getCompany().getName().equalsIgnoreCase(companyName))
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
                .findFirst()
                .orElse(null);
    }

    /** Sends approval e‑mails to both customer and the company */
    private void sendApprovalEmails(Customer customer, IPOApplication app) {
        String subject = "IPO Application Approved";
        String body = buildInfoEmailBody(customer, app);
        // Email to customer
        emailService.sendEmail(customer.getEmail(), subject, body);
        // Email to company (using a placeholder address)
        String companyAddress = "info@" + app.getCompany().getName().toLowerCase().replaceAll("\\s+", "") + ".com";
        emailService.sendEmail(companyAddress, subject, body);
    }

    /** Sends a rejection notice to the customer */
    private void sendRejectionEmail(Customer customer, IPOApplication app) {
        String subject = "IPO Application Rejected";
        String body = "Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n"
                + "We regret to inform you that your IPO application for the company \""
                + app.getCompany().getName() + "\" has been rejected.\n\n"
                + "Regards,\nBank IPO Department";
        emailService.sendEmail(customer.getEmail(), subject, body);
    }

    /** Builds the common e‑mail body used for approved applications */
    private String buildInfoEmailBody(Customer customer, IPOApplication app) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(customer.getName()).append(' ').append(customer.getSurname()).append(",\n\n")
          .append("Your IPO application has been approved.\n\n")
          .append("Customer Information:\n")
          .append("Name: ").append(customer.getName()).append(' ').append(customer.getSurname()).append('\n')
          .append("Email: ").append(customer.getEmail()).append('\n')
          .append("Telephone: ").append(customer.getTelephone()).append("\n\n")
          .append("Application Details:\n")
          .append("Company: ").append(app.getCompany().getName()).append('\n')
          .append("Number of Shares: ").append(app.getNumberOfShares()).append('\n')
          .append("Amount Paid: $").append(String.format("%.2f", app.getAmount())).append("\n\n")
          .append("Thank you for using our services.\n")
          .append("Best regards,\nBank IPO Department");
        return sb.toString();
    }
}