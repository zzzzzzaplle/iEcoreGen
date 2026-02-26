import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customer;
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no applications
        customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0
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
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create document
        Document doc = new Document();
        
        // Create application
        Application app = new Application();
        app.setShare(50);
        app.setAmountOfMoney(2500);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(doc);
        
        // Add application to customer
        customer.getApplications().add(app);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (approved application)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with approved and rejected applications
        customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create companies
        Company company1 = new Company();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@example.com");
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        company2.setEmail("spacey@example.com");
        
        Company company3 = new Company();
        company3.setName("BioGen");
        company3.setEmail("biogen@example.com");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create approved applications
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(doc1);
        
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(doc2);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(doc3);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with mixed status applications
        customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company company1 = new Company();
        company1.setName("RoboCorp");
        company1.setEmail("robocorp@example.com");
        
        Company company2 = new Company();
        company2.setName("AI Ventures");
        company2.setEmail("aiventures@example.com");
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        company3.setEmail("nanomed@example.com");
        
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        company4.setEmail("greenenergy@example.com");
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        company5.setEmail("cloudscale@example.com");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Create approved application
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(doc1);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(doc2);
        
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setAllowance(doc3);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setAllowance(doc4);
        
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setAllowance(doc5);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending not counted)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create document
        Document doc = new Document();
        
        // Create and cancel application
        Application app = new Application();
        app.setShare(10);
        app.setAmountOfMoney(5000);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(doc);
        
        // Add application to customer
        customer.getApplications().add(app);
        
        // Cancel the application (changes status to REJECTED)
        app.cancel();
        
        // Execute: Retrieve application count summary
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (canceled application becomes rejected, which is counted)
        assertEquals("Customer with canceled application should return 1", 1, result);
    }
}