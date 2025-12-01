import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer with no applications
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 when no applications exist
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer with one pending application
        Customer customer = new Customer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        Document document = new Document();
        
        // Create application with APPROVAL status (as specified in test case)
        Application application = new Application();
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL); // Note: Test case says "Status: approval"
        
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for the approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer with mixed application statuses
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create approved application 1
        Company company1 = new Company();
        company1.setName("Neuralink");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setCompany(company1);
        app1.setCustomer(customer);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create approved application 2
        Company company2 = new Company();
        company2.setName("SpaceY");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setCompany(company2);
        app2.setCustomer(customer);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Company company3 = new Company();
        company3.setName("BioGen");
        Document doc3 = new Document();
        Application app3 = new Application();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setCompany(company3);
        app3.setCustomer(customer);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer with 5 applications (mixed statuses)
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company company1 = new Company();
        company1.setName("RoboCorp");
        Document doc1 = new Document();
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setCompany(company1);
        app1.setCustomer(customer);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application 1
        Company company2 = new Company();
        company2.setName("AI Ventures");
        Document doc2 = new Document();
        Application app2 = new Application();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setCompany(company2);
        app2.setCustomer(customer);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        // Create rejected application 2
        Company company3 = new Company();
        company3.setName("NanoMed");
        Document doc3 = new Document();
        Application app3 = new Application();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setCompany(company3);
        app3.setCustomer(customer);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending application 1
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        Document doc4 = new Document();
        Application app4 = new Application();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setCompany(company4);
        app4.setCustomer(customer);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        // Create pending application 2
        Company company5 = new Company();
        company5.setName("CloudScale");
        Document doc5 = new Document();
        Application app5 = new Application();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setCompany(company5);
        app5.setCustomer(customer);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
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
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer with canceled application
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        Company company = new Company();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        Document document = new Document();
        
        // Create pending application
        Application application = new Application();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setCompany(company);
        application.setCustomer(customer);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application should be successfully canceled", cancelResult);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 since canceled applications are set to REJECTED status
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}