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
    private Document docSG2;
    private Document docSG5;
    private Document docSG6;
    
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
        docSG2 = new Document();
        docSG5 = new Document();
        docSG6 = new Document();
        
        // Create customers
        customerC301 = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010");
        customerC302 = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020");
        customerC303 = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030");
        customerC304 = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040");
        customerC306 = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060");
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        Application app1 = new Application(15, 750.0, customerC301, ecoWave, docEW);
        app1.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app1);
        
        // Test: Cancel the pending application for "EcoWave"
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for pending application", result);
        
        // Verify application status changed to REJECTED
        assertEquals("Application status should be REJECTED after cancellation", 
                    ApplicationStatus.REJECTED, app1.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        Application app2 = new Application(30, 3000.0, customerC302, smartGrid, docSG1);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app2);
        
        // Test: Try to cancel the approved application for "SmartGrid"
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for approved application", result);
        
        // Verify application status remains APPROVAL
        assertEquals("Application status should remain APPROVAL", 
                    ApplicationStatus.APPROVAL, app2.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        Application app3 = new Application(20, 1000.0, customerC303, medLife, docSG2);
        app3.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app3);
        
        // Test: Try to cancel the rejected application for "MedLife"
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for rejected application", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                    ApplicationStatus.REJECTED, app3.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications for "UnknownCorp"
        // No setup needed - customer has no applications
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company application", result);
        
        // Verify customer still has no applications
        assertEquals("Customer should have no applications", 0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        Application app5 = new Application(25, 1250.0, customerC306, urbanTech, docSG5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        Application app6 = new Application(40, 2000.0, customerC306, agroSeed, docSG6);
        app6.setStatus(ApplicationStatus.PENDING);
        
        customerC306.getApplications().add(app5);
        customerC306.getApplications().add(app6);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        
        // Verify UrbanTech application status changed to REJECTED
        assertEquals("UrbanTech application should be REJECTED", 
                    ApplicationStatus.REJECTED, app5.getStatus());
        
        // Verify AgroSeed application status remains PENDING (unaffected)
        assertEquals("AgroSeed application should remain PENDING", 
                    ApplicationStatus.PENDING, app6.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 2, customerC306.getApplications().size());
    }
}