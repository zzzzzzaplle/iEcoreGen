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
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setEligible(true);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument("S");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Approve the application
        boolean result = bankSystem.approveOrRejectApplication(customer, application, true);
        
        // Verify
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setEligible(true);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Reject the application
        boolean result = bankSystem.approveOrRejectApplication(customer, application, false);
        
        // Verify
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setEligible(true);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve an already approved application
        boolean result = bankSystem.approveOrRejectApplication(customer, application, true);
        
        // Verify
        assertFalse("Should not be able to modify approved applications", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setEligible(true);
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to reject an already rejected application
        boolean result = bankSystem.approveOrRejectApplication(customer, application, false);
        
        // Verify
        assertFalse("Should not be able to modify rejected applications", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup
        Customer customer = new Customer();
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setEligible(false); // Customer is not eligible
        
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Execute: Attempt to approve application for ineligible customer
        boolean result = bankSystem.approveOrRejectApplication(customer, application, true);
        
        // Verify
        assertFalse("Should not approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}