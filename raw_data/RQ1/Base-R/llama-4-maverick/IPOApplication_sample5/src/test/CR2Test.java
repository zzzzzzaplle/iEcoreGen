import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private IPOService ipoService;
    private Customer customer;
    
    @Before
    public void setUp() {
        ipoService = new IPOService();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer.setName("Michael");
        customer.setSurname("Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephoneNumber("555-1122");
        customer.setFailedAttempts(0); // Eligible for IPO
        
        // Create pending application for SolarMax
        IPOApplication application = new IPOApplication();
        application.setCompanyName("SolarMax");
        application.setNumberOfShares(10);
        application.setAmount(200.0);
        application.setDocument("S");
        application.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(application);
        
        // Test: Approve the pending application
        boolean result = ipoService.approveOrRejectApplication(customer, "SolarMax", true);
        
        // Verify: Operation should succeed and status should be APPROVED
        assertTrue("Application approval should return true", result);
        assertEquals("Application status should be APPROVED", 
                     IPOApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Customer "C008" with pending application for "HealthPlus"
        customer.setName("Olivia");
        customer.setSurname("Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephoneNumber("555-3344");
        customer.setFailedAttempts(0); // Eligible for IPO
        
        // Create pending application for HealthPlus
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(application);
        
        // Test: Reject the pending application
        boolean result = ipoService.approveOrRejectApplication(customer, "HealthPlus", false);
        
        // Verify: Operation should succeed and status should be REJECTED
        assertTrue("Application rejection should return true", result);
        assertEquals("Application status should be REJECTED", 
                     IPOApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with already approved application for "HealthPlus"
        customer.setName("Daniel");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-5566");
        customer.setFailedAttempts(0);
        
        // Create already approved application for HealthPlus
        IPOApplication application = new IPOApplication();
        application.setCompanyName("HealthPlus");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(IPOApplicationStatus.APPROVED); // Already approved
        
        customer.getIpoApplications().add(application);
        
        // Test: Attempt to approve already approved application
        boolean result = ipoService.approveOrRejectApplication(customer, "HealthPlus", true);
        
        // Verify: Operation should fail (cannot modify approved applications)
        assertFalse("Approving already approved application should return false", result);
        assertEquals("Application status should remain APPROVED", 
                     IPOApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with already rejected application
        customer.setName("Sophie");
        customer.setSurname("Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephoneNumber("555-7788");
        customer.setFailedAttempts(0);
        
        // Create already rejected application for "Health"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Health");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(IPOApplicationStatus.REJECTED); // Already rejected
        
        customer.getIpoApplications().add(application);
        
        // Test: Attempt to reject already rejected application
        boolean result = ipoService.approveOrRejectApplication(customer, "Health", false);
        
        // Verify: Operation should fail (cannot modify final decisions)
        assertFalse("Rejecting already rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", 
                     IPOApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" who is ineligible but has pending application
        customer.setName("William");
        customer.setSurname("Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephoneNumber("555-9900");
        customer.setFailedAttempts(3); // Ineligible for IPO (3 failed attempts)
        
        // Create pending application for "Cloud"
        IPOApplication application = new IPOApplication();
        application.setCompanyName("Cloud");
        application.setNumberOfShares(10);
        application.setAmount(5000.0);
        application.setDocument("H");
        application.setStatus(IPOApplicationStatus.PENDING);
        
        customer.getIpoApplications().add(application);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = ipoService.approveOrRejectApplication(customer, "Cloud", true);
        
        // Verify: Operation should fail (customer must maintain eligibility)
        assertFalse("Approving application for ineligible customer should return false", result);
        assertEquals("Application status should remain PENDING", 
                     IPOApplicationStatus.PENDING, application.getStatus());
    }
}