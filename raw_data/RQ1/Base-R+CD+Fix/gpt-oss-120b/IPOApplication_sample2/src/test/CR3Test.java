import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private Company company1;
    private Company company2;
    private Company company3;
    private Company company4;
    private Company company5;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer with no applications
        customer = new Customer("Thomas", "Anderson", "t.anderson@example.com", "555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SingleApprovedRequest() {
        // Setup: Customer with one approved application
        customer = new Customer("Lisa", "Rodriguez", "l.rodriguez@example.com", "555-0202");
        customer.setCanApplyForIPO(true);
        company1 = new Company("QuantumTech", "quantumtech@gmail.com");
        
        // Create application and approve it
        boolean created = customer.createApplication(company1, 50, 2500.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Find the pending application and approve it
        for (Application app : customer.getApplications()) {
            if (app.getStatus() == ApplicationStatus.PENDING) {
                boolean approved = app.approve();
                assertTrue("Application should be approved successfully", approved);
                break;
            }
        }
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer with mix of approved and rejected applications
        customer = new Customer("David", "Kim", "d.kim@example.com", "555-0303");
        customer.setCanApplyForIPO(false); // Customer cannot apply anymore
        
        company1 = new Company("Neuralink", "neuralink@example.com");
        company2 = new Company("SpaceY", "spacey@example.com");
        company3 = new Company("BioGen", "biogen@example.com");
        
        // Create three applications first (they'll be in pending status)
        customer.setCanApplyForIPO(true); // Temporarily allow creation
        boolean app1Created = customer.createApplication(company1, 100, 10000.0, document);
        boolean app2Created = customer.createApplication(company2, 30, 15000.0, document);
        boolean app3Created = customer.createApplication(company3, 20, 1000.0, document);
        customer.setCanApplyForIPO(false); // Restore original state
        
        assertTrue("First application should be created", app1Created);
        assertTrue("Second application should be created", app2Created);
        assertTrue("Third application should be created", app3Created);
        
        // Process applications: approve first two, reject third
        int processed = 0;
        for (Application app : customer.getApplications()) {
            if (processed == 0 || processed == 1) {
                app.approve(); // Approve first two
            } else {
                app.reject(); // Reject third
            }
            processed++;
        }
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer with five applications (1 approved, 2 rejected, 2 pending)
        customer = new Customer("Emma", "Wilson", "e.wilson@example.com", "555-0404");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("RoboCorp", "robocorp@example.com");
        company2 = new Company("AI Ventures", "aiventures@example.com");
        company3 = new Company("NanoMed", "nanomed@example.com");
        company4 = new Company("GreenEnergy", "greenenergy@example.com");
        company5 = new Company("CloudScale", "cloudscale@example.com");
        
        // Create all five applications
        boolean app1Created = customer.createApplication(company1, 100, 10000.0, document);
        boolean app2Created = customer.createApplication(company2, 100, 10000.0, document);
        boolean app3Created = customer.createApplication(company3, 100, 10000.0, document);
        boolean app4Created = customer.createApplication(company4, 100, 10000.0, document);
        boolean app5Created = customer.createApplication(company5, 100, 10000.0, document);
        
        assertTrue("All applications should be created successfully", 
                  app1Created && app2Created && app3Created && app4Created && app5Created);
        
        // Process applications: approve first, reject second and third, leave fourth and fifth pending
        int index = 0;
        for (Application app : customer.getApplications()) {
            if (index == 0) {
                app.approve(); // First application: APPROVED
            } else if (index == 1 || index == 2) {
                app.reject(); // Second and third: REJECTED
            }
            // Fourth and fifth remain PENDING
            index++;
        }
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer with one application that gets canceled
        customer = new Customer("James", "Chen", "j.chen@example.com", "555-0505");
        customer.setCanApplyForIPO(true);
        company1 = new Company("Cloud", "Cloud@gmail.com");
        
        // Create pending application
        boolean created = customer.createApplication(company1, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Cancel the application
        boolean canceled = customer.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", canceled);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled applications are marked as rejected
        // and getApplicationCount includes rejected applications
        // However, the specification says canceled applications should not be counted
        // Since cancel() marks as REJECTED, they would be counted, but test expects 0
        // This reveals a potential issue with the implementation vs specification
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}