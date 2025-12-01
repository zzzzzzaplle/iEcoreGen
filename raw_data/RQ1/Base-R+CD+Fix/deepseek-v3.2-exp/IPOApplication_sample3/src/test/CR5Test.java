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
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify initial status is PENDING
        assertEquals("Should have 1 application", 1, customer.getApplications().size());
        assertEquals("Application should be pending", ApplicationStatus.PENDING, 
                         customer.getApplications().get(0).getStatus());
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Should return true and application status should be REJECTED
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                         ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Approve the application
        Application application = customer.getApplications().get(0);
        boolean approved = application.approve();
        assertTrue("Application should be approved successfully", approved);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        
        // Test: Try to cancel the approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Should return false and status remains APPROVAL
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                         ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Reject the application
        Application application = customer.getApplications().get(0);
        boolean rejected = application.reject();
        assertTrue("Application should be rejected successfully", rejected);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        
        // Test: Try to cancel the rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Should return false and status remains REJECTED
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                         ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Should return false (no application found)
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create first application for UrbanTech
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        boolean app1Created = customer.createApplication(company1, 25, 1250.0, document);
        assertTrue("First application should be created successfully", app1Created);
        
        // Create second application for AgroSeed
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@business.com");
        
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify initial state: 2 pending applications
        assertEquals("Should have 2 applications", 2, customer.getApplications().size());
        assertEquals("First application should be pending", ApplicationStatus.PENDING, 
                         customer.getApplications().get(0).getStatus());
        assertEquals("Second application should be pending", ApplicationStatus.PENDING, 
                         customer.getApplications().get(1).getStatus());
        
        // Test: Cancel UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain unaffected
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        
        // Check UrbanTech application status
        Application urbanTechApp = customer.getApplications().get(0);
        assertEquals("UrbanTech application should be REJECTED", 
                         ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        
        // Check AgroSeed application status remains PENDING
        Application agroSeedApp = customer.getApplications().get(1);
        assertEquals("AgroSeed application should remain PENDING", 
                         ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}