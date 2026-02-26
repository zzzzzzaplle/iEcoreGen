import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private Customer customer301;
    private Customer customer302;
    private Customer customer303;
    private Customer customer304;
    private Customer customer306;
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
    private Document docEW202403;
    private Document docSG202401;
    private Document docSG202403;
    private Document docSG2024005;
    private Document docSG2024006;

    @Before
    public void setUp() {
        // Setup companies
        ecoWave = new Company("EcoWave", "ecowave@gmail.com");
        smartGrid = new Company("SmartGrid", "smartgrid@business.com");
        medLife = new Company("MedLife", "medlife@health.com");
        urbanTech = new Company("UrbanTech", "urbantech@innovate.com");
        agroSeed = new Company("AgroSeed", "agroseed@example.com");

        // Setup documents
        docEW202403 = new Document();
        docSG202401 = new Document();
        docSG202403 = new Document();
        docSG2024005 = new Document();
        docSG2024006 = new Document();

        // Setup customers
        customer301 = new Customer("Benjamin", "Taylor", "b.taylor@example.com", "555-1010", true);
        customer302 = new Customer("Charlotte", "Lee", "c.lee@example.com", "555-2020", true);
        customer303 = new Customer("Lucas", "Martin", "l.martin@example.com", "555-3030", true);
        customer304 = new Customer("Amelia", "Clark", "a.clark@example.com", "555-4040", true);
        customer306 = new Customer("Mia", "Anderson", "m.anderson@example.com", "555-6060", true);

        // Setup applications
        app4001 = new Application(15, 750.0, customer301, ecoWave, docEW202403);
        app4002 = new Application(30, 3000.0, customer302, smartGrid, docSG202401);
        app4002.setStatus(ApplicationStatus.APPROVAL);
        app4003 = new Application(20, 1000.0, customer303, medLife, docSG202403);
        app4003.setStatus(ApplicationStatus.REJECTED);
        app4005 = new Application(25, 1250.0, customer306, urbanTech, docSG2024005);
        app4006 = new Application(40, 2000.0, customer306, agroSeed, docSG2024006);

        // Add applications to customers
        customer301.setApplications(new java.util.ArrayList<Application>());
        customer301.getApplications().add(app4001);
        customer302.setApplications(new java.util.ArrayList<Application>());
        customer302.getApplications().add(app4002);
        customer303.setApplications(new java.util.ArrayList<Application>());
        customer303.getApplications().add(app4003);
        customer304.setApplications(new java.util.ArrayList<Application>());
        customer306.setApplications(new java.util.ArrayList<Application>());
        customer306.getApplications().add(app4005);
        customer306.getApplications().add(app4006);
    }

    @Test
    public void testCase1_cancelPendingRequest() {
        // Customer "C301" requests cancellation for "EcoWave"
        boolean result = customer301.cancelApplication("EcoWave");
        
        assertTrue("Should return true when cancelling pending application", result);
        assertEquals("Application status should be set to REJECTED", ApplicationStatus.REJECTED, app4001.getStatus());
    }

    @Test
    public void testCase2_cancelApprovedRequest() {
        // Customer "C302" tries to cancel IPO for "SmartGrid"
        boolean result = customer302.cancelApplication("SmartGrid");
        
        assertFalse("Should return false when trying to cancel approved application", result);
        assertEquals("Application status should remain APPROVAL", ApplicationStatus.APPROVAL, app4002.getStatus());
    }

    @Test
    public void testCase3_cancelRejectedRequest() {
        // Customer "C303" tries to cancel the filing for "MedLife"
        boolean result = customer303.cancelApplication("MedLife");
        
        assertFalse("Should return false when trying to cancel rejected application", result);
        assertEquals("Application status should remain REJECTED", ApplicationStatus.REJECTED, app4003.getStatus());
    }

    @Test
    public void testCase4_cancelNonexistentCompany() {
        // Customer "C304" requests cancellation for "UnknownCorp"
        boolean result = customer304.cancelApplication("UnknownCorp");
        
        assertFalse("Should return false when no application exists for specified company", result);
        assertEquals("Application list should remain empty", 0, customer304.getApplications().size());
    }

    @Test
    public void testCase5_cancelAfterPriorCancellation() {
        // Customer "C306" cancels "UrbanTech" filing
        boolean result = customer306.cancelApplication("UrbanTech");
        
        assertTrue("Should return true when cancelling pending application", result);
        assertEquals("UrbanTech application status should be REJECTED", ApplicationStatus.REJECTED, app4005.getStatus());
        assertEquals("AgroSeed application status should remain PENDING", ApplicationStatus.PENDING, app4006.getStatus());
        assertEquals("Customer should have 2 applications", 2, customer306.getApplications().size());
    }
}