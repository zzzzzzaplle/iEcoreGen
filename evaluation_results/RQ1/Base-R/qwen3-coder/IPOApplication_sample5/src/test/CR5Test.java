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
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer C301 with pending application for EcoWave
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmailAddress("b.taylor@example.com");
        customer.setTelephoneNumber("555-1010");
        
        Company company = new Company();
        company.setName("EcoWave");
        company.setEmailAddress("ecowave@gmail.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(15);
        application.setAmount(750.0);
        application.setDocument("EW-2024-03");
        // Status remains pending (default values)
        
        customer.getApplications().add(application);
        
        // Test: Cancel pending application for EcoWave
        boolean result = system.cancelPendingApplication(customer, "EcoWave");
        
        // Verify: Should return true since application was pending
        assertTrue("Pending application should be cancellable", result);
        assertEquals("Application should be removed from customer's list", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved application for SmartGrid
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmailAddress("c.lee@example.com");
        customer.setTelephoneNumber("555-2020");
        
        Company company = new Company();
        company.setName("SmartGrid");
        company.setEmailAddress("smartgrid@business.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(30);
        application.setAmount(3000.0);
        application.setDocument("SG-2024-01");
        application.setApproved(true); // Mark as approved
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel approved application for SmartGrid
        boolean result = system.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify: Should return false since application is already approved
        assertFalse("Approved application should not be cancellable", result);
        assertEquals("Application should remain in customer's list", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected application for MedLife
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmailAddress("l.martin@example.com");
        customer.setTelephoneNumber("555-3030");
        
        Company company = new Company();
        company.setName("MedLife");
        company.setEmailAddress("medlife@health.com");
        
        IPOApplication application = new IPOApplication();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setNumberOfShares(20);
        application.setAmount(1000.0);
        application.setDocument("SG-2024-03");
        application.setRejected(true); // Mark as rejected
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel rejected application for MedLife
        boolean result = system.cancelPendingApplication(customer, "MedLife");
        
        // Verify: Should return false since application is already rejected
        assertFalse("Rejected application should not be cancellable", result);
        assertEquals("Application should remain in customer's list", 1, customer.getApplications().size());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmailAddress("a.clark@example.com");
        customer.setTelephoneNumber("555-4040");
        
        // No applications added to customer
        
        // Test: Try to cancel application for non-existent company
        boolean result = system.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify: Should return false since no application exists for the company
        assertFalse("Non-existent application should not be cancellable", result);
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmailAddress("m.anderson@example.com");
        customer.setTelephoneNumber("555-6060");
        
        // First application: UrbanTech
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmailAddress("urbantech@innovate.com");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(25);
        app1.setAmount(1250.0);
        app1.setDocument("SG-2024-005");
        
        // Second application: AgroSeed
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmailAddress("agroseed@innovate.com");
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(40);
        app2.setAmount(2000.0);
        app2.setDocument("SG-2024-006");
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Cancel UrbanTech application
        boolean result = system.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify: Should return true and only UrbanTech application should be removed
        assertTrue("Pending UrbanTech application should be cancellable", result);
        assertEquals("Should have one remaining application", 1, customer.getApplications().size());
        assertEquals("Remaining application should be AgroSeed", "AgroSeed", customer.getApplications().get(0).getCompany().getName());
    }
}