import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_singleManagerWithMultipleSubordinates() {
        // Setup: Create Manager M1, Worker W1, SalesPerson S1, and Manager M2
        Manager manager = new Manager();
        manager.setName("M1");
        
        Worker worker = new Worker();
        worker.setName("W1");
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("S1");
        
        Manager subordinateManager = new Manager();
        subordinateManager.setName("M2");
        
        // Assign Worker W1, SalesPerson S1, and Manager M2 as direct subordinates to M1
        manager.addSubordinate(worker);
        manager.addSubordinate(salesPerson);
        manager.addSubordinate(subordinateManager);
        
        // Expected Output: "The number of direct subordinates for Manager M1 is 3."
        assertEquals("The number of direct subordinates for Manager M1 is 3.", 
                     "The number of direct subordinates for Manager M1 is " + manager.getNumberOfSubordinates() + ".");
    }
    
    @Test
    public void testCase2_managerWithNoSubordinates() {
        // Setup: Create Manager M1 with no subordinates
        Manager manager = new Manager();
        manager.setName("M1");
        
        // Expected Output: "The number of subordinates for the manager is 0."
        assertEquals("The number of subordinates for the manager is 0.", 
                     "The number of subordinates for the manager is " + manager.getNumberOfSubordinates() + ".");
    }
    
    @Test
    public void testCase3_multipleManagersWithDifferentSubordinateCounts() {
        // Setup: Create Manager A with 3 subordinates (1 ShiftWorker, 1 OffShiftWorker, 1 SalesPerson)
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setName("SW1");
        
        OffShiftWorker offShiftWorker = new OffShiftWorker();
        offShiftWorker.setName("OSW1");
        
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setName("SP1");
        
        managerA.addSubordinate(shiftWorker);
        managerA.addSubordinate(offShiftWorker);
        managerA.addSubordinate(salesPerson);
        
        // Setup: Create Manager B with 7 subordinates (2 ShiftWorker, 2 OffShiftWorker, 3 SalesPerson)
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        ShiftWorker shiftWorker1 = new ShiftWorker();
        shiftWorker1.setName("SW2");
        ShiftWorker shiftWorker2 = new ShiftWorker();
        shiftWorker2.setName("SW3");
        
        OffShiftWorker offShiftWorker1 = new OffShiftWorker();
        offShiftWorker1.setName("OSW2");
        OffShiftWorker offShiftWorker2 = new OffShiftWorker();
        offShiftWorker2.setName("OSW3");
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("SP2");
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("SP3");
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("SP4");
        
        managerB.addSubordinate(shiftWorker1);
        managerB.addSubordinate(shiftWorker2);
        managerB.addSubordinate(offShiftWorker1);
        managerB.addSubordinate(offShiftWorker2);
        managerB.addSubordinate(salesPerson1);
        managerB.addSubordinate(salesPerson2);
        managerB.addSubordinate(salesPerson3);
        
        // Expected Output: "The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7."
        String expectedOutput = "The number of subordinates for Manager A is " + managerA.getNumberOfSubordinates() + 
                               ". The number of subordinates for Manager B is " + managerB.getNumberOfSubordinates() + ".";
        assertEquals("The number of subordinates for Manager A is 3. The number of subordinates for Manager B is 7.", 
                     expectedOutput);
    }
    
    @Test
    public void testCase4_managerWithSingleSubordinate() {
        // Setup: Create Manager M1 with 1 subordinate (Worker W1)
        Manager manager = new Manager();
        manager.setName("M1");
        
        Worker worker = new Worker();
        worker.setName("W1");
        
        manager.addSubordinate(worker);
        
        // Expected Output: "The number of subordinates for the manager is 1."
        assertEquals("The number of subordinates for the manager is 1.", 
                     "The number of subordinates for the manager is " + manager.getNumberOfSubordinates() + ".");
    }
    
    @Test
    public void testCase5_multipleManagersWithNestedSubordinates() {
        // Setup: Create Manager B with 1 subordinate (Worker W1)
        Manager managerB = new Manager();
        managerB.setName("Manager B");
        
        Worker workerW1 = new Worker();
        workerW1.setName("W1");
        
        managerB.addSubordinate(workerW1);
        
        // Setup: Create Manager A with 5 subordinates (1 Worker, 3 SalesPerson, and Manager B)
        Manager managerA = new Manager();
        managerA.setName("Manager A");
        
        Worker workerW2 = new Worker();
        workerW2.setName("W2");
        
        SalesPerson salesPerson1 = new SalesPerson();
        salesPerson1.setName("S1");
        SalesPerson salesPerson2 = new SalesPerson();
        salesPerson2.setName("S2");
        SalesPerson salesPerson3 = new SalesPerson();
        salesPerson3.setName("S3");
        
        managerA.addSubordinate(workerW2);
        managerA.addSubordinate(salesPerson1);
        managerA.addSubordinate(salesPerson2);
        managerA.addSubordinate(salesPerson3);
        managerA.addSubordinate(managerB);
        
        // Expected Output: "The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1."
        String expectedOutput = "The number of subordinates for Manager A is " + managerA.getNumberOfSubordinates() + 
                               ". The number of subordinates for Manager B is " + managerB.getNumberOfSubordinates() + ".";
        assertEquals("The number of subordinates for Manager A is 5. The number of subordinates for Manager B is 1.", 
                     expectedOutput);
    }
}