import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_ApprovePendingRequest() {
        // Setup: Customer "C007" with pending application for "SolarMax"
        customer.setName("Michael Brown");
        customer.setEmail("m.brown@example.com");
        customer.setTelephone("555-1122");
        customer.setCanApplyForIPO(true);
        
        // Create pending application for SolarMax
        boolean applicationCreated = customer.createIPOApplication("SolarMax", 10, 200.0, "S");
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Approve the pending application
        boolean result = customer.approveOrRejectApplication("SolarMax", true);
        
        // Verify: Operation should succeed and status should change to APPROVED
        assertTrue("Application approval should succeed", result);
        
        // Verify application status is APPROVED
        Application app = customer.getApplications().get(0);
        assertEquals("Application status should be APPROVED", ApplicationStatus.APPROVED, app.getStatus());
    }
    
    @Test
    public void testCase2_RejectPendingRequest() {
        // Setup: Customer "C008" with pending application for "HealthPlus"
        customer.setName("Olivia Lee");
        customer.setEmail("olivia.l@example.com");
        customer.setTelephone("555-3344");
        customer.setCanApplyForIPO(true);
        
        // Create pending application for HealthPlus
        boolean applicationCreated = customer.createIPOApplication("HealthPlus", 10, 5000.0, "H");
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Reject the pending application
        boolean result = customer.approveOrRejectApplication("HealthPlus", false);
        
        // Verify: Operation should succeed and status should change to REJECTED
        assertTrue("Application rejection should succeed", result);
        
        // Verify application status is REJECTED
        Application app = customer.getApplications().get(0);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Verify failed attempts increased by 1
        assertEquals("Failed attempts should be 1", 1, customer.getFailedAttempts());
    }
    
    @Test
    public void testCase3_ApproveAlreadyApprovedRecord() {
        // Setup: Customer "C009" with existing APPROVED application for "HealthPlus"
        customer.setName("Daniel Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-5566");
        customer.setCanApplyForIPO(true);
        
        // Create and approve application for HealthPlus
        boolean applicationCreated = customer.createIPOApplication("HealthPlus", 10, 5000.0, "H");
        assertTrue("Application should be created successfully", applicationCreated);
        
        boolean approved = customer.approveOrRejectApplication("HealthPlus", true);
        assertTrue("First approval should succeed", approved);
        
        // Test: Attempt to re-approve the already approved application
        boolean result = customer.approveOrRejectApplication("HealthPlus", true);
        
        // Verify: Operation should fail (cannot modify approved applications)
        assertFalse("Re-approving approved application should fail", result);
        
        // Verify application status remains APPROVED
        Application app = customer.getApplications().get(0);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, app.getStatus());
    }
    
    @Test
    public void testCase4_RejectAlreadyRejectedRecord() {
        // Setup: Customer "C010" with existing REJECTED application for "Health"
        customer.setName("Sophie Zhang");
        customer.setEmail("s.zhang@example.com");
        customer.setTelephone("555-7788");
        customer.setCanApplyForIPO(true);
        
        // Create and reject application for Health
        boolean applicationCreated = customer.createIPOApplication("Health", 10, 5000.0, "H");
        assertTrue("Application should be created successfully", applicationCreated);
        
        boolean rejected = customer.approveOrRejectApplication("Health", false);
        assertTrue("First rejection should succeed", rejected);
        
        // Test: Attempt to re-reject the already rejected application
        boolean result = customer.approveOrRejectApplication("Health", false);
        
        // Verify: Operation should fail (cannot modify final decisions)
        assertFalse("Re-rejecting rejected application should fail", result);
        
        // Verify application status remains REJECTED
        Application app = customer.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase5_ApproveRecordTiedToIneligibleCustomer() {
        // Setup: Customer "C011" with pending application but ineligible for IPO
        customer.setName("William Wang");
        customer.setEmail("will.w@example.com");
        customer.setTelephone("555-9900");
        customer.setCanApplyForIPO(false); // Customer is not eligible for IPO
        
        // Create pending application for Cloud
        boolean applicationCreated = customer.createIPOApplication("Cloud", 10, 5000.0, "Cloud");
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Attempt to approve application for ineligible customer
        boolean result = customer.approveOrRejectApplication("Cloud", true);
        
        // Verify: Operation should fail (customer must maintain eligibility)
        assertFalse("Approving application for ineligible customer should fail", result);
        
        // Verify application status remains PENDING
        Application app = customer.getApplications().get(0);
        assertEquals("Application status should remain PENDING", ApplicationStatus.PENDING, app.getStatus());
    }
}