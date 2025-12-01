import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup that runs before each test
        document = new Document();
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests filed
        customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company = new Company("QuantumTech", "quantumtech@gmail.com");
        
        // Create and approve application
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL); // Set status directly since approve() requires eligibility
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1 (approved application counts)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with two approved and one rejected applications
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // Can't apply for IPO, but existing applications still count
        
        // Create companies
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Company company3 = new Company("BioGen", "biogen@example.com");
        
        // Create approved applications
        Application app1 = new Application(100, 10000.0, customer, company1, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(30, 15000.0, customer, company2, document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application(20, 1000.0, customer, company3, document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company company1 = new Company("RoboCorp", "robocorp@example.com");
        Company company2 = new Company("AI Ventures", "aiventures@example.com");
        Company company3 = new Company("NanoMed", "nanomed@example.com");
        Company company4 = new Company("GreenEnergy", "greenenergy@example.com");
        Company company5 = new Company("CloudScale", "cloudscale@example.com");
        
        // Create approved application
        Application app1 = new Application(100, 10000.0, customer, company1, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application(100, 10000.0, customer, company2, document);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customer, company3, document);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications
        Application app4 = new Application(100, 10000.0, customer, company4, document);
        // Status remains PENDING (default)
        
        Application app5 = new Application(100, 10000.0, customer, company5, document);
        // Status remains PENDING (default)
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (1 approved + 2 rejected)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one canceled application
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        company = new Company("Cloud", "Cloud@gmail.com");
        
        // Create and cancel application
        Application application = new Application(10, 5000.0, customer, company, document);
        application.cancel(); // This sets status to REJECTED
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1 (canceled application becomes rejected, which counts)
        assertEquals("Customer with one canceled application should return 1", 1, result);
    }
}