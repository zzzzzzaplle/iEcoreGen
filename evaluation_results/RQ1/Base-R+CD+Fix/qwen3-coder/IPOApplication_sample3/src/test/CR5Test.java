import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    private Application application;
    
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
        
        // Create pending application
        boolean applicationCreated = customer.createApplication(company, 15, 750.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Verify application is pending
        List<Application> applications = customer.getApplications();
        assertEquals("Should have one application", 1, applications.size());
        assertEquals("Application should be pending", ApplicationStatus.PENDING, applications.get(0).getStatus());
        
        // Test: Cancel pending application
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for pending application", result);
        assertEquals("Application status should be REJECTED after cancellation", ApplicationStatus.REJECTED, applications.get(0).getStatus());
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
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 30, 3000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Approve the application
        Application app = customer.getApplications().get(0);
        boolean approved = app.approve();
        assertTrue("Application should be approved successfully", approved);
        assertEquals("Application status should be APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
        
        // Test: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app.getStatus());
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
        
        // Create application
        boolean applicationCreated = customer.createApplication(company, 20, 1000.0, document);
        assertTrue("Application should be created successfully", applicationCreated);
        
        // Reject the application
        Application app = customer.getApplications().get(0);
        boolean rejected = app.reject();
        assertTrue("Application should be rejected successfully", rejected);
        assertEquals("Application status should be REJECTED", ApplicationStatus.REJECTED, app.getStatus());
        
        // Test: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // No applications created for this customer
        
        // Test: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company", result);
        assertEquals("Should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup
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
        assertTrue("First application should be created successfully", app1Created);
        
        boolean app2Created = customer.createApplication(company2, 40, 2000.0, document);
        assertTrue("Second application should be created successfully", app2Created);
        
        // Verify both applications are pending
        List<Application> applications = customer.getApplications();
        assertEquals("Should have two applications", 2, applications.size());
        assertEquals("First application should be pending", ApplicationStatus.PENDING, applications.get(0).getStatus());
        assertEquals("Second application should be pending", ApplicationStatus.PENDING, applications.get(1).getStatus());
        
        // Test: Cancel first application (UrbanTech)
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Cancellation should succeed for pending application", result);
        
        // Verify first application status changed
        assertEquals("First application status should be REJECTED after cancellation", ApplicationStatus.REJECTED, applications.get(0).getStatus());
        
        // Verify second application remains unaffected
        assertEquals("Second application status should remain PENDING", ApplicationStatus.PENDING, applications.get(1).getStatus());
        assertEquals("Second application shares should remain 40", 40, applications.get(1).getShare());
        assertEquals("Second application amount should remain 2000.0", 2000.0, applications.get(1).getAmountOfMoney(), 0.001);
    }
}