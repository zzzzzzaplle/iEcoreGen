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
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        // Create pending application for EcoWave
        IPOApplication app = new IPOApplication();
        app.setCompanyName("EcoWave");
        app.setNumberOfShares(15);
        app.setAmount(750.0);
        app.setDocument("EW-2024-03");
        app.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelPendingApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be canceled successfully", result);
        assertEquals("Application list should be empty after cancellation", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        // Create approved application for SmartGrid
        IPOApplication app = new IPOApplication();
        app.setCompanyName("SmartGrid");
        app.setNumberOfShares(30);
        app.setAmount(3000.0);
        app.setDocument("SG-2024-01");
        app.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelPendingApplication("SmartGrid");
        
        // Verify: Cancellation should fail (cannot cancel approved applications)
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        // Create rejected application for MedLife
        IPOApplication app = new IPOApplication();
        app.setCompanyName("MedLife");
        app.setNumberOfShares(20);
        app.setAmount(1000.0);
        app.setDocument("SG-2024-03");
        app.setStatus(ApplicationStatus.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelPendingApplication("MedLife");
        
        // Verify: Cancellation should fail (cannot cancel rejected applications)
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Customer has no applications initially
        customer.setApplications(new ArrayList<>());
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customer.cancelPendingApplication("UnknownCorp");
        
        // Verify: Cancellation should fail (no application found)
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Application list should remain empty", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create first pending application for UrbanTech
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmount(1250.0);
        app1.setDocument("SG-2024-005");
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create second pending application for AgroSeed
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmount(2000.0);
        app2.setDocument("SG-2024-006");
        app2.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelPendingApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Should have one application remaining", 1, customer.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", customer.getApplications().get(0).getCompanyName());
    }
}