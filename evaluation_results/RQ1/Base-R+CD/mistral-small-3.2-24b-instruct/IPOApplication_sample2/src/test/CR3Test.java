import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO applications
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
    public void testCase2_SingleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create and add an approved application
        Application app = new Application();
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        app.setCompany(company);
        
        Document doc = new Document();
        app.setAllowance(doc);
        app.setCustomer(customer);
        
        customer.getApplications().add(app);
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create approved application 1
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Company company1 = new Company();
        company1.setName("Neuralink");
        app1.setCompany(company1);
        
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        
        // Create approved application 2
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        app2.setCompany(company2);
        
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Company company3 = new Company();
        company3.setName("BioGen");
        app3.setCompany(company3);
        
        Document doc3 = new Document();
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        
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
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Company company1 = new Company();
        company1.setName("RoboCorp");
        app1.setCompany(company1);
        
        Document doc1 = new Document();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Company company2 = new Company();
        company2.setName("AI Ventures");
        app2.setCompany(company2);
        
        Document doc2 = new Document();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        app3.setCompany(company3);
        
        Document doc3 = new Document();
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        app4.setCompany(company4);
        
        Document doc4 = new Document();
        app4.setAllowance(doc4);
        app4.setCustomer(customer);
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        app5.setCompany(company5);
        
        Document doc5 = new Document();
        app5.setAllowance(doc5);
        app5.setCustomer(customer);
        
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
        // Setup: Customer "C105" with a cancelled application
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create a pending application
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.PENDING);
        
        Company company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        app.setCompany(company);
        
        Document doc = new Document();
        app.setAllowance(doc);
        app.setCustomer(customer);
        
        customer.getApplications().add(app);
        
        // Cancel the application (changes status to REJECTED)
        app.cancel();
        
        // Test: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 since cancelled applications become REJECTED
        assertEquals("Customer with cancelled application should return 1", 1, result);
    }
}