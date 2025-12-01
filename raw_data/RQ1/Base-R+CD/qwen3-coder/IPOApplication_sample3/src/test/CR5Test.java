import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company ecoWave, smartGrid, medLife, urbanTech, agroSeed;
    private Document document1, document2, document3, document4, document5;
    
    @Before
    public void setUp() {
        // Initialize customers
        customer1 = new Customer();
        customer1.setName("Benjamin");
        customer1.setSurname("Taylor");
        customer1.setEmail("b.taylor@example.com");
        customer1.setTelephone("555-1010");
        
        customer2 = new Customer();
        customer2.setName("Charlotte");
        customer2.setSurname("Lee");
        customer2.setEmail("c.lee@example.com");
        customer2.setTelephone("555-2020");
        
        customer3 = new Customer();
        customer3.setName("Lucas");
        customer3.setSurname("Martin");
        customer3.setEmail("l.martin@example.com");
        customer3.setTelephone("555-3030");
        
        customer4 = new Customer();
        customer4.setName("Amelia");
        customer4.setSurname("Clark");
        customer4.setEmail("a.clark@example.com");
        customer4.setTelephone("555-4040");
        
        customer5 = new Customer();
        customer5.setName("Mia");
        customer5.setSurname("Anderson");
        customer5.setEmail("m.anderson@example.com");
        customer5.setTelephone("555-6060");
        
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
        agroSeed.setEmail("agroseed@farm.com");
        
        // Initialize documents
        document1 = new Document();
        document2 = new Document();
        document3 = new Document();
        document4 = new Document();
        document5 = new Document();
        
        // Setup applications for test cases
        
        // Test Case 1: Pending application for EcoWave
        customer1.createApplication(ecoWave, 15, 750.0, document1);
        
        // Test Case 2: Approved application for SmartGrid
        customer2.createApplication(smartGrid, 30, 3000.0, document2);
        // Approve the application
        if (!customer2.getApplications().isEmpty()) {
            customer2.getApplications().get(0).approve();
        }
        
        // Test Case 3: Rejected application for MedLife
        customer3.createApplication(medLife, 20, 1000.0, document3);
        // Reject the application
        if (!customer3.getApplications().isEmpty()) {
            customer3.getApplications().get(0).reject();
        }
        
        // Test Case 5: Two pending applications for UrbanTech and AgroSeed
        customer5.createApplication(urbanTech, 25, 1250.0, document4);
        customer5.createApplication(agroSeed, 40, 2000.0, document5);
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Customer "C301" requests cancellation for "EcoWave"
        // Setup: Customer has a pending application for "EcoWave"
        // Expected Output: True
        
        boolean result = customer1.cancelApplication("EcoWave");
        assertTrue("Should be able to cancel a pending application", result);
        
        // Verify the application status is now rejected
        if (!customer1.getApplications().isEmpty()) {
            assertEquals("Application status should be rejected after cancellation", 
                         ApplicationStatus.REJECTED, customer1.getApplications().get(0).getStatus());
        }
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        // Setup: Customer has an approved application for "SmartGrid"
        // Expected Output: False (Cannot cancel approved applications)
        
        boolean result = customer2.cancelApplication("SmartGrid");
        assertFalse("Should not be able to cancel an approved application", result);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Customer "C303" tries to cancel the filing for "MedLife"
        // Setup: Customer has a rejected application for "MedLife"
        // Expected Output: False (Application already finalized)
        
        boolean result = customer3.cancelApplication("MedLife");
        assertFalse("Should not be able to cancel a rejected application", result);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Customer "C304" requests cancellation for "UnknownCorp"
        // Setup: No filing exists IPO for "UnknownCorp"
        // Expected Output: False (No application found for specified company)
        
        boolean result = customer4.cancelApplication("UnknownCorp");
        assertFalse("Should not be able to cancel a non-existent application", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Customer "C306" cancels "UrbanTech" filing
        // Setup: Customer has two pending IPO applications
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        
        // First, verify both applications are pending
        assertEquals("Should have two applications initially", 2, customer5.getApplications().size());
        
        boolean result = customer5.cancelApplication("UrbanTech");
        assertTrue("Should be able to cancel the UrbanTech pending application", result);
        
        // Verify that UrbanTech application is canceled (rejected)
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        for (Application app : customer5.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application should be rejected after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application should still be pending", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}