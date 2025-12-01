import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that might be needed for multiple tests
        document = new Document();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        customer = new Customer("Thomas", "Anderson", "t.anderson@example.com", "555-0101", true);
        
        // Execute: Customer requests count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer = new Customer("Lisa", "Rodriguez", "l.rodriguez@example.com", "555-0202", true);
        company = new Company("QuantumTech", "quantumtech@gmail.com");
        
        // Create and approve the application
        boolean created = customer.createApplication(company, 50, 2500.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Get the application and approve it
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Execute: Customer asks for total number of filings
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with two approved and one rejected applications
        customer = new Customer("David", "Kim", "d.kim@example.com", "555-0303", false);
        
        // Create three applications
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Company company3 = new Company("BioGen", "biogen@example.com");
        
        // Create applications (customer can't apply but we're creating them directly)
        Application app1 = new Application(100, 10000.0, customer, company1, document);
        Application app2 = new Application(30, 15000.0, customer, company2, document);
        Application app3 = new Application(20, 1000.0, customer, company3, document);
        
        // Set statuses directly
        app1.setStatus(ApplicationStatus.APPROVAL);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add to customer's applications
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Customer checks total filings
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer = new Customer("Emma", "Wilson", "e.wilson@example.com", "555-0404", true);
        
        // Create five applications with different statuses
        Application app1 = new Application(100, 10000.0, customer, new Company("RoboCorp", "robocorp@example.com"), document);
        Application app2 = new Application(100, 10000.0, customer, new Company("AI Ventures", "aiventures@example.com"), document);
        Application app3 = new Application(100, 10000.0, customer, new Company("NanoMed", "nanomed@example.com"), document);
        Application app4 = new Application(100, 10000.0, customer, new Company("GreenEnergy", "greenenergy@example.com"), document);
        Application app5 = new Application(100, 10000.0, customer, new Company("CloudScale", "cloudscale@example.com"), document);
        
        // Set statuses
        app1.setStatus(ApplicationStatus.APPROVAL);
        app2.setStatus(ApplicationStatus.REJECTED);
        app3.setStatus(ApplicationStatus.REJECTED);
        app4.setStatus(ApplicationStatus.PENDING);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add to customer's applications
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Customer queries the overall count
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (1 approved + 2 rejected)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        customer = new Customer("James", "Chen", "j.chen@example.com", "555-0505", true);
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create a pending application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Cancel the application
        boolean canceled = customer.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", canceled);
        
        // Execute: Customer asks for the figure
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}