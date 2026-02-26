import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company company1;
    private Company company2;
    private Company company3;
    private Company company4;
    private Company company5;
    private Document doc1;
    private Document doc2;
    private Document doc3;
    private Document doc4;
    private Document doc5;
    
    @Before
    public void setUp() {
        // Create common test objects
        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("TechInc", "techinc@example.com");
        company2 = new Company("BioMed", "biomed@example.com");
        
        // Create pending application
        Application pendingApp = new Application(10, 1500.0, customer, company1, doc1);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pendingApp);
        
        // Create rejected application
        Application rejectedApp = new Application(10, 2000.0, customer, company2, doc2);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(rejectedApp);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved requests should return 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" with one approved application
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create approved application
        Application approvedApp = new Application(84, 4200.0, customer, company1, doc1);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(approvedApp);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approval should return 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with multiple approved applications
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("QuantumTech", "quantumtech@example.com");
        company2 = new Company("Neuralink", "neuralink@example.com");
        
        // Create first approved application
        Application app1 = new Application(40, 2000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Application app2 = new Application(70, 3500.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Multiple approvals should return sum (5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" with large portfolio of approved applications
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("TechGiant", "techgiant@example.com");
        company2 = new Company("AutoFuture", "autofuture@example.com");
        company3 = new Company("AeroSpace", "aerospace@example.com");
        company4 = new Company("BioGenius", "biogenius@example.com");
        company5 = new Company("GreenEnergy", "greenenergy@example.com");
        
        // Create multiple approved applications (all $10,000 each)
        Application app1 = new Application(200, 10000.0, customer, company1, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app1);
        
        Application app2 = new Application(250, 10000.0, customer, company2, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app2);
        
        Application app3 = new Application(125, 10000.0, customer, company3, doc3);
        app3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app3);
        
        Application app4 = new Application(500, 10000.0, customer, company4, doc4);
        app4.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app4);
        
        Application app5 = new Application(200, 10000.0, customer, company5, doc5);
        app5.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(app5);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Large portfolio should return sum (50000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        company1 = new Company("CloudServ", "cloudserv@example.com");
        company2 = new Company("DataCore", "datacore@example.com");
        company3 = new Company("AI Ventures", "aiventures@example.com");
        company4 = new Company("NanoTech", "nanotech@example.com");
        company5 = new Company("RoboWorks", "roboworks@example.com");
        
        // Create approved applications
        Application approvedApp1 = new Application(100, 3000.0, customer, company1, doc1);
        approvedApp1.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(approvedApp1);
        
        Application approvedApp2 = new Application(20, 2750.0, customer, company2, doc2);
        approvedApp2.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(approvedApp2);
        
        Application approvedApp3 = new Application(30, 3000.0, customer, company3, doc3);
        approvedApp3.setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().add(approvedApp3);
        
        // Create pending applications (should not be counted in total)
        Application pendingApp1 = new Application(10, 600.0, customer, company4, doc4);
        pendingApp1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pendingApp1);
        
        Application pendingApp2 = new Application(50, 600.0, customer, company5, doc5);
        pendingApp2.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(pendingApp2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Only approved applications should be counted (8750)
        assertEquals(8750.0, result, 0.001);
    }
}