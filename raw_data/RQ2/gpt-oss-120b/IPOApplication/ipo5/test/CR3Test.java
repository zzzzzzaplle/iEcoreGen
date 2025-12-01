package edu.ipo.ipo5.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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

        // Check application count
        int count = customer.getApplicationCount();
        assertEquals(0, count);
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

        // Create company
        Company company = factory.createCompany();
        company.setName("QuantumTech");
        company.setEmail("quantumtech@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create application
        Application app = factory.createApplication();
        app.setShare(50);
        app.setAmountOfMoney(2500.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCompany(company);
        app.setAllowance(document);
        app.setCustomer(customer); // This will add the app to customer's applications

        // Check application count
        int count = customer.getApplicationCount();
        assertEquals(1, count);
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
        customer.setCanApplyForIPO(false);

        // Create companies
        Company neuralink = factory.createCompany();
        neuralink.setName("Neuralink");
        neuralink.setEmail("info@neuralink.com");

        Company spaceY = factory.createCompany();
        spaceY.setName("SpaceY");
        spaceY.setEmail("contact@spacey.com");

        Company bioGen = factory.createCompany();
        bioGen.setName("BioGen");
        bioGen.setEmail("info@biogen.com");

        // Create documents
        Document doc1 = factory.createDocument();
        Document doc2 = factory.createDocument();
        Document doc3 = factory.createDocument();

        // Create approved application 1
        Application app1 = factory.createApplication();
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(neuralink);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);

        // Create approved application 2
        Application app2 = factory.createApplication();
        app2.setShare(30);
        app2.setAmountOfMoney(15000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCompany(spaceY);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);

        // Create rejected application
        Application app3 = factory.createApplication();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCompany(bioGen);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);

        // Check application count
        int count = customer.getApplicationCount();
        assertEquals(3, count);
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
        nanoMed.setEmail("info@nanomed.com");

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
        app1.setShare(100);
        app1.setAmountOfMoney(10000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCompany(roboCorp);
        app1.setAllowance(doc1);
        app1.setCustomer(customer);

        // Create rejected application 1
        Application app2 = factory.createApplication();
        app2.setShare(100);
        app2.setAmountOfMoney(10000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCompany(aiVentures);
        app2.setAllowance(doc2);
        app2.setCustomer(customer);

        // Create rejected application 2
        Application app3 = factory.createApplication();
        app3.setShare(100);
        app3.setAmountOfMoney(10000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setCompany(nanoMed);
        app3.setAllowance(doc3);
        app3.setCustomer(customer);

        // Create pending application 1
        Application app4 = factory.createApplication();
        app4.setShare(100);
        app4.setAmountOfMoney(10000.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCompany(greenEnergy);
        app4.setAllowance(doc4);
        app4.setCustomer(customer);

        // Create pending application 2
        Application app5 = factory.createApplication();
        app5.setShare(100);
        app5.setAmountOfMoney(10000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCompany(cloudScale);
        app5.setAllowance(doc5);
        app5.setCustomer(customer);

        // Check application count (only approved and rejected should count)
        int count = customer.getApplicationCount();
        assertEquals(3, count);
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

        // Create company
        Company company = factory.createCompany();
        company.setName("Cloud");
        company.setEmail("Cloud@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create a pending application
        Application app = factory.createApplication();
        app.setShare(10);
        app.setAmountOfMoney(5000.0);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCompany(company);
        app.setAllowance(document);
        app.setCustomer(customer);

        // Cancel the application
        app.cancel();

        // Check application count (canceled applications have status REJECTED, so they should count)
        // However, according to the requirement, only APPROVAL and REJECTED applications are counted
        // Since cancel changes status to REJECTED, it should be counted
        // But the test expects 0, so let's recheck the logic.
        // Looking at the cancel method, it sets status to REJECTED AND removes the application from customer's list
        int count = customer.getApplicationCount();
        assertEquals(0, count);
    }
}