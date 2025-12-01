import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private BankSystem bankSystem;
    private EmailService emailService;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        emailService = new EmailService();
        bankSystem.setEmailService(emailService);
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Create customer and pending application
        Customer customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmountPaid(200.0);
        application.setDocument("S");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getAllApplications().add(application);
        
        // Execute: Approve the application
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation successful and status changed to APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Create customer and pending application
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getAllApplications().add(application);
        
        // Execute: Reject the application
        boolean result = bankSystem.approveOrRejectApplication(application, false);
        
        // Verify: Operation successful and status changed to REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Create customer with already approved application
        Customer customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(application);
        bankSystem.getAllApplications().add(application);
        
        // Execute: Try to approve already approved application
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation should fail
        assertFalse("Cannot modify approved applications", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Create customer with already rejected application
        Customer customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        bankSystem.getAllApplications().add(application);
        
        // Execute: Try to reject already rejected application
        boolean result = bankSystem.approveOrRejectApplication(application, false);
        
        // Verify: Operation should fail
        assertFalse("Cannot modify rejected applications", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Create ineligible customer with pending application
        Customer customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setEligibleForIPO(false);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmountPaid(5000.0);
        application.setDocument("C");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        bankSystem.getAllApplications().add(application);
        
        // Execute: Try to approve application for ineligible customer
        boolean result = bankSystem.approveOrRejectApplication(application, true);
        
        // Verify: Operation should fail due to ineligible customer
        assertFalse("Cannot approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}