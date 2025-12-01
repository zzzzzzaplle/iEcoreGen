import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer("Emily", "Chen", "e.chen@example.com", "555-1212");
        customer.setCanApplyForIPO(true);
        
        Company techInc = new Company("TechInc", "tech@example.com");
        Company bioMed = new Company("BioMed", "biomed@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        
        // Create pending application
        Application app1 = new Application();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCustomer(customer);
        app1.setCompany(techInc);
        app1.setAllowance(doc1);
        
        // Create rejected application
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(bioMed);
        app2.setAllowance(doc2);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals("Total approved amount should be 0 for customer with no approved applications", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer("Robert", "Johnson", "r.johnson@example.com", "555-2323");
        
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document();
        
        // Create approved application
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(solarMax);
        app.setAllowance(doc);
        
        customer.getApplications().add(app);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals("Total approved amount should be 4200 for customer with one approved application", 
                     4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        Customer customer = new Customer("Sophia", "Williams", "s.williams@example.com", "555-3434");
        
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc1 = new Document();
        Document doc2 = new Document();
        
        // Create first approved application
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(quantumTech);
        app1.setAllowance(doc1);
        
        // Create second approved application
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(neuralink);
        app2.setAllowance(doc2);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals("Total approved amount should be 5500 for customer with two approved applications", 
                     5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        Customer customer = new Customer("James", "Wilson", "j.wilson@vip.example.com", "555-4545");
        
        Company techGiant = new Company("TechGiant", "techgiant@example.com");
        Company autoFuture = new Company("AutoFuture", "autofuture@example.com");
        Company aeroSpace = new Company("AeroSpace", "aerospace@example.com");
        Company bioGenius = new Company("BioGenius", "biogenius@example.com");
        Company greenEnergy = new Company("GreenEnergy", "greenenergy@example.com");
        
        Document[] docs = {new Document(), new Document(), new Document(), new Document(), new Document()};
        
        // Create five approved applications, each with $10,000
        Application[] apps = new Application[5];
        Company[] companies = {techGiant, autoFuture, aeroSpace, bioGenius, greenEnergy};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < 5; i++) {
            apps[i] = new Application();
            apps[i].setShare(shares[i]);
            apps[i].setAmountOfMoney(10000.0);
            apps[i].setStatus(ApplicationStatus.APPROVAL);
            apps[i].setCustomer(customer);
            apps[i].setCompany(companies[i]);
            apps[i].setAllowance(docs[i]);
            customer.getApplications().add(apps[i]);
        }
        
        // Test: Get total approved amount
        double result = customer.getApprovedTotalAmount();
        assertEquals("Total approved amount should be 50000 for customer with five approved applications of $10,000 each", 
                     50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        Customer customer = new Customer("Olivia", "Brown", "o.brown@example.com", "555-5656");
        
        Company cloudServ = new Company("CloudServ", "cloudserv@example.com");
        Company dataCore = new Company("DataCore", "datacore@example.com");
        Company aiVentures = new Company("AI Ventures", "aiventures@example.com");
        Company nanoTech = new Company("NanoTech", "nanotech@example.com");
        Company roboWorks = new Company("RoboWorks", "roboworks@example.com");
        
        Document[] docs = {new Document(), new Document(), new Document(), new Document(), new Document()};
        
        // Create approved applications
        Application app1 = new Application();
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(cloudServ);
        app1.setAllowance(docs[0]);
        
        Application app2 = new Application();
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(dataCore);
        app2.setAllowance(docs[1]);
        
        Application app3 = new Application();
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setStatus(ApplicationStatus.APPROVAL);
        app3.setCustomer(customer);
        app3.setCompany(aiVentures);
        app3.setAllowance(docs[2]);
        
        // Create pending applications
        Application app4 = new Application();
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setStatus(ApplicationStatus.PENDING);
        app4.setCustomer(customer);
        app4.setCompany(nanoTech);
        app4.setAllowance(docs[3]);
        
        Application app5 = new Application();
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setStatus(ApplicationStatus.PENDING);
        app5.setCustomer(customer);
        app5.setCompany(roboWorks);
        app5.setAllowance(docs[4]);
        
        // Add all applications to customer
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Test: Get total approved amount (should ignore pending applications)
        double result = customer.getApprovedTotalAmount();
        assertEquals("Total approved amount should be 8750 for customer with three approved applications", 
                     8750.0, result, 0.001);
    }
}