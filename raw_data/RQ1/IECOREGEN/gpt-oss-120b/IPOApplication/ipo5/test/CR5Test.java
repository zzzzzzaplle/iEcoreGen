package edu.ipo.ipo5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.IpoFactory;
import edu.ipo.Customer;
import edu.ipo.Company;
import edu.ipo.Document;
import edu.ipo.Application;
import edu.ipo.ApplicationStatus;

public class CR5Test {
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer "Benjamin Taylor"
        Customer customer = factory.createCustomer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        // Setup: Create company "EcoWave"
        Company company = factory.createCompany();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Setup: Create document
        Document document = factory.createDocument();
        
        // Setup: Create pending application for EcoWave
        Application application = factory.createApplication();
        application.setShare(15);
        application.setAmountOfMoney(750.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Execute: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Should return true
        assertTrue(result);
        
        // Additional verification: Status should be REJECTED (canceled)
        assertEquals(ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer "Charlotte Lee"
        Customer customer = factory.createCustomer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        // Setup: Create company "SmartGrid"
        Company company = factory.createCompany();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Setup: Create document
        Document document = factory.createDocument();
        
        // Setup: Create approved application for SmartGrid
        Application application = factory.createApplication();
        application.setShare(30);
        application.setAmountOfMoney(3000.0);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Execute: Try to cancel the approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Should return false (cannot cancel approved applications)
        assertFalse(result);
        
        // Additional verification: Status should remain APPROVAL
        assertEquals(ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer "Lucas Martin"
        Customer customer = factory.createCustomer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        // Setup: Create company "MedLife"
        Company company = factory.createCompany();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Setup: Create document
        Document document = factory.createDocument();
        
        // Setup: Create rejected application for MedLife
        Application application = factory.createApplication();
        application.setShare(20);
        application.setAmountOfMoney(1000.0);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Execute: Try to cancel the rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Should return false (application already finalized)
        assertFalse(result);
        
        // Additional verification: Status should remain REJECTED
        assertEquals(ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer "Amelia Clark"
        Customer customer = factory.createCustomer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Setup: Create some other application (not for UnknownCorp)
        Company company = factory.createCompany();
        company.setName("ExistingCorp");
        company.setEmail("existing@example.com");
        
        Document document = factory.createDocument();
        
        Application application = factory.createApplication();
        application.setShare(10);
        application.setAmountOfMoney(500.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        customer.getApplications().add(application);
        
        // Execute: Try to cancel application for "UnknownCorp"
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Should return false (no application found for specified company)
        assertFalse(result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer "Mia Anderson"
        Customer customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Setup: Create company "UrbanTech"
        Company urbanTech = factory.createCompany();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        // Setup: Create company "AgroSeed"
        Company agroSeed = factory.createCompany();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@farm.com");
        
        // Setup: Create documents
        Document document1 = factory.createDocument();
        Document document2 = factory.createDocument();
        
        // Setup: Create pending application for UrbanTech
        Application urbanTechApp = factory.createApplication();
        urbanTechApp.setShare(25);
        urbanTechApp.setAmountOfMoney(1250.0);
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        urbanTechApp.setCompany(urbanTech);
        urbanTechApp.setAllowance(document1);
        urbanTechApp.setCustomer(customer);
        
        // Setup: Create pending application for AgroSeed
        Application agroSeedApp = factory.createApplication();
        agroSeedApp.setShare(40);
        agroSeedApp.setAmountOfMoney(2000.0);
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        agroSeedApp.setCompany(agroSeed);
        agroSeedApp.setAllowance(document2);
        agroSeedApp.setCustomer(customer);
        
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        
        // Execute: Cancel the UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Should return true (UrbanTech application canceled)
        assertTrue(result);
        
        // Additional verification: UrbanTech application should be canceled (REJECTED status)
        assertEquals(ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Additional verification: AgroSeed application should remain unaffected (still PENDING)
        assertEquals(ApplicationStatus.PENDING, agroSeedApp.getStatus());
        assertEquals(2, customer.getApplications().size()); // Both applications still in list
    }
}