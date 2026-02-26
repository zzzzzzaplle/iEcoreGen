import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private Company company1;
    private Company company2;
    private Company company3;
    private Company company4;
    private Company company5;
    private Document document1;
    private Document document2;
    private Document document3;
    private Document document4;
    private Document document5;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        document1 = new Document();
        document2 = new Document();
        document3 = new Document();
        document4 = new Document();
        document5 = new Document();
        
        company1 = new Company("TechInc", "techinc@example.com");
        company2 = new Company("BioMed", "biomed@example.com");
        company3 = new Company("SolarMax", "solarmax@gmail.com");
        company4 = new Company("QuantumTech", "quantumtech@example.com");
        company5 = new Company("Neuralink", "neuralink@example.com");
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup customer C201
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Application pendingApp = new Application(10, 1500.0, customer, company1, document1);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        // Create rejected application
        Application rejectedApp = new Application(10, 2000.0, customer, company2, document2);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(pendingApp);
        applications.add(rejectedApp);
        customer.setApplications(applications);
        
        // Test: Customer C201 requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 0
        assertEquals("Total approved amount should be 0 when no approved applications exist", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup customer C202
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Application approvedApp = new Application(84, 4200.0, customer, company3, document3);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        List<Application> applications = new ArrayList<>();
        applications.add(approvedApp);
        customer.setApplications(applications);
        
        // Test: Customer C202 checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 4,200
        assertEquals("Total approved amount should be 4200 for single approved application", 
                     4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup customer C203
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create first approved application
        Application app1 = new Application(40, 2000.0, customer, company4, document4);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create second approved application
        Application app2 = new Application(70, 3500.0, customer, company5, document5);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        customer.setApplications(applications);
        
        // Test: Customer C203 asks for combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 5,500
        assertEquals("Total approved amount should be 5500 for multiple approved applications", 
                     5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup customer C204
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create companies for large portfolio
        Company techGiant = new Company("TechGiant", "techgiant@example.com");
        Company autoFuture = new Company("AutoFuture", "autofuture@example.com");
        Company aeroSpace = new Company("AeroSpace", "aerospace@example.com");
        Company bioGenius = new Company("BioGenius", "biogenius@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        
        // Create documents for large portfolio
        Document doc3006 = new Document();
        Document doc3007 = new Document();
        Document doc3008 = new Document();
        Document doc3009 = new Document();
        Document doc3010 = new Document();
        
        // Create all approved applications (each $10,000)
        Application app3006 = new Application(200, 10000.0, customer, techGiant, doc3006);
        app3006.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3007 = new Application(250, 10000.0, customer, autoFuture, doc3007);
        app3007.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3008 = new Application(125, 10000.0, customer, aeroSpace, doc3008);
        app3008.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3009 = new Application(500, 10000.0, customer, bioGenius, doc3009);
        app3009.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3010 = new Application(200, 10000.0, customer, greenEnergy, doc3010);
        app3010.setStatus(ApplicationStatus.APPROVAL);
        
        // Add all applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app3006);
        applications.add(app3007);
        applications.add(app3008);
        applications.add(app3009);
        applications.add(app3010);
        customer.setApplications(applications);
        
        // Test: Customer C204 requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 50,000
        assertEquals("Total approved amount should be 50000 for large portfolio", 
                     50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup customer C205
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company cloudServ = new Company("CloudServ", "cloudserv@example.com");
        Company dataCore = new Company("DataCore", "datacore@example.com");
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Company nanoTech = new Company("NanoTech", "nanotech@example.com");
        Company roboWorks = new Company("RoboWorks", "roboworks@example.com");
        
        // Create documents
        Document doc3011 = new Document();
        Document doc3012 = new Document();
        Document doc3013 = new Document();
        Document doc3014 = new Document();
        Document doc3015 = new Document();
        
        // Create approved applications
        Application app3011 = new Application(100, 3000.0, customer, cloudServ, doc3011);
        app3011.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3012 = new Application(20, 2750.0, customer, dataCore, doc3012);
        app3012.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3013 = new Application(30, 3000.0, customer, aiVentures, doc3013);
        app3013.setStatus(ApplicationStatus.APPROVAL);
        
        // Create pending applications
        Application app3014 = new Application(10, 600.0, customer, nanoTech, doc3014);
        app3014.setStatus(ApplicationStatus.PENDING);
        
        Application app3015 = new Application(50, 600.0, customer, roboWorks, doc3015);
        app3015.setStatus(ApplicationStatus.PENDING);
        
        // Add all applications to customer
        List<Application> applications = new ArrayList<>();
        applications.add(app3011);
        applications.add(app3012);
        applications.add(app3013);
        applications.add(app3014);
        applications.add(app3015);
        customer.setApplications(applications);
        
        // Test: Customer C205 asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 8,750
        assertEquals("Total approved amount should be 8750 when mixing approved and pending applications", 
                     8750.0, result, 0.001);
        
        // Additional verification: check that pending applications are not counted
        assertEquals("Should have 3 approved applications contributing to total", 
                     3, customer.getApplicationCount());
    }
}