import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create a pending application
        Application app = new Application("QuantumTech", 50, 2500.0, "QT-2024-FormA");
        customer.getApplications().add(app);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since pending applications are not counted
        assertEquals("Pending application should not be counted", 0, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with mixed application statuses
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create approved applications
        Application app1 = new Application("Neuralink", 100, 10000.0, "QT-22023-101");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Application app2 = new Application("SpaceY", 30, 15000.0, "QT-2023-102");
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create rejected application
        Application app3 = new Application("BioGen", 20, 1000.0, "QT-2024-002");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Should count both approved and rejected applications", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with multiple applications of different statuses
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application app1 = new Application("RoboCorp", 100, 10000.0, "QT-2023-105");
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Create rejected applications
        Application app2 = new Application("AI Ventures", 100, 10000.0, "QT-2023-106");
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application("NanoMed", 100, 10000.0, "QT-2024-003");
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application("GreenEnergy", 100, 10000.0, "QT-2024-004");
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application("CloudScale", 100, 10000.0, "QT-2024-005");
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending applications excluded)
        assertEquals("Should count only approved and rejected applications", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create and cancel a pending application
        Application app = new Application("Cloud", 10, 5000.0, "QT-1010");
        customer.getApplications().add(app);
        
        // Cancel the application (remove it from the list)
        boolean cancelResult = customer.cancelPendingApplication("Cloud");
        assertTrue("Application should be canceled successfully", cancelResult);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since the application was canceled/removed
        assertEquals("Canceled application should not be counted", 0, result);
    }
}