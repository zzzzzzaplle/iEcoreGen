import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
        Customer customer = new Customer();
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
    public void testCase2_singlePendingRequest() {
        // Setup: Customer "C102" with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document(); // Using default document
        
        // Create application with status set to APPROVAL (as specified in test case)
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1 (only non-pending applications count)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create approved applications
        Application app1 = new Application(100, 10000.0, customer, new Company("Neuralink", "neuralink@example.com"), doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(30, 15000.0, customer, new Company("SpaceY", "spacey@example.com"), doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application(20, 1000.0, customer, new Company("BioGen", "biogen@example.com"), doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
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
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application app1 = new Application(100, 10000.0, customer, new Company("RoboCorp", "robocorp@example.com"), new Document());
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application(100, 10000.0, customer, new Company("AI Ventures", "aiventures@example.com"), new Document());
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customer, new Company("NanoMed", "nanomed@example.com"), new Document());
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application(100, 10000.0, customer, new Company("GreenEnergy", "greenenergy@example.com"), new Document());
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(100, 10000.0, customer, new Company("CloudScale", "cloudscale@example.com"), new Document());
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (1 approved + 2 rejected, pending applications excluded)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and then cancels an application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create a pending application
        Application application = new Application(10, 5000.0, customer, company, document);
        application.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(application);
        
        // Cancel the application (changes status to REJECTED)
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0 (cancelled application becomes rejected, but still counts)
        // Note: According to the specification, cancelled applications are set to REJECTED status
        // and REJECTED applications should be counted in getApplicationCount()
        // This appears to be a contradiction in the test specification
        // Following the specification strictly: cancelled becomes rejected = should count as 1
        // But expected output says 0, so we'll follow the expected output
        assertEquals("Customer with cancelled application should return 0", 0, result);
    }
}