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
        Application pendingApp = new Application();
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(company);
        pendingApp.setShare(15);
        pendingApp.setAmountOfMoney(750.0);
        pendingApp.setAllowance(document);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(pendingApp);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be cancellable", result);
        
        // Verify application status changed to REJECTED (cancelled)
        assertEquals("Application status should be REJECTED after cancellation", 
                    ApplicationStatus.REJECTED, pendingApp.getStatus());
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
        
        // Create approved application
        Application approvedApp = new Application();
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(company);
        approvedApp.setShare(30);
        approvedApp.setAmountOfMoney(3000.0);
        approvedApp.setAllowance(document);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancellable", result);
        
        // Verify application status remains APPROVAL
        assertEquals("Application status should remain APPROVAL", 
                    ApplicationStatus.APPROVAL, approvedApp.getStatus());
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
        
        // Create rejected application
        Application rejectedApp = new Application();
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(company);
        rejectedApp.setShare(20);
        rejectedApp.setAmountOfMoney(1000.0);
        rejectedApp.setAllowance(document);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(rejectedApp);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancellable", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                    ApplicationStatus.REJECTED, rejectedApp.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no applications
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed (no application found)
        assertFalse("Cancellation should fail for non-existent company", result);
        
        // Verify customer has no applications
        assertEquals("Customer should have no applications", 0, customer.getApplications().size());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create first application for "UrbanTech"
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(urbanTech);
        app1.setShare(25);
        app1.setAmountOfMoney(1250.0);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create second application for "AgroSeed"
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@innovate.com");
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(agroSeed);
        app2.setShare(40);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("UrbanTech application should be cancellable", result);
        
        // Verify "UrbanTech" application status changed to REJECTED
        assertEquals("UrbanTech application should be REJECTED after cancellation", 
                    ApplicationStatus.REJECTED, app1.getStatus());
        
        // Verify "AgroSeed" application remains PENDING and unaffected
        assertEquals("AgroSeed application should remain PENDING", 
                    ApplicationStatus.PENDING, app2.getStatus());
        
        // Verify customer still has 2 applications
        assertEquals("Customer should still have 2 applications", 2, customer.getApplications().size());
    }
}