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
    private Document docSG;
    private Document docML;
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
        docSG = new Document();
        docML = new Document();
        docUT = new Document();
        docAS = new Document();
        
        // Initialize customers and their applications
        // Customer C301 - Benjamin Taylor
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        // Create pending application for EcoWave
        Application appC301 = new Application();
        appC301.setShare(15);
        appC301.setAmountOfMoney(750);
        appC301.setCustomer(customerC301);
        appC301.setCompany(ecoWave);
        appC301.setAllowance(docEW);
        appC301.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(appC301);
        
        // Customer C302 - Charlotte Lee
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        // Create approved application for SmartGrid
        Application appC302 = new Application();
        appC302.setShare(30);
        appC302.setAmountOfMoney(3000);
        appC302.setCustomer(customerC302);
        appC302.setCompany(smartGrid);
        appC302.setAllowance(docSG);
        appC302.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(appC302);
        
        // Customer C303 - Lucas Martin
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        // Create rejected application for MedLife
        Application appC303 = new Application();
        appC303.setShare(20);
        appC303.setAmountOfMoney(1000);
        appC303.setCustomer(customerC303);
        appC303.setCompany(medLife);
        appC303.setAllowance(docML);
        appC303.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(appC303);
        
        // Customer C304 - Amelia Clark
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        // No applications for this customer
        
        // Customer C306 - Mia Anderson
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        // Create two pending applications for UrbanTech and AgroSeed
        Application appUT = new Application();
        appUT.setShare(25);
        appUT.setAmountOfMoney(1250);
        appUT.setCustomer(customerC306);
        appUT.setCompany(urbanTech);
        appUT.setAllowance(docUT);
        appUT.setStatus(ApplicationStatus.PENDING);
        customerC306.getApplications().add(appUT);
        
        Application appAS = new Application();
        appAS.setShare(40);
        appAS.setAmountOfMoney(2000);
        appAS.setCustomer(customerC306);
        appAS.setCompany(agroSeed);
        appAS.setAllowance(docAS);
        appAS.setStatus(ApplicationStatus.PENDING);
        customerC306.getApplications().add(appAS);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Customer "C301" requests cancellation for "EcoWave"
        
        // Verify initial state - application exists and is pending
        assertEquals(1, customerC301.getApplications().size());
        assertEquals(ApplicationStatus.PENDING, customerC301.getApplications().get(0).getStatus());
        assertEquals("EcoWave", customerC301.getApplications().get(0).getCompany().getName());
        
        // Execute cancellation
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for pending application", result);
        
        // Verify application status changed to rejected after cancellation
        assertEquals(ApplicationStatus.REJECTED, customerC301.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        
        // Verify initial state - application exists and is approved
        assertEquals(1, customerC302.getApplications().size());
        assertEquals(ApplicationStatus.APPROVAL, customerC302.getApplications().get(0).getStatus());
        assertEquals("SmartGrid", customerC302.getApplications().get(0).getCompany().getName());
        
        // Execute cancellation
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for approved application", result);
        
        // Verify application status remains approved
        assertEquals(ApplicationStatus.APPROVAL, customerC302.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Customer "C303" tries to cancel the filing for "MedLife"
        
        // Verify initial state - application exists and is rejected
        assertEquals(1, customerC303.getApplications().size());
        assertEquals(ApplicationStatus.REJECTED, customerC303.getApplications().get(0).getStatus());
        assertEquals("MedLife", customerC303.getApplications().get(0).getCompany().getName());
        
        // Execute cancellation
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for rejected application", result);
        
        // Verify application status remains rejected
        assertEquals(ApplicationStatus.REJECTED, customerC303.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Customer "C304" requests cancellation for "UnknownCorp"
        
        // Verify initial state - no applications exist
        assertEquals(0, customerC304.getApplications().size());
        
        // Execute cancellation for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail when no application exists for the company", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Customer "C306" cancels "UrbanTech" filing
        
        // Verify initial state - two pending applications exist
        assertEquals(2, customerC306.getApplications().size());
        
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        
        // Identify the UrbanTech and AgroSeed applications
        for (Application app : customerC306.getApplications()) {
            if ("UrbanTech".equals(app.getCompany().getName())) {
                urbanTechApp = app;
            } else if ("AgroSeed".equals(app.getCompany().getName())) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals(ApplicationStatus.PENDING, urbanTechApp.getStatus());
        assertEquals(ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Execute cancellation for UrbanTech
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for UrbanTech pending application", result);
        
        // Verify UrbanTech application status changed to rejected
        assertEquals(ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains pending and unaffected
        assertEquals(ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify both applications still exist in the list
        assertEquals(2, customerC306.getApplications().size());
    }
}