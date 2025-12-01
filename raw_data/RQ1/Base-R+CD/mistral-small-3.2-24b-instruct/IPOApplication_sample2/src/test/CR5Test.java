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
    public void testCase1_CancelStillPendingRequest() {
        // Setup
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create a pending application
        Application app = new Application();
        app.setShare(15);
        app.setAmountOfMoney(750);
        app.setStatus(ApplicationStatus.PENDING);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create an approved application
        Application app = new Application();
        app.setShare(30);
        app.setAmountOfMoney(3000);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create a rejected application
        Application app = new Application();
        app.setShare(20);
        app.setAmountOfMoney(1000);
        app.setStatus(ApplicationStatus.REJECTED);
        app.setCustomer(customer);
        app.setCompany(company);
        app.setAllowance(document);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Customer has no applications
        
        // Test cancellation for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify
        assertFalse("Cancellation should fail for non-existent company", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup - Note: Corrected customer ID from C305 to C306 as per test case description
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first application for UrbanTech
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        Application app1 = new Application();
        app1.setShare(25);
        app1.setAmountOfMoney(1250);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setAllowance(document);
        
        // Create second application for AgroSeed
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@business.com");
        
        Application app2 = new Application();
        app2.setShare(40);
        app2.setAmountOfMoney(2000);
        app2.setStatus(ApplicationStatus.PENDING);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setAllowance(document);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test cancellation for UrbanTech
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify UrbanTech cancellation
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app1.getStatus());
        
        // Verify AgroSeed remains unaffected
        assertEquals("AgroSeed application should remain PENDING", 
                     ApplicationStatus.PENDING, app2.getStatus());
        assertEquals("Customer should still have 2 applications", 
                     2, customer.getApplications().size());
    }
}