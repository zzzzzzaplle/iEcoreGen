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
        
        // Input: Customer requests application count summary
        int result = customer.getApplicationCount();
        
        // Expected Output: 0
        assertEquals("Customer with no applications should return 0", 0, result);
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
        
        // Create company
        Company company = new Company();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create document
        Document document = new Document();
        
        // Create application and set to approved status
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Input: Customer asks for total number of filings
        int result = customer.getApplicationCount();
        
        // Expected Output: 1
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // As per specification: can not apply for IPO
        
        // Create companies
        Company company1 = new Company();
        company1.setName("Neuralink");
        
        Company company2 = new Company();
        company2.setName("SpaceY");
        
        Company company3 = new Company();
        company3.setName("BioGen");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create approved applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Input: Customer checks total filings
        int result = customer.getApplicationCount();
        
        // Expected Output: 3
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
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
        
        // Create companies
        Company company1 = new Company();
        company1.setName("RoboCorp");
        
        Company company2 = new Company();
        company2.setName("AI Ventures");
        
        Company company3 = new Company();
        company3.setName("NanoMed");
        
        Company company4 = new Company();
        company4.setName("GreenEnergy");
        
        Company company5 = new Company();
        company5.setName("CloudScale");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Create approved application
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications (should not be counted)
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Input: Customer queries the overall count
        int result = customer.getApplicationCount();
        
        // Expected Output: 3 (only approved and rejected, not pending)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" with canceled application
        Customer customer = new Customer();
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
        Document document = new Document();
        
        // Create pending application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Cancel the application (canceled applications remain as PENDING status)
        // Note: According to the source code, cancel() returns true but doesn't change status
        // In a real system, cancellation might change status, but based on the provided code,
        // canceled applications remain as PENDING and are not counted
        application.cancel();
        
        // Input: Customer asks for application count
        int result = customer.getApplicationCount();
        
        // Expected Output: 0 (pending applications are not counted, even if canceled)
        assertEquals("Customer with canceled pending application should return 0", 0, result);
    }
}