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
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
        
        // Create companies
        ecoWave = factory.createCompany();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        smartGrid = factory.createCompany();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        medLife = factory.createCompany();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        urbanTech = factory.createCompany();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        agroSeed = factory.createCompany();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@example.com");
        
        // Create customers
        customerC301 = factory.createCustomer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        customerC302 = factory.createCustomer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        customerC303 = factory.createCustomer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        customerC304 = factory.createCustomer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        
        customerC306 = factory.createCustomer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create pending application for EcoWave
        Document doc1 = factory.createDocument();
        boolean created = customerC301.createApplication(ecoWave, 15, 750.0, doc1);
        assertTrue("Application should be created successfully", created);
        
        // Verify initial state
        assertEquals(1, customerC301.getApplications().size());
        Application app = customerC301.getApplications().get(0);
        assertEquals(ApplicationStatus.PENDING, app.getStatus());
        assertEquals("EcoWave", app.getCompany().getName());
        
        // Test: Cancel pending application
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify result
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals(0, customerC301.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create and approve application for SmartGrid
        Document doc2 = factory.createDocument();
        boolean created = customerC302.createApplication(smartGrid, 30, 3000.0, doc2);
        assertTrue("Application should be created successfully", created);
        
        // Approve the application
        Application app = customerC302.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        assertEquals(ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify result
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals(1, customerC302.getApplications().size());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create and reject application for MedLife
        Document doc3 = factory.createDocument();
        boolean created = customerC303.createApplication(medLife, 20, 1000.0, doc3);
        assertTrue("Application should be created successfully", created);
        
        // Reject the application
        Application app = customerC303.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        assertEquals(ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify result
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals(1, customerC303.getApplications().size());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer has no applications
        assertEquals(0, customerC304.getApplications().size());
        
        // Test: Try to cancel application for non-existent company
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify result
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals(0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create two pending applications
        Document doc5 = factory.createDocument();
        Document doc6 = factory.createDocument();
        
        boolean created1 = customerC306.createApplication(urbanTech, 25, 1250.0, doc5);
        boolean created2 = customerC306.createApplication(agroSeed, 40, 2000.0, doc6);
        
        assertTrue("First application should be created successfully", created1);
        assertTrue("Second application should be created successfully", created2);
        assertEquals(2, customerC306.getApplications().size());
        
        // Test: Cancel UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify result
        assertTrue("Cancellation should succeed for UrbanTech", result);
        assertEquals(1, customerC306.getApplications().size());
        
        // Verify AgroSeed application remains
        Application remainingApp = customerC306.getApplications().get(0);
        assertEquals("AgroSeed", remainingApp.getCompany().getName());
        assertEquals(ApplicationStatus.PENDING, remainingApp.getStatus());
    }
}