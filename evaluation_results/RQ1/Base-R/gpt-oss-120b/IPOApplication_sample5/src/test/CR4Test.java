import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Create customer C201 with no approved applications
        Customer customer = new Customer();
        customer.setFirstName("Emily");
        customer.setLastName("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        
        // Create pending application
        Company techInc = new Company("TechInc", "tech@example.com");
        Document doc1 = new Document("QT-3001", new byte[]{1, 2, 3});
        IPOApplication app1 = new IPOApplication(customer, techInc, 10, 1500.0, doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app1);
        
        // Create rejected application
        Company bioMed = new Company("BioMed", "biomed@example.com");
        Document doc2 = new Document("QT-3002", new byte[]{4, 5, 6});
        IPOApplication app2 = new IPOApplication(customer, bioMed, 10, 2000.0, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        customer.getApplications().add(app2);
        
        // Execute: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Create customer C202 with one approved application
        Customer customer = new Customer();
        customer.setFirstName("Robert");
        customer.setLastName("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        // Create approved application
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document("SM-2024-Q1", new byte[]{7, 8, 9});
        IPOApplication app = new IPOApplication(customer, solarMax, 84, 4200.0, doc);
        app.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app);
        
        // Execute: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 4200.0
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Create customer C203 with two approved applications
        Customer customer = new Customer();
        customer.setFirstName("Sophia");
        customer.setLastName("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        // Create first approved application
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Document doc1 = new Document("SM-2024-Q3004", new byte[]{10, 11, 12});
        IPOApplication app1 = new IPOApplication(customer, quantumTech, 40, 2000.0, doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app1);
        
        // Create second approved application
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc2 = new Document("SM-2024-Q3005", new byte[]{13, 14, 15});
        IPOApplication app2 = new IPOApplication(customer, neuralink, 70, 3500.0, doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app2);
        
        // Execute: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 5500.0 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Create customer C204 with five approved applications
        Customer customer = new Customer();
        customer.setFirstName("James");
        customer.setLastName("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        // Create five approved applications, each with $10,000
        Company[] companies = {
            new Company("TechGiant", "techgiant@example.com"),
            new Company("AutoFuture", "autofuture@example.com"),
            new Company("AeroSpace", "aerospace@example.com"),
            new Company("BioGenius", "biogenius@example.com"),
            new Company("GreenEnergy", "greenenergy@example.com")
        };
        
        int[] shares = {200, 250, 125, 500, 200};
        String[] docNames = {"SM-3006", "SM-3007", "SM-3008", "SM-3009", "SM-3010"};
        
        for (int i = 0; i < companies.length; i++) {
            Document doc = new Document(docNames[i], new byte[]{(byte)(i+1), (byte)(i+2), (byte)(i+3)});
            IPOApplication app = new IPOApplication(customer, companies[i], shares[i], 10000.0, doc);
            app.setStatus(ApplicationStatus.APPROVED);
            customer.getApplications().add(app);
        }
        
        // Execute: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 50000.0 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Create customer C205 with three approved and two pending applications
        Customer customer = new Customer();
        customer.setFirstName("Olivia");
        customer.setLastName("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        // Create approved applications
        Company cloudServ = new Company("CloudServ", "cloudserv@example.com");
        Document doc1 = new Document("SM-3011", new byte[]{16, 17, 18});
        IPOApplication app1 = new IPOApplication(customer, cloudServ, 100, 3000.0, doc1);
        app1.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app1);
        
        Company dataCore = new Company("DataCore", "datacore@example.com");
        Document doc2 = new Document("SM-3012", new byte[]{19, 20, 21});
        IPOApplication app2 = new IPOApplication(customer, dataCore, 20, 2750.0, doc2);
        app2.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app2);
        
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Document doc3 = new Document("SM-3013", new byte[]{22, 23, 24});
        IPOApplication app3 = new IPOApplication(customer, aiVentures, 30, 3000.0, doc3);
        app3.setStatus(ApplicationStatus.APPROVED);
        customer.getApplications().add(app3);
        
        // Create pending applications (should not be included in total)
        Company nanoTech = new Company("NanoTech", "nanotech@example.com");
        Document doc4 = new Document("SM-3014", new byte[]{25, 26, 27});
        IPOApplication app4 = new IPOApplication(customer, nanoTech, 10, 600.0, doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app4);
        
        Company roboWorks = new Company("RoboWorks", "roboworks@example.com");
        Document doc5 = new Document("SM-3015", new byte[]{28, 29, 30});
        IPOApplication app5 = new IPOApplication(customer, roboWorks, 50, 600.0, doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        customer.getApplications().add(app5);
        
        // Execute: Get total approved amount
        double result = customer.getTotalApprovedAmount();
        
        // Verify: Should be 8750.0 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}