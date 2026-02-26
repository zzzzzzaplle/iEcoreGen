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
        // Initialize common test objects
        customer = new Customer();
        company1 = new Company();
        company2 = new Company();
        company3 = new Company();
        company4 = new Company();
        company5 = new Company();
        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
    }
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup for Test Case 1: "No approved requests"
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        company1.setName("TechInc");
        company2.setName("BioMed");
        
        // Create PENDING application
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.PENDING);
        
        // Create REJECTED application
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.REJECTED);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup for Test Case 2: "Single approval"
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        company1.setName("SolarMax");
        company1.setEmail("solarmax@gmail.com");
        
        // Create APPROVED application
        Application app = new Application();
        app.setCustomer(customer);
        app.setCompany(company1);
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setAllowance(doc1);
        app.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app);
        
        // Execute: Customer checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup for Test Case 3: "Multiple approvals different firms"
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        company1.setName("QuantumTech");
        company2.setName("Neuralink");
        
        // Create first APPROVED application
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        // Create second APPROVED application
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        
        // Execute: Customer asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup for Test Case 4: "Large portfolio"
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        company1.setName("TechGiant");
        company2.setName("AutoFuture");
        company3.setName("AeroSpace");
        company4.setName("BioGenius");
        company5.setName("GreenEnergy");
        
        // Create multiple APPROVED applications (all $10,000 each)
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(200);
        app1.setAmountOfMoney(10000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(250);
        app2.setAmountOfMoney(10000.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(125);
        app3.setAmountOfMoney(10000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setShare(500);
        app4.setAmountOfMoney(10000.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.APPROVAL);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setShare(200);
        app5.setAmountOfMoney(10000.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.APPROVAL);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup for Test Case 5: "Approvals plus pending"
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        company1.setName("CloudServ");
        company2.setName("DataCore");
        company3.setName("AI Ventures");
        company4.setName("NanoTech");
        company5.setName("RoboWorks");
        
        // Create APPROVED applications
        Application app1 = new Application();
        app1.setCustomer(customer);
        app1.setCompany(company1);
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setAllowance(doc1);
        app1.setStatus(ApplicationStatus.APPROVAL);
        
        Application app2 = new Application();
        app2.setCustomer(customer);
        app2.setCompany(company2);
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setAllowance(doc2);
        app2.setStatus(ApplicationStatus.APPROVAL);
        
        Application app3 = new Application();
        app3.setCustomer(customer);
        app3.setCompany(company3);
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setAllowance(doc3);
        app3.setStatus(ApplicationStatus.APPROVAL);
        
        // Create PENDING applications
        Application app4 = new Application();
        app4.setCustomer(customer);
        app4.setCompany(company4);
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setAllowance(doc4);
        app4.setStatus(ApplicationStatus.PENDING);
        
        Application app5 = new Application();
        app5.setCustomer(customer);
        app5.setCompany(company5);
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setAllowance(doc5);
        app5.setStatus(ApplicationStatus.PENDING);
        
        customer.getApplications().add(app1);
        customer.getApplications().add(app2);
        customer.getApplications().add(app3);
        customer.getApplications().add(app4);
        customer.getApplications().add(app5);
        
        // Execute: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Expected output is 8,750
        assertEquals(8750.0, result, 0.001);
    }
}