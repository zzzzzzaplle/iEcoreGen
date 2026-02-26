import java.util.ArrayList;
import java.util.List;

enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class Application {
    private String company;
    private int shares;
    private double amount;
    private Object document;
    private ApplicationStatus status;

    public Application(String company, int shares, double amount, Object document) {
        this.company = company;
        this.shares = shares;
        this.amount = amount;
        this.document = document;
        this.status = ApplicationStatus.PENDING;
    }

    public String getCompany() {
        return company;
    }

    public int getShares() {
        return shares;
    }

    public double getAmount() {
        return amount;
    }

    public Object getDocument() {
        return document;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean canApplyForIPO;
    private List<Application> applications;
    private int failedAttempts;

    public Customer() {
        this.applications = new ArrayList<>();
        this.canApplyForIPO = true;
        this.failedAttempts = 0;
    }

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

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /**
     * Checks if the customer is eligible for IPO application
     * Retail customers should be eligible for applying IPO
     * If the customer has many failed attempts, the bank should restrict the customer from the application process
     * 
     * @return true if customer is eligible for IPO, false otherwise
     */
    public boolean isEligibleForIPO() {
        return canApplyForIPO && failedAttempts < 3;
    }

    /**
     * Creates an IPO application for the customer
     * A retail customer begins an IPO application by supplying the target company, 
     * the number of shares (>0), the amount of money, and uploading the document(non-null)
     * The system first checks that the customer is still eligible to apply for IPOs and 
     * has no approved application for the same company; only then is an application record created
     * 
     * @param company the target company for IPO application
     * @param shares the number of shares to purchase (must be > 0)
     * @param amount the amount of money to be paid
     * @param doc the legal allowance document
     * @return true if application created successfully, false otherwise
     */
    public boolean createIPOApplication(String company, int shares, double amount, Object doc) {
        if (!isEligibleForIPO() || shares <= 0 || doc == null) {
            return false;
        }
        
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.APPROVED) {
                return false;
            }
        }
        
        applications.add(new Application(company, shares, amount, doc));
        return true;
    }

    /**
     * Approve or reject an application
     * If the application is rejected, the bank immediately emails the customer a rejection notice.
     * The bank approves a specific application after verifying that the customer current can apply for IPO.
     * If the application is approved, the bank sends two information emails: one to the customer and one to the company.
     * 
     * @param company the company name for the application
     * @param approve true to approve, false to reject
     * @return true if operation successful, false otherwise
     */
    public boolean approveOrRejectApplication(String company, boolean approve) {
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.PENDING) {
                if (approve) {
                    if (!isEligibleForIPO()) {
                        return false;
                    }
                    app.setStatus(ApplicationStatus.APPROVED);
                    // Send emails to customer and company
                    sendCustomerEmail(app);
                    sendCompanyEmail(app);
                } else {
                    app.setStatus(ApplicationStatus.REJECTED);
                    // Send rejection email to customer
                    sendRejectionEmail(app);
                    failedAttempts++;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve a customer's application-count summary
     * The customer can report how many IPO applications has filed in total, including approval and rejected applications.
     * Applications that have not yet been reviewed by the bank are not included.
     * 
     * @return number of reviewed applications
     */
    public int getApplicationCountSummary() {
        int count = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED || app.getStatus() == ApplicationStatus.REJECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Query total approval IPO applications amount for a customer
     * A customer may request an aggregate view of the customer's approved IPO purchases.
     * 
     * @return the sum of all approved application amounts
     */
    public double getTotalApprovedAmount() {
        double total = 0;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.APPROVED) {
                total += app.getAmount();
            }
        }
        return total;
    }

    /**
     * Cancel a pending application
     * Providing the specific company name, a customer can cancel an application that is neither approved nor rejected by the bank.
     * 
     * @param company the company name
     * @return true if cancellation succeeds, false otherwise
     */
    public boolean cancelPendingApplication(String company) {
        for (Application app : applications) {
            if (app.getCompany().equals(company) && app.getStatus() == ApplicationStatus.PENDING) {
                applications.remove(app);
                return true;
            }
        }
        return false;
    }

    private void sendCustomerEmail(Application app) {
        // Implementation for sending email to customer
    }

    private void sendCompanyEmail(Application app) {
        // Implementation for sending email to company
    }

    private void sendRejectionEmail(Application app) {
        // Implementation for sending rejection email to customer
    }
}