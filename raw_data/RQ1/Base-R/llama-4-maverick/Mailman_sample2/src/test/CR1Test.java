import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private MailDeliveryManager mailDeliveryManager;
    
    @Before
    public void setUp() {
        mailDeliveryManager = new MailDeliveryManager();
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        GeographicalArea northDistrict = new GeographicalArea();
        northDistrict.setName("NorthDistrict");
        
        // SetUp: Add Mailman "John" to NorthDistrict
        Mailman john = new Mailman();
        john.setName("John");
        northDistrict.addMailman(john);
        john.setGeographicalArea(northDistrict);
        
        // SetUp: Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = new Inhabitant();
        alice.setName("Alice");
        northDistrict.addInhabitant(alice);
        alice.setGeographicalArea(northDistrict);
        
        // SetUp: Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        letter1.setContent("Test letter content");
        northDistrict.addRegisteredMail(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = mailDeliveryManager.assignMailman(letter1, john);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        GeographicalArea eastDistrict = new GeographicalArea();
        eastDistrict.setName("EastDistrict");
        GeographicalArea westDistrict = new GeographicalArea();
        westDistrict.setName("WestDistrict");
        
        // SetUp: Add Mailman "Mike" to EastDistrict
        Mailman mike = new Mailman();
        mike.setName("Mike");
        eastDistrict.addMailman(mike);
        mike.setGeographicalArea(eastDistrict);
        
        // SetUp: Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = new Inhabitant();
        bob.setName("Bob");
        westDistrict.addInhabitant(bob);
        bob.setGeographicalArea(westDistrict);
        
        // SetUp: Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        parcel1.setWeight(2.5);
        westDistrict.addRegisteredMail(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = mailDeliveryManager.assignMailman(parcel1, mike);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to parcel in different area", result);
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        GeographicalArea centralDistrict = new GeographicalArea();
        centralDistrict.setName("CentralDistrict");
        Mailman peter = new Mailman();
        peter.setName("Peter");
        // Note: Peter is created but NOT added to CentralDistrict
        
        // SetUp: Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = new Inhabitant();
        carol.setName("Carol");
        centralDistrict.addInhabitant(carol);
        carol.setGeographicalArea(centralDistrict);
        
        // SetUp: Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        letter2.setContent("Another test letter");
        centralDistrict.addRegisteredMail(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = mailDeliveryManager.assignMailman(letter2, peter);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        GeographicalArea southDistrict = new GeographicalArea();
        southDistrict.setName("SouthDistrict");
        Inhabitant dave = new Inhabitant();
        dave.setName("Dave");
        // Note: Dave is created but NOT added to SouthDistrict
        
        // SetUp: Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = new Mailman();
        sarah.setName("Sarah");
        southDistrict.addMailman(sarah);
        sarah.setGeographicalArea(southDistrict);
        
        // SetUp: Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave); // Dave exists as object but not in SouthDistrict
        parcel2.setWeight(1.8);
        southDistrict.addRegisteredMail(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = mailDeliveryManager.assignMailman(parcel2, sarah);
        
        // Expected Output: false (addressee doesn't exist in the area)
        assertFalse("Mailman should not be assigned to mail for non-existent inhabitant", result);
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = new GeographicalArea();
        downtown.setName("Downtown");
        
        // SetUp: Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = new Mailman();
        tom.setName("Tom");
        downtown.addMailman(tom);
        tom.setGeographicalArea(downtown);
        
        Mailman jerry = new Mailman();
        jerry.setName("Jerry");
        downtown.addMailman(jerry);
        jerry.setGeographicalArea(downtown);
        
        // SetUp: Add Inhabitant "Eve" to Downtown
        Inhabitant eve = new Inhabitant();
        eve.setName("Eve");
        downtown.addInhabitant(eve);
        eve.setGeographicalArea(downtown);
        
        // SetUp: Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        letter3.setContent("Important letter");
        downtown.addRegisteredMail(letter3);
        
        // Action 1: Assign Tom to Letter3 first
        boolean firstAssignment = mailDeliveryManager.assignMailman(letter3, tom);
        assertTrue("First assignment should be successful", firstAssignment);
        
        // Action 2: Reassign Letter3 to Jerry
        boolean reassignment = mailDeliveryManager.assignMailman(letter3, jerry);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", reassignment);
        
        // Additional test as per specification
        // SetUp: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        GeographicalArea midtown = new GeographicalArea();
        midtown.setName("Midtown");
        Inhabitant ieril = new Inhabitant();
        ieril.setName("Ieril");
        midtown.addInhabitant(ieril);
        ieril.setGeographicalArea(midtown);
        
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        letter4.setContent("Another letter");
        midtown.addRegisteredMail(letter4);
        
        // Action: Try to assign mailman (none exists in Midtown)
        boolean result = mailDeliveryManager.assignMailman(letter4, tom); // Tom is in Downtown, not Midtown
        
        // Expected Output 2: false
        assertFalse("Should not be able to assign mailman from different area", result);
    }
}