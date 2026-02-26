import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC305;
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
        // Create companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@example.com");
        
        // Create documents
        docEW = new Document();
        docSG1 = new Document();
        docSG3 = new Document();
        docUT = new Document();
        docAS = new Document();
        
        // Create customers
        customerC301 = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010", true);
        customerC302 = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020", true);
        customerC303 = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030", true);
        customerC304 = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040", true);
        customerC305 = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060", true);
        
        // Setup applications as per test specifications
        setupTestApplications();
    }
    
    private void setupTestApplications() {
        // Test Case 1: C301 has pending application for EcoWave
        Application app4001 = new Application(15, 750.0, customerC301, ecoWave, docEW);
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);
        
        // Test Case 2: C302 has approved application for SmartGrid
        Application app4002 = new Application(30, 3000.0, customerC302, smartGrid, docSG1);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app4002);
        
        // Test Case 3: C303 has rejected application for MedLife
        Application app4003 = new Application(20, 1000.0, customerC303, medLife, docSG3);
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);
        
        // Test Case 4: C304 has no applications
        
        // Test Case 5: C305 has two pending applications
        Application app4005 = new Application(25, 1250.0, customerC305, urbanTech, docUT);
        app4005.setStatus(ApplicationStatus.PENDING);
        customerC305.getApplications().add(app4005);
        
        Application app4006 = new Application(40, 2000.0, customerC305, agroSeed, docAS);
        app4006.setStatus(ApplicationStatus.PENDING);
        customerC305.getApplications().add(app4006);
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Customer "C301" requests cancellation for "EcoWave"
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify application status changed to REJECTED (cancelled)
        Application cancelledApp = customerC301.getApplications().get(0);
        assertEquals("Cancelled application status should be REJECTED", 
                    ApplicationStatus.REJECTED, cancelledApp.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed (cannot cancel approved applications)
        assertFalse("Cancellation of approved application should return false", result);
        
        // Verify application status remains APPROVAL
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Approved application status should remain APPROVAL", 
                    ApplicationStatus.APPROVAL, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Customer "C303" tries to cancel the filing for "MedLife"
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed (cannot cancel rejected applications)
        assertFalse("Cancellation of rejected application should return false", result);
        
        // Verify application status remains REJECTED
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Rejected application status should remain REJECTED", 
                    ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Customer "C304" requests cancellation for "UnknownCorp"
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed (no application found for specified company)
        assertFalse("Cancellation for non-existent company should return false", result);
        
        // Verify customer has no applications
        assertEquals("Customer should have no applications", 
                    0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Customer "C305" cancels "UrbanTech" filing
        boolean result = customerC305.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation of pending UrbanTech application should return true", result);
        
        // Verify UrbanTech application status changed to REJECTED (cancelled)
        Application urbanTechApp = findApplicationByCompany(customerC305, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("Cancelled UrbanTech application status should be REJECTED", 
                    ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING (unaffected)
        Application agroSeedApp = findApplicationByCompany(customerC305, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application status should remain PENDING", 
                    ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should have 2 applications", 
                    2, customerC305.getApplications().size());
    }
    
    private Application findApplicationByCompany(Customer customer, String companyName) {
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals(companyName)) {
                return app;
            }
        }
        return null;
    }
}