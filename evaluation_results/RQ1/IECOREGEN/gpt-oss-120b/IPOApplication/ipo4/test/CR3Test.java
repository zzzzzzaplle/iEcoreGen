package edu.ipo.ipo4.test;

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

    /**
     * Test Case 1: "No applications at all"
     * Input: Customer "C101" requests a count summary.
     * Setup:
     * 1. Customer "C101" (named "Thomas Anderson", email "t.anderson@example.com", phone "555-0101", can apply for IPO) 
     * 2. No IPO requests have ever been filed.
     * Expected Output: 0
     */
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Create customer C101
        Customer customer = factory.createCustomer();
        customer.setName("Thomas");
        customer.setSurname("Anderson");
        customer.setEmail("t.anderson@example.com");
        customer.setTelephone("555-0101");
        customer.setCanApplyForIPO(true);

        // Call getApplicationCount
        int result = customer.getApplicationCount();

        // Assert expected output
        assertEquals(0, result);
    }

    /**
     * Test Case 2: "Single pending request"
     * Input: Customer "C102" asks for the total number of filings.
     * Setup:
     * 1. Customer "C102" (named "Lisa Rodriguez", email "l.rodriguez@example.com", phone "555-0202", can apply for IPO)
     * 2. One record exists in pending status:
     *     Application ID: "APP-2024-001"
     *     Company: "QuantumTech" (quantumtech@gmail.com)
     *     Shares: 50 ($2,500)
     *     Document: 'QT-2024-FormA'
     *     Status: approval
     * Expected Output: 1
     */
    @Test
    public void testCase2_singleApprovedRequest() {
        // Create customer C102
        Customer customer = factory.createCustomer();
        customer.setName("Lisa");
        customer.setSurname("Rodriguez");
        customer.setEmail("l.rodriguez@example.com");
        customer.setTelephone("555-0202");
        customer.setCanApplyForIPO(true);

        // Create company QuantumTech
        Company company = factory.createCompany();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application
        Application app = factory.createApplication();
        app.setCompany(company);
        app.setCustomer(customer);
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.APPROVAL); // Set status to APPROVAL

        // Add application to customer
        customer.getApplications().add(app);

        // Call getApplicationCount
        int result = customer.getApplicationCount();

        // Assert expected output
        assertEquals(1, result);
    }

    /**
     * Test Case 3: "Mix of approved and rejected"
     * Input: Customer "C103" checks total filings.
     * Setup:
     * 1. Customer "C103" (named "David Kim", email "d.kim@example.com", phone "555-0303", can not apply for IPO) 
     * 2. Two APPROVAL records and one REJECTED record are stored:
     * - Approved applications:
     *     "APP-2023-101" (Neuralink, 100 shares/$10,000, Document: 'QT-22023-101')
     *     "APP-2023-102" (SpaceY, 30 shares/$15,000, Document: 'QT-2023-102')
     * - Rejected application:
     *     "APP-2024-002" (BioGen, 20 shares/$1,000, Document: 'QT-2024-002')
     * Expected Output: 3
     */
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Create customer C103
        Customer customer = factory.createCustomer();
        customer.setName("David");
        customer.setSurname("Kim");
        customer.setEmail("d.kim@example.com");
        customer.setTelephone("555-0303");
        customer.setCanApplyForIPO(false); // can not apply for IPO

        // Create companies
        Company neuralink = factory.createCompany();
        neuralink.setName("Neuralink");
        neuralink.setEmail("info@neuralink.com");

        Company spaceY = factory.createCompany();
        spaceY.setName("SpaceY");
        spaceY.setEmail("contact@spacey.com");

        Company bioGen = factory.createCompany();
        bioGen.setName("BioGen");
        bioGen.setEmail("support@biogen.com");

        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();

        // Create approved application 1
        Application app1 = factory.createApplication();
        app1.setCompany(neuralink);
        app1.setCustomer(customer);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);

        // Create approved application 2
        Application app2 = factory.createApplication();
        app2.setCompany(spaceY);
        app2.setCustomer(customer);
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);

        // Create rejected application
        Application app3 = factory.createApplication();
        app3.setCompany(bioGen);
        app3.setCustomer(customer);
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);

        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);

        // Call getApplicationCount
        int result = customer.getApplicationCount();

        // Assert expected output
        assertEquals(3, result);
    }

    /**
     * Test Case 4: "Five historical requests"
     * Input: Customer "C104" queries the overall count.
     * Setup:
     * 1. Customer "C104" (named "Emma Wilson", email "e.wilson@example.com", phone "555-0404", can apply for IPO)
     * 2. Five records exist: 1 APPROVAL, 2 REJECTED, 2 pending:
     * - APPROVED: "APP-2023-105" (RoboCorp, 100 shares/$10,000, Document: 'QT-2023-105')
     * - REJECTED:
     *     "APP-2023-106" (AI Ventures, 100 shares/$10,000, Document: 'QT-2023-106')
     *     "APP-2024-003" (NanoMed, 100 shares/$10,000, Document: 'QT-2024-003')
     * - PENDING:
     *     "APP-2024-004" (GreenEnergy, 100 shares/$10,000, Document: 'QT-2024-004')
     *     "APP-2024-005" (CloudScale, 100 shares/$10,000, Document: 'QT-2024-005')
     * Expected Output: 3
     */
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Create customer C104
        Customer customer = factory.createCustomer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setEmail("e.wilson@example.com");
        customer.setTelephone("555-0404");
        customer.setCanApplyForIPO(true);

        // Create companies
        Company roboCorp = factory.createCompany();
        roboCorp.setName("RoboCorp");
        roboCorp.setEmail("info@robocorp.com");

        Company aiVentures = factory.createCompany();
        aiVentures.setName("AI Ventures");
        aiVentures.setEmail("contact@aiventures.com");

        Company nanoMed = factory.createCompany();
        nanoMed.setName("NanoMed");
        nanoMed.setEmail("support@nanomed.com");

        Company greenEnergy = factory.createCompany();
        greenEnergy.setName("GreenEnergy");
        greenEnergy.setEmail("info@greenenergy.com");

        Company cloudScale = factory.createCompany();
        cloudScale.setName("CloudScale");
        cloudScale.setEmail("contact@cloudscale.com");

        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();
        Document doc4 = factory.createDocument();
        Document doc5 = factory.createDocument();

        // Create approved application
        Application app1 = factory.createApplication();
        app1.setCompany(roboCorp);
        app1.setCustomer(customer);
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);

        // Create rejected application 1
        Application app2 = factory.createApplication();
        app2.setCompany(aiVentures);
        app2.setCustomer(customer);
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);

        // Create rejected application 2
        Application app3 = factory.createApplication();
        app3.setCompany(nanoMed);
        app3.setCustomer(customer);
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.REJECTED);

        // Create pending application 1
        Application app4 = factory.createApplication();
        app4.setCompany(greenEnergy);
        app4.setCustomer(customer);
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);

        // Create pending application 2
        Application app5 = factory.createApplication();
        app5.setCompany(cloudScale);
        app5.setCustomer(customer);
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);

        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);

        // Call getApplicationCount
        int result = customer.getApplicationCount();

        // Assert expected output (only approved and rejected count)
        assertEquals(3, result);
    }

    /**
     * Test Case 5: "All requests canceled"
     * Input: Customer "C105" asks for the figure.
     * Setup:
     * 1. Customer "C105" (named "James Chen", email "j.chen@example.com", phone "555-0505", can apply for IPO)
     * 2. Create a pending application "APP-1010" : Applied for 10 shares ($5000) in "Cloud" (email: Cloud@gmail.com), Document: 'QT-1010'
     * 3. Cancel application "APP-1010" to "Cloud"
     * Expected Output: 0
     */
    @Test
    public void testCase5_allRequestsCanceled() {
        // Create customer C105
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Chen");
        customer.setEmail("j.chen@example.com");
        customer.setTelephone("555-0505");
        customer.setCanApplyForIPO(true);

        // Create company Cloud
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create pending application
        Application app = factory.createApplication();
        app.setCompany(company);
        app.setCustomer(customer);
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.PENDING);

        // Add application to customer
        customer.getApplications().add(app);

        // Cancel the application
        boolean canceled = app.cancel();
        assertTrue(canceled);
        assertEquals(ApplicationStatus.REJECTED, app.getStatus()); // Canceled applications have REJECTED status

        // Call getApplicationCount
        int result = customer.getApplicationCount();

        // Assert expected output (canceled applications are not counted as reviewed)
        assertEquals(0, result);
    }
}