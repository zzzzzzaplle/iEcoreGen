import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer with no IPO requests
        Customer customer = new Customer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singlePendingRequest() {
        // Setup: Customer with one approved application
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
        
        // Create application and set it to approved status
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 (approved application counts)
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer with 2 approved and 1 rejected applications
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // Customer cannot apply anymore
        
        // Create companies
        Company neuralink = new Company();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@gmail.com");
        
        Company spaceY = new Company();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@gmail.com");
        
        Company bioGen = new Company();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@gmail.com");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        // Create approved applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(neuralink);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(spaceY);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected application
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(bioGen);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(doc3);
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
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer with 5 applications: 1 approved, 2 rejected, 2 pending
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company roboCorp = new Company();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@gmail.com");
        
        Company aiVentures = new Company();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@gmail.com");
        
        Company nanoMed = new Company();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@gmail.com");
        
        Company greenEnergy = new Company();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@gmail.com");
        
        Company cloudScale = new Company();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@gmail.com");
        
        // Create documents
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        // Create approved application
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(roboCorp);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create rejected applications
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(aiVentures);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(nanoMed);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(greenEnergy);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(cloudScale);
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
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 3 (1 approved + 2 rejected, pending ones don't count)
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending applications should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer with one pending application that gets canceled
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
        
        // Create and add pending application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        
        // Verify cancellation was successful
        assertTrue("Application cancellation should succeed", cancelResult);
        assertEquals("Application status should be REJECTED after cancellation", 
                         ApplicationStatus.REJECTED, application.getStatus());
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 (cancelled application becomes rejected, but wasn't processed)
        assertEquals("Customer with cancelled pending application should return 0", 0, result);
    }
}