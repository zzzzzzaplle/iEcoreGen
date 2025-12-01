import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Simple placeholder for a legal document that must be uploaded with an IPO application.
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
 * Represents a company that can receive IPO applications.
 */
class Company {
    private String name;
    private String email; // address to which the information email will be sent

    public Company() {
    }

    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /** @return the company name */
    public String getName() {
        return name;
    }

    /** @param name the company name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the email address of the company */
    public String getEmail() {
        return email;
    }

    /** @param email the email address of the company */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Utility class that pretends to send e‑mails.
 */
class EmailService {
    /**
     * Sends an e‑mail.
     *
     * @param to      recipient e‑mail address
     * @param subject e‑mail subject line
     * @param body    e‑mail body text
     */
    public static void sendEmail(String to, String subject, String body) {
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
 * The possible states of an IPO application.
 */
enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELED
}

/**
 * Represents a single IPO application made by a customer.
 */
class IPOApplication {
    private Customer customer;
    private Company company;
    private int numberOfShares;
    private double amountPaid;
    private Document legalDocument;
    private ApplicationStatus status;

    public IPOApplication() {
    }

    public IPOApplication(Customer customer, Company company, int numberOfShares,
                         double amountPaid, Document legalDocument) {
        this.customer = customer;
        this.company = company;
        this.numberOfShares = numberOfShares;
        this.amountPaid = amountPaid;
        this.legalDocument = legalDocument;
        this.status = ApplicationStatus.PENDING;
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

    /** @param company the target company */
    public void setCompany(Company company) {
        this.company = company;
    }

    /** @return number of shares requested */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /** @param numberOfShares number of shares requested */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /** @return amount of money to be paid */
    public double getAmountPaid() {
        return amountPaid;
    }

    /** @param amountPaid amount of money to be paid */
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /** @return the uploaded legal document */
    public Document getLegalDocument() {
        return legalDocument;
    }

    /** @param legalDocument the legal document to set */
    public void setLegalDocument(Document legalDocument) {
        this.legalDocument = legalDocument;
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
 * Represents a retail customer that can apply for IPOs.
 */
class Customer {
    private static final int MAX_FAILED_ATTEMPTS = 3;

    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private List<IPOApplication> applications;
    private int failedAttempts; // number of rejected applications

    public Customer() {
        this.applications = new ArrayList<>();
        this.failedAttempts = 0;
    }

    /** @return customer's first name */
    public String getFirstName() {
        return firstName;
    }

    /** @param firstName customer's first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return customer's last name */
    public String getLastName() {
        return lastName;
    }

    /** @param lastName customer's last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return customer's e‑mail address */
    public String getEmail() {
        return email;
    }

    /** @param email customer's e‑mail address */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return customer's telephone number */
    public String getTelephone() {
        return telephone;
    }

    /** @param telephone customer's telephone number */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /** @return list of all applications made by this customer */
    public List<IPOApplication> getApplications() {
        return applications;
    }

    /** @param applications list of applications to set */
    public void setApplications(List<IPOApplication> applications) {
        this.applications = applications;
    }

    /** @return number of rejected applications (failed attempts) */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /** @param failedAttempts the failed attempts count to set */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks whether the customer is still eligible to apply for IPOs.
     *
     * @return {@code true} if the number of failed attempts is below the allowed maximum;
     *         {@code false} otherwise.
     */
    public boolean isEligible() {
        return failedAttempts < MAX_FAILED_ATTEMPTS;
    }

    /**
     * Creates a new IPO application.
     *
     * @param company       the target company for the IPO
     * @param shares        number of shares requested (must be &gt; 0)
     * @param amount        amount of money to be paid
     * @param legalDocument uploaded legal document (must be non‑null)
     * @return {@code true} if the application was successfully created; {@code false} otherwise.
     */
    public boolean createIPOApplication(Company company, int shares,
                                        double amount, Document legalDocument) {
        // eligibility check
        if (!isEligible()) {
            System.out.println("Customer is restricted due to too many failed attempts.");
            return false;
        }

        // basic parameter validation
        if (company == null || shares <= 0 || legalDocument == null) {
            System.out.println("Invalid parameters for IPO application.");
            return false;
        }

        // ensure no already approved application for the same company
        boolean alreadyApproved = applications.stream()
                .anyMatch(app -> app.getCompany().getName().equals(company.getName())
                        && app.getStatus() == ApplicationStatus.APPROVED);
        if (alreadyApproved) {
            System.out.println("An approved application for this company already exists.");
            return false;
        }

        // create and store the new application
        IPOApplication newApp = new IPOApplication(this, company, shares, amount, legalDocument);
        applications.add(newApp);
        System.out.println("IPO application created successfully.");
        return true;
    }

    /**
     * Approves or rejects a pending IPO application.
     *
     * @param application the application to be processed
     * @param approve     {@code true} to approve, {@code false} to reject
     * @return {@code true} if the operation succeeded; {@code false} otherwise.
     */
    public boolean approveOrRejectApplication(IPOApplication application, boolean approve) {
        if (application == null) {
            System.out.println("Application is null.");
            return false;
        }

        if (!applications.contains(application)) {
            System.out.println("Application does not belong to this customer.");
            return false;
        }

        if (application.getStatus() != ApplicationStatus.PENDING) {
            System.out.println("Only pending applications can be processed.");
            return false;
        }

        if (approve) {
            // verify eligibility again before approval
            if (!isEligible()) {
                System.out.println("Customer is restricted and cannot be approved.");
                return false;
            }

            application.setStatus(ApplicationStatus.APPROVED);
            sendInformationEmails(application);
            System.out.println("Application approved and information emails sent.");
            return true;
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            failedAttempts++; // count as a failed attempt
            sendRejectionEmail(application);
            System.out.println("Application rejected and rejection email sent.");
            return true;
        }
    }

    /**
     * Returns a summary of how many applications the customer has filed that have already been
     * reviewed (approved or rejected).
     *
     * @return total number of reviewed applications; {@code 0} if none exist.
     */
    public int getApplicationCountSummary() {
        long count = applications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.APPROVED
                        || app.getStatus() == ApplicationStatus.REJECTED)
                .count();
        return (int) count;
    }

    /**
     * Calculates the total monetary amount of all approved IPO applications for this customer.
     *
     * @return sum of the amounts of approved applications; {@code 0.0} if none are approved.
     */
    public double getTotalApprovedAmount() {
        return applications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.APPROVED)
                .mapToDouble(IPOApplication::getAmountPaid)
                .sum();
    }

    /**
     * Cancels a pending application for a given company name.
     *
     * @param companyName name of the company whose pending application should be cancelled
     * @return {@code true} if the cancellation succeeded; {@code false} otherwise.
     */
    public boolean cancelPendingApplication(String companyName) {
        List<IPOApplication> pendingApps = applications.stream()
                .filter(app -> app.getCompany().getName().equals(companyName)
                        && app.getStatus() == ApplicationStatus.PENDING)
                .collect(Collectors.toList());

        if (pendingApps.isEmpty()) {
            System.out.println("No pending application found for company: " + companyName);
            return false;
        }

        // Assuming only one pending application per company per customer
        IPOApplication toCancel = pendingApps.get(0);
        toCancel.setStatus(ApplicationStatus.CANCELED);
        System.out.println("Pending application for company '" + companyName + "' has been cancelled.");
        return true;
    }

    /**
     * Sends the information e‑mail to both the customer and the target company after a successful
     * approval.
     *
     * @param application the approved application
     */
    private void sendInformationEmails(IPOApplication application) {
        String subject = "IPO Application Approved – Details";
        String body = buildEmailBody(application);

        // Email to customer
        EmailService.sendEmail(this.email, subject, body);

        // Email to company
        String companyEmail = application.getCompany().getEmail();
        if (companyEmail != null && !companyEmail.isEmpty()) {
            EmailService.sendEmail(companyEmail, subject, body);
        }
    }

    /**
     * Sends a rejection e‑mail to the customer.
     *
     * @param application the rejected application
     */
    private void sendRejectionEmail(IPOApplication application) {
        String subject = "IPO Application Rejected";
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(firstName).append(' ').append(lastName).append(",\n\n");
        sb.append("We regret to inform you that your IPO application to ")
                .append(application.getCompany().getName())
                .append(" has been rejected.\n\n");
        sb.append("Please contact our call centre if you wish to discuss this decision.\n\n");
        sb.append("Best regards,\nYour Bank");
        EmailService.sendEmail(this.email, subject, sb.toString());
    }

    /**
     * Builds the common e‑mail body used for both customer and company information messages.
     *
     * @param application the approved application
     * @return formatted e‑mail body text
     */
    private String buildEmailBody(IPOApplication application) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(firstName).append(' ').append(lastName).append(",\n\n");
        sb.append("Your IPO application has been approved. Below are the details:\n");
        sb.append("Customer Name: ").append(firstName).append(' ').append(lastName).append('\n');
        sb.append("Email: ").append(email).append('\n');
        sb.append("Telephone: ").append(telephone).append('\n');
        sb.append("Target Company: ").append(application.getCompany().getName()).append('\n');
        sb.append("Number of Shares: ").append(application.getNumberOfShares()).append('\n');
        sb.append("Amount Paid: $").append(String.format("%.2f", application.getAmountPaid())).append('\n');
        sb.append("\nThank you for using our services.\n");
        sb.append("\nBest regards,\nYour Bank");
        return sb.toString();
    }
}