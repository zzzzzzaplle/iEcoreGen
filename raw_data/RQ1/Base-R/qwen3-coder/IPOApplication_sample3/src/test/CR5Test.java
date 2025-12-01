import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private SoftwareSystem softwareSystem;
    
    @Before
    public void setUp() {
        softwareSystem = new SoftwareSystem();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompany("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmount(750.0);
        
        Document document = new Document();
        document.setContent("EW-2024-03");
        pendingApp.setDocument(document);
        pendingApp.setCustomer(customer);
        
        // Application is pending (neither approved nor rejected)
        pendingApp.setApproved(false);
        pendingApp.setRejected(false);
        
        customer.getApplications().add(pendingApp);
        
        // Test: Cancel pending application for EcoWave
        boolean result = softwareSystem.cancelPendingApplication(customer, "EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be canceled successfully", result);
        assertEquals("Application list should be empty after cancellation", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompany("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmount(3000.0);
        
        Document document = new Document();
        document.setContent("SG-2024-01");
        approvedApp.setDocument(document);
        approvedApp.setCustomer(customer);
        
        // Application is approved
        approvedApp.setApproved(true);
        approvedApp.setRejected(false);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = softwareSystem.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application list should still contain the approved application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompany("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmount(1000.0);
        
        Document document = new Document();
        document.setContent("SG-2024-03");
        rejectedApp.setDocument(document);
        rejectedApp.setCustomer(customer);
        
        // Application is rejected
        rejectedApp.setApproved(false);
        rejectedApp.setRejected(true);
        
        customer.getApplications().add(rejectedApp);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = softwareSystem.cancelPendingApplication(customer, "MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application list should still contain the rejected application", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Customer has no applications
        
        // Test: Try to cancel application for non-existent company UnknownCorp
        boolean result = softwareSystem.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify cancellation failed (no application found)
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Application list should remain empty", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // First pending application: UrbanTech
        IPOApplication app1 = new IPOApplication();
        app1.setCompany("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmount(1250.0);
        
        Document doc1 = new Document();
        doc1.setContent("SG-2024-005");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        app1.setApproved(false);
        app1.setRejected(false);
        
        // Second pending application: AgroSeed
        IPOApplication app2 = new IPOApplication();
        app2.setCompany("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmount(2000.0);
        
        Document doc2 = new Document();
        doc2.setContent("SG-2024-006");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        app2.setApproved(false);
        app2.setRejected(false);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Cancel UrbanTech application
        boolean result = softwareSystem.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify UrbanTech cancellation was successful and AgroSeed remains
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Should have one remaining application (AgroSeed)", 1, customer.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", customer.getApplications().get(0).getCompany());
    }
}