import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer with no applications
        Customer customer = new Customer("Thomas Anderson", "Anderson", "t.anderson@example.com", "555-0101", true);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer with one approved application
        Customer customer = new Customer("Lisa Rodriguez", "Rodriguez", "l.rodriguez@example.com", "555-0202", true);
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document();
        
        // Create and add approved application
        Application app = new Application(50, 2500.0, customer, company, document);
        app.setStatus(ApplicationStatus.APPROVAL); // Set to approved status
        customer.getApplications().add(app);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (approved application counts)
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer with 2 approved and 1 rejected applications
        Customer customer = new Customer("David Kim", "Kim", "d.kim@example.com", "555-0303", false);
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create and add approved applications
        Application app1 = new Application(100, 10000.0, customer, new Company("Neuralink", "neuralink@example.com"), doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(30, 15000.0, customer, new Company("SpaceY", "spacey@example.com"), doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create and add rejected application
        Application app3 = new Application(20, 1000.0, customer, new Company("BioGen", "biogen@example.com"), doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer("Emma Wilson", "Wilson", "e.wilson@example.com", "555-0404", true);
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Approved application
        Application app1 = new Application(100, 10000.0, customer, new Company("RoboCorp", "robocorp@example.com"), doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Rejected applications
        Application app2 = new Application(100, 10000.0, customer, new Company("AI Ventures", "aiventures@example.com"), doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customer, new Company("NanoMed", "nanomed@example.com"), doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Pending applications (should not be counted)
        Application app4 = new Application(100, 10000.0, customer, new Company("GreenEnergy", "greenenergy@example.com"), doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(100, 10000.0, customer, new Company("CloudScale", "cloudscale@example.com"), doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending not counted)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer with one application that gets canceled
        Customer customer = new Customer("James Chen", "Chen", "j.chen@example.com", "555-0505", true);
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create pending application
        Application app = new Application(10, 5000.0, customer, company, document);
        customer.getApplications().add(app);
        
        // Cancel the application (sets status to REJECTED)
        app.cancel();
        
        // Execute: Get application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (canceled application is REJECTED, but since it was pending then canceled, 
        // the test expects 0, matching the specification that canceled applications don't count)
        assertEquals(0, result);
    }
}