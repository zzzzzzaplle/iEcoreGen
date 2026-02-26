import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that can be reused across tests
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        customer = new Customer("Thomas", "Anderson", "t.anderson@example.com", "555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SingleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer = new Customer("Lisa", "Rodriguez", "l.rodriguez@example.com", "555-0202");
        customer.setCanApplyForIPO(true);
        
        company = new Company("QuantumTech", "quantumtech@gmail.com");
        
        // Create and add approved application
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL); // Set status to approved
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 since one approved application exists
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer = new Customer("David", "Kim", "d.kim@example.com", "555-0303");
        customer.setCanApplyForIPO(false); // Customer cannot apply for IPO but has historical records
        
        // Create approved application 1: Neuralink
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Application app1 = new Application(100, 10000.0, customer, company1, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create approved application 2: SpaceY
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Application app2 = new Application(30, 15000.0, customer, company2, document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Create rejected application: BioGen
        Company company3 = new Company("BioGen", "biogen@example.com");
        Application app3 = new Application(20, 1000.0, customer, company3, document);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer = new Customer("Emma", "Wilson", "e.wilson@example.com", "555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application: RoboCorp
        Company company1 = new Company("RoboCorp", "robocorp@example.com");
        Application app1 = new Application(100, 10000.0, customer, company1, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create rejected application 1: AI Ventures
        Company company2 = new Company("AI Ventures", "aiventures@example.com");
        Application app2 = new Application(100, 10000.0, customer, company2, document);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Create rejected application 2: NanoMed
        Company company3 = new Company("NanoMed", "nanomed@example.com");
        Application app3 = new Application(100, 10000.0, customer, company3, document);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Create pending application 1: GreenEnergy
        Company company4 = new Company("GreenEnergy", "greenenergy@example.com");
        Application app4 = new Application(100, 10000.0, customer, company4, document);
        app4.setStatus(ApplicationStatus.PENDING); // Pending applications should not be counted
        customer.getApplications().add(app4);
        
        // Create pending application 2: CloudScale
        Company company5 = new Company("CloudScale", "cloudscale@example.com");
        Application app5 = new Application(100, 10000.0, customer, company5, document);
        app5.setStatus(ApplicationStatus.PENDING); // Pending applications should not be counted
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected) - pending applications excluded
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with one canceled application
        customer = new Customer("James", "Chen", "j.chen@example.com", "555-0505");
        customer.setCanApplyForIPO(true);
        
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create pending application
        Application application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(application);
        
        // Cancel the application (changes status to REJECTED)
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled application becomes REJECTED, but getApplicationCount only counts non-pending
        // However, the specification says "All requests canceled" should return 0, so we need to clarify
        // Since cancellation sets status to REJECTED, and REJECTED applications ARE counted, this contradicts the spec
        // Let's follow the spec literally: "All requests canceled" should return 0
        // This suggests the test setup might be incorrect, but we'll implement as specified
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}