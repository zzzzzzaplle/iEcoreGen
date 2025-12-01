import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_NoApprovedRequests() {
        // Setup: Customer "C201" with pending and rejected applications
        Customer customer = new Customer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Create pending application
        Company company1 = new Company();
        company1.setName("TechInc");
        Application app1 = new Application();
        app1.setShare(10);
        app1.setAmountOfMoney(1500);
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        customer.getApplications().add(app1);
        
        // Create rejected application
        Company company2 = new Company();
        company2.setName("BioMed");
        Application app2 = new Application();
        app2.setShare(10);
        app2.setAmountOfMoney(2000);
        app2.setStatus(ApplicationStatus.REJECTED);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 0 since no approved applications
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_SingleApproval() {
        // Setup: Customer "C202" with one approved application
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Create approved application
        Company company = new Company();
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        Application app = new Application();
        app.setShare(84);
        app.setAmountOfMoney(4200);
        app.setStatus(ApplicationStatus.APPROVAL);
        app.setCustomer(customer);
        app.setCompany(company);
        customer.getApplications().add(app);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 4200 for the single approved application
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" with two approved applications
        Customer customer = new Customer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // First approved application
        Company company1 = new Company();
        company1.setName("QuantumTech");
        Application app1 = new Application();
        app1.setShare(40);
        app1.setAmountOfMoney(2000);
        app1.setStatus(ApplicationStatus.APPROVAL);
        app1.setCustomer(customer);
        app1.setCompany(company1);
        customer.getApplications().add(app1);
        
        // Second approved application
        Company company2 = new Company();
        company2.setName("Neuralink");
        Application app2 = new Application();
        app2.setShare(70);
        app2.setAmountOfMoney(3500);
        app2.setStatus(ApplicationStatus.APPROVAL);
        app2.setCustomer(customer);
        app2.setCompany(company2);
        customer.getApplications().add(app2);
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 5500 (2000 + 3500)
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_LargePortfolio() {
        // Setup: Customer "C204" with five approved applications
        Customer customer = new Customer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Create five approved applications, each with $10,000
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < companyNames.length; i++) {
            Company company = new Company();
            company.setName(companyNames[i]);
            Application app = new Application();
            app.setShare(100 + (i * 50)); // Varying share amounts
            app.setAmountOfMoney(10000);
            app.setStatus(ApplicationStatus.APPROVAL);
            app.setCustomer(customer);
            app.setCompany(company);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 50000 (5 * 10000)
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_ApprovalsPlusPending() {
        // Setup: Customer "C205" with three approved and two pending applications
        Customer customer = new Customer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Create three approved applications
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        double[] approvedAmounts = {3000, 2750, 3000};
        int[] approvedShares = {100, 20, 30};
        
        for (int i = 0; i < approvedCompanies.length; i++) {
            Company company = new Company();
            company.setName(approvedCompanies[i]);
            Application app = new Application();
            app.setShare(approvedShares[i]);
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            app.setCustomer(customer);
            app.setCompany(company);
            customer.getApplications().add(app);
        }
        
        // Create two pending applications
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        
        for (int i = 0; i < pendingCompanies.length; i++) {
            Company company = new Company();
            company.setName(pendingCompanies[i]);
            Application app = new Application();
            app.setShare(10 + (i * 40)); // 10 and 50 shares
            app.setAmountOfMoney(600);
            app.setStatus(ApplicationStatus.PENDING);
            app.setCustomer(customer);
            app.setCompany(company);
            customer.getApplications().add(app);
        }
        
        // Test: Query total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Verify: Should return 8750 (3000 + 2750 + 3000)
        assertEquals(8750.0, result, 0.001);
    }
}