import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private Customer customerC101;
    private Customer customerC102;
    private Customer customerC103;
    private Customer customerC104;
    private Customer customerC105;
    
    @Before
    public void setUp() {
        // Initialize customers as specified in test cases
        customerC101 = new Customer();
        customerC101.setName("Thomas");
        customerC101.setSurname("Anderson");
        customerC101.setEmail("t.anderson@example.com");
        customerC101.setTelephone("555-0101");
        customerC101.setCanApplyForIPO(true);
        
        customerC102 = new Customer();
        customerC102.setName("Lisa");
        customerC102.setSurname("Rodriguez");
        customerC102.setEmail("l.rodriguez@example.com");
        customerC102.setTelephone("555-0202");
        customerC102.setCanApplyForIPO(true);
        
        customerC103 = new Customer();
        customerC103.setName("David");
        customerC103.setSurname("Kim");
        customerC103.setEmail("d.kim@example.com");
        customerC103.setTelephone("555-0303");
        customerC103.setCanApplyForIPO(false);
        
        customerC104 = new Customer();
        customerC104.setName("Emma");
        customerC104.setSurname("Wilson");
        customerC104.setEmail("e.wilson@example.com");
        customerC104.setTelephone("555-0404");
        customerC104.setCanApplyForIPO(true);
        
        customerC105 = new Customer();
        customerC105.setName("James");
        customerC105.setSurname("Chen");
        customerC105.setEmail("j.chen@example.com");
        customerC105.setTelephone("555-0505");
        customerC105.setCanApplyForIPO(true);
    }
    
    @Test
    public void testCase1_noApplicationsAtAll() {
        // Customer "C101" requests count summary with no applications
        int result = customerC101.getApplicationCount();
        assertEquals("Customer with no applications should return 0", 0, result);
    }
    
    @Test
    public void testCase2_singleApprovedRequest() {
        // Setup: Create approved application for customerC102
        Company quantumTech = new Company("QuantumTech", "quantumtech@gmail.com");
        Document doc = new Document(); // Using default document
        Application app = new Application(50, 2500.0, customerC102, quantumTech, doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        customerC102.getApplications().add(app);
        
        // Customer "C102" asks for total number of filings
        int result = customerC102.getApplicationCount();
        assertEquals("Customer with one approved application should return 1", 1, result);
    }
    
    @Test
    public void testCase3_mixOfApprovedAndRejected() {
        // Setup: Create approved and rejected applications for customerC103
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Company spaceY = new Company("SpaceY", "spacey@example.com");
        Company bioGen = new Company("BioGen", "biogen@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        
        Application app1 = new Application(100, 10000.0, customerC103, neuralink, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(30, 15000.0, customerC103, spaceY, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(20, 1000.0, customerC103, bioGen, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        customerC103.getApplications().add(app1);
        customerC103.getApplications().add(app2);
        customerC103.getApplications().add(app3);
        
        // Customer "C103" checks total filings
        int result = customerC103.getApplicationCount();
        assertEquals("Customer with 2 approved and 1 rejected applications should return 3", 3, result);
    }
    
    @Test
    public void testCase4_fiveHistoricalRequests() {
        // Setup: Create mixed status applications for customerC104
        Company roboCorp = new Company("RoboCorp", "robocorp@example.com");
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Company nanoMed = new Company("NanoMed", "nanomed@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        Company cloudScale = new Company("CloudScale", "cloudscale@example.com");
        
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Document doc4 = new Document();
        Document doc5 = new Document();
        
        Application app1 = new Application(100, 10000.0, customerC104, roboCorp, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(100, 10000.0, customerC104, aiVentures, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        Application app3 = new Application(100, 10000.0, customerC104, nanoMed, doc3);
        app3.setStatus(ApplicationStatus.REJECTED);
        
        Application app4 = new Application(100, 10000.0, customerC104, greenEnergy, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(100, 10000.0, customerC104, cloudScale, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customerC104.getApplications().add(app1);
        customerC104.getApplications().add(app2);
        customerC104.getApplications().add(app3);
        customerC104.getApplications().add(app4);
        customerC104.getApplications().add(app5);
        
        // Customer "C104" queries overall count
        int result = customerC104.getApplicationCount();
        assertEquals("Customer with 1 approved, 2 rejected, and 2 pending should return 3", 3, result);
    }
    
    @Test
    public void testCase5_allRequestsCanceled() {
        // Setup: Create and cancel application for customerC105
        Company cloud = new Company("Cloud", "Cloud@gmail.com");
        Document doc = new Document();
        
        // Create pending application
        Application app = new Application(10, 5000.0, customerC105, cloud, doc);
        app.setStatus(ApplicationStatus.PENDING);
        customerC105.getApplications().add(app);
        
        // Cancel the application (sets status to REJECTED)
        boolean cancelResult = customerC105.cancelApplication("Cloud");
        assertTrue("Application should be canceled successfully", cancelResult);
        
        // Customer "C105" asks for the figure
        int result = customerC105.getApplicationCount();
        assertEquals("Customer with canceled application should return 0", 0, result);
    }
}