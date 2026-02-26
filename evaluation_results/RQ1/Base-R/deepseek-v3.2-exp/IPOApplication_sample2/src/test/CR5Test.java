import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private IPOApplicationSystem system;
    
    @Before
    public void setUp() {
        system = new IPOApplicationSystem();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        IPOApplication pendingApp = new IPOApplication();
        pendingApp.setCompanyName("EcoWave");
        pendingApp.setNumberOfShares(15);
        pendingApp.setAmountPaid(750.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        Document document = new Document();
        document.setFileName("EW-2024-03");
        pendingApp.setDocument(document);
        pendingApp.setCustomer(customer);
        
        customer.getApplications().add(pendingApp);
        
        // Execute: Cancel the pending application for "EcoWave"
        boolean result = system.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be canceled successfully", result);
        assertEquals("Application list should be empty after cancellation", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        IPOApplication approvedApp = new IPOApplication();
        approvedApp.setCompanyName("SmartGrid");
        approvedApp.setNumberOfShares(30);
        approvedApp.setAmountPaid(3000.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        
        Document document = new Document();
        document.setFileName("SG-2024-01");
        approvedApp.setDocument(document);
        approvedApp.setCustomer(customer);
        
        customer.getApplications().add(approvedApp);
        
        // Execute: Try to cancel the approved application for "SmartGrid"
        boolean result = system.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail (approved applications cannot be canceled)
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getApplications().size());
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        IPOApplication rejectedApp = new IPOApplication();
        rejectedApp.setCompanyName("MedLife");
        rejectedApp.setNumberOfShares(20);
        rejectedApp.setAmountPaid(1000.0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        Document document = new Document();
        document.setFileName("SG-2024-03");
        rejectedApp.setDocument(document);
        rejectedApp.setCustomer(customer);
        
        customer.getApplications().add(rejectedApp);
        
        // Execute: Try to cancel the rejected application for "MedLife"
        boolean result = system.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail (rejected applications cannot be canceled)
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application list should remain unchanged", 1, customer.getApplications().size());
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no application for "UnknownCorp"
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Customer has no applications
        
        // Execute: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = system.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail (no application found for specified company)
        assertFalse("Cancellation should fail for non-existent company", result);
        assertTrue("Application list should remain empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // First application: UrbanTech
        IPOApplication app1 = new IPOApplication();
        app1.setCompanyName("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmountPaid(1250.0);
        app1.setStatus(ApplicationStatus.PENDING);
        
        Document doc1 = new Document();
        doc1.setFileName("SG-2024-005");
        app1.setDocument(doc1);
        app1.setCustomer(customer);
        
        // Second application: AgroSeed
        IPOApplication app2 = new IPOApplication();
        app2.setCompanyName("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmountPaid(2000.0);
        app2.setStatus(ApplicationStatus.PENDING);
        
        Document doc2 = new Document();
        doc2.setFileName("SG-2024-006");
        app2.setDocument(doc2);
        app2.setCustomer(customer);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute: Cancel the "UrbanTech" application
        boolean result = system.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: UrbanTech application should be canceled, AgroSeed should remain
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Should have one remaining application", 1, customer.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", "AgroSeed", customer.getApplications().get(0).getCompanyName());
        assertEquals("Remaining application should still be pending", ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
}