package edu.ipo.ipo3.test;

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
    public void testCase1_noApplicationsAtAll() {
        // Setup: Customer "C101" with no applications
        Customer customer = factory.createCustomer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);

        // Execute and verify: getApplicationCount should return 0
        assertEquals(0, customer.getApplicationCount());
    }

    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102" with one approved application
        Customer customer = factory.createCustomer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);

        Company company = factory.createCompany();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");

        Document document = factory.createDocument();

        Application app = factory.createApplication();
        app.setCompany(company);
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);

        customer.getApplications().add(app);

        // Execute and verify: getApplicationCount should return 1
        assertEquals(1, customer.getApplicationCount());
    }

    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103" with 2 approved and 1 rejected applications
        Customer customer = factory.createCustomer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // can not apply, but still can have history

        // Approved application 1
        Company company1 = factory.createCompany();
        company1.setName("Neuralink");
        company1.setEmail("neuralink@example.com");
        Document doc1 = factory.createDocument();
        Application app1 = factory.createApplication();
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);

        // Approved application 2
        Company company2 = factory.createCompany();
        company2.setName("SpaceY");
        company2.setEmail("spacey@example.com");
        Document doc2 = factory.createDocument();
        Application app2 = factory.createApplication();
        app2.setCompany(company2);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);

        // Rejected application
        Company company3 = factory.createCompany();
        company3.setName("BioGen");
        company3.setEmail("biogen@example.com");
        Document doc3 = factory.createDocument();
        Application app3 = factory.createApplication();
        app3.setCompany(company3);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);

        // Execute and verify: getApplicationCount should return 3
        assertEquals(3, customer.getApplicationCount());
    }

    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104" with 1 approved, 2 rejected, 2 pending applications
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);

        // Approved application
        Company company1 = factory.createCompany();
        company1.setName("RoboCorp");
        Document doc1 = factory.createDocument();
        Application app1 = factory.createApplication();
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);

        // Rejected applications
        Company company2 = factory.createCompany();
        company2.setName("AI Ventures");
        Document doc2 = factory.createDocument();
        Application app2 = factory.createApplication();
        app2.setCompany(company2);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);

        Company company3 = factory.createCompany();
        company3.setName("NanoMed");
        Document doc3 = factory.createDocument();
        Application app3 = factory.createApplication();
        app3.setCompany(company3);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);

        // Pending applications (should not be counted)
        Company company4 = factory.createCompany();
        company4.setName("GreenEnergy");
        Document doc4 = factory.createDocument();
        Application app4 = factory.createApplication();
        app4.setCompany(company4);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        customer.getApplications().add(app4);

        Company company5 = factory.createCompany();
        company5.setName("CloudScale");
        Document doc5 = factory.createDocument();
        Application app5 = factory.createApplication();
        app5.setCompany(company5);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        customer.getApplications().add(app5);

        // Execute and verify: getApplicationCount should return 3 (1 approved + 2 rejected)
        assertEquals(3, customer.getApplicationCount());
    }

    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105" creates and then cancels an application
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);

        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");

        Document document = factory.createDocument();

        Application app = factory.createApplication();
        app.setCompany(company);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);

        customer.getApplications().add(app);

        // Cancel the application
        boolean canceled = app.cancel();
        assertTrue("Application should be canceled", canceled);
        customer.getApplications().remove(app);

        // Execute and verify: getApplicationCount should return 0
        assertEquals(0, customer.getApplicationCount());
    }
}