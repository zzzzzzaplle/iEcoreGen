import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Customer customer;
    private Company company1;
    private Company company2;
    private Company company3;
    private Document document;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company1 = new Company();
        company2 = new Company();
        company3 = new Company();
        document = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Application history:
        // - PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        // - REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        
        company1.setName("TechInc");
        company2.setName("BioMed");
        
        // Create pending application
        customer.createApplication(company1, 10, 1500.0, document);
        
        // Create rejected application
        customer.createApplication(company2, 10, 2000.0, document);
        customer.getApplications().get(1).setStatus(ApplicationStatus.REJECTED);
        
        // Execute: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Approved application:
        // Company: "SolarMax" (solarmax@gmail.com)
        // Amount: $4,200
        // Shares: 84
        // Document: 'SM-2024-Q1'
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");
        
        // Create and approve application
        customer.createApplication(company1, 84, 4200.0, document);
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        
        // Execute: Customer checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Approved applications:
        // - Company: "QuantumTech", Amount: $2,000, Shares: 40, Document: 'SM-2024-Q3004'
        // - Company: "Neuralink", Amount: $3,500, Shares: 70, Document: 'SM-2024-Q3005'
        company1.setName("QuantumTech");
        company2.setName("Neuralink");
        
        // Create and approve first application
        customer.createApplication(company1, 40, 2000.0, document);
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        
        // Create and approve second application
        customer.createApplication(company2, 70, 3500.0, document);
        customer.getApplications().get(1).setStatus(ApplicationStatus.APPROVAL);
        
        // Execute: Customer asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Approved applications (all $10,000 each):
        // "APP-3006": TechGiant (200 shares)
        // "APP-3007": AutoFuture (250 shares)
        // "APP-3008": AeroSpace (125 shares)
        // "APP-3009": BioGenius (500 shares)
        // "APP-3010": GreenEnergy (200 shares)
        company1.setName("TechGiant");
        company2.setName("AutoFuture");
        company3.setName("AeroSpace");
        Company company4 = new Company();
        company4.setName("BioGenius");
        Company company5 = new Company();
        company5.setName("GreenEnergy");
        
        // Create and approve all applications
        customer.createApplication(company1, 200, 10000.0, document);
        customer.createApplication(company2, 250, 10000.0, document);
        customer.createApplication(company3, 125, 10000.0, document);
        customer.createApplication(company4, 500, 10000.0, document);
        customer.createApplication(company5, 200, 10000.0, document);
        
        // Approve all applications
        for (Application app : customer.getApplications()) {
            app.setStatus(ApplicationStatus.APPROVAL);
        }
        
        // Execute: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Approved applications:
        // - 100 shares, $3,000 (CloudServ)
        // - 20 shares, $2,750 (DataCore)
        // - 30 shares, $3,000 (AI Ventures)
        company1.setName("CloudServ");
        company2.setName("DataCore");
        company3.setName("AI Ventures");
        Company company4 = new Company();
        company4.setName("NanoTech");
        Company company5 = new Company();
        company5.setName("RoboWorks");
        
        // Create approved applications
        customer.createApplication(company1, 100, 3000.0, document);
        customer.createApplication(company2, 20, 2750.0, document);
        customer.createApplication(company3, 30, 3000.0, document);
        
        // Approve the first three applications
        customer.getApplications().get(0).setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().get(1).setStatus(ApplicationStatus.APPROVAL);
        customer.getApplications().get(2).setStatus(ApplicationStatus.APPROVAL);
        
        // Create pending applications (should not be counted in approved total)
        customer.createApplication(company4, 10, 600.0, document);
        customer.createApplication(company5, 50, 600.0, document);
        
        // Execute: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}