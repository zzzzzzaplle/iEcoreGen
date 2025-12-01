package edu.ipo.ipo2.test;

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
        customer.setName("Thomas Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);

        // Execute: Get application count
        int count = customer.getApplicationCount();

        // Verify: Should return 0
        assertEquals(0, count);
    }

    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Customer "C102"
        Customer customer = factory.createCustomer();
        customer.setName("Lisa Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);

        // Setup: One approved application
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

        // Execute: Get application count
        int count = customer.getApplicationCount();

        // Verify: Should return 1
        assertEquals(1, count);
    }

    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Customer "C103"
        Customer customer = factory.createCustomer();
        customer.setName("David Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // Can't apply, but can still have history

        // Setup: Two approved applications
        Company neuralink = factory.createCompany();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");

        Company spaceY = factory.createCompany();
        spaceY.setName("SpaceY");
        spaceY.setEmail("spacey@example.com");

        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();

        Application app1 = factory.createApplication();
        app1.setCompany(neuralink);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);

        Application app2 = factory.createApplication();
        app2.setCompany(spaceY);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);

        // Setup: One rejected application
        Company bioGen = factory.createCompany();
        bioGen.setName("BioGen");
        bioGen.setEmail("biogen@example.com");

        Document doc3 = factory.createDocument();

        Application app3 = factory.createApplication();
        app3.setCompany(bioGen);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);

        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);

        // Execute: Get application count
        int count = customer.getApplicationCount();

        // Verify: Should return 3 (2 approved + 1 rejected)
        assertEquals(3, count);
    }

    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Customer "C104"
        Customer customer = factory.createCustomer();
        customer.setName("Emma Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);

        // Setup: 1 approved application
        Company roboCorp = factory.createCompany();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("robocorp@example.com");

        Document doc1 = factory.createDocument();

        Application app1 = factory.createApplication();
        app1.setCompany(roboCorp);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);

        // Setup: 2 rejected applications
        Company aiVentures = factory.createCompany();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("aiventures@example.com");

        Company nanoMed = factory.createCompany();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("nanomed@example.com");

        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();

        Application app2 = factory.createApplication();
        app2.setCompany(aiVentures);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);

        Application app3 = factory.createApplication();
        app3.setCompany(nanoMed);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCustomer(customer);

        // Setup: 2 pending applications
        Company greenEnergy = factory.createCompany();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("greenenergy@example.com");

        Company cloudScale = factory.createCompany();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("cloudscale@example.com");

        Document doc4 = factory.createDocument();
        Document doc5 = factory.createDocument();

        Application app4 = factory.createApplication();
        app4.setCompany(greenEnergy);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);

        Application app5 = factory.createApplication();
        app5.setCompany(cloudScale);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);

        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);

        // Execute: Get application count
        int count = customer.getApplicationCount();

        // Verify: Should return 3 (1 approved + 2 rejected)
        assertEquals(3, count);
    }

    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Customer "C105"
        Customer customer = factory.createCustomer();
        customer.setName("James Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);

        // Setup: Create a pending application
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

        // Setup: Cancel the application (which changes status to REJECTED)
        application.cancel();

        // Execute: Get application count
        int count = customer.getApplicationCount();

        // Verify: Should return 1 (canceled applications are in REJECTED status, which counts)
        assertEquals(1, count);
    }
}