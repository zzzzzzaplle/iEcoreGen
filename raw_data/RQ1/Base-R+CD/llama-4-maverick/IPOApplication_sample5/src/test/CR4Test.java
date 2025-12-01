import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Initialize common objects
        document = new Document();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212") 
        customer = new Customer("Emily", "Chen", "e.chen@example.com", "555-1212", true);
        
        // Setup: Application history with PENDING and REJECTED status
        Company techInc = new Company("TechInc", "info@techinc.com");
        Company bioMed = new Company("BioMed", "info@biomed.com");
        
        Application pendingApp = new Application(10, 1500.0, customer, techInc, document);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        Application rejectedApp = new Application(10, 2000.0, customer, bioMed, document);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer = new Customer("Robert", "Johnson", "r.johnson@example.com", "555-2323", true);
        
        // Setup: Approved application
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        
        Application approvedApp = new Application(84, 4200.0, customer, solarMax, document);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(approvedApp);
        
        // Test: Customer checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer = new Customer("Sophia", "Williams", "s.williams@example.com", "555-3434", true);
        
        // Setup: Approved applications for different companies
        Company quantumTech = new Company("QuantumTech", "info@quantumtech.com");
        Company neuralink = new Company("Neuralink", "info@neuralink.com");
        
        Application app3004 = new Application(40, 2000.0, customer, quantumTech, document);
        app3004.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3005 = new Application(70, 3500.0, customer, neuralink, document);
        app3005.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app3004);
        customer.getApplications().add(app3005);
        
        // Test: Customer asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer = new Customer("James", "Wilson", "j.wilson@vip.example.com", "555-4545", true);
        
        // Setup: Approved applications (all $10,000 each)
        Company techGiant = new Company("TechGiant", "info@techgiant.com");
        Company autoFuture = new Company("AutoFuture", "info@autofuture.com");
        Company aeroSpace = new Company("AeroSpace", "info@aerospace.com");
        Company bioGenius = new Company("BioGenius", "info@biogenius.com");
        Company greenEnergy = new Company("GreenEnergy", "info@greenenergy.com");
        
        Application app3006 = new Application(200, 10000.0, customer, techGiant, document);
        app3006.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3007 = new Application(250, 10000.0, customer, autoFuture, document);
        app3007.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3008 = new Application(125, 10000.0, customer, aeroSpace, document);
        app3008.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3009 = new Application(500, 10000.0, customer, bioGenius, document);
        app3009.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3010 = new Application(200, 10000.0, customer, greenEnergy, document);
        app3010.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app3006);
        customer.getApplications().add(app3007);
        customer.getApplications().add(app3008);
        customer.getApplications().add(app3009);
        customer.getApplications().add(app3010);
        
        // Test: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer = new Customer("Olivia", "Brown", "o.brown@example.com", "555-5656", true);
        
        // Setup: Approved applications
        Company cloudServ = new Company("CloudServ", "info@cloudserv.com");
        Company dataCore = new Company("DataCore", "info@datacore.com");
        Company aiVentures = new Company("AI Ventures", "info@aiventures.com");
        
        Application app3011 = new Application(100, 3000.0, customer, cloudServ, document);
        app3011.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3012 = new Application(20, 2750.0, customer, dataCore, document);
        app3012.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3013 = new Application(30, 3000.0, customer, aiVentures, document);
        app3013.setStatus(ApplicationStatus.APPROVAL);
        
        // Setup: Pending applications (should not be included in total)
        Company nanoTech = new Company("NanoTech", "info@nanotech.com");
        Company roboWorks = new Company("RoboWorks", "info@roboworks.com");
        
        Application app3014 = new Application(10, 600.0, customer, nanoTech, document);
        app3014.setStatus(ApplicationStatus.PENDING);
        
        Application app3015 = new Application(50, 600.0, customer, roboWorks, document);
        app3015.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app3011);
        customer.getApplications().add(app3012);
        customer.getApplications().add(app3013);
        customer.getApplications().add(app3014);
        customer.getApplications().add(app3015);
        
        // Test: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750 (only approved applications)
        assertEquals(8750.0, result, 0.001);
    }
}