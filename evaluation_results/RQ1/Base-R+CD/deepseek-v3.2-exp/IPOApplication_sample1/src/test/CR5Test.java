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
    public void testCase1_cancelStillPendingRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        
        company = new Company();
        company.setName("EcoWave");
        company.setEmail("ecowave@gmail.com");
        
        // Create pending application
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(15);
        app.setAmountOfMoney(750);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify result
        assertTrue("Cancellation should succeed for pending application", result);
    }
    
    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        
        company = new Company();
        company.setName("SmartGrid");
        company.setEmail("smartgrid@business.com");
        
        // Create approved application
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(30);
        app.setAmountOfMoney(3000);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify result
        assertFalse("Cancellation should fail for approved application", result);
    }
    
    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup
        customer = new Customer();
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        
        company = new Company();
        company.setName("MedLife");
        company.setEmail("medlife@health.com");
        
        // Create rejected application
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company);
        app.setShare(20);
        app.setAmountOfMoney(1000);
        app.setAllowance(document);
        app.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app);
        
        // Test cancellation
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify result
        assertFalse("Cancellation should fail for rejected application", result);
    }
    
    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup
        customer = new Customer();
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        
        // Customer has no applications
        
        // Test cancellation for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify result
        assertFalse("Cancellation should fail for non-existent company application", result);
    }
    
    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup - Note: Fixed customer ID from C306 to C305 as per specification
        customer = new Customer();
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        
        // Create UrbanTech application
        Company urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(urbanTech);
        app1.setShare(25);
        app1.setAmountOfMoney(1250);
        app1.setAllowance(document);
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create AgroSeed application
        Company agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@business.com");
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(agroSeed);
        app2.setShare(40);
        app2.setAmountOfMoney(2000);
        app2.setAllowance(document);
        app2.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test cancellation of UrbanTech application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify result
        assertTrue("Cancellation should succeed for UrbanTech application", result);
        
        // Verify AgroSeed application remains unaffected
        boolean agroSeedStillExists = false;
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("AgroSeed") && app.getStatus() == ApplicationStatus.PENDING) {
                agroSeedStillExists = true;
                break;
            }
        }
        assertTrue("AgroSeed application should remain unaffected", agroSeedStillExists);
    }
}