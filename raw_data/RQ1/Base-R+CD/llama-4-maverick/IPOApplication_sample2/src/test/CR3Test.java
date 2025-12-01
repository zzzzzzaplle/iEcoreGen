import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer with no IPO applications
        Customer customer = new Customer("Thomas", "Anderson", "t.anderson@example.com", "555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer with one pending application
        Customer customer = new Customer("Lisa", "Rodriguez", "l.rodriguez@example.com", "555-0202");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document(); // Assuming document creation
        
        // Create application and set to APPROVAL status
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (APPROVAL status is processed)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer with 2 approved and 1 rejected applications
        Customer customer = new Customer("David", "Kim", "d.kim@example.com", "555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create approved applications
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(30, 15000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Company company3 = new Company("BioGen", "biogen@example.com");
        Document doc3 = new Document();
        Application app3 = new Application(20, 1000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer("Emma", "Wilson", "e.wilson@example.com", "555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company company1 = new Company("RoboCorp", "robocorp@example.com");
        Document doc1 = new Document();
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Company company2 = new Company("AI Ventures", "aiventures@example.com");
        Document doc2 = new Document();
        Application app2 = new Application(100, 10000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Company company3 = new Company("NanoMed", "nanomed@example.com");
        Document doc3 = new Document();
        Application app3 = new Application(100, 10000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Company company4 = new Company("GreenEnergy", "greenenergy@example.com");
        Document doc4 = new Document();
        Application app4 = new Application(100, 10000.0, customer, company4, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Company company5 = new Company("CloudScale", "cloudscale@example.com");
        Document doc5 = new Document();
        Application app5 = new Application(100, 10000.0, customer, company5, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, excluding 2 pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer with one canceled application
        Customer customer = new Customer("James", "Chen", "j.chen@example.com", "555-0505");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create pending application
        Application application = new Application(10, 5000.0, customer, company, document);
        customer.getApplications().add(application);
        
        // Cancel the application (sets status to REJECTED)
        application.cancel();
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (canceled application has REJECTED status, which is processed)
        assertEquals("Customer with one canceled (rejected) application should return 1", 1, result);
    }
}