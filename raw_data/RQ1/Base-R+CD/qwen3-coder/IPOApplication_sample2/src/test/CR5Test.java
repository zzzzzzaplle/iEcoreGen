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
        
        // Set up applications according to test specifications
        
        // Customer 1: Pending application for EcoWave
        Application app1 = new Application();
        app1.setShare(15);
        app1.setAmountOfMoney(750.0);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCustomer(customer1);
        app1.setCompany(ecoWave);
        app1.setAllowance(document1);
        customer1.getApplications().add(app1);
        
        // Customer 2: Approved application for SmartGrid
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(3000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer2);
        app2.setCompany(smartGrid);
        app2.setAllowance(document2);
        customer2.getApplications().add(app2);
        
        // Customer 3: Rejected application for MedLife
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer3);
        app3.setCompany(medLife);
        app3.setAllowance(document3);
        customer3.getApplications().add(app3);
        
        // Customer 5: Two pending applications - UrbanTech and AgroSeed
        Application app4 = new Application();
        app4.setShare(25);
        app4.setAmountOfMoney(1250.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer5);
        app4.setCompany(urbanTech);
        app4.setAllowance(document4);
        customer5.getApplications().add(app4);
        
        Application app5 = new Application();
        app5.setShare(40);
        app5.setAmountOfMoney(2000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer5);
        app5.setCompany(agroSeed);
        app5.setAllowance(document5);
        customer5.getApplications().add(app5);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Input: Customer "C301" requests cancellation for "EcoWave"
        // Setup: Customer has a pending application for "EcoWave"
        // Expected Output: True
        
        boolean result = customer1.cancelApplication("EcoWave");
        
        assertTrue("Should be able to cancel a pending application", result);
        assertEquals("Application status should be changed to REJECTED (canceled)", 
                    ApplicationStatus.REJECTED, 
                    customer1.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Input: Customer "C302" tries to cancel IPO for "SmartGrid"
        // Setup: Customer has an approved application for "SmartGrid"
        // Expected Output: False (Cannot cancel approved applications)
        
        boolean result = customer2.cancelApplication("SmartGrid");
        
        assertFalse("Should not be able to cancel an approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                    ApplicationStatus.APPROVAL, 
                    customer2.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Input: Customer "C303" tries to cancel the filing for "MedLife"
        // Setup: Customer has a rejected application for "MedLife"
        // Expected Output: False (Application already finalized)
        
        boolean result = customer3.cancelApplication("MedLife");
        
        assertFalse("Should not be able to cancel a rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                    ApplicationStatus.REJECTED, 
                    customer3.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Input: Customer "C304" requests cancellation for "UnknownCorp"
        // Setup: No filing exists IPO for "UnknownCorp"
        // Expected Output: False (No application found for specified company)
        
        boolean result = customer4.cancelApplication("UnknownCorp");
        
        assertFalse("Should not be able to cancel a non-existent application", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Input: Customer "C306" cancels "UrbanTech" filing
        // Setup: Customer has two pending IPO applications
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        
        boolean result = customer5.cancelApplication("UrbanTech");
        
        assertTrue("Should be able to cancel one of multiple pending applications", result);
        
        // Check that UrbanTech application is canceled (status changed to REJECTED)
        boolean urbanTechCanceled = false;
        boolean agroSeedStillPending = false;
        
        for (Application app : customer5.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechCanceled = (app.getStatus() == ApplicationStatus.REJECTED);
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedStillPending = (app.getStatus() == ApplicationStatus.PENDING);
            }
        }
        
        assertTrue("UrbanTech application should be canceled", urbanTechCanceled);
        assertTrue("AgroSeed application should still be pending", agroSeedStillPending);
    }
}