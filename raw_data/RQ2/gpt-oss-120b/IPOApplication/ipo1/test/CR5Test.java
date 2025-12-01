package edu.ipo.ipo1.test;

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
    
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Company ecoWave, smartGrid, medLife, urbanTech, agroSeed;
    private Document doc1, doc2, doc3, doc4, doc5;
    private Application app1, app2, app3, app4, app5;
    
    @Before
    public void setUp() {
        // Create documents
        doc1 = IpoFactory.eINSTANCE.createDocument();
        doc2 = IpoFactory.eINSTANCE.createDocument();
        doc3 = IpoFactory.eINSTANCE.createDocument();
        doc4 = IpoFactory.eINSTANCE.createDocument();
        doc5 = IpoFactory.eINSTANCE.createDocument();
        
        // Create companies
        ecoWave = IpoFactory.eINSTANCE.createCompany();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        smartGrid = IpoFactory.eINSTANCE.createCompany();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        medLife = IpoFactory.eINSTANCE.createCompany();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        urbanTech = IpoFactory.eINSTANCE.createCompany();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        agroSeed = IpoFactory.eINSTANCE.createCompany();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@farm.com");
        
        // Create customer C301 - Benjamin Taylor
        customer1 = IpoFactory.eINSTANCE.createCustomer();
        customer1.setName("Benjamin");
        customer1.setSurname("Taylor");
        customer1.setEmail("b.taylor@example.com");
        customer1.setTelephone("555-1010");
        customer1.setCanApplyForIPO(true);
        
        // Create customer C302 - Charlotte Lee
        customer2 = IpoFactory.eINSTANCE.createCustomer();
        customer2.setName("Charlotte");
        customer2.setSurname("Lee");
        customer2.setEmail("c.lee@example.com");
        customer2.setTelephone("555-2020");
        customer2.setCanApplyForIPO(true);
        
        // Create customer C303 - Lucas Martin
        customer3 = IpoFactory.eINSTANCE.createCustomer();
        customer3.setName("Lucas");
        customer3.setSurname("Martin");
        customer3.setEmail("l.martin@example.com");
        customer3.setTelephone("555-3030");
        customer3.setCanApplyForIPO(true);
        
        // Create customer C304 - Amelia Clark
        customer4 = IpoFactory.eINSTANCE.createCustomer();
        customer4.setName("Amelia");
        customer4.setSurname("Clark");
        customer4.setEmail("a.clark@example.com");
        customer4.setTelephone("555-4040");
        customer4.setCanApplyForIPO(true);
        
        // Create customer C306 - Mia Anderson
        customer5 = IpoFactory.eINSTANCE.createCustomer();
        customer5.setName("Mia");
        customer5.setSurname("Anderson");
        customer5.setEmail("m.anderson@example.com");
        customer5.setTelephone("555-6060");
        customer5.setCanApplyForIPO(true);
        
        // Create applications
        
        // APP-4001: Pending application for EcoWave (Customer C301)
        app1 = IpoFactory.eINSTANCE.createApplication();
        app1.setShare(15);
        app1.setAmountOfMoney(750.0);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setAllowance(doc1);
        app1.setCompany(ecoWave);
        app1.setCustomer(customer1);
        customer1.getApplications().add(app1);
        
        // APP-4002: Approved application for SmartGrid (Customer C302)
        app2 = IpoFactory.eINSTANCE.createApplication();
        app2.setShare(30);
        app2.setAmountOfMoney(3000.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setAllowance(doc2);
        app2.setCompany(smartGrid);
        app2.setCustomer(customer2);
        customer2.getApplications().add(app2);
        
        // APP-4003: Rejected application for MedLife (Customer C303)
        app3 = IpoFactory.eINSTANCE.createApplication();
        app3.setShare(20);
        app3.setAmountOfMoney(1000.0);
        app3.setStatus(ApplicationStatus.REJECTED);
        app3.setAllowance(doc3);
        app3.setCompany(medLife);
        app3.setCustomer(customer3);
        customer3.getApplications().add(app3);
        
        // Customer C304 has no applications
        
        // APP-4005: Pending application for UrbanTech (Customer C306)
        app4 = IpoFactory.eINSTANCE.createApplication();
        app4.setShare(25);
        app4.setAmountOfMoney(1250.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setAllowance(doc4);
        app4.setCompany(urbanTech);
        app4.setCustomer(customer5);
        customer5.getApplications().add(app4);
        
        // APP-4006: Pending application for AgroSeed (Customer C306)
        app5 = IpoFactory.eINSTANCE.createApplication();
        app5.setShare(40);
        app5.setAmountOfMoney(2000.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setAllowance(doc5);
        app5.setCompany(agroSeed);
        app5.setCustomer(customer5);
        customer5.getApplications().add(app5);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Input: Customer "C301" requests cancellation for "EcoWave"
        // Setup: Customer has a pending application for "EcoWave"
        // Expected Output: True
        
        boolean result = customer1.cancelApplication("EcoWave");
        
        assertTrue("Should be able to cancel a pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app1.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Input: Customer "C302" tries to cancel IPO for "SmartGrid"
        // Setup: Customer has an approved application for "SmartGrid"
        // Expected Output: False (Cannot cancel approved applications)
        
        boolean result = customer2.cancelApplication("SmartGrid");
        
        assertFalse("Should not be able to cancel an approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app2.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Input: Customer "C303" tries to cancel the filing for "MedLife"
        // Setup: Customer has a rejected application for "MedLife"
        // Expected Output: False (Application already finalized)
        
        boolean result = customer3.cancelApplication("MedLife");
        
        assertFalse("Should not be able to cancel a rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app3.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Input: Customer "C304" requests cancellation for "UnknownCorp"
        // Setup: No filing exists IPO for "UnknownCorp"
        // Expected Output: False (No application found for specified company)
        
        boolean result = customer4.cancelApplication("UnknownCorp");
        
        assertFalse("Should not be able to cancel a non-existent application", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Input: Customer "C306" cancels "UrbanTech" filing
        // Setup: Customer has two pending IPO applications
        // Expected Output: True ("UrbanTech" application canceled, "AgroSeed" remains unaffected)
        
        boolean result = customer5.cancelApplication("UrbanTech");
        
        assertTrue("Should be able to cancel a pending application", result);
        assertEquals("UrbanTech application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app4.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, app5.getStatus());
    }
}