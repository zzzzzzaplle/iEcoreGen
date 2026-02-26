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
        
        // Setup applications for test cases
        // C301: Pending application for EcoWave
        Application appC301 = new Application();
        appC301.setShare(15);
        appC301.setAmountOfMoney(750);
        appC301.setStatus(ApplicationStatus.PENDING);
        appC301.setCustomer(customerC301);
        appC301.setCompany(ecoWave);
        appC301.setAllowance(docEW);
        customerC301.getApplications().add(appC301);
        
        // C302: Approved application for SmartGrid
        Application appC302 = new Application();
        appC302.setShare(30);
        appC302.setAmountOfMoney(3000);
        appC302.setStatus(ApplicationStatus.APPROVED);
        appC302.setCustomer(customerC302);
        appC302.setCompany(smartGrid);
        appC302.setAllowance(docSG1);
        customerC302.getApplications().add(appC302);
        
        // C303: Rejected application for MedLife
        Application appC303 = new Application();
        appC303.setShare(20);
        appC303.setAmountOfMoney(1000);
        appC303.setStatus(ApplicationStatus.REJECTED);
        appC303.setCustomer(customerC303);
        appC303.setCompany(medLife);
        appC303.setAllowance(docSG3);
        customerC303.getApplications().add(appC303);
        
        // C306: Two pending applications for UrbanTech and AgroSeed
        Application appC306_1 = new Application();
        appC306_1.setShare(25);
        appC306_1.setAmountOfMoney(1250);
        appC306_1.setStatus(ApplicationStatus.PENDING);
        appC306_1.setCustomer(customerC306);
        appC306_1.setCompany(urbanTech);
        appC306_1.setAllowance(docUT);
        customerC306.getApplications().add(appC306_1);
        
        Application appC306_2 = new Application();
        appC306_2.setShare(40);
        appC306_2.setAmountOfMoney(2000);
        appC306_2.setStatus(ApplicationStatus.PENDING);
        appC306_2.setCustomer(customerC306);
        appC306_2.setCompany(agroSeed);
        appC306_2.setAllowance(docAS);
        customerC306.getApplications().add(appC306_2);
    }

    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test cancelling a pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        assertTrue("Cancellation of pending application should succeed", result);
        
        // Verify the application status changed to rejected
        Application canceledApp = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, canceledApp.getStatus());
    }

    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test cancelling an approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        assertFalse("Cancellation of approved application should fail", result);
        
        // Verify the application status remains approved
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, approvedApp.getStatus());
    }

    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test cancelling a rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        assertFalse("Cancellation of rejected application should fail", result);
        
        // Verify the application status remains rejected
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }

    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test cancelling application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        assertFalse("Cancellation for non-existent company should fail", result);
    }

    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test cancelling one application while another remains unaffected
        boolean result = customerC306.cancelApplication("UrbanTech");
        assertTrue("Cancellation of pending UrbanTech application should succeed", result);
        
        // Verify UrbanTech application was canceled
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        
        for (Application app : customerC306.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("UrbanTech application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}