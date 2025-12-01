import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Area area;
    private Area otherArea;
    private Mailman mailman;
    private Mailman otherMailman;
    private Inhabitant inhabitant;
    private Inhabitant otherInhabitant;
    private Letter letter;
    private Parcel parcel;
    
    @Before
    public void setUp() {
        area = new Area();
        area.setName("NorthDistrict");
        
        otherArea = new Area();
        otherArea.setName("EastDistrict");
        
        mailman = new Mailman();
        mailman.setName("John");
        
        otherMailman = new Mailman();
        otherMailman.setName("Mike");
        
        inhabitant = new Inhabitant();
        inhabitant.setName("Alice");
        inhabitant.setArea(area);
        
        otherInhabitant = new Inhabitant();
        otherInhabitant.setName("Bob");
        otherInhabitant.setArea(otherArea);
        
        letter = new Letter();
        letter.setContent("Test letter content");
        
        parcel = new Parcel();
        parcel.setWeight(2.5);
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // Setup: Create GeographicalArea "NorthDistrict", add Mailman "John" and Inhabitant "Alice"
        area.addMailman(mailman);
        area.addInhabitant(inhabitant);
        
        // Create Registered Letter "Letter1" for Alice
        letter.setAddressee(inhabitant);
        area.getMails().add(letter);
        
        // Action: Assign John to deliver Letter1
        boolean result = area.assignMailmanToMail(letter, mailman, inhabitant);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Mail should be assigned to John", mailman, letter.getMailman());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // Setup: Create GeographicalAreas "EastDistrict" and "WestDistrict"
        Area eastDistrict = new Area();
        eastDistrict.setName("EastDistrict");
        
        Area westDistrict = new Area();
        westDistrict.setName("WestDistrict");
        
        // Add Mailman "Mike" to EastDistrict
        eastDistrict.addMailman(otherMailman);
        
        // Add Inhabitant "Bob" to WestDistrict
        westDistrict.addInhabitant(otherInhabitant);
        
        // Create RegisteredParcel "Parcel1" for Bob
        parcel.setAddressee(otherInhabitant);
        westDistrict.getMails().add(parcel);
        
        // Action: Assign Mike to deliver Parcel1 (mailman in different area)
        boolean result = westDistrict.assignMailmanToMail(parcel, otherMailman, otherInhabitant);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Should return false when mailman is not in same area", result);
        assertNull("Mail should not be assigned to any mailman", parcel.getMailman());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // Setup: Create GeographicalArea "CentralDistrict"
        Area centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        
        // Create Mailman "Peter" but don't add to area
        Mailman peter = new Mailman();
        peter.setName("Peter");
        
        // Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        carol.setArea(centralDistrict);
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setContent("Letter2 content");
        letter2.setAddressee(carol);
        centralDistrict.getMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2 (Peter not in area)
        boolean result = centralDistrict.assignMailmanToMail(letter2, peter, carol);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Should return false when mailman is not in the area", result);
        assertNull("Mail should not be assigned to any mailman", letter2.getMailman());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // Setup: Create GeographicalArea "SouthDistrict"
        Area southDistrict = new Area();
        southDistrict.setName("SouthDistrict");
        
        // Create Inhabitant "Dave" but don't add to area
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        
        // Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setWeight(1.5);
        parcel2.setAddressee(dave);
        southDistrict.getMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2 (addressee not in area)
        boolean result = southDistrict.assignMailmanToMail(parcel2, sarah, dave);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Should return false when addressee is not in the area", result);
        assertNull("Mail should not be assigned to any mailman", parcel2.getMailman());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // Setup: Create GeographicalArea "Downtown"
        Area downtown = new Area();
        downtown.setName("Downtown");
        
        // Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        
        // Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        eve.setArea(downtown);
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setContent("Letter3 content");
        letter3.setAddressee(eve);
        letter3.setMailman(tom);
        downtown.getMails().add(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignMailmanToMail(letter3, jerry, eve);
        
        // Expected Output 1: false (mail already assigned)
        assertFalse("Should return false when mail is already assigned to another mailman", result1);
        assertEquals("Mail should remain assigned to Tom", tom, letter3.getMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        Area midtown = new Area();
        midtown.setName("Midtown");
        
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        ieril.setArea(midtown);
        midtown.addInhabitant(ieril);
        
        Letter letter4 = new Letter();
        letter4.setContent("Letter4 content");
        letter4.setAddressee(ieril);
        midtown.getMails().add(letter4);
        
        // Expected Output 2: false (no mailman assignment attempted)
        // Note: This part seems incomplete in the specification. The test should verify that 
        // no mailman assignment was attempted for Letter4 since no mailman was specified.
        // The method wasn't called, so we just verify the initial state
        assertNull("Letter4 should not have any mailman assigned", letter4.getMailman());
    }
}