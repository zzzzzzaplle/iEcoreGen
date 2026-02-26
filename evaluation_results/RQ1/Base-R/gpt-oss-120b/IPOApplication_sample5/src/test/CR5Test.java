import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup customer C301
        Customer customer = new Customer();
        customer.setFirstName("Benjamin");
        customer.setLastName("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        // Setup company EcoWave
        Company company = new Company("EcoWave", "ecowave@gmail.com");
        
        // Setup document
        Document document = new Document("EW-2024-03", new byte[]{});
        
        // Create pending application
        IPOApplication application = new IPOApplication(customer, company, 15, 750.0, document);
        application.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(application);
        
        // Test cancellation
        boolean result = customer.cancelPendingApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals("Application status should be CANCELED", ApplicationStatus.CANCELED, application.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup customer C302
        Customer customer = new Customer();
        customer.setFirstName("Charlotte");
        customer.setLastName("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        // Setup company SmartGrid
        Company company = new Company("SmartGrid", "smartgrid@business.com");
        
        // Setup document
        Document document = new Document("SG-2024-01", new byte[]{});
        
        // Create approved application
        IPOApplication application = new IPOApplication(customer, company, 30, 3000.0, document);
        application.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(application);
        
        // Test cancellation
        boolean result = customer.cancelPendingApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals("Application status should remain APPROVED", ApplicationStatus.APPROVED, application.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup customer C303
        Customer customer = new Customer();
        customer.setFirstName("Lucas");
        customer.setLastName("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        // Setup company MedLife
        Company company = new Company("MedLife", "medlife@health.com");
        
        // Setup document
        Document document = new Document("SG-2024-03", new byte[]{});
        
        // Create rejected application
        IPOApplication application = new IPOApplication(customer, company, 20, 1000.0, document);
        application.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(application);
        
        // Test cancellation
        boolean result = customer.cancelPendingApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup customer C304
        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Test cancellation for non-existent company
        boolean result = customer.cancelPendingApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company", result);
        assertTrue("Customer applications should remain empty", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup customer C306
        Customer customer = new Customer();
        customer.setFirstName("Mia");
        customer.setLastName("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Setup companies
        Company urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        Company agroSeed = new Company("AgroSeed", "agroseed@innovate.com");
        
        // Setup documents
        Document doc1 = new Document("SG-2024-005", new byte[]{});
        Document doc2 = new Document("SG-2024-006", new byte[]{});
        
        // Create pending applications
        IPOApplication app1 = new IPOApplication(customer, urbanTech, 25, 1250.0, doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app2 = new IPOApplication(customer, agroSeed, 40, 2000.0, doc2);
        app2.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test cancellation of UrbanTech application
        boolean result = customer.cancelPendingApplication("UrbanTech");
        
        // Verify UrbanTech cancellation succeeded
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        assertEquals("UrbanTech application status should be CANCELED", ApplicationStatus.CANCELED, app1.getStatus());
        
        // Verify AgroSeed application remains unaffected
        assertEquals("AgroSeed application status should remain PENDING", ApplicationStatus.PENDING, app2.getStatus());
        
        // Verify both applications still exist in customer's list
        assertEquals("Customer should still have 2 applications", 2, customer.getApplications().size());
    }
}