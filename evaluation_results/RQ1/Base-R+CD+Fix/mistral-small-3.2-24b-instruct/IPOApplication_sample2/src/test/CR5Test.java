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
        // Setup
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Test cancellation
        boolean cancellationResult = customer.cancelApplication("EcoWave");
        assertTrue("Cancellation should succeed for pending application", cancellationResult);
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create application and approve it
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and approve it
        Application application = customer.getApplications().get(0);
        boolean approvalResult = application.approve();
        assertTrue("Application should be approved successfully", approvalResult);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, application.getStatus());
        
        // Test cancellation
        boolean cancellationResult = customer.cancelApplication("SmartGrid");
        assertFalse("Cancellation should fail for approved application", cancellationResult);
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create application and reject it
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Get the application and reject it
        Application application = customer.getApplications().get(0);
        boolean rejectionResult = application.reject();
        assertTrue("Application should be rejected successfully", rejectionResult);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, application.getStatus());
        
        // Test cancellation
        boolean cancellationResult = customer.cancelApplication("MedLife");
        assertFalse("Cancellation should fail for rejected application", cancellationResult);
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test cancellation for non-existent company
        boolean cancellationResult = customer.cancelApplication("UnknownCorp");
        assertFalse("Cancellation should fail for non-existent company application", cancellationResult);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create first company and application
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        // Create second company and application
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@innovate.com");
        
        // Create both applications
        boolean app1Created = customer.createApplication(company1, 25, 1250.0, document);
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        
        assertTrue("First application should be created successfully", app1Created);
        assertTrue("Second application should be created successfully", app2Created);
        assertEquals("Customer should have 2 applications", 2, customer.getApplications().size());
        
        // Cancel first application
        boolean cancellationResult1 = customer.cancelApplication("UrbanTech");
        assertTrue("Cancellation of UrbanTech should succeed", cancellationResult1);
        
        // Verify first application is canceled (status becomes REJECTED)
        Application canceledApp = customer.getApplications().get(0);
        if (canceledApp.getCompany().getName().equals("UrbanTech")) {
            assertEquals("UrbanTech application should be REJECTED after cancellation", 
                        ApplicationStatus.REJECTED, canceledApp.getStatus());
        }
        
        // Verify second application remains unaffected (still PENDING)
        Application remainingApp = customer.getApplications().get(1);
        if (remainingApp.getCompany().getName().equals("AgroSeed")) {
            assertEquals("AgroSeed application should remain PENDING", 
                        ApplicationStatus.PENDING, remainingApp.getStatus());
        }
        
        // Verify total applications count remains 2
        assertEquals("Total applications count should remain 2", 2, customer.getApplications().size());
    }
}