import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Customer {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean isEligible;
    private int failedAttempts;
    private List<IpoApplication> applications;

    public Customer() {
        this.isEligible = true;
        this.failedAttempts = 0;
        this.applications = new ArrayList<>();
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

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public List<IpoApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<IpoApplication> applications) {
        this.applications = applications;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
        if (this.failedAttempts >= 3) {
            this.isEligible = false;
        }
    }

    public int getTotalApplicationCount() {
        int count = 0;
        for (IpoApplication application : applications) {
            if (application.isProcessed()) {
                count++;
            }
        }
        return count;
    }

    public double getTotalApprovedAmount() {
        double total = 0;
        for (IpoApplication application : applications) {
            if (application.isApproved()) {
                total += application.getAmount();
            }
        }
        return total;
    }

    public boolean canApplyForIpo(String companyName) {
        if (!isEligible) {
            return false;
        }
        for (IpoApplication application : applications) {
            if (application.getCompany().equals(companyName) && application.isApproved()) {
                return false;
            }
        }
        return true;
    }
}

class IpoApplication {
    private String company;
    private int numberOfShares;
    private double amount;
    private String document;
    private boolean isApproved;
    private boolean isRejected;
    private Customer customer;

    public IpoApplication() {
        this.isApproved = false;
        this.isRejected = false;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isProcessed() {
        return isApproved || isRejected;
    }
}

class Bank {
    private Map<String, Customer> customers;
    private Map<String, IpoApplication> applications;

    public Bank() {
        this.customers = new HashMap<>();
        this.applications = new HashMap<>();
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<String, Customer> customers) {
        this.customers = customers;
    }

    public Map<String, IpoApplication> getApplications() {
        return applications;
    }

    public void setApplications(Map<String, IpoApplication> applications) {
        this.applications = applications;
    }

    /**
     * Creates an IPO application for a customer.
     * @param customer The customer creating the application.
     * @param company The target company for the IPO.
     * @param numberOfShares The number of shares to purchase.
     * @param amount The amount of money to invest.
     * @param document The legal allowance document.
     * @return true if the application is created successfully, false otherwise.
     */
    public boolean createIpoApplication(Customer customer, String company, int numberOfShares, double amount, String document) {
        if (!customer.canApplyForIpo(company)) {
            customer.incrementFailedAttempts();
            return false;
        }

        IpoApplication application = new IpoApplication();
        application.setCompany(company);
        application.setNumberOfShares(numberOfShares);
        application.setAmount(amount);
        application.setDocument(document);
        application.setCustomer(customer);

        customer.getApplications().add(application);
        applications.put(generateApplicationId(customer, company), application);
        return true;
    }

    /**
     * Approves or rejects an IPO application.
     * @param applicationId The ID of the application.
     * @param isApproved Whether the application is approved.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean approveOrRejectApplication(String applicationId, boolean isApproved) {
        IpoApplication application = applications.get(applicationId);
        if (application == null) {
            return false;
        }

        if (isApproved) {
            application.setApproved(true);
            sendEmailToCustomer(application, true);
            sendEmailToCompany(application);
        } else {
            application.setRejected(true);
            sendEmailToCustomer(application, false);
        }
        return true;
    }

    /**
     * Cancels a pending IPO application.
     * @param applicationId The ID of the application.
     * @return true if the cancellation is successful, false otherwise.
     */
    public boolean cancelApplication(String applicationId) {
        IpoApplication application = applications.get(applicationId);
        if (application == null || application.isProcessed()) {
            return false;
        }

        applications.remove(applicationId);
        return true;
    }

    private String generateApplicationId(Customer customer, String company) {
        return customer.getEmail() + "_" + company;
    }

    private void sendEmailToCustomer(IpoApplication application, boolean isApproved) {
        String emailContent = "Dear " + application.getCustomer().getName() + " " + application.getCustomer().getSurname() + ",\n\n";
        if (isApproved) {
            emailContent += "Your IPO application for " + application.getCompany() + " has been approved.\n";
        } else {
            emailContent += "Your IPO application for " + application.getCompany() + " has been rejected.\n";
        }
        emailContent += "Number of shares: " + application.getNumberOfShares() + "\n";
        emailContent += "Amount: " + application.getAmount() + "\n\n";
        emailContent += "Best regards,\nBank Team";

        System.out.println("Sending email to customer: " + emailContent);
    }

    private void sendEmailToCompany(IpoApplication application) {
        String emailContent = "Dear " + application.getCompany() + ",\n\n";
        emailContent += "A new IPO application has been approved for " + application.getCustomer().getName() + " " + application.getCustomer().getSurname() + ".\n";
        emailContent += "Number of shares: " + application.getNumberOfShares() + "\n";
        emailContent += "Amount: " + application.getAmount() + "\n\n";
        emailContent += "Best regards,\nBank Team";

        System.out.println("Sending email to company: " + emailContent);
    }
}