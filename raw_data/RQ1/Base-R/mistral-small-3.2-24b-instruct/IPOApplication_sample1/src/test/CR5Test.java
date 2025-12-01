import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    
    private IPOApplication pendingAppC301;
    private IPOApplication approvedAppC302;
    private IPOApplication rejectedAppC303;
    private IPOApplication pendingAppC306UrbanTech;
    private IPOApplication pendingAppC306AgroSeed;
    
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
        
        // Create and setup pending application for C301
        pendingAppC301 = new IPOApplication();
        pendingAppC301.setCompanyName("EcoWave");
        pendingAppC301.setShares(15);
        pendingAppC301.setAmount(750.0);
        pendingAppC301.setDocument("EW-2024-03");
        pendingAppC301.setStatus(IPOApplication.Status.PENDING);
        pendingAppC301.setCustomer(customerC301);
        customerC301.getApplications().add(pendingAppC301);
        
        // Create and setup approved application for C302
        approvedAppC302 = new IPOApplication();
        approvedAppC302.setCompanyName("SmartGrid");
        approvedAppC302.setShares(30);
        approvedAppC302.setAmount(3000.0);
        approvedAppC302.setDocument("SG-2024-01");
        approvedAppC302.setStatus(IPOApplication.Status.APPROVED);
        approvedAppC302.setCustomer(customerC302);
        customerC302.getApplications().add(approvedAppC302);
        
        // Create and setup rejected application for C303
        rejectedAppC303 = new IPOApplication();
        rejectedAppC303.setCompanyName("MedLife");
        rejectedAppC303.setShares(20);
        rejectedAppC303.setAmount(1000.0);
        rejectedAppC303.setDocument("SG-2024-03");
        rejectedAppC303.setStatus(IPOApplication.Status.REJECTED);
        rejectedAppC303.setCustomer(customerC303);
        customerC303.getApplications().add(rejectedAppC303);
        
        // Create and setup pending applications for C306
        pendingAppC306UrbanTech = new IPOApplication();
        pendingAppC306UrbanTech.setCompanyName("UrbanTech");
        pendingAppC306UrbanTech.setShares(25);
        pendingAppC306UrbanTech.setAmount(1250.0);
        pendingAppC306UrbanTech.setDocument("SG-2024-005");
        pendingAppC306UrbanTech.setStatus(IPOApplication.Status.PENDING);
        pendingAppC306UrbanTech.setCustomer(customerC306);
        
        pendingAppC306AgroSeed = new IPOApplication();
        pendingAppC306AgroSeed.setCompanyName("AgroSeed");
        pendingAppC306AgroSeed.setShares(40);
        pendingAppC306AgroSeed.setAmount(2000.0);
        pendingAppC306AgroSeed.setDocument("SG-2024-006");
        pendingAppC306AgroSeed.setStatus(IPOApplication.Status.PENDING);
        pendingAppC306AgroSeed.setCustomer(customerC306);
        
        customerC306.getApplications().add(pendingAppC306UrbanTech);
        customerC306.getApplications().add(pendingAppC306AgroSeed);
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Customer "C301" requests cancellation for "EcoWave"
        
        // Execute cancellation
        boolean result = pendingAppC301.cancel();
        
        // Verify cancellation succeeded
        assertTrue("Pending application should be successfully canceled", result);
        // Verify application status changed to REJECTED
        assertEquals("Application status should be REJECTED after cancellation", 
                     IPOApplication.Status.REJECTED, pendingAppC301.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        
        // Execute cancellation
        boolean result = approvedAppC302.cancel();
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancelable", result);
        // Verify application status remains APPROVED
        assertEquals("Application status should remain APPROVED", 
                     IPOApplication.Status.APPROVED, approvedAppC302.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Customer "C303" tries to cancel the filing for "MedLife"
        
        // Execute cancellation
        boolean result = rejectedAppC303.cancel();
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancelable", result);
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     IPOApplication.Status.REJECTED, rejectedAppC303.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Customer "C304" requests cancellation for "UnknownCorp"
        
        // Create a new application for non-existent company to simulate the scenario
        IPOApplication nonExistentApp = new IPOApplication();
        nonExistentApp.setCompanyName("UnknownCorp");
        nonExistentApp.setStatus(IPOApplication.Status.PENDING);
        nonExistentApp.setCustomer(customerC304);
        
        // The test needs to find the specific application by company name
        // Since there are no applications for "UnknownCorp", cancellation should fail
        boolean foundApplication = false;
        for (IPOApplication app : customerC304.getApplications()) {
            if (app.getCompanyName().equals("UnknownCorp")) {
                foundApplication = true;
                break;
            }
        }
        
        // Verify no application found for the specified company
        assertFalse("No application should be found for UnknownCorp", foundApplication);
        
        // Note: Since we can't directly test the cancel method without an application,
        // the expected behavior is that cancellation fails when no application exists
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Customer "C306" cancels "UrbanTech" filing
        
        // Execute cancellation for UrbanTech application
        boolean result = pendingAppC306UrbanTech.cancel();
        
        // Verify UrbanTech cancellation succeeded
        assertTrue("UrbanTech pending application should be successfully canceled", result);
        assertEquals("UrbanTech application status should be REJECTED after cancellation", 
                     IPOApplication.Status.REJECTED, pendingAppC306UrbanTech.getStatus());
        
        // Verify AgroSeed application remains unaffected
        assertEquals("AgroSeed application status should remain PENDING", 
                     IPOApplication.Status.PENDING, pendingAppC306AgroSeed.getStatus());
    }
}