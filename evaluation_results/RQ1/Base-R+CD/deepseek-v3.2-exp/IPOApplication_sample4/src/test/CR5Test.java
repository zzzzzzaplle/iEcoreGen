import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        document = new Document();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        customer.createApplication(company, 15, 750.0, document);
        
        // Test: Cancel the pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Pending application should be cancellable", result);
        
        // Verify application status changed to rejected (cancelled)
        assertEquals("Application should be rejected after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create and approve application
        customer.createApplication(company, 30, 3000.0, document);
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify application status remains approved
        assertEquals("Application status should remain approved", 
                     ApplicationStatus.APPROVAL, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create and reject application
        customer.createApplication(company, 20, 1000.0, document);
        customer.getApplications().get(0).setStatus(ApplicationStatus.REJECTED);
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify application status remains rejected
        assertEquals("Application status should remain rejected", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Customer "C304" with no application for "UnknownCorp"
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Create an application for a different company
        company = new Company();
        company.setName("ExistingCorp");
        company.setEmail("existing@corp.com");
        customer.createApplication(company, 10, 500.0, document);
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company", result);
        
        // Verify existing application remains unchanged
        assertEquals("Existing application should remain pending", 
                     ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create first application for "UrbanTech"
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        customer.createApplication(company1, 25, 1250.0, document);
        
        // Create second application for "AgroSeed"
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@agriculture.com");
        customer.createApplication(company2, 40, 2000.0, document);
        
        // Verify initial state: 2 pending applications
        assertEquals("Should have 2 applications initially", 2, customer.getApplications().size());
        assertEquals("First application should be pending", 
                     ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
        assertEquals("Second application should be pending", 
                     ApplicationStatus.PENDING, customer.getApplications().get(1).getStatus());
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("UrbanTech application should be cancellable", result);
        
        // Verify "UrbanTech" application is rejected (cancelled)
        assertEquals("UrbanTech application should be rejected after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
        
        // Verify "AgroSeed" application remains pending and unaffected
        assertEquals("AgroSeed application should remain pending", 
                     ApplicationStatus.PENDING, customer.getApplications().get(1).getStatus());
        
        // Verify both applications still exist
        assertEquals("Should still have 2 applications", 2, customer.getApplications().size());
    }
}