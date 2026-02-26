import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        customer = new Customer("Emily", "Chen", "e.chen@example.com", "555-1212", true);
        
        // Create pending application
        Company techInc = new Company("TechInc", "tech@example.com");
        Document doc1 = new Document();
        customer.createApplication(techInc, 10, 1500.0, doc1);
        
        // Create rejected application
        Company bioMed = new Company("BioMed", "biomed@example.com");
        Document doc2 = new Document();
        customer.createApplication(bioMed, 10, 2000.0, doc2);
        
        // Manually set the second application to rejected status
        customer.getApplications().get(1).setStatus(ApplicationStatus.REJECTED);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: No approved applications, should return 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        customer = new Customer("Robert", "Johnson", "r.johnson@example.com", "555-2323", true);
        
        Company solarMax = new Company("SolarMax", "solarmax@gmail.com");
        Document doc = new Document();
        customer.createApplication(solarMax, 84, 4200.0, doc);
        
        // Manually set the application to approved status
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Single approved application amount of 4200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        customer = new Customer("Sophia", "Williams", "s.williams@example.com", "555-3434", true);
        
        // Create first approved application
        Company quantumTech = new Company("QuantumTech", "quantum@example.com");
        Document doc1 = new Document();
        customer.createApplication(quantumTech, 40, 2000.0, doc1);
        
        // Create second approved application
        Company neuralink = new Company("Neuralink", "neuralink@example.com");
        Document doc2 = new Document();
        customer.createApplication(neuralink, 70, 3500.0, doc2);
        
        // Set both applications to approved status
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().get(1).setStatus(ApplicationStatus.APPROVAL);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of both approved applications (2000 + 3500 = 5500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        customer = new Customer("James", "Wilson", "j.wilson@vip.example.com", "555-4545", true);
        
        // Create five approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        int[] shares = {200, 250, 125, 500, 200};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company(companyNames[i], companyNames[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            customer.createApplication(company, shares[i], 10000.0, doc);
            customer.getApplications().get(i).setStatus(ApplicationStatus.APPROVAL);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Sum of five $10,000 applications = $50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with approved and pending applications
        customer = new Customer("Olivia", "Brown", "o.brown@example.com", "555-5656", true);
        
        // Create three approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        int[] approvedShares = {100, 20, 30};
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company(approvedCompanies[i], approvedCompanies[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            customer.createApplication(company, approvedShares[i], approvedAmounts[i], doc);
            customer.getApplications().get(i).setStatus(ApplicationStatus.APPROVAL);
        }
        
        // Create two pending applications (should not be counted in total)
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company(pendingCompanies[i], pendingCompanies[i].toLowerCase() + "@example.com");
            Document doc = new Document();
            customer.createApplication(company, pendingShares[i], pendingAmounts[i], doc);
            // Leave as PENDING (default status)
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Only approved applications counted (3000 + 2750 + 3000 = 8750)
        assertEquals(8750.0, result, 0.001);
    }
}