import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = new Customer();
    }
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmount(750.0);
        
        Document doc = new Document();
        doc.setDocumentId("EW-2024-03");
        pendingApp.setDocument(doc);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(pendingApp);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelPendingApplication("EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Cancellation should succeed for pending application", result);
        
        // Verify application status changed to CANCELLED
        assertEquals("Application status should be CANCELLED", 
                     ApplicationStatus.CANCELLED, pendingApp.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmount(3000.0);
        
        Document doc = new Document();
        doc.setDocumentId("SG-2024-01");
        approvedApp.setDocument(doc);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelPendingApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for approved application", result);
        
        // Verify application status remains APPROVED
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, approvedApp.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmount(1000.0);
        
        Document doc = new Document();
        doc.setDocumentId("SG-2024-03");
        rejectedApp.setDocument(doc);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(rejectedApp);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelPendingApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for rejected application", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customer.cancelPendingApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company", result);
        
        // Verify no applications were affected (list remains empty)
        assertEquals("Applications list should remain empty", 
                     0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // First application: UrbanTech
        IPOApplication urbanTechApp = new IPOApplication();
        urbanTechApp.setCompanyName("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmount(1250.0);
        
        Document doc1 = new Document();
        doc1.setDocumentId("SG-2024-005");
        urbanTechApp.setDocument(doc1);
        urbanTechApp.setStatus(ApplicationStatus.PENDING);
        
        // Second application: AgroSeed
        IPOApplication agroSeedApp = new IPOApplication();
        agroSeedApp.setCompanyName("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroSeedApp.setAmount(2000.0);
        
        Document doc2 = new Document();
        doc2.setDocumentId("SG-2024-006");
        agroSeedApp.setDocument(doc2);
        agroSeedApp.setStatus(ApplicationStatus.PENDING);
        
        // Add both applications to customer
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelPendingApplication("UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        
        // Verify UrbanTech application status changed to CANCELLED
        assertEquals("UrbanTech application status should be CANCELLED", 
                     ApplicationStatus.CANCELLED, urbanTechApp.getStatus());
        
        // Verify AgroSeed application remains PENDING (unaffected)
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
        
        // Verify both applications still exist in the list
        assertEquals("Customer should still have 2 applications", 
                     2, customer.getApplications().size());
    }
}