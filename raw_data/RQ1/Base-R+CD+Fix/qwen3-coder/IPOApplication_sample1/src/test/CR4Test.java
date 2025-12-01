import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Customer customer;
    private Company company1;
    private Company company2;
    private Company company3;
    private Document document1;
    private Document document2;
    private Document document3;
    
    @Before
    public void setUp() {
        customer = new Customer();
        company1 = new Company();
        company2 = new Company();
        company3 = new Company();
        document1 = new Document();
        document2 = new Document();
        document3 = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        company1.setName("TechInc");
        company2.setName("BioMed");
        
        // Create pending application
        Application pendingApp = new Application();
        pendingApp.setShare(10);
        pendingApp.setAmountOfMoney(1500.0);
        pendingApp.setStatus(ApplicationStatus.PENDING);
        pendingApp.setCustomer(customer);
        pendingApp.setCompany(company1);
        pendingApp.setAllowance(document1);
        
        // Create rejected application
        Application rejectedApp = new Application();
        rejectedApp.setShare(10);
        rejectedApp.setAmountOfMoney(2000.0);
        pendingApp.setStatus(ApplicationStatus.REJECTED);
        rejectedApp.setCustomer(customer);
        rejectedApp.setCompany(company2);
        rejectedApp.setAllowance(document2);
        
        // Add applications to customer
        customer.getApplications().add(pendingApp);
        customer.getApplications().add(rejectedApp);
        
        // Test: Customer "C201" requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");
        
        // Create approved application
        Application approvedApp = new Application();
        approvedApp.setShare(84);
        approvedApp.setAmountOfMoney(4200.0);
        approvedApp.setStatus(ApplicationStatus.APPROVED);
        approvedApp.setCustomer(customer);
        approvedApp.setCompany(company1);
        approvedApp.setAllowance(document1);
        
        // Add application to customer
        customer.getApplications().add(approvedApp);
        
        // Test: Customer "C202" checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        
        company1.setName("QuantumTech");
        company2.setName("Neuralink");
        
        // Create first approved application
        Application approvedApp1 = new Application();
        approvedApp1.setShare(40);
        approvedApp1.setAmountOfMoney(2000.0);
        approvedApp1.setStatus(ApplicationStatus.APPROVED);
        approvedApp1.setCustomer(customer);
        approvedApp1.setCompany(company1);
        approvedApp1.setAllowance(document1);
        
        // Create second approved application
        Application approvedApp2 = new Application();
        approvedApp2.setShare(70);
        approvedApp2.setAmountOfMoney(3500.0);
        approvedApp2.setStatus(ApplicationStatus.APPROVED);
        approvedApp2.setCustomer(customer);
        approvedApp2.setCompany(company2);
        approvedApp2.setAllowance(document2);
        
        // Add applications to customer
        customer.getApplications().add(approvedApp1);
        customer.getApplications().add(approvedApp2);
        
        // Test: Customer "C203" asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        
        company1.setName("TechGiant");
        company2.setName("AutoFuture");
        company3.setName("AeroSpace");
        
        // Create multiple approved applications, each with $10,000
        Application approvedApp1 = new Application();
        approvedApp1.setShare(200);
        approvedApp1.setAmountOfMoney(10000.0);
        approvedApp1.setStatus(ApplicationStatus.APPROVED);
        approvedApp1.setCustomer(customer);
        approvedApp1.setCompany(company1);
        approvedApp1.setAllowance(document1);
        
        Application approvedApp2 = new Application();
        approvedApp2.setShare(250);
        approvedApp2.setAmountOfMoney(10000.0);
        approvedApp2.setStatus(ApplicationStatus.APPROVED);
        approvedApp2.setCustomer(customer);
        approvedApp2.setCompany(company2);
        approvedApp2.setAllowance(document2);
        
        Application approvedApp3 = new Application();
        approvedApp3.setShare(125);
        approvedApp3.setAmountOfMoney(10000.0);
        approvedApp3.setStatus(ApplicationStatus.APPROVED);
        approvedApp3.setCustomer(customer);
        approvedApp3.setCompany(company3);
        approvedApp3.setAllowance(document3);
        
        // Add two more applications to reach 5 total approved applications
        Company company4 = new Company();
        company4.setName("BioGenius");
        Company company5 = new Company();
        company5.setName("GreenEnergy");
        
        Application approvedApp4 = new Application();
        approvedApp4.setShare(500);
        approvedApp4.setAmountOfMoney(10000.0);
        approvedApp4.setStatus(ApplicationStatus.APPROVED);
        approvedApp4.setCustomer(customer);
        approvedApp4.setCompany(company4);
        approvedApp4.setAllowance(new Document());
        
        Application approvedApp5 = new Application();
        approvedApp5.setShare(200);
        approvedApp5.setAmountOfMoney(10000.0);
        approvedApp5.setStatus(ApplicationStatus.APPROVED);
        approvedApp5.setCustomer(customer);
        approvedApp5.setCompany(company5);
        approvedApp5.setAllowance(new Document());
        
        // Add all applications to customer
        customer.getApplications().add(approvedApp1);
        customer.getApplications().add(approvedApp2);
        customer.getApplications().add(approvedApp3);
        customer.getApplications().add(approvedApp4);
        customer.getApplications().add(approvedApp5);
        
        // Test: Customer "C204" requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 50,000 (5 applications × $10,000 each)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        
        company1.setName("CloudServ");
        company2.setName("DataCore");
        company3.setName("AI Ventures");
        
        // Create approved applications
        Application approvedApp1 = new Application();
        approvedApp1.setShare(100);
        approvedApp1.setAmountOfMoney(3000.0);
        approvedApp1.setStatus(ApplicationStatus.APPROVED);
        approvedApp1.setCustomer(customer);
        approvedApp1.setCompany(company1);
        approvedApp1.setAllowance(document1);
        
        Application approvedApp2 = new Application();
        approvedApp2.setShare(20);
        approvedApp2.setAmountOfMoney(2750.0);
        approvedApp2.setStatus(ApplicationStatus.APPROVED);
        approvedApp2.setCustomer(customer);
        approvedApp2.setCompany(company2);
        approvedApp2.setAllowance(document2);
        
        Application approvedApp3 = new Application();
        approvedApp3.setShare(30);
        approvedApp3.setAmountOfMoney(3000.0);
        approvedApp3.setStatus(ApplicationStatus.APPROVED);
        approvedApp3.setCustomer(customer);
        approvedApp3.setCompany(company3);
        approvedApp3.setAllowance(document3);
        
        // Create pending applications
        Company company4 = new Company();
        company4.setName("NanoTech");
        Company company5 = new Company();
        company5.setName("RoboWorks");
        
        Application pendingApp1 = new Application();
        pendingApp1.setShare(10);
        pendingApp1.setAmountOfMoney(600.0);
        pendingApp1.setStatus(ApplicationStatus.PENDING);
        pendingApp1.setCustomer(customer);
        pendingApp1.setCompany(company4);
        pendingApp1.setAllowance(new Document());
        
        Application pendingApp2 = new Application();
        pendingApp2.setShare(50);
        pendingApp2.setAmountOfMoney(600.0);
        pendingApp2.setStatus(ApplicationStatus.PENDING);
        pendingApp2.setCustomer(customer);
        pendingApp2.setCompany(company5);
        pendingApp2.setAllowance(new Document());
        
        // Add all applications to customer
        customer.getApplications().add(approvedApp1);
        customer.getApplications().add(approvedApp2);
        customer.getApplications().add(approvedApp3);
        customer.getApplications().add(pendingApp1);
        customer.getApplications().add(pendingApp2);
        
        // Test: Customer "C205" asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Verify expected output: 8,750 (3,000 + 2,750 + 3,000)
        assertEquals(8750.0, result, 0.001);
    }
}