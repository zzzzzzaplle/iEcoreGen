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
    private Document docSG5;
    private Document docSG6;

    @Before
    public void setUp() {
        // Initialize companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@innovate.com");

        // Initialize documents
        docEW = new Document();
        docSG1 = new Document();
        docSG3 = new Document();
        docSG5 = new Document();
        docSG6 = new Document();

        // Setup Customer C301 with pending EcoWave application
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        Application app4001 = new Application(15, 750.0, customerC301, ecoWave, docEW);
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);

        // Setup Customer C302 with approved SmartGrid application
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        Application app4002 = new Application(30, 3000.0, customerC302, smartGrid, docSG1);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        customerC302.getApplications().add(app4002);

        // Setup Customer C303 with rejected MedLife application
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        Application app4003 = new Application(20, 1000.0, customerC303, medLife, docSG3);
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);

        // Setup Customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);

        // Setup Customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        Application app4005 = new Application(25, 1250.0, customerC306, urbanTech, docSG5);
        app4005.setStatus(ApplicationStatus.PENDING);
        
        Application app4006 = new Application(40, 2000.0, customerC306, agroSeed, docSG6);
        app4006.setStatus(ApplicationStatus.PENDING);
        
        customerC306.getApplications().add(app4005);
        customerC306.getApplications().add(app4006);
    }

    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Customer "C301" cancels pending application for "EcoWave"
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be canceled successfully", result);
        
        // Verify application status is now REJECTED (since cancel sets status to REJECTED)
        Application canceledApp = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, canceledApp.getStatus());
    }

    @Test
    public void testCase2_cancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Customer "C302" tries to cancel approved application for "SmartGrid"
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancelable", result);
        
        // Verify application status remains APPROVAL
        Application approvedApp = customerC302.getApplications().get(0);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, approvedApp.getStatus());
    }

    @Test
    public void testCase3_cancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Customer "C303" tries to cancel rejected application for "MedLife"
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancelable", result);
        
        // Verify application status remains REJECTED
        Application rejectedApp = customerC303.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }

    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Customer "C304" tries to cancel application for "UnknownCorp" (which doesn't exist)
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Non-existent application should not be cancelable", result);
        
        // Verify no applications were affected
        assertEquals("Customer should have no applications", 0, customerC304.getApplications().size());
    }

    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Customer "C306" cancels "UrbanTech" application
        
        // Get initial application count for verification
        int initialCount = customerC306.getApplications().size();
        
        // Cancel UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("Pending UrbanTech application should be canceled successfully", result);
        
        // Verify UrbanTech application status is now REJECTED
        Application urbanTechApp = findApplicationByCompany(customerC306, "UrbanTech");
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING and unaffected
        Application agroSeedApp = findApplicationByCompany(customerC306, "AgroSeed");
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify total application count remains the same
        assertEquals("Total application count should remain unchanged", 
                     initialCount, customerC306.getApplications().size());
    }

    // Helper method to find application by company name
    private Application findApplicationByCompany(Customer customer, String companyName) {
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals(companyName)) {
                return app;
            }
        }
        return null;
    }
}