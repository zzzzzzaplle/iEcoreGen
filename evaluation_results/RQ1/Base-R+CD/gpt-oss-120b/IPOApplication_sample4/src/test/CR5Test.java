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
    
    private Document docEW;
    private Document docSG1;
    private Document docSG3;
    private Document docUT;
    private Document docAS;
    
    @Before
    public void setUp() {
        // Initialize companies
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
        agroSeed.setEmail("agroseed@innovate.com");
        
        // Initialize documents
        docEW = new Document();
        docSG1 = new Document();
        docSG3 = new Document();
        docUT = new Document();
        docAS = new Document();
        
        // Initialize customers
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
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        boolean created = customerC301.createApplication(ecoWave, 15, 750.0, docEW);
        assertTrue("Application should be created successfully", created);
        
        // Verify the application is pending
        assertEquals("Application should be pending", ApplicationStatus.PENDING, 
                     customerC301.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation of pending application should return true", result);
        assertTrue("Application list should be empty after cancellation", 
                   customerC301.getApplications().isEmpty());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create and approve application for SmartGrid
        boolean created = customerC302.createApplication(smartGrid, 30, 3000.0, docSG1);
        assertTrue("Application should be created successfully", created);
        
        // Approve the application
        Application app = customerC302.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation of approved application should return false", result);
        assertEquals("Application should still exist in the list", 1, customerC302.getApplications().size());
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, 
                     customerC302.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create and reject application for MedLife
        boolean created = customerC303.createApplication(medLife, 20, 1000.0, docSG3);
        assertTrue("Application should be created successfully", created);
        
        // Reject the application
        Application app = customerC303.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation of rejected application should return false", result);
        assertEquals("Application should still exist in the list", 1, customerC303.getApplications().size());
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, 
                     customerC303.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer has no applications
        assertTrue("Customer should have no applications initially", customerC304.getApplications().isEmpty());
        
        // Test: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation for non-existent company should return false", result);
        assertTrue("Customer should still have no applications", customerC304.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        boolean created1 = customerC306.createApplication(urbanTech, 25, 1250.0, docUT);
        assertTrue("First application should be created successfully", created1);
        
        boolean created2 = customerC306.createApplication(agroSeed, 40, 2000.0, docAS);
        assertTrue("Second application should be created successfully", created2);
        
        assertEquals("Customer should have 2 applications", 2, customerC306.getApplications().size());
        
        // Test: Cancel UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify UrbanTech cancellation succeeded and AgroSeed remains
        assertTrue("Cancellation of UrbanTech application should return true", result);
        assertEquals("Customer should have 1 application remaining", 1, customerC306.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", 
                     customerC306.getApplications().get(0).getCompany().getName());
        assertEquals("AgroSeed application should still be pending", ApplicationStatus.PENDING, 
                     customerC306.getApplications().get(0).getStatus());
    }
}