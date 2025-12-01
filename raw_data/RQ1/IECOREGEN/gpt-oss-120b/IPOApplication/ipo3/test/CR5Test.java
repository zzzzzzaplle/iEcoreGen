package edu.ipo.ipo3.test;

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
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup Customer "C301" (Benjamin Taylor)
        Customer customer = factory.createCustomer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "EcoWave"
        Company company = factory.createCompany();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create pending application for EcoWave
        Application application = factory.createApplication();
        application.setShare(15);
        application.setAmountOfMoney(750.0);
        application.setStatus(ApplicationStatus.PENDING);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Execute cancellation for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Assert that cancellation succeeded
        assertTrue(result);
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup Customer "C302" (Charlotte Lee)
        Customer customer = factory.createCustomer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "SmartGrid"
        Company company = factory.createCompany();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create approved application for SmartGrid
        Application application = factory.createApplication();
        application.setShare(30);
        application.setAmountOfMoney(3000.0);
        application.setStatus(ApplicationStatus.APPROVAL);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Assert that cancellation failed
        assertFalse(result);
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup Customer "C303" (Lucas Martin)
        Customer customer = factory.createCustomer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "MedLife"
        Company company = factory.createCompany();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Setup Document
        Document document = factory.createDocument();
        
        // Create rejected application for MedLife
        Application application = factory.createApplication();
        application.setShare(20);
        application.setAmountOfMoney(1000.0);
        application.setStatus(ApplicationStatus.REJECTED);
        application.setCompany(company);
        application.setAllowance(document);
        application.setCustomer(customer);
        
        // Add application to customer's applications
        customer.getApplications().add(application);
        
        // Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelApplication("MedLife");
        
        // Assert that cancellation failed
        assertFalse(result);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup Customer "C304" (Amelia Clark)
        Customer customer = factory.createCustomer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Try to cancel application for "UnknownCorp" (which doesn't exist)
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Assert that cancellation failed
        assertFalse(result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup Customer "C306" (Mia Anderson)
        Customer customer = factory.createCustomer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Setup Company "UrbanTech"
        Company urbanTechCompany = factory.createCompany();
        urbanTechCompany.setName("UrbanTech");
        urbanTechCompany.setEmail("urbantech@innovate.com");
        
        // Setup Company "AgroSeed"
        Company agroSeedCompany = factory.createCompany();
        agroSeedCompany.setName("AgroSeed");
        agroSeedCompany.setEmail("agroseed@farm.com");
        
        // Setup Documents
        Document document1 = factory.createDocument();
        Document document2 = factory.createDocument();
        
        // Create pending application for UrbanTech
        Application urbanTechApp = factory.createApplication();
        urbanTechApp.setShare(25);
        urbanTechApp.setAmountOfMoney(1250.0);
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        urbanTechApp.setCompany(urbanTechCompany);
        urbanTechApp.setAllowance(document1);
        urbanTechApp.setCustomer(customer);
        
        // Create pending application for AgroSeed
        Application agroSeedApp = factory.createApplication();
        agroSeedApp.setShare(40);
        agroSeedApp.setAmountOfMoney(2000.0);
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        agroSeedApp.setCompany(agroSeedCompany);
        agroSeedApp.setAllowance(document2);
        agroSeedApp.setCustomer(customer);
        
        // Add both applications to customer's applications
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        
        // Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Assert that cancellation succeeded
        assertTrue(result);
        
        // Verify that UrbanTech application was removed
        boolean urbanTechExists = false;
        boolean agroSeedExists = false;
        for (Application app : customer.getApplications()) {
            if (app.getCompany() != null) {
                if ("UrbanTech".equals(app.getCompany().getName())) {
                    urbanTechExists = true;
                }
                if ("AgroSeed".equals(app.getCompany().getName())) {
                    agroSeedExists = true;
                }
            }
        }
        
        // UrbanTech should no longer exist, AgroSeed should still be there
        assertFalse(urbanTechExists);
        assertTrue(agroSeedExists);
    }
}