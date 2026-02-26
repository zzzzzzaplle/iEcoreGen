import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmailAddress("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        customer.setEligibleForIPO(true);
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmountOfMoney(750.0);
        pendingApp.setDocument("EW-2024-03");
        // Status remains pending (not approved, not rejected, not reviewed)
        
        customer.getApplications().add(pendingApp);
        system.getApplications().add(pendingApp);
        
        // Test: Cancel pending application for EcoWave
        boolean result = system.cancelPendingApplication(customer, "EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Should return true for successful cancellation of pending application", result);
        
        // Verify application is now marked as rejected (cancelled)
        assertTrue("Application should be marked as rejected after cancellation", pendingApp.isRejected());
        assertTrue("Application should be marked as reviewed after cancellation", pendingApp.isReviewed());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmailAddress("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        customer.setEligibleForIPO(true);
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmountOfMoney(3000.0);
        approvedApp.setDocument("SG-2024-01");
        approvedApp.setApproved(true); // Mark as approved
        
        customer.getApplications().add(approvedApp);
        system.getApplications().add(approvedApp);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = system.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Should return false when trying to cancel approved application", result);
        
        // Verify application status remains unchanged
        assertTrue("Application should remain approved", approvedApp.isApproved());
        assertFalse("Application should not be rejected", approvedApp.isRejected());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmailAddress("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        customer.setEligibleForIPO(true);
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmountOfMoney(1000.0);
        rejectedApp.setDocument("SG-2024-03");
        rejectedApp.setRejected(true); // Mark as rejected
        
        customer.getApplications().add(rejectedApp);
        system.getApplications().add(rejectedApp);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = system.cancelPendingApplication(customer, "MedLife");
        
        // Verify cancellation failed
        assertFalse("Should return false when trying to cancel rejected application", result);
        
        // Verify application status remains unchanged
        assertTrue("Application should remain rejected", rejectedApp.isRejected());
        assertFalse("Application should not be approved", rejectedApp.isApproved());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmailAddress("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        customer.setEligibleForIPO(true);
        
        // Customer has no applications
        
        // Test: Try to cancel application for non-existent company
        boolean result = system.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Should return false when no application exists for the company", result);
        
        // Verify customer's application list remains empty
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmailAddress("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        customer.setEligibleForIPO(true);
        
        IPOApplication urbanTechApp = new IPOApplication();
        urbanTechApp.setCompanyName("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmountOfMoney(1250.0);
        urbanTechApp.setDocument("SG-2024-005");
        
        IPOApplication agroSeedApp = new IPOApplication();
        agroSeedApp.setCompanyName("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroSeedApp.setAmountOfMoney(2000.0);
        agroSeedApp.setDocument("SG-2024-006");
        
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        system.getApplications().add(urbanTechApp);
        system.getApplications().add(agroSeedApp);
        
        // Test: Cancel UrbanTech application
        boolean result = system.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify UrbanTech cancellation was successful
        assertTrue("Should return true for successful cancellation of UrbanTech application", result);
        
        // Verify UrbanTech application is marked as rejected
        assertTrue("UrbanTech application should be marked as rejected", urbanTechApp.isRejected());
        assertTrue("UrbanTech application should be marked as reviewed", urbanTechApp.isReviewed());
        
        // Verify AgroSeed application remains pending and unaffected
        assertFalse("AgroSeed application should not be approved", agroSeedApp.isApproved());
        assertFalse("AgroSeed application should not be rejected", agroSeedApp.isRejected());
        assertFalse("AgroSeed application should not be reviewed", agroSeedApp.isReviewed());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 2, customer.getApplications().size());
    }
}