import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    
    @Before
    public void setUp() {
        // Initialize test data for all test cases
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@innovate.com");
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        Document doc1 = new Document();
        Application app1 = new Application(15, 750.0, customerC301, ecoWave, doc1);
        customerC301.getApplications().add(app1);
        
        // Execute: Cancel application for EcoWave
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app1.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create approved application for SmartGrid
        Document doc2 = new Document();
        Application app2 = new Application(30, 3000.0, customerC302, smartGrid, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app2);
        
        // Execute: Try to cancel approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of approved application should return false", result);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVAL, app2.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create rejected application for MedLife
        Document doc3 = new Document();
        Application app3 = new Application(20, 1000.0, customerC303, medLife, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app3);
        
        // Execute: Try to cancel rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of rejected application should return false", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app3.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer has no applications
        
        // Execute: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation for non-existent company should return false", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        Document doc5 = new Document();
        Document doc6 = new Document();
        Application app5 = new Application(25, 1250.0, customerC306, urbanTech, doc5);
        Application app6 = new Application(40, 2000.0, customerC306, agroSeed, doc6);
        customerC306.getApplications().add(app5);
        customerC306.getApplications().add(app6);
        
        // Execute: Cancel UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", result);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app5.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, app6.getStatus());
    }
}