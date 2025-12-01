import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        document = new Document(); // Simple document instance
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company = new Company("EcoWave", "ecowave@gmail.com");
        
        // Create pending application
        Application app = new Application(15, 750.0, customer, company, document);
        app.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app);
        
        // Test: Cancel application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Should return true and application status should be REJECTED
        assertTrue("Cancellation of pending application should succeed", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company = new Company("SmartGrid", "smartgrid@business.com");
        
        // Create approved application
        Application app = new Application(30, 3000.0, customer, company, document);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Should return false (cannot cancel approved applications)
        assertFalse("Cancellation of approved application should fail", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company = new Company("MedLife", "medlife@health.com");
        
        // Create rejected application
        Application app = new Application(20, 1000.0, customer, company, document);
        app.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Should return false (cannot cancel rejected applications)
        assertFalse("Cancellation of rejected application should fail", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no applications
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Customer has no applications
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Should return false (no application found)
        assertFalse("Cancellation for non-existent company should fail", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        Company urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        Company agroSeed = new Company("AgroSeed", "agroseed@business.com");
        
        // Create two pending applications
        Application app1 = new Application(25, 1250.0, customer, urbanTech, document);
        app1.setStatus(ApplicationStatus.PENDING);
        
        Application app2 = new Application(40, 2000.0, customer, agroSeed, document);
        app2.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain unaffected
        assertTrue("Cancellation of UrbanTech application should succeed", result);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app1.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, app2.getStatus());
        assertEquals("Customer should still have 2 applications", 2, customer.getApplications().size());
    }
}