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
        // Initialize all customers
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        // Initialize all companies
        ecoWave = new Company();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        medLife = new Company();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com");
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        Document doc1 = new Document();
        boolean created = customerC301.createApplication(ecoWave, 15, 750.0, doc1);
        assertTrue("Application should be created successfully", created);
        
        // Verify the application is pending
        assertEquals("Application should be in PENDING status", 
                     ApplicationStatus.PENDING, 
                     customerC301.getApplications().get(0).getStatus());
        
        // Execute: Cancel the pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify application status changed to REJECTED
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, 
                     customerC301.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create and approve application for SmartGrid
        Document doc2 = new Document();
        boolean created = customerC302.createApplication(smartGrid, 30, 3000.0, doc2);
        assertTrue("Application should be created successfully", created);
        
        // Approve the application
        Application app = customerC302.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Verify the application is approved
        assertEquals("Application should be in APPROVAL status", 
                     ApplicationStatus.APPROVAL, 
                     app.getStatus());
        
        // Execute: Try to cancel the approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of approved application should return false", result);
        
        // Verify application status remains APPROVED
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVAL, 
                     app.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create and reject application for MedLife
        Document doc3 = new Document();
        boolean created = customerC303.createApplication(medLife, 20, 1000.0, doc3);
        assertTrue("Application should be created successfully", created);
        
        // Reject the application
        Application app = customerC303.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Verify the application is rejected
        assertEquals("Application should be in REJECTED status", 
                     ApplicationStatus.REJECTED, 
                     app.getStatus());
        
        // Execute: Try to cancel the rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation of rejected application should return false", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, 
                     app.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer has no applications
        assertEquals("Customer should have no applications initially", 
                     0, customerC304.getApplications().size());
        
        // Execute: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Cancellation for non-existent company should return false", result);
        
        // Verify customer still has no applications
        assertEquals("Customer should still have no applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        Document doc5 = new Document();
        Document doc6 = new Document();
        
        boolean created1 = customerC306.createApplication(urbanTech, 25, 1250.0, doc5);
        boolean created2 = customerC306.createApplication(agroSeed, 40, 2000.0, doc6);
        
        assertTrue("First application should be created successfully", created1);
        assertTrue("Second application should be created successfully", created2);
        
        // Verify both applications are pending
        assertEquals("Customer should have 2 applications", 
                     2, customerC306.getApplications().size());
        assertEquals("First application should be PENDING", 
                     ApplicationStatus.PENDING, 
                     customerC306.getApplications().get(0).getStatus());
        assertEquals("Second application should be PENDING", 
                     ApplicationStatus.PENDING, 
                     customerC306.getApplications().get(1).getStatus());
        
        // Execute: Cancel the UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify UrbanTech application is rejected
        Application urbanTechApp = customerC306.getApplications().get(0);
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, 
                     urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains pending
        Application agroSeedApp = customerC306.getApplications().get(1);
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, 
                     agroSeedApp.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 
                     2, customerC306.getApplications().size());
    }
}