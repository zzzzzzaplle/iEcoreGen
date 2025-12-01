import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a retail or corporate customer that can apply for IPOs.
 */
 class Customer {

    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean retail;                     // true for retail customers
    private int failedAttempts;                 // number of failed attempts
    private boolean locked;                     // true if the customer is restricted
    private final List<IPOApplication> applications = new ArrayList<>();

    /** No‑argument constructor required for tests. */
    public Customer() {
    }

    // -------------------- Getters and Setters --------------------

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

    public boolean isRetail() {
        return retail;
    }

    public void setRetail(boolean retail) {
        this.retail = retail;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<IPOApplication> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    /**
     * Adds an application to the internal list.
     *
     * @param app the application to add
     */
    public void addApplication(IPOApplication app) {
        this.applications.add(app);
    }

    /**
     * Removes an application from the internal list.
     *
     * @param app the application to remove
     */
    public void removeApplication(IPOApplication app) {
        this.applications.remove(app);
    }
}

/**
 * Simple representation of a company that receives IPO applications.
 */
 class Company {

    private String name;

    /** No‑argument constructor required for tests. */
    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Status of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

/**
 * Represents an IPO application submitted by a customer.
 */
 class IPOApplication {

    private Customer customer;
    private Company company;
    private int numberOfShares;          // must be > 0
    private double amount;               // amount of money for the purchase
    private byte[] document;             // uploaded legal allowance documentation (non‑null)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    /** No‑argument constructor required for tests. */
    public IPOApplication() {
    }

    // -------------------- Getters and Setters --------------------

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

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
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
 * Service responsible for sending e‑mails.
 */
 class EmailService {

    /** No‑argument constructor required for tests. */
    public EmailService() {
    }

    /**
     * Sends an e‑mail.
     *
     * @param to      recipient e‑mail address
     * @param subject subject line
     * @param body    e‑mail body
     */
    public void sendEmail(String to, String subject, String body) {
        // In a real system this would integrate with an SMTP server.
        // For this exercise we simply print to the console.
        System.out.println("=== Sending e‑mail ===");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("======================");
    }
}

/**
 * Core service that implements all business rules concerning IPO applications.
 */
 class IPOService {

    private static final int MAX_FAILED_ATTEMPTS = 3; // after this the customer is locked

    private final EmailService emailService = new EmailService();

    /** No‑argument constructor required for tests. */
    public IPOService() {
    }

    /**
     * Creates a new IPO application.
     *
     * @param customer       the retail customer who applies
     * @param company        target company for the IPO
     * @param shares         number of shares requested (&gt; 0)
     * @param amount         amount of money to be paid
     * @param document       uploaded legal allowance documentation (must be non‑null)
     * @return {@code true} if the application was successfully created; {@code false}
     *         otherwise (e.g., ineligible customer, duplicate approved application, invalid data)
     */
    public boolean createApplication(Customer customer, Company company, int shares,
                                     double amount, byte[] document) {
        // Basic validation
        if (!customer.isRetail() || customer.isLocked()) {
            return false; // not eligible
        }
        if (shares <= 0 || document == null) {
            return false; // invalid request
        }

        // Ensure there is no already approved application for the same company
        for (IPOApplication a : customer.getApplications()) {
            if (a.getCompany().getName().equals(company.getName())
                    && a.getStatus() == ApplicationStatus.APPROVED) {
                return false; // duplicate approved application
            }
        }

        // Create and register the new application
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(shares);
        app.setAmount(amount);
        app.setDocument(document);
        app.setStatus(ApplicationStatus.PENDING);

        customer.addApplication(app);
        return true;
    }

    /**
     * Approves a pending application.
     *
     * @param customer the customer who submitted the application
     * @param company  the company for which the application was made
     * @return {@code true} if the application was approved and the two e‑mails were sent;
     *         {@code false} if the application could not be approved (e.g., not found,
     *         already processed, or customer ineligible)
     */
    public boolean approveApplication(Customer customer, Company company) {
        IPOApplication app = findPendingApplication(customer, company);
        if (app == null) {
            return false;
        }

        // Verify that the customer is still eligible
        if (!customer.isRetail() || customer.isLocked()) {
            return false;
        }

        // Approve the application
        app.setStatus(ApplicationStatus.APPROVED);

        // Send e‑mail to customer
        String customerBody = buildInformationEmailBody(customer, company,
                app.getNumberOfShares(), app.getAmount());
        emailService.sendEmail(customer.getEmail(),
                "IPO Application Approved",
                customerBody);

        // Send e‑mail to company (we assume the company has a generic contact address)
        String companyEmail = "contact@" + company.getName().toLowerCase().replaceAll("\\s+", "") + ".com";
        String companyBody = buildInformationEmailBody(customer, company,
                app.getNumberOfShares(), app.getAmount());
        emailService.sendEmail(companyEmail,
                "New IPO Application Received",
                companyBody);

        return true;
    }

    /**
     * Rejects a pending application.
     *
     * @param customer the customer who submitted the application
     * @param company  the company for which the application was made
     * @return {@code true} if the application was rejected and the rejection e‑mail was sent;
     *         {@code false} otherwise
     */
    public boolean rejectApplication(Customer customer, Company company) {
        IPOApplication app = findPendingApplication(customer, company);
        if (app == null) {
            return false;
        }

        app.setStatus(ApplicationStatus.REJECTED);
        customer.setFailedAttempts(customer.getFailedAttempts() + 1);

        // Lock the customer if failed attempts exceed the threshold
        if (customer.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
            customer.setLocked(true);
        }

        // Send rejection e‑mail to the customer
        String body = "Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n"
                + "We regret to inform you that your IPO application for company '"
                + company.getName() + "' has been rejected.\n"
                + "Please contact our call centre if you wish to discuss this further.\n\n"
                + "Best regards,\nIPO Department";
        emailService.sendEmail(customer.getEmail(),
                "IPO Application Rejected",
                body);

        return true;
    }

    /**
     * Retrieves the total number of reviewed applications (approved or rejected) for a customer.
     *
     * @param customer the customer whose summary is requested
     * @return the count of reviewed applications; {@code 0} if none exist
     */
    public int getApplicationCountSummary(Customer customer) {
        int count = 0;
        for (IPOApplication a : customer.getApplications()) {
            if (a.getStatus() == ApplicationStatus.APPROVED
                    || a.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the aggregate amount of money for all approved IPO applications of a customer.
     *
     * @param customer the customer whose approved total is requested
     * @return the sum of the {@code amount} fields of all approved applications; {@code 0.0}
     *         if there are none
     */
    public double getTotalApprovedAmount(Customer customer) {
        double sum = 0.0;
        for (IPOApplication a : customer.getApplications()) {
            if (a.getStatus() == ApplicationStatus.APPROVED) {
                sum += a.getAmount();
            }
        }
        return sum;
    }

    /**
     * Cancels a pending application.
     *
     * @param customer    the customer who wants to cancel
     * @param companyName the name of the company for which the pending application was made
     * @return {@code true} if a pending application was found and removed; {@code false}
     *         otherwise
     */
    public boolean cancelPendingApplication(Customer customer, String companyName) {
        for (IPOApplication a : new ArrayList<>(customer.getApplications())) {
            if (a.getCompany().getName().equals(companyName)
                    && a.getStatus() == ApplicationStatus.PENDING) {
                customer.removeApplication(a);
                return true;
            }
        }
        return false;
    }

    // -----------------------------------------------------------------
    // Helper methods (private)
    // -----------------------------------------------------------------

    /**
     * Finds a pending application for a given customer/company pair.
     *
     * @param customer the customer
     * @param company  the company
     * @return the pending {@link IPOApplication} if found; {@code null} otherwise
     */
    private IPOApplication findPendingApplication(Customer customer, Company company) {
        for (IPOApplication a : customer.getApplications()) {
            if (Objects.equals(a.getCompany().getName(), company.getName())
                    && a.getStatus() == ApplicationStatus.PENDING) {
                return a;
            }
        }
        return null;
    }

    /**
     * Builds the common e‑mail body used for both the customer and the company when an
     * application is approved.
     *
     * @param customer        the applicant
     * @param company         the target company
     * @param numberOfShares  number of shares purchased
     * @param amount          total amount paid
     * @return formatted e‑mail body text
     */
    private String buildInformationEmailBody(Customer customer, Company company,
                                              int numberOfShares, double amount) {
        return "Dear " + customer.getName() + " " + customer.getSurname() + ",\n\n"
                + "Your IPO application has been processed successfully.\n\n"
                + "Customer Details:\n"
                + "Name: " + customer.getName() + "\n"
                + "Surname: " + customer.getSurname() + "\n"
                + "Email: " + customer.getEmail() + "\n"
                + "Telephone: " + customer.getTelephone() + "\n\n"
                + "Applied Company: " + company.getName() + "\n"
                + "Number of Shares Purchased: " + numberOfShares + "\n"
                + "Amount Paid: $" + String.format("%.2f", amount) + "\n\n"
                + "Thank you for using our IPO service.\n"
                + "Best regards,\nIPO Department";
    }
}