import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private IPOApplication app4001;
    private IPOApplication app4002;
    private IPOApplication app4003;
    private IPOApplication app4005;
    private IPOApplication app4006;
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: Customer C301 with pending EcoWave application
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        app4001 = new IPOApplication();
        app4001.setCustomer(customerC301);
        app4001.setCompanyName("EcoWave");
        app4001.setNumberOfShares(15);
        app4001.setAmount(750.0);
        app4001.setDocument("EW-2024-03");
        app4001.setStatus(ApplicationStatus.PENDING);
        customerC301.getApplications().add(app4001);
        
        // Setup for Test Case 2: Customer C302 with approved SmartGrid application
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        app4002 = new IPOApplication();
        app4002.setCustomer(customerC302);
        app4002.setCompanyName("SmartGrid");
        app4002.setNumberOfShares(30);
        app4002.setAmount(3000.0);
        app4002.setDocument("SG-2024-01");
        app4002.setStatus(ApplicationStatus.APPROVED);
        customerC302.getApplications().add(app4002);
        
        // Setup for Test Case 3: Customer C303 with rejected MedLife application
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        app4003 = new IPOApplication();
        app4003.setCustomer(customerC303);
        app4003.setCompanyName("MedLife");
        app4003.setNumberOfShares(20);
        app4003.setAmount(1000.0);
        app4003.setDocument("SG-2024-03");
        app4003.setStatus(ApplicationStatus.REJECTED);
        customerC303.getApplications().add(app4003);
        
        // Setup for Test Case 4: Customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        // Setup for Test Case 5: Customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        
        app4005 = new IPOApplication();
        app4005.setCustomer(customerC306);
        app4005.setCompanyName("UrbanTech");
        app4005.setNumberOfShares(25);
        app4005.setAmount(1250.0);
        app4005.setDocument("SG-2024-005");
        app4005.setStatus(ApplicationStatus.PENDING);
        
        app4006 = new IPOApplication();
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
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request for EcoWave
        boolean result = IPOApplication.cancelPendingApplication(customerC301, "EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify application was removed from customer's applications
        assertEquals("Customer should have 0 applications after cancellation", 
                     0, customerC301.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: Cancel approved request for SmartGrid
        boolean result = IPOApplication.cancelPendingApplication(customerC302, "SmartGrid");
        
        // Verify cancellation failed (cannot cancel approved applications)
        assertFalse("Cancellation of approved application should return false", result);
        
        // Verify application remains in customer's applications
        assertEquals("Customer should still have 1 application", 
                     1, customerC302.getApplications().size());
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, customerC302.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: Cancel rejected request for MedLife
        boolean result = IPOApplication.cancelPendingApplication(customerC303, "MedLife");
        
        // Verify cancellation failed (cannot cancel rejected applications)
        assertFalse("Cancellation of rejected application should return false", result);
        
        // Verify application remains in customer's applications
        assertEquals("Customer should still have 1 application", 
                     1, customerC303.getApplications().size());
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, customerC303.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: Cancel application for non-existent company UnknownCorp
        boolean result = IPOApplication.cancelPendingApplication(customerC304, "UnknownCorp");
        
        // Verify cancellation failed (no application found)
        assertFalse("Cancellation for non-existent company should return false", result);
        
        // Verify customer still has no applications
        assertEquals("Customer should have 0 applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: Cancel UrbanTech application while AgroSeed remains
        boolean result = IPOApplication.cancelPendingApplication(customerC306, "UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("Cancellation of pending application should return true", result);
        
        // Verify UrbanTech application was removed
        assertEquals("Customer should have 1 application remaining", 
                     1, customerC306.getApplications().size());
        
        // Verify AgroSeed application remains unaffected
        IPOApplication remainingApp = customerC306.getApplications().get(0);
        assertEquals("Remaining application should be for AgroSeed", 
                     "AgroSeed", remainingApp.getCompanyName());
        assertEquals("AgroSeed application should still be PENDING", 
                     ApplicationStatus.PENDING, remainingApp.getStatus());
    }
}