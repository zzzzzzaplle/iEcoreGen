import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer with no applications
        Customer customer = new Customer("Thomas Anderson", "Anderson", "t.anderson@example.com", "555-0101");
        customer.setCanApplyForIPO(true);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer with one approved application
        Customer customer = new Customer("Lisa Rodriguez", "Rodriguez", "l.rodriguez@example.com", "555-0202");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document();
        
        // Create and approve application
        boolean created = customer.createApplication(company, 50, 2500.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Find and approve the application
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer with mixed application statuses
        Customer customer = new Customer("David Kim", "Kim", "d.kim@example.com", "555-0303");
        customer.setCanApplyForIPO(false); // Customer cannot apply, but has historical applications
        
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Company company3 = new Company("BioGen", "biogen@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Manually create applications since customer cannot apply
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        Application app2 = new Application(30, 15000.0, customer, company2, doc2);
        Application app3 = new Application(20, 1000.0, customer, company3, doc3);
        
        // Set statuses directly
        app1.setStatus(ApplicationStatus.APPROVAL);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer with 5 applications (mixed statuses)
        Customer customer = new Customer("Emma Wilson", "Wilson", "e.wilson@example.com", "555-0404");
        customer.setCanApplyForIPO(true);
        
        Company company1 = new Company("RoboCorp", "robocorp@example.com");
        Company company2 = new Company("AI Ventures", "aiventures@example.com");
        Company company3 = new Company("NanoMed", "nanomed@example.com");
        Company company4 = new Company("GreenEnergy", "greenenergy@example.com");
        Company company5 = new Company("CloudScale", "cloudscale@example.com");
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Manually create and set application statuses
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        Application app2 = new Application(100, 10000.0, customer, company2, doc2);
        Application app3 = new Application(100, 10000.0, customer, company3, doc3);
        Application app4 = new Application(100, 10000.0, customer, company4, doc4);
        Application app5 = new Application(100, 10000.0, customer, company5, doc5);
        
        app1.setStatus(ApplicationStatus.APPROVAL);
        app2.setStatus(ApplicationStatus.REJECTED);
        app3.setStatus(ApplicationStatus.REJECTED);
        app4.setStatus(ApplicationStatus.PENDING);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer with one application that gets canceled
        Customer customer = new Customer("James Chen", "Chen", "j.chen@example.com", "555-0505");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create pending application
        boolean created = customer.createApplication(company, 10, 5000.0, document);
        assertTrue("Application should be created successfully", created);
        
        // Cancel the application
        boolean canceled = customer.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", canceled);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled applications are rejected and not counted
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}