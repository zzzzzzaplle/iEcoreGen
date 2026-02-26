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

public class CR5Test {

    private IpoFactory factory;

    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }

    /**
     * Test Case 1: Cancel still-pending request
     * Input: Customer "C301" requests cancellation for "EcoWave"
     * Setup:
     * 1. Customer "C301" (named "Benjamin Taylor", email "b.taylor@example.com", phone "555-1010") has a pending application for "EcoWave" (ecowave@gmail.com):
     * - Pending Application:
     *     ID: "APP-4001"
     *     Shares: 15 ($750)
     *     Document: 'EW-2024-03'
     *     Status: pending
     * Expected Output: True
     */
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Create customer C301
        Customer customer = factory.createCustomer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);

        // Create company EcoWave
        Company company = factory.createCompany();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");

        // Create document
        Document document = factory.createDocument();

        // Create pending application
        Application application = factory.createApplication();
        application.setShare(15);
        application.setAmountOfMoney(750.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);

        // Add application to customer
        customer.getApplications().add(application);

        // Perform cancellation
        boolean result = customer.cancelApplication("EcoWave");

        // Verify result
        assertTrue(result);
        assertEquals(ApplicationStatus.REJECTED, application.getStatus()); // Cancellation sets status to REJECTED
    }

    /**
     * Test Case 2: Cancel approved request
     * Input: Customer "C302" tries to cancel IPO for "SmartGrid".
     * Setup:
     * 1. Customer "C302" (named "Charlotte Lee", email "c.lee@example.com", phone "555-2020") has an approved application for "SmartGrid" (smartgrid@business.com)
     * - Approved Application:
     *     ID: "APP-4002"
     *     Shares: 30 ($3,000)
     *     Document: 'SG-2024-01'
     *     Status: approval
     * Expected Output: False (Cannot cancel approved applications)
     */
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Create customer C302
        Customer customer = factory.createCustomer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);

        // Create company SmartGrid
        Company company = factory.createCompany();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");

        // Create document
        Document document = factory.createDocument();

        // Create approved application
        Application application = factory.createApplication();
        application.setShare(30);
        application.setAmountOfMoney(3000.0);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);

        // Add application to customer
        customer.getApplications().add(application);

        // Perform cancellation
        boolean result = customer.cancelApplication("SmartGrid");

        // Verify result
        assertFalse(result);
        assertEquals(ApplicationStatus.APPROVAL, application.getStatus()); // Status should remain unchanged
    }

    /**
     * Test Case 3: Cancel rejected request
     * Input: Customer "C303" tries to cancel the filing for "MedLife".
     * Setup:
     * 1. Customer "C303" (named "Lucas Martin", email "l.martin@example.com", phone "555-3030") has a rejected application "MedLife" (medlife@health.com) application
     * - Rejected Application:
     *     ID: "APP-4003"
     *     Shares: 20 ($1,000)
     *     Rejection Reason: "Insufficient funds"
     *     Document: 'SG-2024-03'
     *     Status: rejected
     * Expected Output: False (Application already finalized)
     */
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Create customer C303
        Customer customer = factory.createCustomer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);

        // Create company MedLife
        Company company = factory.createCompany();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");

        // Create document
        Document document = factory.createDocument();

        // Create rejected application
        Application application = factory.createApplication();
        application.setShare(20);
        application.setAmountOfMoney(1000.0);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);

        // Add application to customer
        customer.getApplications().add(application);

        // Perform cancellation
        boolean result = customer.cancelApplication("MedLife");

        // Verify result
        assertFalse(result);
        assertEquals(ApplicationStatus.REJECTED, application.getStatus()); // Status should remain unchanged
    }

    /**
     * Test Case 4: Cancel nonexistent company
     * Input: Customer "C304" requests cancellation for "UnknownCorp".
     * Setup:
     * 1. Customer "C304" (named "Amelia Clark", email "a.clark@example.com", phone "555-4040") requests cancellation for "UnknownCorp"
     * 1. No filing exists IPO for "UnknownCorp".
     * Expected Output: False (No application found for specified company)
     */
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Create customer C304
        Customer customer = factory.createCustomer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);

        // Perform cancellation for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");

        // Verify result
        assertFalse(result);
    }

    /**
     * Test Case 5: Cancel after prior cancellation
     * Input: Customer "C306" cancels "UrbanTech" (urbantech@innovate.com) filing
     * Setup:
     * 1. Customer "C305" "C306" (named "Mia Anderson", email "m.anderson@example.com", phone "555-6060") has two pending IPO.
     * 2. Pending Applications:
     * - "APP-4005":
     *     Company: "UrbanTech"
     *     Shares: 25 ($1,250)
     *     Document: 'SG-2024-005'
     *
     * - "APP-4006":
     *     Company: "AgroSeed"
     *     Shares: 40 ($2,000)
     *     Document: 'SG-2024-006'
     * Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
     */
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Create customer C306
        Customer customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);

        // Create company UrbanTech
        Company urbanTech = factory.createCompany();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");

        // Create company AgroSeed
        Company agroSeed = factory.createCompany();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@farm.com");

        // Create documents
        Document document1 = factory.createDocument();
        Document document2 = factory.createDocument();

        // Create UrbanTech application
        Application urbanTechApp = factory.createApplication();
        urbanTechApp.setShare(25);
        urbanTechApp.setAmountOfMoney(1250.0);
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        urbanTechApp.setCompany(urbanTech);
        urbanTechApp.setAllowance(document1);
        urbanTechApp.setCustomer(customer);

        // Create AgroSeed application
        Application agroSeedApp = factory.createApplication();
        agroSeedApp.setShare(40);
        agroSeedApp.setAmountOfMoney(2000.0);
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        agroSeedApp.setCompany(agroSeed);
        agroSeedApp.setAllowance(document2);
        agroSeedApp.setCustomer(customer);

        // Add applications to customer
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);

        // Perform cancellation for UrbanTech
        boolean result = customer.cancelApplication("UrbanTech");

        // Verify result
        assertTrue(result);
        assertEquals(ApplicationStatus.REJECTED, urbanTechApp.getStatus()); // UrbanTech app should be cancelled
        assertEquals(ApplicationStatus.PENDING, agroSeedApp.getStatus()); // AgroSeed app should remain pending
    }
}