import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Customer customerC301;
    private Customer customerC302;
    private Customer customerC303;
    private Customer customerC304;
    private Customer customerC306;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    private Application app4001;
    private Application app4002;
    private Application app4003;
    private Application app4005;
    private Application app4006;
    
    @Before
    public void setUp() {
        // Initialize companies
        ecoWave = new Company();
        ecoWave.setName("EcoWave");
        ecoWave.setEmail("ecowave@gmail.com");
        
        smartGrid = new Company();
        smartGrid.setName("SmartGrid");
        smartGrid.setEmail("smartgrid@business.com");
        
        medLife = new Company();
        medLife.setName("MedLife");
        medLife.setEmail("medlife@health.com");
        
        urbanTech = new Company();
        urbanTech.setName("UrbanTech");
        urbanTech.setEmail("urbantech@innovate.com");
        
        agroSeed = new Company();
        agroSeed.setName("AgroSeed");
        agroSeed.setEmail("agroseed@innovate.com");
        
        // Initialize customer C301 with pending application
        customerC301 = new Customer();
        customerC301.setName("Benjamin");
        customerC301.setSurname("Taylor");
        customerC301.setEmail("b.taylor@example.com");
        customerC301.setTelephone("555-1010");
        customerC301.setCanApplyForIPO(true);
        
        app4001 = new Application();
        app4001.setCompany(ecoWave);
        app4001.setShare(15);
        app4001.setAmountOfMoney(750.0);
        app4001.setAllowance(new Document());
        app4001.setCustomer(customerC301);
        app4001.setStatus(ApplicationStatus.PENDING);
        
        List<Application> appsC301 = new ArrayList<>();
        appsC301.add(app4001);
        customerC301.setApplications(appsC301);
        
        // Initialize customer C302 with approved application
        customerC302 = new Customer();
        customerC302.setName("Charlotte");
        customerC302.setSurname("Lee");
        customerC302.setEmail("c.lee@example.com");
        customerC302.setTelephone("555-2020");
        customerC302.setCanApplyForIPO(true);
        
        app4002 = new Application();
        app4002.setCompany(smartGrid);
        app4002.setShare(30);
        app4002.setAmountOfMoney(3000.0);
        app4002.setAllowance(new Document());
        app4002.setCustomer(customerC302);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        
        List<Application> appsC302 = new ArrayList<>();
        appsC302.add(app4002);
        customerC302.setApplications(appsC302);
        
        // Initialize customer C303 with rejected application
        customerC303 = new Customer();
        customerC303.setName("Lucas");
        customerC303.setSurname("Martin");
        customerC303.setEmail("l.martin@example.com");
        customerC303.setTelephone("555-3030");
        customerC303.setCanApplyForIPO(true);
        
        app4003 = new Application();
        app4003.setCompany(medLife);
        app4003.setShare(20);
        app4003.setAmountOfMoney(1000.0);
        app4003.setAllowance(new Document());
        app4003.setCustomer(customerC303);
        app4003.setStatus(ApplicationStatus.REJECTED);
        
        List<Application> appsC303 = new ArrayList<>();
        appsC303.add(app4003);
        customerC303.setApplications(appsC303);
        
        // Initialize customer C304 with no applications
        customerC304 = new Customer();
        customerC304.setName("Amelia");
        customerC304.setSurname("Clark");
        customerC304.setEmail("a.clark@example.com");
        customerC304.setTelephone("555-4040");
        customerC304.setCanApplyForIPO(true);
        customerC304.setApplications(new ArrayList<>());
        
        // Initialize customer C306 with two pending applications
        customerC306 = new Customer();
        customerC306.setName("Mia");
        customerC306.setSurname("Anderson");
        customerC306.setEmail("m.anderson@example.com");
        customerC306.setTelephone("555-6060");
        customerC306.setCanApplyForIPO(true);
        
        app4005 = new Application();
        app4005.setCompany(urbanTech);
        app4005.setShare(25);
        app4005.setAmountOfMoney(1250.0);
        app4005.setAllowance(new Document());
        app4005.setCustomer(customerC306);
        app4005.setStatus(ApplicationStatus.PENDING);
        
        app4006 = new Application();
        app4006.setCompany(agroSeed);
        app4006.setShare(40);
        app4006.setAmountOfMoney(2000.0);
        app4006.setAllowance(new Document());
        app4006.setCustomer(customerC306);
        app4006.setStatus(ApplicationStatus.PENDING);
        
        List<Application> appsC306 = new ArrayList<>();
        appsC306.add(app4005);
        appsC306.add(app4006);
        customerC306.setApplications(appsC306);
    }
    
    @Test
    public void testCase1_CancelStillPendingRequest() {
        // Test Case 1: Cancel still-pending request
        // Customer "C301" requests cancellation for "EcoWave"
        boolean result = customerC301.cancelApplication("EcoWave");
        
        // Verify cancellation succeeded
        assertTrue("Pending application should be cancelable", result);
        
        // Verify application status changed to REJECTED
        assertEquals("Application status should be REJECTED after cancellation", 
                     ApplicationStatus.REJECTED, app4001.getStatus());
    }
    
    @Test
    public void testCase2_CancelApprovedRequest() {
        // Test Case 2: Cancel approved request
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        boolean result = customerC302.cancelApplication("SmartGrid");
        
        // Verify cancellation failed (approved applications cannot be canceled)
        assertFalse("Approved application should not be cancelable", result);
        
        // Verify application status remains APPROVAL
        assertEquals("Application status should remain APPROVAL", 
                     ApplicationStatus.APPROVAL, app4002.getStatus());
    }
    
    @Test
    public void testCase3_CancelRejectedRequest() {
        // Test Case 3: Cancel rejected request
        // Customer "C303" tries to cancel the filing for "MedLife"
        boolean result = customerC303.cancelApplication("MedLife");
        
        // Verify cancellation failed (rejected applications cannot be canceled)
        assertFalse("Rejected application should not be cancelable", result);
        
        // Verify application status remains REJECTED
        assertEquals("Application status should remain REJECTED", 
                     ApplicationStatus.REJECTED, app4003.getStatus());
    }
    
    @Test
    public void testCase4_CancelNonexistentCompany() {
        // Test Case 4: Cancel nonexistent company
        // Customer "C304" requests cancellation for "UnknownCorp"
        boolean result = customerC304.cancelApplication("UnknownCorp");
        
        // Verify cancellation failed (no application found for specified company)
        assertFalse("Cancellation should fail for non-existent company", result);
        
        // Verify customer still has no applications
        assertEquals("Customer should have no applications", 
                     0, customerC304.getApplications().size());
    }
    
    @Test
    public void testCase5_CancelAfterPriorCancellation() {
        // Test Case 5: Cancel after prior cancellation
        // Customer "C306" cancels "UrbanTech" filing
        
        // Get initial count of applications
        int initialAppCount = customerC306.getApplications().size();
        
        // Cancel UrbanTech application
        boolean result = customerC306.cancelApplication("UrbanTech");
        
        // Verify cancellation succeeded
        assertTrue("Pending UrbanTech application should be cancelable", result);
        
        // Verify UrbanTech application status changed to REJECTED
        assertEquals("UrbanTech application status should be REJECTED", 
                     ApplicationStatus.REJECTED, app4005.getStatus());
        
        // Verify AgroSeed application remains PENDING
        assertEquals("AgroSeed application status should remain PENDING", 
                     ApplicationStatus.PENDING, app4006.getStatus());
        
        // Verify customer still has both applications (one canceled, one pending)
        assertEquals("Customer should still have both applications", 
                     initialAppCount, customerC306.getApplications().size());
    }
}