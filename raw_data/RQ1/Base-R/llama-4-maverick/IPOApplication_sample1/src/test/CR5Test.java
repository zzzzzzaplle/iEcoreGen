import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        
        // Create pending application for EcoWave
        IPOApplication app = new IPOApplication("EcoWave", 15, 750.0, "EW-2024-03");
        app.setStatus(IPOApplication.Status.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Cancel the pending application for "EcoWave"
        boolean result = customer.cancelPendingApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        assertEquals("Application status should be CANCELLED", 
                    IPOApplication.Status.CANCELLED, app.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        
        // Create approved application for SmartGrid
        IPOApplication app = new IPOApplication("SmartGrid", 30, 3000.0, "SG-2024-01");
        app.setStatus(IPOApplication.Status.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Try to cancel the approved application for "SmartGrid"
        boolean result = customer.cancelPendingApplication("SmartGrid");
        
        // Verify: Cancellation should fail for approved applications
        assertFalse("Approved application should not be cancellable", result);
        assertEquals("Application status should remain APPROVED", 
                    IPOApplication.Status.APPROVED, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        
        // Create rejected application for MedLife
        IPOApplication app = new IPOApplication("MedLife", 20, 1000.0, "SG-2024-03");
        app.setStatus(IPOApplication.Status.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Try to cancel the rejected application for "MedLife"
        boolean result = customer.cancelPendingApplication("MedLife");
        
        // Verify: Cancellation should fail for rejected applications
        assertFalse("Rejected application should not be cancellable", result);
        assertEquals("Application status should remain REJECTED", 
                    IPOApplication.Status.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no applications
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        
        // Customer has no applications initially
        customer.setApplications(new ArrayList<>());
        
        // Test: Try to cancel application for "UnknownCorp" which doesn't exist
        boolean result = customer.cancelPendingApplication("UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists for the company
        assertFalse("Cancellation should fail for non-existent company", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        
        // Create two pending applications
        IPOApplication app1 = new IPOApplication("UrbanTech", 25, 1250.0, "SG-2024-005");
        app1.setStatus(IPOApplication.Status.PENDING);
        
        IPOApplication app2 = new IPOApplication("AgroSeed", 40, 2000.0, "SG-2024-006");
        app2.setStatus(IPOApplication.Status.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Cancel the "UrbanTech" application
        boolean result = customer.cancelPendingApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("UrbanTech application should be cancellable", result);
        assertEquals("UrbanTech status should be CANCELLED", 
                    IPOApplication.Status.CANCELLED, app1.getStatus());
        
        // Verify: AgroSeed application should remain unaffected
        assertEquals("AgroSeed status should remain PENDING", 
                    IPOApplication.Status.PENDING, app2.getStatus());
    }
}