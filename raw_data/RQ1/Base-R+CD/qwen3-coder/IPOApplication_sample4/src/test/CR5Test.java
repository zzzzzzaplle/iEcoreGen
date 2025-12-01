import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company ecoWave, smartGrid, medLife, urbanTech, agroSeed;
    private Document doc1, doc2, doc3, doc4, doc5;
    
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
        agroSeed.setEmail("agroseed@farming.com");
        
        // Initialize documents
        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
        
        // Create applications as per test specifications
        
        // Customer 1 - Pending application for EcoWave
        customer1.createApplication(ecoWave, 15, 750.0, doc1);
        
        // Customer 2 - Approved application for SmartGrid
        customer2.createApplication(smartGrid, 30, 3000.0, doc2);
        // Approve the application
        if (!customer2.getApplications().isEmpty()) {
            customer2.getApplications().get(0).approve();
        }
        
        // Customer 3 - Rejected application for MedLife
        customer3.createApplication(medLife, 20, 1000.0, doc3);
        // Reject the application
        if (!customer3.getApplications().isEmpty()) {
            customer3.getApplications().get(0).reject();
        }
        
        // Customer 5 - Two pending applications
        customer5.createApplication(urbanTech, 25, 1250.0, doc4);
        customer5.createApplication(agroSeed, 40, 2000.0, doc5);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Customer "C301" (Benjamin Taylor) requests cancellation for "EcoWave"
        // The application for EcoWave is pending
        boolean result = customer1.cancelApplication("EcoWave");
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify that the application status is now REJECTED (canceled)
        assertEquals(ApplicationStatus.REJECTED, customer1.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Customer "C302" (Charlotte Lee) tries to cancel IPO for "SmartGrid"
        // The application for SmartGrid is approved
        boolean result = customer2.cancelApplication("SmartGrid");
        
        // Expected Output: False (Cannot cancel approved applications)
        assertFalse(result);
        
        // Verify that the application status is still APPROVAL
        assertEquals(ApplicationStatus.APPROVAL, customer2.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Customer "C303" (Lucas Martin) tries to cancel the filing for "MedLife"
        // The application for MedLife is rejected
        boolean result = customer3.cancelApplication("MedLife");
        
        // Expected Output: False (Application already finalized)
        assertFalse(result);
        
        // Verify that the application status is still REJECTED
        assertEquals(ApplicationStatus.REJECTED, customer3.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Customer "C304" (Amelia Clark) requests cancellation for "UnknownCorp"
        // No filing exists IPO for "UnknownCorp"
        boolean result = customer4.cancelApplication("UnknownCorp");
        
        // Expected Output: False (No application found for specified company)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Customer "C306" (Mia Anderson) cancels "UrbanTech" filing
        // Customer has two pending IPOs: "UrbanTech" and "AgroSeed"
        boolean result = customer5.cancelApplication("UrbanTech");
        
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        assertTrue(result);
        
        // Verify that the UrbanTech application status is now REJECTED (canceled)
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        
        for (Application app : customer5.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull(urbanTechApp);
        assertEquals(ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify that the AgroSeed application is still PENDING
        assertNotNull(agroSeedApp);
        assertEquals(ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}