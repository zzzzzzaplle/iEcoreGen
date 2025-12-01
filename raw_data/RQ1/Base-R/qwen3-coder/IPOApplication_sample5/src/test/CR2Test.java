import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOApplicationSystem system;
    private EmailService emailService;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
        emailService = new EmailService();
        system.setEmailService(emailService);
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmailAddress("m.brown@example.com");
        customer.setTelephoneNumber("555-1122");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("SolarMax");
        company.setEmailAddress("solarmax@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument("S");
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertTrue("Application status should be approved", application.isApproved());
        assertTrue("Application should be marked as reviewed", application.isReviewed());
        assertFalse("Application should not be rejected", application.isRejected());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmailAddress("olivia.l@example.com");
        customer.setTelephoneNumber("555-3344");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("HealthPlus");
        company.setEmailAddress("healthplus@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = system.approveOrRejectApplication(application, false);
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertTrue("Application status should be rejected", application.isRejected());
        assertTrue("Application should be marked as reviewed", application.isReviewed());
        assertFalse("Application should not be approved", application.isApproved());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmailAddress("d.kim@example.com");
        customer.setTelephoneNumber("555-5566");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("HealthPlus");
        company.setEmailAddress("healthplus@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setApproved(true); // Already approved
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify
        assertFalse("Should not be able to approve an already approved application", result);
        assertTrue("Application should remain approved", application.isApproved());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmailAddress("s.zhang@example.com");
        customer.setTelephoneNumber("555-7788");
        customer.setEligibleForIPO(true);
        
        Company company = new Company();
        company.setName("Health");
        company.setEmailAddress("health@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setRejected(true); // Already rejected
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = system.approveOrRejectApplication(application, false);
        
        // Verify
        assertFalse("Should not be able to reject an already rejected application", result);
        assertTrue("Application should remain rejected", application.isRejected());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup
        Customer customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmailAddress("will.w@example.com");
        customer.setTelephoneNumber("555-9900");
        customer.setEligibleForIPO(false); // Customer is ineligible
        
        Company company = new Company();
        company.setName("Cloud");
        company.setEmailAddress("Cloud@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        
        customer.getApplications().add(application);
        
        // Execute
        boolean result = system.approveOrRejectApplication(application, true);
        
        // Verify
        assertFalse("Should not be able to approve application for ineligible customer", result);
        assertFalse("Application should not be approved", application.isApproved());
        assertFalse("Application should not be rejected", application.isRejected());
        assertFalse("Application should not be marked as reviewed", application.isReviewed());
    }
}