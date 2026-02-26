import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("EcoWave");
        application.setNumberOfShares(15);
        application.setAmount(750.0);
        
        Document document = new Document();
        document.setContent("EW-2024-03");
        application.setDocument(document);
        
        // Application is pending by default (not approved, not rejected)
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Test: Cancel pending application for EcoWave
        boolean result = bankSystem.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        assertFalse("Application should be removed from customer's list", 
                   customer.getApplications().contains(application));
        assertFalse("Application should be removed from bank system", 
                   bankSystem.getApplications().contains(application));
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("SmartGrid");
        application.setNumberOfShares(30);
        application.setAmount(3000.0);
        
        Document document = new Document();
        document.setContent("SG-2024-01");
        application.setDocument(document);
        
        // Mark application as approved
        application.setApproved(true);
        
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = bankSystem.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Cancellation should fail
        assertFalse("Approved application should not be cancellable", result);
        assertTrue("Application should remain in customer's list", 
                  customer.getApplications().contains(application));
        assertTrue("Application should remain in bank system", 
                  bankSystem.getApplications().contains(application));
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setEligibleForIPO(true);
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany("MedLife");
        application.setNumberOfShares(20);
        application.setAmount(1000.0);
        
        Document document = new Document();
        document.setContent("SG-2024-03");
        application.setDocument(document);
        
        // Mark application as rejected
        application.setRejected(true);
        
        customer.getApplications().add(application);
        bankSystem.getApplications().add(application);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = bankSystem.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Cancellation should fail
        assertFalse("Rejected application should not be cancellable", result);
        assertTrue("Application should remain in customer's list", 
                  customer.getApplications().contains(application));
        assertTrue("Application should remain in bank system", 
                  bankSystem.getApplications().contains(application));
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setEligibleForIPO(true);
        
        // Customer has no applications
        
        // Test: Try to cancel application for non-existent company
        boolean result = bankSystem.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Cancellation should fail
        assertFalse("Non-existent application should not be cancellable", result);
        assertTrue("Customer's application list should remain empty", 
                  customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setEligibleForIPO(true);
        
        // First application: UrbanTech
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany("UrbanTech");
        app1.setNumberOfShares(25);
        app1.setAmount(1250.0);
        
        Document doc1 = new Document();
        doc1.setContent("SG-2024-005");
        app1.setDocument(doc1);
        
        // Second application: AgroSeed
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany("AgroSeed");
        app2.setNumberOfShares(40);
        app2.setAmount(2000.0);
        
        Document doc2 = new Document();
        doc2.setContent("SG-2024-006");
        app2.setDocument(doc2);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        bankSystem.getApplications().add(app1);
        bankSystem.getApplications().add(app2);
        
        // Test: Cancel UrbanTech application
        boolean result = bankSystem.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain
        assertTrue("UrbanTech application should be cancellable", result);
        assertFalse("UrbanTech application should be removed from customer's list", 
                   customer.getApplications().contains(app1));
        assertFalse("UrbanTech application should be removed from bank system", 
                   bankSystem.getApplications().contains(app1));
        assertTrue("AgroSeed application should remain in customer's list", 
                  customer.getApplications().contains(app2));
        assertTrue("AgroSeed application should remain in bank system", 
                  bankSystem.getApplications().contains(app2));
        assertEquals("Customer should have 1 application remaining", 
                    1, customer.getApplications().size());
        assertEquals("Bank system should have 1 application remaining", 
                    1, bankSystem.getApplications().size());
    }
}