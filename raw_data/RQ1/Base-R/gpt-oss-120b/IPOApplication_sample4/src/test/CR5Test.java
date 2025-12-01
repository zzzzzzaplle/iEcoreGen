import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private IPOSystem ipoSystem;
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    
    @Before
    public void setUp() {
        ipoSystem = new IPOSystem();
        
        // Setup Customer C301
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        
        // Setup Customer C302
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        
        // Setup Customer C303
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        
        // Setup Customer C304
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        
        // Setup Customer C306
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Create pending application for EcoWave
        Company ecoWave = new Company();
        ecoWave.setName("EcoWave");
        
        Document doc = new Document();
        doc.setFileName("EW-2024-03");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customerC301);
        app.setCompany(ecoWave);
        app.setShares(15);
        app.setAmount(750.0);
        app.setDocument(doc);
        app.setStatus(ApplicationStatus.PENDING);
        
        customerC301.getApplications().add(app);
        ipoSystem.getAllApplications().add(app);
        
        // Test: Cancel pending application for EcoWave
        boolean result = ipoSystem.cancelPendingApplication(customerC301, "EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Pending application should be canceled successfully", result);
        assertFalse("Application should be removed from customer's applications", 
                   customerC301.getApplications().contains(app));
        assertFalse("Application should be removed from system's applications", 
                   ipoSystem.getAllApplications().contains(app));
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Create approved application for SmartGrid
        Company smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        
        Document doc = new Document();
        doc.setFileName("SG-2024-01");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customerC302);
        app.setCompany(smartGrid);
        app.setShares(30);
        app.setAmount(3000.0);
        app.setDocument(doc);
        app.setStatus(ApplicationStatus.APPROVED);
        
        customerC302.getApplications().add(app);
        ipoSystem.getAllApplications().add(app);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = ipoSystem.cancelPendingApplication(customerC302, "SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancelable", result);
        assertTrue("Application should remain in customer's applications", 
                  customerC302.getApplications().contains(app));
        assertTrue("Application should remain in system's applications", 
                  ipoSystem.getAllApplications().contains(app));
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Create rejected application for MedLife
        Company medLife = new Company();
        medLife.setName("MedLife");
        
        Document doc = new Document();
        doc.setFileName("SG-2024-03");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customerC303);
        app.setCompany(medLife);
        app.setShares(20);
        app.setAmount(1000.0);
        app.setDocument(doc);
        app.setStatus(ApplicationStatus.REJECTED);
        
        customerC303.getApplications().add(app);
        ipoSystem.getAllApplications().add(app);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = ipoSystem.cancelPendingApplication(customerC303, "MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancelable", result);
        assertTrue("Application should remain in customer's applications", 
                  customerC303.getApplications().contains(app));
        assertTrue("Application should remain in system's applications", 
                  ipoSystem.getAllApplications().contains(app));
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer C304 has no applications
        
        // Test: Try to cancel application for non-existent company
        boolean result = ipoSystem.cancelPendingApplication(customerC304, "UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent application", result);
        assertEquals("Customer should have no applications", 0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Create two pending applications for customer C306
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        
        Document doc1 = new Document();
        doc1.setFileName("SG-2024-005");
        
        Document doc2 = new Document();
        doc2.setFileName("SG-2024-006");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customerC306);
        app1.setCompany(urbanTech);
        app1.setShares(25);
        app1.setAmount(1250.0);
        app1.setDocument(doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customerC306);
        app2.setCompany(agroSeed);
        app2.setShares(40);
        app2.setAmount(2000.0);
        app2.setDocument(doc2);
        app2.setStatus(ApplicationStatus.PENDING);
        
        customerC306.getApplications().add(app1);
        customerC306.getApplications().add(app2);
        ipoSystem.getAllApplications().add(app1);
        ipoSystem.getAllApplications().add(app2);
        
        // Test: Cancel UrbanTech application
        boolean result = ipoSystem.cancelPendingApplication(customerC306, "UrbanTech");
        
        // Verify UrbanTech cancellation succeeded and AgroSeed remains
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertFalse("UrbanTech application should be removed from customer's applications", 
                   customerC306.getApplications().contains(app1));
        assertFalse("UrbanTech application should be removed from system's applications", 
                   ipoSystem.getAllApplications().contains(app1));
        assertTrue("AgroSeed application should remain in customer's applications", 
                  customerC306.getApplications().contains(app2));
        assertTrue("AgroSeed application should remain in system's applications", 
                  ipoSystem.getAllApplications().contains(app2));
        assertEquals("Customer should have one remaining application", 1, customerC306.getApplications().size());
        assertEquals("System should have one remaining application", 1, ipoSystem.getAllApplications().size());
    }
}