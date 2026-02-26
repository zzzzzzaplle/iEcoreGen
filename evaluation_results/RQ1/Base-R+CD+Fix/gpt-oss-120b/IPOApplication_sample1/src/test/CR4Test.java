import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer("Emily", "Chen", "e.chen@example.com", "555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create applications
        Company techInc = new Company("TechInc", "tech@example.com");
        Company bioMed = new Company("BioMed", "biomed@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        
        // Create applications manually since createApplication method has validation
        Application app1 = new Application(10, 1500.0, customer, techInc, doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        Application app2 = new Application(10, 2000.0, customer, bioMed, doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer("Robert", "Johnson", "r.johnson@example.com", "555-2323");
        customer.setCanApplyForIPO(true);
        
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document();
        
        // Create and approve application
        Application app = new Application(84, 4200.0, customer, solarMax, doc);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        Customer customer = new Customer("Sophia", "Williams", "s.williams@example.com", "555-3434");
        customer.setCanApplyForIPO(true);
        
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        
        // Create and approve applications
        Application app1 = new Application(40, 2000.0, customer, quantumTech, doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(70, 3500.0, customer, neuralink, doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 5500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        Customer customer = new Customer("James", "Wilson", "j.wilson@vip.example.com", "555-4545");
        customer.setCanApplyForIPO(true);
        
        Company techGiant = new Company("TechGiant", "techgiant@example.com");
        Company autoFuture = new Company("AutoFuture", "autofuture@example.com");
        Company aeroSpace = new Company("AeroSpace", "aerospace@example.com");
        Company bioGenius = new Company("BioGenius", "biogenius@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        
        Document[] docs = {new Document(), new Document(), new Document(), new Document(), new Document()};
        
        // Create and approve applications (all $10,000 each)
        Application app1 = new Application(200, 10000.0, customer, techGiant, docs[0]);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(250, 10000.0, customer, autoFuture, docs[1]);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(125, 10000.0, customer, aeroSpace, docs[2]);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        Application app4 = new Application(500, 10000.0, customer, bioGenius, docs[3]);
        app4.setStatus(ApplicationStatus.APPROVAL);
        
        Application app5 = new Application(200, 10000.0, customer, greenEnergy, docs[4]);
        app5.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 50000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer("Olivia", "Brown", "o.brown@example.com", "555-5656");
        customer.setCanApplyForIPO(true);
        
        Company cloudServ = new Company("CloudServ", "cloudserv@example.com");
        Company dataCore = new Company("DataCore", "datacore@example.com");
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Company nanoTech = new Company("NanoTech", "nanotech@example.com");
        Company roboWorks = new Company("RoboWorks", "roboworks@example.com");
        
        Document[] docs = {new Document(), new Document(), new Document(), new Document(), new Document()};
        
        // Create and approve applications
        Application app1 = new Application(100, 3000.0, customer, cloudServ, docs[0]);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application(20, 2750.0, customer, dataCore, docs[1]);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application(30, 3000.0, customer, aiVentures, docs[2]);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        // Create pending applications
        Application app4 = new Application(10, 600.0, customer, nanoTech, docs[3]);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application(50, 600.0, customer, roboWorks, docs[4]);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 8750
        assertEquals(8750.0, result, 0.001);
    }
}