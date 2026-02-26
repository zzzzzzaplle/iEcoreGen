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
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company and document
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document document = new Document();
        
        // Create and add application with APPROVAL status
        Application application = new Application(50, 2500.0, customer, company, document);
        application.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals(1, result);
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
        
        // Create companies and documents
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Company spaceY = new Company("SpaceY", "spacey@example.com");
        Company bioGen = new Company("BioGen", "biogen@example.com");
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create and add approved applications
        Application app1 = new Application(100, 10000.0, customer, neuralink, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(30, 15000.0, customer, spaceY, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Create and add rejected application
        Application app3 = new Application(20, 1000.0, customer, bioGen, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 2 approved + 1 rejected
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 5 applications: 1 approved, 2 rejected, 2 pending
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies and documents
        Company roboCorp = new Company("RoboCorp", "robocorp@example.com");
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Company nanoMed = new Company("NanoMed", "nanomed@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        Company cloudScale = new Company("CloudScale", "cloudscale@example.com");
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Add approved application
        Application app1 = new Application(100, 10000.0, customer, roboCorp, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Add rejected applications
        Application app2 = new Application(100, 10000.0, customer, aiVentures, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        Application app3 = new Application(100, 10000.0, customer, nanoMed, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app3);
        
        // Add pending applications (should not be counted)
        Application app4 = new Application(100, 10000.0, customer, greenEnergy, doc4);
        // Status remains PENDING by default
        
        Application app5 = new Application(100, 10000.0, customer, cloudScale, doc5);
        // Status remains PENDING by default
        
        // Add pending applications to customer
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 for 1 approved + 2 rejected (2 pending excluded)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with one application that gets canceled
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company and document
        Company cloud = new Company("Cloud", "Cloud@gmail.com");
        Document document = new Document();
        
        // Create pending application
        Application application = new Application(10, 5000.0, customer, cloud, document);
        // Status is PENDING by default
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Cancel the application
        customer.cancelApplication("Cloud");
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled application becomes rejected
        // and getApplicationCount() only counts APPROVAL and REJECTED statuses
        assertEquals(0, result);
    }
}