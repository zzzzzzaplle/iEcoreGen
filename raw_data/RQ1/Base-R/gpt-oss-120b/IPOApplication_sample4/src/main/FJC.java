import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a retail customer that can apply for IPOs.
 */
 class Customer {

    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean eligible = true;
    private int failedAttempts = 0;
    private List<IPOApplication> applications = new ArrayList<>();

    /** Unparameterized constructor. */
    public Customer() {
    }

    /* --------------------- Getters & Setters --------------------- */

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

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /**
     * Increments the number of failed attempts and updates eligibility.
     * Eligibility becomes false when the number of failed attempts reaches the limit.
     *
     * @param limit the maximum allowed failed attempts before the customer is blocked
     */
    public void recordFailedAttempt(int limit) {
        this.failedAttempts++;
        if (this.failedAttempts >= limit) {
            this.eligible = false;
        }
    }

    /**
     * Resets the failed attempts counter and restores eligibility.
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
        this.eligible = true;
    }
}

/**
 * Represents a company whose shares are offered in an IPO.
 */
class Company {

    private String name;

    /** Unparameterized constructor. */
    public Company() {
    }

    /* --------------------- Getters & Setters --------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Simple placeholder for a legal allowance document that must be uploaded.
 */
class Document {

    private String fileName; // just an identifier, could be path, etc.

    /** Unparameterized constructor. */
    public Document() {
    }

    /* --------------------- Getters & Setters --------------------- */

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

/**
 * Enum representing the possible status of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents a single IPO application made by a customer for a company.
 */
class IPOApplication {

    private Customer customer;
    private Company company;
    private int shares;
    private double amount;
    private Document document;
    private ApplicationStatus status = ApplicationStatus.PENDING;

    /** Unparameterized constructor. */
    public IPOApplication() {
    }

    /* --------------------- Getters & Setters --------------------- */

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

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

/**
 * Core class that encapsulates the IPO application workflow,
 * email notifications and business rules.
 */
class IPOSystem {

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private List<IPOApplication> allApplications = new ArrayList<>();

    /** Unparameterized constructor. */
    public IPOSystem() {
    }

    /* --------------------- Getters & Setters --------------------- */

    public List<IPOApplication> getAllApplications() {
        return allApplications;
    }

    public void setAllApplications(List<IPOApplication> allApplications) {
        this.allApplications = allApplications;
    }

    /**
     * Creates a new IPO application for a retail customer.
     *
     * @param customer   the retail customer initiating the application
     * @param company    the target company for the IPO
     * @param shares     number of shares requested (must be &gt; 0)
     * @param amount     monetary amount for the purchase
     * @param document   legal allowance documentation (must not be {@code null})
     * @return {@code true} if the application was successfully created; {@code false} otherwise
     */
    public boolean createIPOApplication(Customer customer, Company company,
                                        int shares, double amount, Document document) {
        // Validate input
        if (customer == null || company == null || document == null) {
            return false;
        }
        if (!customer.isEligible()) {
            return false;
        }
        if (shares <= 0) {
            return false;
        }

        // Ensure there is no already approved application for the same company
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompany().getName().equalsIgnoreCase(company.getName())
                    && app.getStatus() == ApplicationStatus.APPROVED) {
                return false; // duplicate approved application
            }
        }

        // Create and store the new application
        IPOApplication newApp = new IPOApplication();
        newApp.setCustomer(customer);
        newApp.setCompany(company);
        newApp.setShares(shares);
        newApp.setAmount(amount);
        newApp.setDocument(document);
        newApp.setStatus(ApplicationStatus.PENDING);

        customer.getApplications().add(newApp);
        allApplications.add(newApp);
        return true;
    }

    /**
     * Approves a pending IPO application.
     *
     * @param customer   the customer who filed the application
     * @param companyName the name of the company for which the application was made
     * @return {@code true} if the approval succeeded; {@code false} otherwise
     */
    public boolean approveApplication(Customer customer, String companyName) {
        IPOApplication app = findPendingApplication(customer, companyName);
        if (app == null) {
            return false;
        }

        // Verify that the customer is still eligible to apply
        if (!customer.isEligible()) {
            return false;
        }

        app.setStatus(ApplicationStatus.APPROVED);
        sendInfoEmailToCustomer(app);
        sendInfoEmailToCompany(app);
        return true;
    }

    /**
     * Rejects a pending IPO application and notifies the customer.
     *
     * @param customer    the customer who filed the application
     * @param companyName the name of the company for which the application was made
     * @return {@code true} if the rejection succeeded; {@code false} otherwise
     */
    public boolean rejectApplication(Customer customer, String companyName) {
        IPOApplication app = findPendingApplication(customer, companyName);
        if (app == null) {
            return false;
        }

        app.setStatus(ApplicationStatus.REJECTED);
        // Record a failed attempt for the customer
        customer.recordFailedAttempt(MAX_FAILED_ATTEMPTS);
        sendRejectionEmailToCustomer(app);
        return true;
    }

    /**
     * Retrieves the total number of reviewed (approved or rejected) applications
     * filed by a customer.
     *
     * @param customer the customer whose applications are to be counted
     * @return the number of reviewed applications; {@code 0} if none exist
     */
    public int getApplicationCountSummary(Customer customer) {
        int count = 0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED ||
                app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the aggregate monetary amount of all approved IPO applications
     * for a given customer.
     *
     * @param customer the customer whose approved amounts are summed
     * @return the sum of the amounts of all approved applications (0.0 if none)
     */
    public double getTotalApprovedAmount(Customer customer) {
        double total = 0.0;
        for (IPOApplication app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancels a pending application for a specific company.
     *
     * @param customer    the customer who wants to cancel the application
     * @param companyName the name of the company whose application is to be cancelled
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        IPOApplication app = findPendingApplication(customer, companyName);
        if (app == null) {
            return false;
        }
        // Remove from both lists
        customer.getApplications().remove(app);
        allApplications.remove(app);
        return true;
    }

    /* --------------------- Helper Methods --------------------- */

    /**
     * Finds a pending application for a given customer and company name.
     *
     * @param customer    the customer owning the application
     * @param companyName the target company name
     * @return the pending {@link IPOApplication} if found; {@code null} otherwise
     */
    private IPOApplication findPendingApplication(Customer customer, String companyName) {
        for (IPOApplication app : customer.getApplications()) {
            if (app.getCompany().getName().equalsIgnoreCase(companyName)
                    && app.getStatus() == ApplicationStatus.PENDING) {
                return app;
            }
        }
        return null;
    }

    /**
     * Sends an informational email to the customer after a successful approval.
     *
     * @param app the approved application
     */
    private void sendInfoEmailToCustomer(IPOApplication app) {
        String content = buildInfoEmailContent(app);
        // Placeholder for actual email sending logic
        System.out.println("=== Email to Customer ===");
        System.out.println("To: " + app.getCustomer().getEmail());
        System.out.println(content);
        System.out.println("=========================\n");
    }

    /**
     * Sends an informational email to the company after a successful approval.
     *
     * @param app the approved application
     */
    private void sendInfoEmailToCompany(IPOApplication app) {
        String content = buildInfoEmailContent(app);
        // Placeholder for actual email sending logic
        System.out.println("=== Email to Company ===");
        System.out.println("To: " + app.getCompany().getName() + "@company.com");
        System.out.println(content);
        System.out.println("=========================\n");
    }

    /**
     * Sends a rejection notice email to the customer.
     *
     * @param app the rejected application
     */
    private void sendRejectionEmailToCustomer(IPOApplication app) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(app.getCustomer().getName()).append(' ')
          .append(app.getCustomer().getSurname()).append(",\n\n")
          .append("We regret to inform you that your IPO application for company ")
          .append(app.getCompany().getName()).append(" has been REJECTED.\n")
          .append("Please contact the call centre if you wish to discuss the matter.\n\n")
          .append("Best regards,\n")
          .append("Your Bank");
        // Placeholder for actual email sending logic
        System.out.println("=== Rejection Email to Customer ===");
        System.out.println("To: " + app.getCustomer().getEmail());
        System.out.println(sb.toString());
        System.out.println("====================================\n");
    }

    /**
     * Builds the common email content used for both customer and company notifications.
     *
     * @param app the approved application
     * @return a formatted email body string
     */
    private String buildInfoEmailContent(IPOApplication app) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(app.getCustomer().getName()).append(' ')
          .append(app.getCustomer().getSurname()).append(",\n\n")
          .append("Your IPO application has been APPROVED.\n\n")
          .append("Customer Details:\n")
          .append("Name: ").append(app.getCustomer().getName()).append(' ')
          .append(app.getCustomer().getSurname()).append('\n')
          .append("Email: ").append(app.getCustomer().getEmail()).append('\n')
          .append("Telephone: ").append(app.getCustomer().getTelephone()).append("\n\n")
          .append("Applied Company: ").append(app.getCompany().getName()).append('\n')
          .append("Number of Shares Purchased: ").append(app.getShares()).append('\n')
          .append("Amount Paid: $").append(String.format("%.2f", app.getAmount())).append("\n\n")
          .append("Thank you for using our services.\n")
          .append("Best regards,\n")
          .append("Your Bank");
        return sb.toString();
    }
}