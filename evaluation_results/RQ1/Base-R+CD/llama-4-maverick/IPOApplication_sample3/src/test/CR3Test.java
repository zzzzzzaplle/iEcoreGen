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
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0
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
        
        Company company = new Company("QuantumTech", "quantumtech@gmail.com");
        Document doc = new Document();
        
        // Create and approve application
        Application app = new Application(50, 2500.0, customer, company, doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 1
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
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3
        assertEquals(3, result);
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
        
        // Create approved application
        Application app1 = new Application(100, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application(100, 10000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application(100, 10000.0, customer, company4, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(100, 10000.0, customer, company5, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 3 (only approved and rejected count)
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with a canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company("Cloud", "Cloud@gmail.com");
        Document doc = new Document();
        
        // Create pending application
        Application app = new Application(10, 5000.0, customer, company, doc);
        app.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app);
        
        // Cancel the application (sets status to REJECTED)
        app.cancel();
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Expected output is 0 (cancelled applications are set to REJECTED but not counted)
        assertEquals(0, result);
    }
}