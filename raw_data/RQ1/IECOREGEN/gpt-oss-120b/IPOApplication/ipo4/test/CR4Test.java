package edu.ipo.ipo4.test;

import org.junit.Before;
import org.junit.Test;
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
        // Setup Customer C201
        Customer customer = factory.createCustomer();
        customer.setName("Emily");
        customer.setSurname("Chen");
        customer.setEmail("e.chen@example.com");
        customer.setTelephone("555-1212");
        customer.setCanApplyForIPO(true);

        // Setup PENDING application
        Application app1 = factory.createApplication();
        app1.setShare(10);
        app1.setAmountOfMoney(1500.0);
        app1.setStatus(ApplicationStatus.PENDING);
        Company techInc = factory.createCompany();
        techInc.setName("TechInc");
        techInc.setEmail("techinc@example.com");
        app1.setCompany(techInc);
        Document doc1 = factory.createDocument();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);

        // Setup REJECTED application
        Application app2 = factory.createApplication();
        app2.setShare(10);
        app2.setAmountOfMoney(2000.0);
        app2.setStatus(ApplicationStatus.REJECTED);
        Company bioMed = factory.createCompany();
        bioMed.setName("BioMed");
        bioMed.setEmail("biomed@example.com");
        app2.setCompany(bioMed);
        Document doc2 = factory.createDocument();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);

        // Execute and verify
        double result = customer.getApprovedTotalAmount();
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase2_singleApproval() {
        // Setup Customer C202
        Customer customer = factory.createCustomer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setEmail("r.johnson@example.com");
        customer.setTelephone("555-2323");
        customer.setCanApplyForIPO(true);

        // Setup APPROVED application
        Application app = factory.createApplication();
        app.setShare(84);
        app.setAmountOfMoney(4200.0);
        app.setStatus(ApplicationStatus.APPROVAL);
        Company solarMax = factory.createCompany();
        solarMax.setName("SolarMax");
        solarMax.setEmail("solarmax@gmail.com");
        app.setCompany(solarMax);
        Document doc = factory.createDocument();
        doc.toString(); // Just to use the doc
        app.setAllowance(doc);
        app.setCustomer(customer);
        customer.getApplications().add(app);

        // Execute and verify
        double result = customer.getApprovedTotalAmount();
        assertEquals(4200.0, result, 0.001);
    }

    @Test
    public void testCase3_multipleApprovalsDifferentFirms() {
        // Setup Customer C203
        Customer customer = factory.createCustomer();
        customer.setName("Sophia");
        customer.setSurname("Williams");
        customer.setEmail("s.williams@example.com");
        customer.setTelephone("555-3434");
        customer.setCanApplyForIPO(true);

        // Setup first APPROVED application
        Application app1 = factory.createApplication();
        app1.setShare(40);
        app1.setAmountOfMoney(2000.0);
        app1.setStatus(ApplicationStatus.APPROVAL);
        Company quantumTech = factory.createCompany();
        quantumTech.setName("QuantumTech");
        quantumTech.setEmail("quantumtech@example.com");
        app1.setCompany(quantumTech);
        Document doc1 = factory.createDocument();
        app1.setAllowance(doc1);
        app1.setCustomer(customer);
        customer.getApplications().add(app1);

        // Setup second APPROVED application
        Application app2 = factory.createApplication();
        app2.setShare(70);
        app2.setAmountOfMoney(3500.0);
        app2.setStatus(ApplicationStatus.APPROVAL);
        Company neuralink = factory.createCompany();
        neuralink.setName("Neuralink");
        neuralink.setEmail("neuralink@example.com");
        app2.setCompany(neuralink);
        Document doc2 = factory.createDocument();
        app2.setAllowance(doc2);
        app2.setCustomer(customer);
        customer.getApplications().add(app2);

        // Execute and verify
        double result = customer.getApprovedTotalAmount();
        assertEquals(5500.0, result, 0.001);
    }

    @Test
    public void testCase4_largePortfolio() {
        // Setup Customer C204
        Customer customer = factory.createCustomer();
        customer.setName("James");
        customer.setSurname("Wilson");
        customer.setEmail("j.wilson@vip.example.com");
        customer.setTelephone("555-4545");
        customer.setCanApplyForIPO(true);

        // Create 5 approved applications, each for $10,000
        double[] amounts = {10000.0, 10000.0, 10000.0, 10000.0, 10000.0};
        String[] companyNames = {"TechGiant", "AutoFuture", "AeroSpace", "BioGenius", "GreenEnergy"};
        
        for (int i = 0; i < amounts.length; i++) {
            Application app = factory.createApplication();
            app.setAmountOfMoney(amounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            Company company = factory.createCompany();
            company.setName(companyNames[i]);
            company.setEmail(companyNames[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = factory.createDocument();
            app.setAllowance(doc);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }

        // Execute and verify
        double result = customer.getApprovedTotalAmount();
        assertEquals(50000.0, result, 0.001);
    }

    @Test
    public void testCase5_approvalsPlusPending() {
        // Setup Customer C205
        Customer customer = factory.createCustomer();
        customer.setName("Olivia");
        customer.setSurname("Brown");
        customer.setEmail("o.brown@example.com");
        customer.setTelephone("555-5656");
        customer.setCanApplyForIPO(true);

        // Setup 3 APPROVED applications
        double[] approvedAmounts = {3000.0, 2750.0, 3000.0};
        String[] approvedCompanies = {"CloudServ", "DataCore", "AI Ventures"};
        
        for (int i = 0; i < approvedAmounts.length; i++) {
            Application app = factory.createApplication();
            app.setAmountOfMoney(approvedAmounts[i]);
            app.setStatus(ApplicationStatus.APPROVAL);
            Company company = factory.createCompany();
            company.setName(approvedCompanies[i]);
            company.setEmail(approvedCompanies[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = factory.createDocument();
            app.setAllowance(doc);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }

        // Setup 2 PENDING applications
        int[] pendingShares = {10, 50};
        double[] pendingAmounts = {600.0, 600.0};
        String[] pendingCompanies = {"NanoTech", "RoboWorks"};
        
        for (int i = 0; i < pendingAmounts.length; i++) {
            Application app = factory.createApplication();
            app.setShare(pendingShares[i]);
            app.setAmountOfMoney(pendingAmounts[i]);
            app.setStatus(ApplicationStatus.PENDING);
            Company company = factory.createCompany();
            company.setName(pendingCompanies[i]);
            company.setEmail(pendingCompanies[i].toLowerCase() + "@example.com");
            app.setCompany(company);
            Document doc = factory.createDocument();
            app.setAllowance(doc);
            app.setCustomer(customer);
            customer.getApplications().add(app);
        }

        // Execute and verify (only approved amounts should count)
        double result = customer.getApprovedTotalAmount();
        assertEquals(8750.0, result, 0.001);
    }
}