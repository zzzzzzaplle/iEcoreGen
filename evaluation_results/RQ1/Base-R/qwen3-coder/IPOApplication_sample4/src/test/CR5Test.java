import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private BankSystem bankSystem;
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;

    @Before
    public void setUp() {
        bankSystem = new BankSystem();
        
        // Setup Customer C301 (Benjamin Taylor) with pending EcoWave application
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        IPOApplication app4001 = new IPOApplication();
        app4001.setCustomer(customerC301);
        app4001.setCompany("EcoWave");
        app4001.setNumberOfShares(15);
        app4001.setAmount(750.0);
        app4001.setDocument("EW-2024-03");
        // Status remains pending (not approved, not rejected, not reviewed)
        
        customerC301.getApplications().add(app4001);
        bankSystem.getApplications().add(app4001);
        
        // Setup Customer C302 (Charlotte Lee) with approved SmartGrid application
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        IPOApplication app4002 = new IPOApplication();
        app4002.setCustomer(customerC302);
        app4002.setCompany("SmartGrid");
        app4002.setNumberOfShares(30);
        app4002.setAmount(3000.0);
        app4002.setDocument("SG-2024-01");
        app4002.setApproved(true); // Approved application
        
        customerC302.getApplications().add(app4002);
        bankSystem.getApplications().add(app4002);
        
        // Setup Customer C303 (Lucas Martin) with rejected MedLife application
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        IPOApplication app4003 = new IPOApplication();
        app4003.setCustomer(customerC303);
        app4003.setCompany("MedLife");
        app4003.setNumberOfShares(20);
        app4003.setAmount(1000.0);
        app4003.setDocument("SG-2024-03");
        app4003.setRejected(true); // Rejected application
        
        customerC303.getApplications().add(app4003);
        bankSystem.getApplications().add(app4003);
        
        // Setup Customer C304 (Amelia Clark) with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        // Setup Customer C306 (Mia Anderson) with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        
        IPOApplication app4005 = new IPOApplication();
        app4005.setCustomer(customerC306);
        app4005.setCompany("UrbanTech");
        app4005.setNumberOfShares(25);
        app4005.setAmount(1250.0);
        app4005.setDocument("SG-2024-005");
        // Status remains pending
        
        IPOApplication app4006 = new IPOApplication();
        app4006.setCustomer(customerC306);
        app4006.setCompany("AgroSeed");
        app4006.setNumberOfShares(40);
        app4006.setAmount(2000.0);
        app4006.setDocument("SG-2024-006");
        // Status remains pending
        
        customerC306.getApplications().add(app4005);
        customerC306.getApplications().add(app4006);
        bankSystem.getApplications().add(app4005);
        bankSystem.getApplications().add(app4006);
    }

    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Input: Customer "C301" requests cancellation for "EcoWave"
        boolean result = bankSystem.cancelPendingApplication(customerC301, "EcoWave");
        
        // Expected Output: True
        assertTrue("Pending application should be successfully canceled", result);
        
        // Verify the application was removed from the bank system
        assertFalse("Application should be removed from bank system", 
            bankSystem.getApplications().stream()
                .anyMatch(app -> app.getCompany().equals("EcoWave") && app.getCustomer() == customerC301));
    }

    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Input: Customer "C302" tries to cancel IPO for "SmartGrid"
        boolean result = bankSystem.cancelPendingApplication(customerC302, "SmartGrid");
        
        // Expected Output: False (Cannot cancel approved applications)
        assertFalse("Approved application should not be cancelable", result);
        
        // Verify the approved application still exists
        assertTrue("Approved application should still exist in bank system",
            bankSystem.getApplications().stream()
                .anyMatch(app -> app.getCompany().equals("SmartGrid") && app.getCustomer() == customerC302));
    }

    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Input: Customer "C303" tries to cancel the filing for "MedLife"
        boolean result = bankSystem.cancelPendingApplication(customerC303, "MedLife");
        
        // Expected Output: False (Application already finalized)
        assertFalse("Rejected application should not be cancelable", result);
        
        // Verify the rejected application still exists
        assertTrue("Rejected application should still exist in bank system",
            bankSystem.getApplications().stream()
                .anyMatch(app -> app.getCompany().equals("MedLife") && app.getCustomer() == customerC303));
    }

    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Input: Customer "C304" requests cancellation for "UnknownCorp"
        boolean result = bankSystem.cancelPendingApplication(customerC304, "UnknownCorp");
        
        // Expected Output: False (No application found for specified company)
        assertFalse("Cancellation for nonexistent company should return false", result);
    }

    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Input: Customer "C306" cancels "UrbanTech" filing
        boolean result = bankSystem.cancelPendingApplication(customerC306, "UrbanTech");
        
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        assertTrue("Pending UrbanTech application should be successfully canceled", result);
        
        // Verify UrbanTech application was removed
        assertFalse("UrbanTech application should be removed from bank system",
            bankSystem.getApplications().stream()
                .anyMatch(app -> app.getCompany().equals("UrbanTech") && app.getCustomer() == customerC306));
        
        // Verify AgroSeed application still exists and is pending
        assertTrue("AgroSeed application should still exist",
            bankSystem.getApplications().stream()
                .anyMatch(app -> app.getCompany().equals("AgroSeed") && app.getCustomer() == customerC306));
        
        // Verify AgroSeed application is still pending (not reviewed)
        IPOApplication agroSeedApp = bankSystem.getApplications().stream()
            .filter(app -> app.getCompany().equals("AgroSeed") && app.getCustomer() == customerC306)
            .findFirst()
            .orElse(null);
        assertNotNull("AgroSeed application should be found", agroSeedApp);
        assertFalse("AgroSeed application should not be reviewed", agroSeedApp.isReviewed());
    }
}