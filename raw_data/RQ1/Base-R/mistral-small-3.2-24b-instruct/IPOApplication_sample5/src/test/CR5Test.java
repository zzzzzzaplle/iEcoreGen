import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

public class CR5Test {
    
    private Bank bank;
    private Map<String, Customer> customers;
    private Map<String, IpoApplication> applications;
    
    @Before
    public void setUp() {
        bank = new Bank();
        customers = new HashMap<>();
        applications = new HashMap<>();
        bank.setCustomers(customers);
        bank.setApplications(applications);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Setup: Create customer C301 with pending EcoWave application
        Customer customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        IpoApplication application = new IpoApplication();
        application.setCompany("EcoWave");
        application.setNumberOfShares(15);
        application.setAmount(750.0);
        application.setDocument("EW-2024-03");
        application.setCustomer(customer);
        
        // Add application to customer and bank
        customer.getApplications().add(application);
        String applicationId = customer.getEmail() + "_" + application.getCompany();
        applications.put(applicationId, application);
        customers.put("C301", customer);
        
        // Test: Cancel pending application
        boolean result = bank.cancelApplication(applicationId);
        
        // Verify: Cancellation should succeed
        assertTrue("Pending application should be cancellable", result);
        assertFalse("Application should be removed from bank records", applications.containsKey(applicationId));
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Setup: Create customer C302 with approved SmartGrid application
        Customer customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        IpoApplication application = new IpoApplication();
        application.setCompany("SmartGrid");
        application.setNumberOfShares(30);
        application.setAmount(3000.0);
        application.setDocument("SG-2024-01");
        application.setCustomer(customer);
        application.setApproved(true); // Mark as approved
        
        // Add application to customer and bank
        customer.getApplications().add(application);
        String applicationId = customer.getEmail() + "_" + application.getCompany();
        applications.put(applicationId, application);
        customers.put("C302", customer);
        
        // Test: Try to cancel approved application
        boolean result = bank.cancelApplication(applicationId);
        
        // Verify: Cancellation should fail
        assertFalse("Approved application should not be cancellable", result);
        assertTrue("Application should remain in bank records", applications.containsKey(applicationId));
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Setup: Create customer C303 with rejected MedLife application
        Customer customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        IpoApplication application = new IpoApplication();
        application.setCompany("MedLife");
        application.setNumberOfShares(20);
        application.setAmount(1000.0);
        application.setDocument("SG-2024-03");
        application.setCustomer(customer);
        application.setRejected(true); // Mark as rejected
        
        // Add application to customer and bank
        customer.getApplications().add(application);
        String applicationId = customer.getEmail() + "_" + application.getCompany();
        applications.put(applicationId, application);
        customers.put("C303", customer);
        
        // Test: Try to cancel rejected application
        boolean result = bank.cancelApplication(applicationId);
        
        // Verify: Cancellation should fail
        assertFalse("Rejected application should not be cancellable", result);
        assertTrue("Application should remain in bank records", applications.containsKey(applicationId));
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Setup: Create customer C304 with no applications
        Customer customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customers.put("C304", customer);
        
        // Test: Try to cancel application for non-existent company
        String nonExistentApplicationId = "a.clark@example.com_UnknownCorp";
        boolean result = bank.cancelApplication(nonExistentApplicationId);
        
        // Verify: Cancellation should fail
        assertFalse("Non-existent application should not be cancellable", result);
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Setup: Create customer C306 with two pending applications
        Customer customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create UrbanTech application
        IpoApplication urbanTechApp = new IpoApplication();
        urbanTechApp.setCompany("UrbanTech");
        urbanTechApp.setNumberOfShares(25);
        urbanTechApp.setAmount(1250.0);
        urbanTechApp.setDocument("SG-2024-005");
        urbanTechApp.setCustomer(customer);
        
        // Create AgroSeed application
        IpoApplication agroSeedApp = new IpoApplication();
        agroSeedApp.setCompany("AgroSeed");
        agroSeedApp.setNumberOfShares(40);
        agroSeedApp.setAmount(2000.0);
        agroSeedApp.setDocument("SG-2024-006");
        agroSeedApp.setCustomer(customer);
        
        // Add applications to customer and bank
        customer.getApplications().add(urbanTechApp);
        customer.getApplications().add(agroSeedApp);
        
        String urbanTechAppId = customer.getEmail() + "_" + urbanTechApp.getCompany();
        String agroSeedAppId = customer.getEmail() + "_" + agroSeedApp.getCompany();
        
        applications.put(urbanTechAppId, urbanTechApp);
        applications.put(agroSeedAppId, agroSeedApp);
        customers.put("C306", customer);
        
        // Test: Cancel UrbanTech application
        boolean result = bank.cancelApplication(urbanTechAppId);
        
        // Verify: UrbanTech cancellation should succeed, AgroSeed should remain
        assertTrue("UrbanTech application should be cancellable", result);
        assertFalse("UrbanTech application should be removed from bank records", 
                   applications.containsKey(urbanTechAppId));
        assertTrue("AgroSeed application should remain in bank records", 
                  applications.containsKey(agroSeedAppId));
    }
}