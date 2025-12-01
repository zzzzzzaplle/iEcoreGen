import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private BankSystem bankSystem;
    private Customer customer;
    private IPOApplication application;
    private Document document;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        customer = new Customer();
        application = new IPOApplication();
        document = new Document();
    }
    
    @Test
    public void testCase1_approvePendingRequest() {
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Approve the pending application
        boolean result = bankSystem.processApplication(customer, application, true);
        
        // Verify: Operation should succeed and status should be APPROVED
        assertTrue("Application should be approved successfully", result);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_rejectPendingRequest() {
        // Setup: Customer "C008" with pending application for "HealthPlus"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Reject the pending application
        boolean result = bankSystem.processApplication(customer, application, false);
        
        // Verify: Operation should succeed and status should be REJECTED
        assertTrue("Application should be rejected successfully", result);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_approveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with already approved application for "HealthPlus"
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.APPROVED); // Already approved
        
        // Test: Attempt to re-approve an already approved application
        boolean result = bankSystem.processApplication(customer, application, true);
        
        // Verify: Operation should fail since application is already approved
        assertFalse("Should not be able to modify approved applications", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_rejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with already rejected application for "Health"
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setEligibleForIPO(true);
        customer.setRestricted(false);
        
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.REJECTED); // Already rejected
        
        // Test: Attempt to re-reject an already rejected application
        boolean result = bankSystem.processApplication(customer, application, false);
        
        // Verify: Operation should fail since application is already rejected
        assertFalse("Should not be able to modify final decisions", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_approveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is ineligible with pending application for "Cloud"
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setEligibleForIPO(false); // Ineligible customer
        customer.setRestricted(false);
        
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = bankSystem.processApplication(customer, application, true);
        
        // Verify: Operation should fail due to customer ineligibility
        assertFalse("Should not approve application for ineligible customer", result);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
}