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

public class CR5Test {
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    /**
     * Test Case 1: Cancel still-pending request
     * Input: Customer "C301" requests cancellation for "EcoWave"
     * Expected Output: True
     */
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Create pending application
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setCustomer(customer);
        application.setShare(15);
        application.setAmountOfMoney(750.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute cancel operation
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify result
        assertTrue("Should be able to cancel pending application", result);
        assertEquals("Application status should be REJECTED (cancelled)", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    /**
     * Test Case 2: Cancel approved request
     * Input: Customer "C302" tries to cancel IPO for "SmartGrid"
     * Expected Output: False
     */
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Create approved application
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setCustomer(customer);
        application.setShare(30);
        application.setAmountOfMoney(3000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute cancel operation
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify result
        assertFalse("Should not be able to cancel approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    /**
     * Test Case 3: Cancel rejected request
     * Input: Customer "C303" tries to cancel the filing for "MedLife"
     * Expected Output: False
     */
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        // Setup company
        Company company = factory.createCompany();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Create rejected application
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setCustomer(customer);
        application.setShare(20);
        application.setAmountOfMoney(1000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute cancel operation
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify result
        assertFalse("Should not be able to cancel rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    /**
     * Test Case 4: Cancel nonexistent company
     * Input: Customer "C304" requests cancellation for "UnknownCorp"
     * Expected Output: False
     */
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Setup existing company (but not the one we're trying to cancel)
        Company company = factory.createCompany();
        company.setName("ExistingCorp");
        company.setEmail("existing@example.com");
        
        // Setup document
        Document document = factory.createDocument();
        
        // Create application for existing company
        Application application = factory.createApplication();
        application.setCompany(company);
        application.setCustomer(customer);
        application.setShare(10);
        application.setAmountOfMoney(500.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        // Add application to customer
        customer.getApplications().add(application);
        
        // Execute cancel operation for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify result
        assertFalse("Should not be able to cancel non-existent application", result);
        assertEquals("Existing application should remain PENDING", ApplicationStatus.PENDING, application.getStatus());
    }
    
    /**
     * Test Case 5: Cancel after prior cancellation
     * Input: Customer "C306" cancels "UrbanTech" filing
     * Expected Output: True
     */
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup customer
        Customer customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Setup companies
        Company urbanTech = factory.createCompany();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Company agroSeed = factory.createCompany();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@farm.com");
        
        // Setup documents
        Document document1 = factory.createDocument();
        Document document2 = factory.createDocument();
        
        // Create first pending application (UrbanTech)
        Application app1 = factory.createApplication();
        app1.setCompany(urbanTech);
        app1.setCustomer(customer);
        app1.setShare(25);
        app1.setAmountOfMoney(1250.0);
        app1.setAllowance(document1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create second pending application (AgroSeed)
        Application app2 = factory.createApplication();
        app2.setCompany(agroSeed);
        app2.setCustomer(customer);
        app2.setShare(40);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(document2);
        app2.setStatus(ApplicationStatus.PENDING);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute cancel operation for UrbanTech
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify result
        assertTrue("Should be able to cancel UrbanTech pending application", result);
        assertEquals("UrbanTech application status should be REJECTED (cancelled)", ApplicationStatus.REJECTED, app1.getStatus());
        assertEquals("AgroSeed application should remain PENDING", ApplicationStatus.PENDING, app2.getStatus());
    }
}