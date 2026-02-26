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
    
    @Before
    public void setUp() {
        // Setup for Test Case 1: Customer C301 with pending EcoWave application
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        IpoApplication app1 = new IpoApplication();
        app1.setCompanyName("EcoWave");
        app1.setShares(15);
        app1.setAmount(750.0);
        app1.setDocument("EW-2024-03");
        // Status remains pending (neither approved nor rejected)
        
        List<IpoApplication> apps1 = new ArrayList<>();
        apps1.add(app1);
        customerC301.setApplications(apps1);
        
        // Setup for Test Case 2: Customer C302 with approved SmartGrid application
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        IpoApplication app2 = new IpoApplication();
        app2.setCompanyName("SmartGrid");
        app2.setShares(30);
        app2.setAmount(3000.0);
        app2.setDocument("SG-2024-01");
        app2.setApproved(true); // Approved application
        
        List<IpoApplication> apps2 = new ArrayList<>();
        apps2.add(app2);
        customerC302.setApplications(apps2);
        
        // Setup for Test Case 3: Customer C303 with rejected MedLife application
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        IpoApplication app3 = new IpoApplication();
        app3.setCompanyName("MedLife");
        app3.setShares(20);
        app3.setAmount(1000.0);
        app3.setDocument("SG-2024-03");
        app3.setRejected(true); // Rejected application
        
        List<IpoApplication> apps3 = new ArrayList<>();
        apps3.add(app3);
        customerC303.setApplications(apps3);
        
        // Setup for Test Case 4: Customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setApplications(new ArrayList<>()); // Empty applications list
        
        // Setup for Test Case 5: Customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        
        IpoApplication app5 = new IpoApplication();
        app5.setCompanyName("UrbanTech");
        app5.setShares(25);
        app5.setAmount(1250.0);
        app5.setDocument("SG-2024-005");
        // Status pending
        
        IpoApplication app6 = new IpoApplication();
        app6.setCompanyName("AgroSeed");
        app6.setShares(40);
        app6.setAmount(2000.0);
        app6.setDocument("SG-2024-006");
        // Status pending
        
        List<IpoApplication> apps5 = new ArrayList<>();
        apps5.add(app5);
        apps5.add(app6);
        customerC306.setApplications(apps5);
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Input: Customer C301 requests cancellation for "EcoWave"
        boolean result = customerC301.cancelPendingApplication("EcoWave");
        
        // Expected Output: True
        assertTrue("Cancellation should succeed for pending application", result);
        // Verify application was removed
        assertEquals("Applications list should be empty after cancellation", 0, customerC301.getApplications().size());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Input: Customer C302 tries to cancel IPO for "SmartGrid"
        boolean result = customerC302.cancelPendingApplication("SmartGrid");
        
        // Expected Output: False (Cannot cancel approved applications)
        assertFalse("Cancellation should fail for approved application", result);
        // Verify application was NOT removed
        assertEquals("Applications list should still contain 1 application", 1, customerC302.getApplications().size());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Input: Customer C303 tries to cancel the filing for "MedLife"
        boolean result = customerC303.cancelPendingApplication("MedLife");
        
        // Expected Output: False (Application already finalized)
        assertFalse("Cancellation should fail for rejected application", result);
        // Verify application was NOT removed
        assertEquals("Applications list should still contain 1 application", 1, customerC303.getApplications().size());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Input: Customer C304 requests cancellation for "UnknownCorp"
        boolean result = customerC304.cancelPendingApplication("UnknownCorp");
        
        // Expected Output: False (No application found for specified company)
        assertFalse("Cancellation should fail for non-existent company", result);
        // Verify applications list remains empty
        assertEquals("Applications list should remain empty", 0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Input: Customer C306 cancels "UrbanTech" filing
        boolean result = customerC306.cancelPendingApplication("UrbanTech");
        
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        assertTrue("Cancellation should succeed for pending UrbanTech application", result);
        // Verify only UrbanTech application was removed, AgroSeed remains
        assertEquals("Applications list should contain 1 application after cancellation", 1, customerC306.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", customerC306.getApplications().get(0).getCompanyName());
    }
}