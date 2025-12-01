package edu.ipo.ipo3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.ipo.Application;
import edu.ipo.ApplicationStatus;
import edu.ipo.Company;
import edu.ipo.Customer;
import edu.ipo.Document;
import edu.ipo.IpoFactory;

public class CR4Test {
    
    private IpoFactory factory;
    
    @Before
    public void setUp() {
        factory = IpoFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_noApprovedRequests() {
        // Setup: Customer "C201" (named "Emily Chen", email "e.chen@example.com", phone "555-1212")
        Customer customer = factory.createCustomer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);
        
        // Setup: Application "APP-3001": PENDING (10 shares, $1,500, TechInc, document: "QT-3001")
        Application app1 = factory.createApplication();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setStatus(ApplicationStatus.PENDING);
        Company techInc = factory.createCompany();
        techInc.setName("TechInc");
        app1.setCompany(techInc);
        Document doc1 = factory.createDocument();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // Setup: Application "APP-3002": REJECTED (10 shares, $2,000, BioMed, document: "QT-3002")
        Application app2 = factory.createApplication();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        Company bioMed = factory.createCompany();
        bioMed.setName("BioMed");
        app2.setCompany(bioMed);
        Document doc2 = factory.createDocument();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // Execute: Customer requests total approved amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase2_singleApproval() {
        // Setup: Customer "C202" (named "Robert Johnson", email "r.johnson@example.com", phone "555-2323")
        Customer customer = factory.createCustomer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);
        
        // Setup: Approved application APP-3003
        Application app = factory.createApplication();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        Company company = factory.createCompany();
        company.setName("SolarMax");
        company.setEmail("solarmax@gmail.com");
        app.setCompany(company);
        Document doc = factory.createDocument();
        app.setAllowance(doc);
        app.setCustomer(customer);
        customer.getApplications().add(app);
        
        // Execute: Customer checks the total approved sum
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 4,200
        assertEquals(4200.0, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup: Customer "C203" (named "Sophia Williams", email "s.williams@example.com", phone "555-3434")
        Customer customer = factory.createCustomer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);
        
        // Setup: Approved application APP-3004
        Application app1 = factory.createApplication();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Company company1 = factory.createCompany();
        company1.setName("QuantumTech");
        app1.setCompany(company1);
        Document doc1 = factory.createDocument();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // Setup: Approved application APP-3005
        Application app2 = factory.createApplication();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Company company2 = factory.createCompany();
        company2.setName("Neuralink");
        app2.setCompany(company2);
        Document doc2 = factory.createDocument();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // Execute: Customer asks for the combined figure
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 5,500
        assertEquals(5500.0, result, 0.001);
    }
    
    @Test
    public void testCase4_largePortfolio() {
        // Setup: Customer "C204" (named "James Wilson", email "j.wilson@vip.example.com", phone "555-4545")
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);
        
        // Setup: 5 Approved applications (all $10,000 each)
        double amount = 10000.0;
        for (int i = 0; i < 5; i++) {
            Application app = factory.createApplication();
            app.setAmountOfMoney(amount);
            app.setStatus(ApplicationStatus.APPROVAL);
            Company company = factory.createCompany();
            switch (i) {
                case 0: company.setName("TechGiant"); break;
                case 1: company.setName("AutoFuture"); break;
                case 2: company.setName("AeroSpace"); break;
                case 3: company.setName("BioGenius"); break;
                case 4: company.setName("GreenEnergy"); break;
            }
            app.setCompany(company);
            Document doc = factory.createDocument();
            app.setAllowance(doc);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }
        
        // Execute: Customer requests total amount
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 50,000
        assertEquals(50000.0, result, 0.001);
    }
    
    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup: Customer "C205" (named "Olivia Brown", email "o.brown@example.com", phone "555-5656")
        Customer customer = factory.createCustomer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);
        
        // Setup: 3 Approved applications
        // APP-3011: $3,000
        Application app1 = factory.createApplication();
        app1.setShare(100);
        app1.setAmountOfMoney(3000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Company company1 = factory.createCompany();
        company1.setName("CloudServ");
        app1.setCompany(company1);
        Document doc1 = factory.createDocument();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);
        
        // APP-3012: $2,750
        Application app2 = factory.createApplication();
        app2.setShare(20);
        app2.setAmountOfMoney(2750.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Company company2 = factory.createCompany();
        company2.setName("DataCore");
        app2.setCompany(company2);
        Document doc2 = factory.createDocument();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);
        
        // APP-3013: $3,000
        Application app3 = factory.createApplication();
        app3.setShare(30);
        app3.setAmountOfMoney(3000.0);
        app3.setStatus(ApplicationStatus.APPROVAL);
        Company company3 = factory.createCompany();
        company3.setName("AI Ventures");
        app3.setCompany(company3);
        Document doc3 = factory.createDocument();
        app3.setAllowance(doc3);
        app3.setCustomer(customer);
        customer.getApplications().add(app3);
        
        // Setup: 2 Pending applications
        // APP-3014: $600
        Application app4 = factory.createApplication();
        app4.setShare(10);
        app4.setAmountOfMoney(600.0);
        app4.setStatus(ApplicationStatus.PENDING);
        Company company4 = factory.createCompany();
        company4.setName("NanoTech");
        app4.setCompany(company4);
        Document doc4 = factory.createDocument();
        app4.setAllowance(doc4);
        app4.setCustomer(customer);
        customer.getApplications().add(app4);
        
        // APP-3015: $600
        Application app5 = factory.createApplication();
        app5.setShare(50);
        app5.setAmountOfMoney(600.0);
        app5.setStatus(ApplicationStatus.PENDING);
        Company company5 = factory.createCompany();
        company5.setName("RoboWorks");
        app5.setCompany(company5);
        Document doc5 = factory.createDocument();
        app5.setAllowance(doc5);
        app5.setCustomer(customer);
        customer.getApplications().add(app5);
        
        // Execute: Customer asks for aggregate approved spending
        double result = customer.getApprovedTotalAmount();
        
        // Expected Output: 8,750
        assertEquals(8750.0, result, 0.001);
    }
}