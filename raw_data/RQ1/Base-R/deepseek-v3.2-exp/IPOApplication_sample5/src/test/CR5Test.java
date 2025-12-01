import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        // Initialize customer C301 for Test Case 1
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        // Initialize customer C302 for Test Case 2
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        // Initialize customer C303 for Test Case 3
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        // Initialize customer C304 for Test Case 4
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        // Initialize customer C306 for Test Case 5
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        
        bankSystem.getCustomers().add(customerC301);
        bankSystem.getCustomers().add(customerC302);
        bankSystem.getCustomers().add(customerC303);
        bankSystem.getCustomers().add(customerC304);
        bankSystem.getCustomers().add(customerC306);
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCustomer(customerC301);
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmountPaid(750.0);
        pendingApp.setDocument("EW-2024-03");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        customerC301.getApplications().add(pendingApp);
        
        // Execute: Cancel pending application for EcoWave
        boolean result = bankSystem.cancelPendingApplication(customerC301, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be canceled successfully", result);
        assertEquals("Application list should be empty after cancellation", 0, customerC301.getApplications().size());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create approved application for SmartGrid
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCustomer(customerC302);
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmountPaid(3000.0);
        approvedApp.setDocument("SG-2024-01");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        customerC302.getApplications().add(approvedApp);
        
        // Execute: Try to cancel approved application
        boolean result = bankSystem.cancelPendingApplication(customerC302, "SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application list should still contain the approved application", 1, customerC302.getApplications().size());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create rejected application for MedLife
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCustomer(customerC303);
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmountPaid(1000.0);
        rejectedApp.setDocument("SG-2024-03");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        customerC303.getApplications().add(rejectedApp);
        
        // Execute: Try to cancel rejected application
        boolean result = bankSystem.cancelPendingApplication(customerC303, "MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application list should still contain the rejected application", 1, customerC303.getApplications().size());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer has no applications
        customerC304.setApplications(new ArrayList<>());
        
        // Execute: Try to cancel application for non-existent company
        boolean result = bankSystem.cancelPendingApplication(customerC304, "UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists for the company
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Application list should remain empty", 0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customerC306);
        app1.setCompanyName("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmountPaid(1250.0);
        app1.setDocument("SG-2024-005");
        app1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customerC306);
        app2.setCompanyName("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmountPaid(2000.0);
        app2.setDocument("SG-2024-006");
        app2.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customerC306.setApplications(applications);
        
        // Execute: Cancel UrbanTech application
        boolean result = bankSystem.cancelPendingApplication(customerC306, "UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed and AgroSeed should remain
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Should have one remaining application after cancellation", 1, customerC306.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", customerC306.getApplications().get(0).getCompanyName());
    }
}