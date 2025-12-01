import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private IPOApplicationService service;
    private Customer customer;
    
    @Before
    public void setUp() {
        service = new IPOApplicationService();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmountOfMoney(750.0);
        pendingApp.setDocument("EW-2024-03");
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(pendingApp);
        customer.setIpoApplications(applications);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = service.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be canceled successfully", result);
        assertEquals("Application list should be empty after cancellation", 0, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmountOfMoney(3000.0);
        approvedApp.setDocument("SG-2024-01");
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setIpoApplications(applications);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = service.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getIpoApplications().size());
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, customer.getIpoApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmountOfMoney(1000.0);
        rejectedApp.setDocument("SG-2024-03");
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(rejectedApp);
        customer.setIpoApplications(applications);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = service.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getIpoApplications().size());
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, customer.getIpoApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications for "UnknownCorp"
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        
        // Customer has no applications initially
        customer.setIpoApplications(new ArrayList<>());
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = service.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists
        assertFalse("Cancellation should fail for non-existent application", result);
        assertEquals("Application list should remain empty", 0, customer.getIpoApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmountOfMoney(1250.0);
        app1.setDocument("SG-2024-005");
        app1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmountOfMoney(2000.0);
        app2.setDocument("SG-2024-006");
        app2.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setIpoApplications(applications);
        
        // Test: Cancel "UrbanTech" application
        boolean result = service.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: "UrbanTech" cancellation should succeed and "AgroSeed" should remain
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Should have one remaining application", 1, customer.getIpoApplications().size());
        assertEquals("Remaining application should be AgroSeed", "AgroSeed", customer.getIpoApplications().get(0).getCompanyName());
        assertEquals("AgroSeed application status should remain PENDING", ApplicationStatus.PENDING, customer.getIpoApplications().get(0).getStatus());
    }
}