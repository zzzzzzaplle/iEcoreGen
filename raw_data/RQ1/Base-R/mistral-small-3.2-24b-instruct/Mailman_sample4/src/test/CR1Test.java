import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private MailManagementSystem mailSystem;
    private GeographicalArea northDistrict;
    private GeographicalArea eastDistrict;
    private GeographicalArea westDistrict;
    private GeographicalArea centralDistrict;
    private GeographicalArea southDistrict;
    private GeographicalArea downtown;
    private GeographicalArea midtown;
    
    private Mailman john;
    private Mailman mike;
    private Mailman peter;
    private Mailman sarah;
    private Mailman tom;
    private Mailman jerry;
    
    private Inhabitant alice;
    private Inhabitant bob;
    private Inhabitant carol;
    private Inhabitant dave;
    private Inhabitant eve;
    private Inhabitant ieril;
    
    private Letter letter1;
    private Parcel parcel1;
    private Letter letter2;
    private Parcel parcel2;
    private Letter letter3;
    private Letter letter4;

    @Before
    public void setUp() {
        mailSystem = new MailManagementSystem();
        
        // Create geographical areas
        northDistrict = new GeographicalArea("NorthDistrict");
        eastDistrict = new GeographicalArea("EastDistrict");
        westDistrict = new GeographicalArea("WestDistrict");
        centralDistrict = new GeographicalArea("CentralDistrict");
        southDistrict = new GeographicalArea("SouthDistrict");
        downtown = new GeographicalArea("Downtown");
        midtown = new GeographicalArea("Midtown");
        
        // Add areas to mail system
        mailSystem.addGeographicalArea(northDistrict);
        mailSystem.addGeographicalArea(eastDistrict);
        mailSystem.addGeographicalArea(westDistrict);
        mailSystem.addGeographicalArea(centralDistrict);
        mailSystem.addGeographicalArea(southDistrict);
        mailSystem.addGeographicalArea(downtown);
        mailSystem.addGeographicalArea(midtown);
        
        // Create mailmen
        john = new Mailman("John");
        mike = new Mailman("Mike");
        peter = new Mailman("Peter");
        sarah = new Mailman("Sarah");
        tom = new Mailman("Tom");
        jerry = new Mailman("Jerry");
        
        // Create inhabitants
        alice = new Inhabitant("Alice");
        bob = new Inhabitant("Bob");
        carol = new Inhabitant("Carol");
        dave = new Inhabitant("Dave");
        eve = new Inhabitant("Eve");
        ieril = new Inhabitant("Ieril");
        
        // Create mail items
        letter1 = new Letter("Letter1", alice);
        parcel1 = new Parcel("Parcel1", bob);
        letter2 = new Letter("Letter2", carol);
        parcel2 = new Parcel("Parcel2", dave);
        letter3 = new Letter("Letter3", eve);
        letter4 = new Letter("Letter4", ieril);
    }

    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict", add Mailman "John" and Inhabitant "Alice", create Letter "Letter1"
        mailSystem.addMailmanToArea("NorthDistrict", john);
        mailSystem.addInhabitantToArea("NorthDistrict", alice);
        alice.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = mailSystem.assignMailToMailman("NorthDistrict", letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
    }

    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalAreas "EastDistrict" and "WestDistrict", add Mailman "Mike" to EastDistrict, 
        // add Inhabitant "Bob" to WestDistrict, create Parcel "Parcel1"
        mailSystem.addMailmanToArea("EastDistrict", mike);
        mailSystem.addInhabitantToArea("WestDistrict", bob);
        bob.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mailSystem.assignMailToMailman("WestDistrict", parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to mail in different area", result);
    }

    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", add Inhabitant "Carol", create Letter "Letter2"
        mailSystem.addInhabitantToArea("CentralDistrict", carol);
        carol.addRegisteredMail(letter2);
        
        // Note: Mailman "Peter" is created but NOT added to any area
        // Action: Assign Peter to Letter2
        boolean result = mailSystem.assignMailToMailman("CentralDistrict", letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
    }

    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", add Mailman "Sarah"
        mailSystem.addMailmanToArea("SouthDistrict", sarah);
        
        // Note: Inhabitant "Dave" is created but NOT added to any area
        // Action: Assign Sarah to deliver Parcel2
        boolean result = mailSystem.assignMailToMailman("SouthDistrict", parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail should not be assigned for non-existent inhabitant", result);
    }

    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown", add Mailmen "Tom" and "Jerry", 
        // add Inhabitant "Eve", create Letter "Letter3" assigned to Tom
        mailSystem.addMailmanToArea("Downtown", tom);
        mailSystem.addMailmanToArea("Downtown", jerry);
        mailSystem.addInhabitantToArea("Downtown", eve);
        eve.addRegisteredMail(letter3);
        
        // Action 1: Assign Tom to Letter3
        boolean firstAssignment = mailSystem.assignMailToMailman("Downtown", letter3, tom);
        assertTrue("First assignment should be successful", firstAssignment);
        
        // Action 2: Reassign Letter3 to Jerry
        boolean reassignment = mailSystem.assignMailToMailman("Downtown", letter3, jerry);
        
        // Expected Output 1: false (successful reassignment) - This seems contradictory in the spec
        // Based on the code logic, reassignment should return false since mail is already assigned
        assertFalse("Reassignment to already assigned mail should fail", reassignment);
        
        // Action 3: Create GeographicalArea "Midtown", add Inhabitant "Ieril", create Letter "Letter4"
        mailSystem.addInhabitantToArea("Midtown", ieril);
        ieril.addRegisteredMail(letter4);
        
        // Expected Output 2: false - Need clarification on this part of the test case
        // Since no mailman is assigned to Midtown, assignment should fail
        boolean assignmentToNewArea = mailSystem.assignMailToMailman("Midtown", letter4, jerry);
        assertFalse("Assignment without mailman in area should fail", assignmentToNewArea);
    }
}