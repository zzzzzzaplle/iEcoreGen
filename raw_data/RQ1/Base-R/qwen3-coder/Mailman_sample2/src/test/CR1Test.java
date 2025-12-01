import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Area area;
    private MailDeliverySystem system;
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        area = new Area();
        area.setName("NorthDistrict");
        
        // SetUp: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setName("John");
        area.addMailman(john);
        
        // SetUp: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setName("Alice");
        alice.setArea(area);
        area.addInhabitant(alice);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        area.getMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = area.assignMailToMailman(letter1, john, alice);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Mailman should be assigned to the letter", john, letter1.getMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        Area eastDistrict = new Area();
        eastDistrict.setName("EastDistrict");
        Area westDistrict = new Area();
        westDistrict.setName("WestDistrict");
        
        // SetUp: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setName("Mike");
        eastDistrict.addMailman(mike);
        
        // SetUp: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setName("Bob");
        bob.setArea(westDistrict);
        westDistrict.addInhabitant(bob);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        westDistrict.getMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignMailToMailman(parcel1, mike, bob);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman from different area should not be assignable", result);
        assertNull("Mail should remain unassigned", parcel1.getMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        Area centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        Mailman peter = new Mailman();
        peter.setName("Peter");
        // Note: Peter is created but NOT added to CentralDistrict
        
        // SetUp: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        carol.setArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        centralDistrict.getMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignMailToMailman(letter2, peter, carol);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman in area should not be assignable", result);
        assertNull("Mail should remain unassigned", letter2.getMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        Area southDistrict = new Area();
        southDistrict.setName("SouthDistrict");
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        // Note: Dave is created but NOT added to SouthDistrict
        
        // SetUp: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        southDistrict.addMailman(sarah);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave);
        southDistrict.getMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignMailToMailman(parcel2, sarah, dave);
        
        // Expected Output: false (addressee doesn't exist)
        assertFalse("Mail for non-existent inhabitant should not be assignable", result);
        assertNull("Mail should remain unassigned", parcel2.getMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        Area downtown = new Area();
        downtown.setName("Downtown");
        
        // SetUp: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        downtown.addMailman(tom);
        
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        downtown.addMailman(jerry);
        
        // SetUp: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        eve.setArea(downtown);
        downtown.addInhabitant(eve);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        letter3.setMailman(tom);
        downtown.getMails().add(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMailToMailman(letter3, jerry, eve);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Already assigned mail should not be reassignable", result1);
        assertEquals("Mail should remain assigned to original mailman", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        Area midtown = new Area();
        midtown.setName("Midtown");
        
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        ieril.setArea(midtown);
        midtown.addInhabitant(ieril);
        
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        midtown.getMails().add(letter4);
        
        // Expected Output 2: false
        // Since no mailman exists in Midtown, assignment should fail
        boolean result2 = midtown.assignMailToMailman(letter4, new Mailman(), ieril);
        assertFalse("Assignment should fail when no mailman exists in area", result2);
        assertNull("Mail should remain unassigned", letter4.getMailman());
    }
}