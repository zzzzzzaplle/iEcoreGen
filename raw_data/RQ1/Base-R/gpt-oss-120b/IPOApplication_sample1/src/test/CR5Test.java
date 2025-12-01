import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank();
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        Company company = new Company("EcoWave");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(15);
        app.setAmount(750.0);
        app.setDocument("EW-2024-03");
        app.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute
        boolean result = bank.cancelPendingApplication(customer, "EcoWave");
        
        // Verify
        assertTrue("Pending application should be canceled successfully", result);
        assertTrue("Application should be removed from customer's list", 
                   customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        Company company = new Company("SmartGrid");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(30);
        app.setAmount(3000.0);
        app.setDocument("SG-2024-01");
        app.setStatus(ApplicationStatus.APPROVED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute
        boolean result = bank.cancelPendingApplication(customer, "SmartGrid");
        
        // Verify
        assertFalse("Approved application should not be cancelable", result);
        assertEquals("Application should remain in customer's list", 
                     1, customer.getApplications().size());
        assertEquals("Application status should remain APPROVED", 
                     ApplicationStatus.APPROVED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        Company company = new Company("MedLife");
        
        IPOApplication app = new IPOApplication();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setNumberOfShares(20);
        app.setAmount(1000.0);
        app.setDocument("SG-2024-03");
        app.setStatus(ApplicationStatus.REJECTED);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app);
        customer.setApplications(applications);
        
        // Execute
        boolean result = bank.cancelPendingApplication(customer, "MedLife");
        
        // Verify
        assertFalse("Rejected application should not be cancelable", result);
        assertEquals("Application should remain in customer's list", 
                     1, customer.getApplications().size());
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, customer.getApplications().get(0).getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Customer has no applications
        customer.setApplications(new ArrayList<>());
        
        // Execute
        boolean result = bank.cancelPendingApplication(customer, "UnknownCorp");
        
        // Verify
        assertFalse("Non-existent application should not be cancelable", result);
        assertTrue("Customer's application list should remain empty", 
                   customer.getApplications().isEmpty());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        Company company1 = new Company("UrbanTech");
        Company company2 = new Company("AgroSeed");
        
        IPOApplication app1 = new IPOApplication();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setNumberOfShares(25);
        app1.setAmount(1250.0);
        app1.setDocument("SG-2024-005");
        app1.setStatus(ApplicationStatus.PENDING);
        
        IPOApplication app2 = new IPOApplication();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setNumberOfShares(40);
        app2.setAmount(2000.0);
        app2.setDocument("SG-2024-006");
        app2.setStatus(ApplicationStatus.PENDING);
        
        List<IPOApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Execute - Cancel UrbanTech application
        boolean result = bank.cancelPendingApplication(customer, "UrbanTech");
        
        // Verify
        assertTrue("UrbanTech application should be canceled successfully", result);
        assertEquals("Only one application should remain", 
                     1, customer.getApplications().size());
        assertEquals("Remaining application should be for AgroSeed", 
                     "AgroSeed", customer.getApplications().get(0).getCompany().getName());
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, customer.getApplications().get(0).getStatus());
    }
}