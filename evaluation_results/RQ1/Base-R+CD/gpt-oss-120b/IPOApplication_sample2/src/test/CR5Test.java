import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Cancellation should succeed
        assertTrue("Cancellation of pending application should return true", result);
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and approve it
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Cancellation should fail for approved application
        assertFalse("Cancellation of approved application should return false", result);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and reject it
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Cancellation should fail for rejected application
        assertFalse("Cancellation of rejected application should return false", result);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No application created for "UnknownCorp"
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Cancellation should fail when no application exists
        assertFalse("Cancellation for non-existent company should return false", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first application for "UrbanTech"
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        boolean app1Created = customer.createApplication(urbanTech, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        // Create second application for "AgroSeed"
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@innovate.com");
        
        boolean app2Created = customer.createApplication(agroSeed, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications exist and are pending
        assertEquals("Customer should have 2 applications", 2, customer.getApplications().size());
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: Cancellation should succeed for "UrbanTech"
        assertTrue("Cancellation of UrbanTech application should return true", result);
        
        // Verify: "AgroSeed" application should remain unaffected
        boolean agroSeedStillExists = false;
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("AgroSeed") && 
                app.getStatus() == ApplicationStatus.PENDING) {
                agroSeedStillExists = true;
                break;
            }
        }
        assertTrue("AgroSeed application should remain pending", agroSeedStillExists);
    }
}