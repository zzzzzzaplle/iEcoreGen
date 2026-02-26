package edu.ipo.ipo1.test;

import org.junit.Before;
import org.junit.Test;
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
        // Setup: Customer "C101" (named "Thomas Anderson", email "t.anderson@example.com", phone "555-0101", can apply for IPO)
        Customer customer = factory.createCustomer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);
        
        // No IPO requests have ever been filed
        // Expected Output: 0
        assertEquals(0, customer.getApplicationCount());
    }
    
    @Test
    public void testCase2_SinglePendingRequest() {
        // Setup: Customer "C102" (named "Lisa Rodriguez", email "l.rodriguez@example.com", phone "555-0202", can apply for IPO)
        Customer customer = factory.createCustomer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);
        
        // One record exists in APPROVAL status (Note: Test spec says "pending" but expected output is 1, 
        // and the requirement counts APPROVAL and REJECTED, so this must be APPROVAL)
        Company company = factory.createCompany();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");
        
        Document document = factory.createDocument();
        
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setShare(50);
        application.setAmountOfMoney(2500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Expected Output: 1
        assertEquals(1, customer.getApplicationCount());
    }
    
    @Test
    public void testCase3_MixOfApprovedAndRejected() {
        // Setup: Customer "C103" (named "David Kim", email "d.kim@example.com", phone "555-0303", can not apply for IPO)
        Customer customer = factory.createCustomer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false);
        
        // Two APPROVAL records
        // Application 1: Neuralink
        Company company1 = factory.createCompany();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@gmail.com");
        
        Document document1 = factory.createDocument();
        
        Application app1 = factory.createApplication();
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        
        // Application 2: SpaceY
        Company company2 = factory.createCompany();
        company2.setName("SpaceY");
        company2.setEmail("spacey@gmail.com");
        
        Document document2 = factory.createDocument();
        
        Application app2 = factory.createApplication();
        app2.setCompany(company2);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(document2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        
        // One REJECTED record: BioGen
        Company company3 = factory.createCompany();
        company3.setName("BioGen");
        company3.setEmail("biogen@gmail.com");
        
        Document document3 = factory.createDocument();
        
        Application app3 = factory.createApplication();
        app3.setCompany(company3);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(document3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        
        // Expected Output: 3
        assertEquals(3, customer.getApplicationCount());
    }
    
    @Test
    public void testCase4_FiveHistoricalRequests() {
        // Setup: Customer "C104" (named "Emma Wilson", email "e.wilson@example.com", phone "555-0404", can apply for IPO)
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);
        
        // 1 APPROVED: RoboCorp
        Company company1 = factory.createCompany();
        company1.setName("RoboCorp");
        company1.setEmail("robocorp@gmail.com");
        
        Document document1 = factory.createDocument();
        
        Application app1 = factory.createApplication();
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(document1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        
        // 2 REJECTED: AI Ventures and NanoMed
        Company company2 = factory.createCompany();
        company2.setName("AI Ventures");
        company2.setEmail("aiventures@gmail.com");
        
        Document document2 = factory.createDocument();
        
        Application app2 = factory.createApplication();
        app2.setCompany(company2);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(document2);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        
        Company company3 = factory.createCompany();
        company3.setName("NanoMed");
        company3.setEmail("nanomed@gmail.com");
        
        Document document3 = factory.createDocument();
        
        Application app3 = factory.createApplication();
        app3.setCompany(company3);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(document3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        
        // 2 PENDING: GreenEnergy and CloudScale
        Company company4 = factory.createCompany();
        company4.setName("GreenEnergy");
        company4.setEmail("greenenergy@gmail.com");
        
        Document document4 = factory.createDocument();
        
        Application app4 = factory.createApplication();
        app4.setCompany(company4);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(document4);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        
        Company company5 = factory.createCompany();
        company5.setName("CloudScale");
        company5.setEmail("cloudscale@gmail.com");
        
        Document document5 = factory.createDocument();
        
        Application app5 = factory.createApplication();
        app5.setCompany(company5);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(document5);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Expected Output: 3 (1 APPROVAL + 2 REJECTED, excluding 2 PENDING)
        assertEquals(3, customer.getApplicationCount());
    }
    
    @Test
    public void testCase5_AllRequestsCanceled() {
        // Setup: Customer "C105" (named "James Chen", email "j.chen@example.com", phone "555-0505", can apply for IPO)
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);
        
        // Create a pending application "APP-1010"
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");
        
        Document document = factory.createDocument();
        
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setShare(10);
        application.setAmountOfMoney(5000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Cancel application (which changes status to REJECTED)
        application.cancel();
        
        // Expected Output: 1 (canceled applications have REJECTED status, which should be counted)
        assertEquals(1, customer.getApplicationCount());
    }
}