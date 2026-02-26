import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Bank bank;
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;

    @Before
    public void setUp() {
        bank = new Bank();
        
        // Setup customer C301 with pending application for EcoWave
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephoneNumber("555-1010");
        IPOApplication app4001 = new IPOApplication();
        app4001.setCustomer(customerC301);
        app4001.setCompanyName("EcoWave");
        app4001.setNumberOfShares(15);
        app4001.setAmount(750.0);
        app4001.setDocument("EW-2024-03");
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);
        
        // Setup customer C302 with approved application for SmartGrid
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephoneNumber("555-2020");
        IPOApplication app4002 = new IPOApplication();
        app4002.setCustomer(customerC302);
        app4002.setCompanyName("SmartGrid");
        app4002.setNumberOfShares(30);
        app4002.setAmount(3000.0);
        app4002.setDocument("SG-2024-01");
        app4002.setStatus(ApplicationStatus.APPROVED);
        customerC302.getApplications().add(app4002);
        
        // Setup customer C303 with rejected application for MedLife
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephoneNumber("555-3030");
        IPOApplication app4003 = new IPOApplication();
        app4003.setCustomer(customerC303);
        app4003.setCompanyName("MedLife");
        app4003.setNumberOfShares(20);
        app4003.setAmount(1000.0);
        app4003.setDocument("SG-2024-03");
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);
        
        // Setup customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephoneNumber("555-4040");
        
        // Setup customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephoneNumber("555-6060");
        
        IPOApplication app4005 = new IPOApplication();
        app4005.setCustomer(customerC306);
        app4005.setCompanyName("UrbanTech");
        app4005.setNumberOfShares(25);
        app4005.setAmount(1250.0);
        app4005.setDocument("SG-2024-005");
        app4005.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app4006 = new IPOApplication();
        app4006.setCustomer(customerC306);
        app4006.setCompanyName("AgroSeed");
        app4006.setNumberOfShares(40);
        app4006.setAmount(2000.0);
        app4006.setDocument("SG-2024-006");
        app4006.setStatus(ApplicationStatus.PENDING);
        
        customerC306.getApplications().add(app4005);
        customerC306.getApplications().add(app4006);
    }

    @Test
    public void testCase1_cancelPendingRequest() {
        // Test cancellation of pending application for EcoWave
        boolean result = bank.cancelPendingApplication(customerC301, "EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be cancellable", result);
        
        // Verify application status changed to REJECTED
        IPOApplication application = customerC301.getApplications().get(0);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }

    @Test
    public void testCase2_cancelApprovedRequest() {
        // Test cancellation of approved application for SmartGrid
        boolean result = bank.cancelPendingApplication(customerC302, "SmartGrid");
        
        // Verify cancellation failed because application is already approved
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify application status remains APPROVED
        IPOApplication application = customerC302.getApplications().get(0);
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, application.getStatus());
    }

    @Test
    public void testCase3_cancelRejectedRequest() {
        // Test cancellation of rejected application for MedLife
        boolean result = bank.cancelPendingApplication(customerC303, "MedLife");
        
        // Verify cancellation failed because application is already rejected
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify application status remains REJECTED
        IPOApplication application = customerC303.getApplications().get(0);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }

    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Test cancellation for company that doesn't exist in customer's applications
        boolean result = bank.cancelPendingApplication(customerC304, "UnknownCorp");
        
        // Verify cancellation failed because no application exists for specified company
        assertFalse("Non-existent application should not be cancellable", result);
    }

    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Test cancellation of UrbanTech application while AgroSeed remains unaffected
        boolean result = bank.cancelPendingApplication(customerC306, "UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be cancellable", result);
        
        // Verify UrbanTech application status changed to REJECTED
        IPOApplication urbanTechApp = null;
        IPOApplication agroSeedApp = null;
        
        for (IPOApplication app : customerC306.getApplications()) {
            if (app.getCompanyName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompanyName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}