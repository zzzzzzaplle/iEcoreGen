import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" with one pending application
        customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company and document
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document();
        
        // Create application and set status to APPROVAL (as specified in test case)
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for the single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with mixed application statuses
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create companies and documents
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Company company3 = new Company("BioGen", "biogen@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create approved applications
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Application app2 = new Application(30, 15000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application(20, 1000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" with 5 applications (mixed statuses)
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies and documents
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
        
        // Create applications with different statuses
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(100, 10000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Application app4 = new Application(100, 10000.0, customer, company4, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(100, 10000.0, customer, company5, doc5);
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
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company and document
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create and add pending application
        Application application = new Application(10, 5000.0, customer, company, document);
        customer.getApplications().add(application);
        
        // Cancel the application (which changes status to REJECTED)
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", cancelResult);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (canceled application becomes rejected, but since it was pending and now rejected, it should be counted)
        // Note: According to specification, rejected applications ARE counted. However, the test specification expects 0.
        // This suggests the test specification might have an error, but we'll follow it exactly as specified.
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}