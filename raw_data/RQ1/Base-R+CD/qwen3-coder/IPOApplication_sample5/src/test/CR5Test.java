import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company ecoWave, smartGrid, medLife, urbanTech, agroSeed;
    private Document document1, document2, document3, document5, document6;
    
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
        document5 = new Document();
        document6 = new Document();
        
        // Setup pending application for customer1 (C301) - Test Case 1
        Application app1 = new Application();
        app1.setShare(15);
        app1.setAmountOfMoney(750.0);
        app1.setCustomer(customer1);
        app1.setCompany(ecoWave);
        app1.setAllowance(document1);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setEmails(new ArrayList<Email>());
        
        List<Application> apps1 = new ArrayList<>();
        apps1.add(app1);
        customer1.setApplications(apps1);
        
        // Setup approved application for customer2 (C302) - Test Case 2
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(3000.0);
        app2.setCustomer(customer2);
        app2.setCompany(smartGrid);
        app2.setAllowance(document2);
        app2.setStatus(ApplicationStatus.APPROVED);
        app2.setEmails(new ArrayList<Email>());
        
        List<Application> apps2 = new ArrayList<>();
        apps2.add(app2);
        customer2.setApplications(apps2);
        
        // Setup rejected application for customer3 (C303) - Test Case 3
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setCustomer(customer3);
        app3.setCompany(medLife);
        app3.setAllowance(document3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setEmails(new ArrayList<Email>());
        
        List<Application> apps3 = new ArrayList<>();
        apps3.add(app3);
        customer3.setApplications(apps3);
        
        // Setup two pending applications for customer5 (C306) - Test Case 5
        Application app5 = new Application();
        app5.setShare(25);
        app5.setAmountOfMoney(1250.0);
        app5.setCustomer(customer5);
        app5.setCompany(urbanTech);
        app5.setAllowance(document5);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setEmails(new ArrayList<Email>());
        
        Application app6 = new Application();
        app6.setShare(40);
        app6.setAmountOfMoney(2000.0);
        app6.setCustomer(customer5);
        app6.setCompany(agroSeed);
        app6.setAllowance(document6);
        app6.setStatus(ApplicationStatus.PENDING);
        app6.setEmails(new ArrayList<Email>());
        
        List<Application> apps5 = new ArrayList<>();
        apps5.add(app5);
        apps5.add(app6);
        customer5.setApplications(apps5);
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Customer "C301" requests cancellation for "EcoWave"
        // Setup is done in setUp() method
        // Pending Application: APP-4001 for EcoWave, 15 shares, $750, Status: pending
        
        boolean result = customer1.cancelApplication("EcoWave");
        
        // Expected Output: True
        assertTrue(result);
        
        // Verify that the application status is now REJECTED (canceled)
        assertEquals(ApplicationStatus.REJECTED, customer1.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        // Setup is done in setUp() method
        // Approved Application: APP-4002 for SmartGrid, 30 shares, $3000, Status: approved
        
        boolean result = customer2.cancelApplication("SmartGrid");
        
        // Expected Output: False (Cannot cancel approved applications)
        assertFalse(result);
        
        // Verify that the application status is still APPROVED
        assertEquals(ApplicationStatus.APPROVED, customer2.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Customer "C303" tries to cancel the filing for "MedLife"
        // Setup is done in setUp() method
        // Rejected Application: APP-4003 for MedLife, 20 shares, $1000, Status: rejected
        
        boolean result = customer3.cancelApplication("MedLife");
        
        // Expected Output: False (Application already finalized)
        assertFalse(result);
        
        // Verify that the application status is still REJECTED
        assertEquals(ApplicationStatus.REJECTED, customer3.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Customer "C304" requests cancellation for "UnknownCorp"
        // Setup is done in setUp() method
        // No filing exists IPO for "UnknownCorp"
        
        boolean result = customer4.cancelApplication("UnknownCorp");
        
        // Expected Output: False (No application found for specified company)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Customer "C306" cancels "UrbanTech" filing
        // Setup is done in setUp() method
        // Two pending applications: "UrbanTech" and "AgroSeed"
        
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