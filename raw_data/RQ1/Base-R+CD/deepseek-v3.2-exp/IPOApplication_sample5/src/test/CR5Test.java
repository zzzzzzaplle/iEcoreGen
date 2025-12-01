import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Common setup for test cases
        document = new Document();
    }
    
    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" has a pending application for "EcoWave"
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(15);
        application.setAmountOfMoney(750.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application);
        
        // Test: Cancel pending application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify cancellation was successful
        assertTrue("Pending application should be cancelable", result);
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" has an approved application for "SmartGrid"
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create approved application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(30);
        application.setAmountOfMoney(3000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel approved application for "SmartGrid"
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify cancellation failed
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, application.getStatus());
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" has a rejected application for "MedLife"
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create rejected application
        Application application = new Application();
        application.setCustomer(customer);
        application.setCompany(company);
        application.setShare(20);
        application.setAmountOfMoney(1000.0);
        application.setAllowance(document);
        application.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(application);
        
        // Test: Try to cancel rejected application for "MedLife"
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify cancellation failed
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, application.getStatus());
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" has no applications
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Test: Try to cancel application for non-existent company "UnknownCorp"
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed
        assertFalse("Cancellation should fail for non-existent company", result);
        assertTrue("Customer should have no applications", customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" has two pending applications
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create first application for "UrbanTech"
        Company company1 = new Company();
        company1.setName("UrbanTech");
        company1.setEmail("urbantech@innovate.com");
        
        Application application1 = new Application();
        application1.setCustomer(customer);
        application1.setCompany(company1);
        application1.setShare(25);
        application1.setAmountOfMoney(1250.0);
        application1.setAllowance(document);
        application1.setStatus(ApplicationStatus.PENDING);
        
        // Create second application for "AgroSeed"
        Company company2 = new Company();
        company2.setName("AgroSeed");
        company2.setEmail("agroseed@business.com");
        
        Application application2 = new Application();
        application2.setCustomer(customer);
        application2.setCompany(company2);
        application2.setShare(40);
        application2.setAmountOfMoney(2000.0);
        application2.setAllowance(document);
        application2.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(application1);
        customer.getApplications().add(application2);
        
        // Test: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify cancellation was successful
        assertTrue("UrbanTech application should be cancelable", result);
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, application1.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, application2.getStatus());
        assertEquals("Customer should still have 2 applications", 
                     2, customer.getApplications().size());
    }
}