import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_CancelPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        // Create pending application for EcoWave
        Object document = "EW-2024-03";
        boolean result = customer.createIPOApplication("EcoWave", 15, 750.0, document);
        assertTrue("Application should be created successfully", result);
        
        // Verify the application is pending
        assertEquals(1, customer.getApplications().size());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean cancelResult = customer.cancelPendingApplication("EcoWave");
        
        // Verify: Should return true and application should be removed
        assertTrue("Cancellation should succeed for pending application", cancelResult);
        assertEquals(0, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        // Create and approve application for SmartGrid
        Object document = "SG-2024-01";
        boolean createResult = customer.createIPOApplication("SmartGrid", 30, 3000.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Approve the application
        boolean approveResult = customer.approveOrRejectApplication("SmartGrid", true);
        assertTrue("Application should be approved successfully", approveResult);
        
        // Verify the application is approved
        assertEquals(ApplicationStatus.APPROVED, customer.getApplications().get(0).getStatus());
        
        // Test: Try to cancel the approved application
        boolean cancelResult = customer.cancelPendingApplication("SmartGrid");
        
        // Verify: Should return false (cannot cancel approved applications)
        assertFalse("Cancellation should fail for approved application", cancelResult);
        assertEquals(1, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        // Create and reject application for MedLife
        Object document = "SG-2024-03";
        boolean createResult = customer.createIPOApplication("MedLife", 20, 1000.0, document);
        assertTrue("Application should be created successfully", createResult);
        
        // Reject the application
        boolean rejectResult = customer.approveOrRejectApplication("MedLife", false);
        assertTrue("Application should be rejected successfully", rejectResult);
        
        // Verify the application is rejected
        assertEquals(ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
        
        // Test: Try to cancel the rejected application
        boolean cancelResult = customer.cancelPendingApplication("MedLife");
        
        // Verify: Should return false (cannot cancel rejected applications)
        assertFalse("Cancellation should fail for rejected application", cancelResult);
        assertEquals(1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications for UnknownCorp
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Verify no applications exist initially
        assertEquals(0, customer.getApplications().size());
        
        // Test: Try to cancel application for non-existent company
        boolean cancelResult = customer.cancelPendingApplication("UnknownCorp");
        
        // Verify: Should return false (no application found)
        assertFalse("Cancellation should fail for non-existent company", cancelResult);
        assertEquals(0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create two pending applications
        Object document1 = "SG-2024-005";
        Object document2 = "SG-2024-006";
        
        boolean result1 = customer.createIPOApplication("UrbanTech", 25, 1250.0, document1);
        boolean result2 = customer.createIPOApplication("AgroSeed", 40, 2000.0, document2);
        
        assertTrue("First application should be created successfully", result1);
        assertTrue("Second application should be created successfully", result2);
        
        // Verify both applications exist and are pending
        assertEquals(2, customer.getApplications().size());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        assertEquals(ApplicationStatus.PENDING, customer.getApplications().get(1).getStatus());
        
        // Test: Cancel UrbanTech application
        boolean cancelResult = customer.cancelPendingApplication("UrbanTech");
        
        // Verify: UrbanTech application should be canceled, AgroSeed should remain
        assertTrue("Cancellation should succeed for UrbanTech application", cancelResult);
        assertEquals(1, customer.getApplications().size());
        assertEquals("AgroSeed", customer.getApplications().get(0).getCompany());
    }
}