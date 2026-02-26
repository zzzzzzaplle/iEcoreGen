import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a simple document that a customer uploads when applying for an IPO.
 */
class Document {
    private String fileName;
    private byte[] content;

    public Document() {
    }

    public Document(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

/**
 * Simple email service used by the system to send notifications.
 */
class EmailService {
    public EmailService() {
    }

    /**
     * Sends an e‑mail.
     *
     * @param to      recipient e‑mail address
     * @param subject e‑mail subject line
     * @param body    e‑mail body text
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
 * Represents a retail customer that can apply for IPOs.
 */
class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private List<IPOApplication> applications;
    private int failedAttempts;               // number of rejected applications
    private boolean restricted;               // true if the bank blocked further applications

    private static final int MAX_FAILED_ATTEMPTS = 3; // threshold after which the customer gets restricted

    public Customer() {
        this.applications = new ArrayList<>();
        this.failedAttempts = 0;
        this.restricted = false;
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

    public List<IPOApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    /* ---------- Business Methods ---------- */

    /**
     * Creates a new IPO application for the given company.
     *
     * @param companyName name of the target company
     * @param shares      number of shares the customer wants to purchase (must be > 0)
     * @param amount      total amount of money to be paid
     * @param document    legal allowance documentation (must be non‑null)
     * @return {@code true} if the application was successfully created; {@code false} otherwise
     */
    public boolean createIPOApplication(String companyName, int shares, double amount, Document document) {
        if (restricted) {
            System.out.println("Customer is restricted from applying due to many failed attempts.");
            return false;
        }
        if (shares <= 0) {
            System.out.println("Number of shares must be greater than zero.");
            return false;
        }
        if (document == null) {
            System.out.println("Document must be provided.");
            return false;
        }
        // check there is no already approved application for the same company
        boolean alreadyApproved = applications.stream()
                .anyMatch(app -> app.getCompanyName().equalsIgnoreCase(companyName)
                        && app.getStatus() == IPOApplication.Status.APPROVED);
        if (alreadyApproved) {
            System.out.println("An approved application for this company already exists.");
            return false;
        }

        IPOApplication newApp = new IPOApplication();
        newApp.setCustomer(this);
        newApp.setCompanyName(companyName);
        newApp.setShares(shares);
        newApp.setAmount(amount);
        newApp.setDocument(document);
        newApp.setStatus(IPOApplication.Status.PENDING);
        applications.add(newApp);
        System.out.println("IPO application created successfully.");
        return true;
    }

    /**
     * Returns the total number of IPO applications that have been reviewed (approved or rejected).
     *
     * @return count of reviewed applications; {@code 0} if none exist
     */
    public int getApplicationCountSummary() {
        return (int) applications.stream()
                .filter(app -> app.getStatus() == IPOApplication.Status.APPROVED
                        || app.getStatus() == IPOApplication.Status.REJECTED)
                .count();
    }

    /**
     * Calculates the total amount of money of all approved IPO applications.
     *
     * @return sum of the {@code amount} fields for approved applications; {@code 0.0} if none exist
     */
    public double getTotalApprovedAmount() {
        return applications.stream()
                .filter(app -> app.getStatus() == IPOApplication.Status.APPROVED)
                .mapToDouble(IPOApplication::getAmount)
                .sum();
    }

    /**
     * Cancels a pending application for a given company.
     *
     * @param companyName name of the company whose pending application should be cancelled
     * @return {@code true} if a pending application was found and cancelled; {@code false} otherwise
     */
    public boolean cancelPendingApplication(String companyName) {
        for (IPOApplication app : applications) {
            if (app.getCompanyName().equalsIgnoreCase(companyName)
                    && app.getStatus() == IPOApplication.Status.PENDING) {
                app.setStatus(IPOApplication.Status.CANCELED);
                System.out.println("Pending application for company '" + companyName + "' cancelled.");
                return true;
            }
        }
        System.out.println("No pending application found for company '" + companyName + "'.");
        return false;
    }

    /**
     * Internal helper called by the bank when an application is rejected.
     *
     * @param app the rejected application
     */
    void handleRejection(IPOApplication app) {
        failedAttempts++;
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            restricted = true;
        }
    }

    /**
     * Internal helper called by the bank when an application is approved.
     *
     * @param app the approved application
     */
    void handleApproval(IPOApplication app) {
        // Reset the failure counter – a successful approval lifts the restriction.
        failedAttempts = 0;
        restricted = false;
    }
}

/**
 * Represents a single IPO application made by a customer.
 */
class IPOApplication {
 enum Status {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELED
    }

    private Customer customer;
    private String companyName;
    private int shares;
    private double amount;
    private Document document;
    private Status status;

    public IPOApplication() {
    }

    /* ---------- Getters & Setters ---------- */

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

/**
 * Core service that processes IPO applications, approvals, rejections and sends e‑mails.
 */
class Bank {
    private EmailService emailService;
    private List<Customer> customers; // registry of known customers (for demonstration)

    public Bank() {
        this.emailService = new EmailService();
        this.customers = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Approves or rejects a given IPO application.
     *
     * @param app      the application to process
     * @param approve  {@code true} to approve; {@code false} to reject
     * @return {@code true} if the operation succeeded; {@code false} otherwise
     */
    public boolean approveOrRejectApplication(IPOApplication app, boolean approve) {
        if (app == null) {
            System.out.println("Application is null.");
            return false;
        }
        if (app.getStatus() != IPOApplication.Status.PENDING) {
            System.out.println("Only pending applications can be processed.");
            return false;
        }

        Customer customer = app.getCustomer();
        if (customer == null) {
            System.out.println("Application has no associated customer.");
            return false;
        }

        // Re‑check eligibility before approving
        if (approve) {
            if (customer.isRestricted()) {
                System.out.println("Customer is currently restricted and cannot be approved.");
                return false;
            }

            // Approve
            app.setStatus(IPOApplication.Status.APPROVED);
            customer.handleApproval(app);
            sendInformationEmails(app);
            System.out.println("Application approved and information e‑mails sent.");
            return true;
        } else {
            // Reject
            app.setStatus(IPOApplication.Status.REJECTED);
            customer.handleRejection(app);
            sendRejectionEmail(app);
            System.out.println("Application rejected and rejection e‑mail sent.");
            return true;
        }
    }

    /**
     * Sends two information e‑mails (to customer and to the company) after a successful approval.
     *
     * @param app the approved application
     */
    private void sendInformationEmails(IPOApplication app) {
        Customer cust = app.getCustomer();

        String commonBody = buildEmailBody(cust, app);

        // Email to the customer
        String subjectCustomer = "Your IPO application for " + app.getCompanyName() + " has been approved";
        emailService.sendEmail(cust.getEmail(), subjectCustomer, commonBody);

        // Email to the company (using a placeholder address)
        String companyEmail = "ipo@" + app.getCompanyName().toLowerCase().replaceAll("\\s+", "") + ".com";
        String subjectCompany = "New IPO application from " + cust.getName() + " " + cust.getSurname();
        emailService.sendEmail(companyEmail, subjectCompany, commonBody);
    }

    /**
     * Sends a rejection notice to the customer.
     *
     * @param app the rejected application
     */
    private void sendRejectionEmail(IPOApplication app) {
        Customer cust = app.getCustomer();
        String subject = "Your IPO application for " + app.getCompanyName() + " was rejected";
        String body = "Dear " + cust.getName() + " " + cust.getSurname() + ",\n\n"
                + "We regret to inform you that your IPO application for the company '"
                + app.getCompanyName() + "' has been rejected.\n"
                + "Please contact the call centre if you wish to request further access.\n\n"
                + "Best regards,\n"
                + "Your Bank";
        emailService.sendEmail(cust.getEmail(), subject, body);
    }

    /**
     * Builds the common email body used for both customer and company notifications.
     *
     * @param cust the applicant
     * @param app  the approved application
     * @return the formatted e‑mail body
     */
    private String buildEmailBody(Customer cust, IPOApplication app) {
        return "Dear " + cust.getName() + " " + cust.getSurname() + ",\n\n"
                + "Your IPO application has been approved with the following details:\n"
                + "Customer Name : " + cust.getName() + " " + cust.getSurname() + "\n"
                + "Email          : " + cust.getEmail() + "\n"
                + "Telephone      : " + cust.getTelephone() + "\n"
                + "Company        : " + app.getCompanyName() + "\n"
                + "Shares         : " + app.getShares() + "\n"
                + "Amount Paid    : $" + String.format("%.2f", app.getAmount()) + "\n\n"
                + "Thank you for using our services.\n"
                + "Best regards,\n"
                + "Your Bank";
    }

    /**
     * Registers a new customer in the bank system.
     *
     * @param customer the customer to add
     */
    public void registerCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
        }
    }

    /**
     * Retrieves a customer by e‑mail address.
     *
     * @param email the e‑mail address to look for
     * @return the matching {@link Customer} or {@code null} if not found
     */
    public Customer findCustomerByEmail(String email) {
        for (Customer c : customers) {
            if (Objects.equals(c.getEmail(), email)) {
                return c;
            }
        }
        return null;
    }
}

/* --------------------------------------------------------------
   Example usage (for illustration only; not part of the required API)
   --------------------------------------------------------------
 class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // create a customer
        Customer alice = new Customer();
        alice.setName("Alice");
        alice.setSurname("Smith");
        alice.setEmail("alice.smith@example.com");
        alice.setTelephone("+1-555-1234");
        bank.registerCustomer(alice);

        // create a document
        Document doc = new Document();
        doc.setFileName("allowance.pdf");
        doc.setContent(new byte[]{0x25, 0x50, 0x44, 0x46}); // dummy content

        // Alice applies for an IPO
        alice.createIPOApplication("TechCorp", 100, 5000.0, doc);

        // Find the pending application
        IPOApplication pending = alice.getApplications().stream()
                .filter(a -> a.getStatus() == IPOApplication.Status.PENDING)
                .findFirst()
                .orElse(null);

        // Bank approves the application
        if (pending != null) {
            bank.approveOrRejectApplication(pending, true);
        }

        // Summary
        System.out.println("Reviewed applications: " + alice.getApplicationCountSummary());
        System.out.println("Total approved amount: $" + alice.getTotalApprovedAmount());
    }
}