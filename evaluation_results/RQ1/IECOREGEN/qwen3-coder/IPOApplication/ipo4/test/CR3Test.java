package edu.ipo.ipo4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.IpoFactory;

public class CR3Test {
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_NoApplicationsAtAll() {
        // Setup: Customer "C101" with no IPO requests
        Customer customer = factory.createCustomer();
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
    public void testCase2_SingleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        Customer customer = factory.createCustomer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company company = factory.createCompany();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        // Create document
        Document document = factory.createDocument();
        
        // Create and configure application
        Application application = factory.createApplication();
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 1 for single approved application
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = factory.createCustomer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Create companies
        Company company1 = factory.createCompany();
        company1.setName("Neuralink");
        
        Company company2 = factory.createCompany();
        company2.setName("SpaceY");
        
        Company company3 = factory.createCompany();
        company3.setName("BioGen");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();
        
        // Create approved applications
        Application app1 = factory.createApplication();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(company1);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        
        Application app2 = factory.createApplication();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCompany(company2);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        
        // Create rejected application
        Application app3 = factory.createApplication();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCompany(company3);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        
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
        // Setup: Customer "C104" with 1 approved, 2 rejected, and 2 pending applications
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company company1 = factory.createCompany();
        company1.setName("RoboCorp");
        
        Company company2 = factory.createCompany();
        company2.setName("AI Ventures");
        
        Company company3 = factory.createCompany();
        company3.setName("NanoMed");
        
        Company company4 = factory.createCompany();
        company4.setName("GreenEnergy");
        
        Company company5 = factory.createCompany();
        company5.setName("CloudScale");
        
        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();
        Document doc4 = factory.createDocument();
        Document doc5 = factory.createDocument();
        
        // Create approved application
        Application app1 = factory.createApplication();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(company1);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        
        // Create rejected applications
        Application app2 = factory.createApplication();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCompany(company2);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        
        Application app3 = factory.createApplication();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCompany(company3);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        
        // Create pending applications (should not be counted)
        Application app4 = factory.createApplication();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCompany(company4);
        app4.setAllowance(doc4);
        app4.setCustomer(customer);
        
        Application app5 = factory.createApplication();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCompany(company5);
        app5.setAllowance(doc5);
        app5.setCustomer(customer);
        
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
        // Setup: Customer "C105" creates and then cancels a pending application
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        // Create document
        Document document = factory.createDocument();
        
        // Create pending application
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Cancel the application
        boolean cancelResult = customer.cancelApplication("Cloud");
        assertTrue("Application cancellation should succeed", cancelResult);
        
        // Execute: Get application count
        int result = customer.getApplicationCount();
        
        // Verify: Should return 0 after cancellation (pending applications are not counted)
        assertEquals("Customer with canceled pending application should return 0", 0, result);
    }
}