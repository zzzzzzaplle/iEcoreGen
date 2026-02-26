import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company company;
    private Document document;
    
    @Before
    public void setUp() {
        // Create a basic document for testing
        document = new Document();
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup customer
        customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techInc = new Company("TechInc", "techinc@example.com");
        Company bioMed = new Company("BioMed", "biomed@example.com");
        
        // Create applications
        Application pendingApp = new Application(10, 1500.0, customer, techInc, document);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        
        Application rejectedApp = new Application(10, 2000.0, customer, bioMed, document);
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        
        // Add applications to customer
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test the method
        double result = customer.getApprovedTotalAmount();
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup customer
        customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create company
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        
        // Create approved application
        Application approvedApp = new Application(84, 4200.0, customer, solarMax, document);
        approvedApp.setStatus(ApplicationStatus.APPROVAL);
        
        // Add application to customer
        customer.getApplications().add(approvedApp);
        
        // Test the method
        double result = customer.getApprovedTotalAmount();
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup customer
        customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        
        // Create approved applications
        Application app1 = new Application(40, 2000.0, customer, quantumTech, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(70, 3500.0, customer, neuralink, document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test the method
        double result = customer.getApprovedTotalAmount();
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup customer
        customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create companies
        Company techGiant = new Company("TechGiant", "techgiant@example.com");
        Company autoFuture = new Company("AutoFuture", "autofuture@example.com");
        Company aeroSpace = new Company("AeroSpace", "aerospace@example.com");
        Company bioGenius = new Company("BioGenius", "biogenius@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        
        // Create approved applications (all $10,000 each)
        Application app1 = new Application(200, 10000.0, customer, techGiant, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(250, 10000.0, customer, autoFuture, document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(125, 10000.0, customer, aeroSpace, document);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        Application app4 = new Application(500, 10000.0, customer, bioGenius, document);
        app4.setStatus(ApplicationStatus.APPROVAL);
        
        Application app5 = new Application(200, 10000.0, customer, greenEnergy, document);
        app5.setStatus(ApplicationStatus.APPROVAL);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test the method
        double result = customer.getApprovedTotalAmount();
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup customer
        customer = new Customer();
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
        
        // Create approved applications
        Application app1 = new Application(100, 3000.0, customer, cloudServ, document);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(20, 2750.0, customer, dataCore, document);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(30, 3000.0, customer, aiVentures, document);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        // Create pending applications
        Application app4 = new Application(10, 600.0, customer, nanoTech, document);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(50, 600.0, customer, roboWorks, document);
        app5.setStatus(ApplicationStatus.PENDING);
        
        // Add applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test the method
        double result = customer.getApprovedTotalAmount();
        assertEquals(8750.0, result, 0.001);
    }
}