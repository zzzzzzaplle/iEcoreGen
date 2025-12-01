import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_cancelStillPendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        Company company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        Document document = new Document();
        
        // Create pending application
        customer.createApplication(company, 15, 750.0, document);
        
        // Execute
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify
        assertTrue("Should successfully cancel pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        Company company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        Document document = new Document();
        
        // Create and approve application
        customer.createApplication(company, 30, 3000.0, document);
        Application app = customer.getApplications().get(0);
        app.approve(); // Change status to APPROVAL
        
        // Execute
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify
        assertFalse("Should not be able to cancel approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        Company company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        Document document = new Document();
        
        // Create and reject application
        customer.createApplication(company, 20, 1000.0, document);
        Application app = customer.getApplications().get(0);
        app.reject(); // Change status to REJECTED
        
        // Execute
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify
        assertFalse("Should not be able to cancel rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // No applications created
        
        // Execute
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify
        assertFalse("Should return false when no application exists for specified company", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@business.com");
        
        Document document1 = new Document();
        Document document2 = new Document();
        
        // Create two pending applications
        customer.createApplication(company1, 25, 1250.0, document1);
        customer.createApplication(company2, 40, 2000.0, document2);
        
        // Execute - cancel UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify
        assertTrue("Should successfully cancel UrbanTech application", result);
        assertEquals("Should have 2 applications total", 2, customer.getApplications().size());
        
        // Check UrbanTech application status
        Application urbanTechApp = null;
        Application agroSeedApp = null;
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("UrbanTech")) {
                urbanTechApp = app;
            } else if (app.getCompany().getName().equals("AgroSeed")) {
                agroSeedApp = app;
            }
        }
        
        assertNotNull("UrbanTech application should exist", urbanTechApp);
        assertNotNull("AgroSeed application should exist", agroSeedApp);
        
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, urbanTechApp.getStatus());
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, agroSeedApp.getStatus());
    }
}