import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer;
    private Company ecoWave;
    private Company smartGrid;
    private Company medLife;
    private Company urbanTech;
    private Company agroSeed;
    private Document document;

    @Before
    public void setUp() {
        customer = new Customer();
        document = new Document();
        
        // Set up companies
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
        agroSeed.setEmail("agroseed@business.com");
    }

    @Test
    public void testCase1_cancelPendingRequest() {
        // Setup: Customer "C301" with pending application for "EcoWave"
        customer.setName("Benjamin");
        customer.setSurname("Taylor");
        customer.setEmail("b.taylor@example.com");
        customer.setTelephone("555-1010");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        customer.createApplication(ecoWave, 15, 750.0, document);
        
        // Execute: Cancel application for "EcoWave"
        boolean result = customer.cancelApplication("EcoWave");
        
        // Verify: Should return true for successful cancellation
        assertTrue("Pending application should be canceled successfully", result);
    }

    @Test
    public void testCase2_cancelApprovedRequest() {
        // Setup: Customer "C302" with approved application for "SmartGrid"
        customer.setName("Charlotte");
        customer.setSurname("Lee");
        customer.setEmail("c.lee@example.com");
        customer.setTelephone("555-2020");
        customer.setCanApplyForIPO(true);
        
        // Create application and approve it
        customer.createApplication(smartGrid, 30, 3000.0, document);
        Application approvedApp = customer.getApplications().get(0);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Execute: Try to cancel approved application
        boolean result = customer.cancelApplication("SmartGrid");
        
        // Verify: Should return false (cannot cancel approved applications)
        assertFalse("Approved application should not be cancelable", result);
    }

    @Test
    public void testCase3_cancelRejectedRequest() {
        // Setup: Customer "C303" with rejected application for "MedLife"
        customer.setName("Lucas");
        customer.setSurname("Martin");
        customer.setEmail("l.martin@example.com");
        customer.setTelephone("555-3030");
        customer.setCanApplyForIPO(true);
        
        // Create application and reject it
        customer.createApplication(medLife, 20, 1000.0, document);
        Application rejectedApp = customer.getApplications().get(0);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Execute: Try to cancel rejected application
        boolean result = customer.cancelApplication("MedLife");
        
        // Verify: Should return false (cannot cancel rejected applications)
        assertFalse("Rejected application should not be cancelable", result);
    }

    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Setup: Customer "C304" with no applications
        customer.setName("Amelia");
        customer.setSurname("Clark");
        customer.setEmail("a.clark@example.com");
        customer.setTelephone("555-4040");
        customer.setCanApplyForIPO(true);
        
        // Execute: Try to cancel application for non-existent company
        boolean result = customer.cancelApplication("UnknownCorp");
        
        // Verify: Should return false (no application found)
        assertFalse("Non-existent company application should not be cancelable", result);
    }

    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Setup: Customer "C306" with two pending applications
        customer.setName("Mia");
        customer.setSurname("Anderson");
        customer.setEmail("m.anderson@example.com");
        customer.setTelephone("555-6060");
        customer.setCanApplyForIPO(true);
        
        // Create two pending applications
        customer.createApplication(urbanTech, 25, 1250.0, document);
        customer.createApplication(agroSeed, 40, 2000.0, document);
        
        // Verify initial state: 2 applications, both pending
        assertEquals("Should have 2 applications initially", 2, customer.getApplications().size());
        
        // Execute: Cancel "UrbanTech" application
        boolean result = customer.cancelApplication("UrbanTech");
        
        // Verify: UrbanTech cancellation should succeed
        assertTrue("UrbanTech application should be canceled successfully", result);
        
        // Verify: AgroSeed application should remain unaffected
        assertEquals("Should still have 2 applications after cancellation", 2, customer.getApplications().size());
        
        // Check that AgroSeed application is still pending
        boolean agroSeedPending = false;
        for (Application app : customer.getApplications()) {
            if (app.getCompany().getName().equals("AgroSeed") && app.getStatus() == ApplicationStatus.PENDING) {
                agroSeedPending = true;
                break;
            }
        }
        assertTrue("AgroSeed application should remain pending", agroSeedPending);
    }
}