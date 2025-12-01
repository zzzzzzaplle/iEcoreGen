import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer with no IPO applications
        Customer customer = new Customer();
        customer.setFirstName("Thomas");
        customer.setLastName("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer with one pending application
        Customer customer = new Customer();
        customer.setFirstName("Lisa");
        customer.setLastName("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document("QT-2024-FormA", new byte[]{1, 2, 3});
        
        IPOApplication application = new IPOApplication(customer, company, 50, 2500.0, document);
        application.setStatus(ApplicationStatus.APPROVED); // Note: Test spec says "Status: approval"
        
        customer.getApplications().add(application);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 1 for the single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setFirstName("David");
        customer.setLastName("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setFailedAttempts(3); // Cannot apply for IPO as per test spec
        
        // Create and add approved applications
        Company company1 = new Company("Neuralink", "neuralink@example.com");
        Document doc1 = new Document("QT-22023-101", new byte[]{1});
        IPOApplication app1 = new IPOApplication(customer, company1, 100, 10000.0, doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        Company company2 = new Company("SpaceY", "spacey@example.com");
        Document doc2 = new Document("QT-2023-102", new byte[]{2});
        IPOApplication app2 = new IPOApplication(customer, company2, 30, 15000.0, doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        
        // Create and add rejected application
        Company company3 = new Company("BioGen", "biogen@example.com");
        Document doc3 = new Document("QT-2024-002", new byte[]{3});
        IPOApplication app3 = new IPOApplication(customer, company3, 20, 1000.0, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = new Customer();
        customer.setFirstName("Emma");
        customer.setLastName("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        
        // Approved application
        Company company1 = new Company("RoboCorp", "robocorp@example.com");
        Document doc1 = new Document("QT-2023-105", new byte[]{1});
        IPOApplication app1 = new IPOApplication(customer, company1, 100, 10000.0, doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        
        // Rejected applications
        Company company2 = new Company("AI Ventures", "aiventures@example.com");
        Document doc2 = new Document("QT-2023-106", new byte[]{2});
        IPOApplication app2 = new IPOApplication(customer, company2, 100, 10000.0, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Company company3 = new Company("NanoMed", "nanomed@example.com");
        Document doc3 = new Document("QT-2024-003", new byte[]{3});
        IPOApplication app3 = new IPOApplication(customer, company3, 100, 10000.0, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Pending applications (should not be counted)
        Company company4 = new Company("GreenEnergy", "greenenergy@example.com");
        Document doc4 = new Document("QT-2024-004", new byte[]{4});
        IPOApplication app4 = new IPOApplication(customer, company4, 100, 10000.0, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Company company5 = new Company("CloudScale", "cloudscale@example.com");
        Document doc5 = new Document("QT-2024-005", new byte[]{5});
        IPOApplication app5 = new IPOApplication(customer, company5, 100, 10000.0, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending applications excluded)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer with one canceled application
        Customer customer = new Customer();
        customer.setFirstName("James");
        customer.setLastName("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        
        // Create and add a pending application
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document("QT-1010", new byte[]{1});
        IPOApplication application = new IPOApplication(customer, company, 10, 5000.0, document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Cancel the application
        customer.cancelPendingApplication("Cloud");
        
        // Execute: Get application count summary
        int result = customer.getApplicationCountSummary();
        
        // Verify: Should return 0 since canceled applications are not counted
        assertEquals("Customer with only canceled application should return 0", 0, result);
    }
}