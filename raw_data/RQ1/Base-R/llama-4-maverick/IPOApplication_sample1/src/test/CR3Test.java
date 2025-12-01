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
        // Setup: Customer "C101" with no IPO applications
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephoneNumber("555-0101");
        customer.setRestricted(false);
        customer.setApplications(new java.util.ArrayList<IPOApplication>());
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since no applications exist
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephoneNumber("555-0202");
        customer.setRestricted(false);
        
        // Create and add a pending application
        IPOApplication app = new IPOApplication("QuantumTech", 50, 2500.0, "QT-2024-FormA");
        app.setStatus(IPOApplication.Status.PENDING);
        
        java.util.List<IPOApplication> apps = new java.util.ArrayList<IPOApplication>();
        apps.add(app);
        customer.setApplications(apps);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since only pending applications are not counted
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephoneNumber("555-0303");
        customer.setRestricted(true);
        
        // Create approved applications
        IPOApplication app1 = new IPOApplication("Neuralink", 100, 10000.0, "QT-22023-101");
        app1.setStatus(IPOApplication.Status.APPROVED);
        
        IPOApplication app2 = new IPOApplication("SpaceY", 30, 15000.0, "QT-2023-102");
        app2.setStatus(IPOApplication.Status.APPROVED);
        
        // Create rejected application
        IPOApplication app3 = new IPOApplication("BioGen", 20, 1000.0, "QT-2024-002");
        app3.setStatus(IPOApplication.Status.REJECTED);
        
        java.util.List<IPOApplication> apps = new java.util.ArrayList<IPOApplication>();
        apps.add(app1);
        apps.add(app2);
        apps.add(app3);
        customer.setApplications(apps);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephoneNumber("555-0404");
        customer.setRestricted(false);
        
        // Create approved application
        IPOApplication app1 = new IPOApplication("RoboCorp", 100, 10000.0, "QT-2023-105");
        app1.setStatus(IPOApplication.Status.APPROVED);
        
        // Create rejected applications
        IPOApplication app2 = new IPOApplication("AI Ventures", 100, 10000.0, "QT-2023-106");
        app2.setStatus(IPOApplication.Status.REJECTED);
        
        IPOApplication app3 = new IPOApplication("NanoMed", 100, 10000.0, "QT-2024-003");
        app3.setStatus(IPOApplication.Status.REJECTED);
        
        // Create pending applications (should not be counted)
        IPOApplication app4 = new IPOApplication("GreenEnergy", 100, 10000.0, "QT-2024-004");
        app4.setStatus(IPOApplication.Status.PENDING);
        
        IPOApplication app5 = new IPOApplication("CloudScale", 100, 10000.0, "QT-2024-005");
        app5.setStatus(IPOApplication.Status.PENDING);
        
        java.util.List<IPOApplication> apps = new java.util.ArrayList<IPOApplication>();
        apps.add(app1);
        apps.add(app2);
        apps.add(app3);
        apps.add(app4);
        apps.add(app5);
        customer.setApplications(apps);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending applications not counted)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with one canceled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephoneNumber("555-0505");
        customer.setRestricted(false);
        
        // Create and add a canceled application
        IPOApplication app = new IPOApplication("Cloud", 10, 5000.0, "QT-1010");
        app.setStatus(IPOApplication.Status.CANCELLED);
        
        java.util.List<IPOApplication> apps = new java.util.ArrayList<IPOApplication>();
        apps.add(app);
        customer.setApplications(apps);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since canceled applications are not counted
        assertEquals(0, result);
    }
}