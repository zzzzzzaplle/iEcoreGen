import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private MailDeliverySystem system;
    private Area northDistrict;
    private Area eastDistrict;
    private Area westDistrict;
    private Area centralDistrict;
    private Area southDistrict;
    private Area downtown;
    private Area midtown;
    
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
    
    @Before
    public void setUp() {
        system = new MailDeliverySystem();
        
        // Create areas
        northDistrict = new Area();
        northDistrict.setName("NorthDistrict");
        
        eastDistrict = new Area();
        eastDistrict.setName("EastDistrict");
        
        westDistrict = new Area();
        westDistrict.setName("WestDistrict");
        
        centralDistrict = new Area();
        centralDistrict.setName("CentralDistrict");
        
        southDistrict = new Area();
        southDistrict.setName("SouthDistrict");
        
        downtown = new Area();
        downtown.setName("Downtown");
        
        midtown = new Area();
        midtown.setName("Midtown");
        
        // Add areas to system
        List<Area> areas = new ArrayList<>();
        areas.add(northDistrict);
        areas.add(eastDistrict);
        areas.add(westDistrict);
        areas.add(centralDistrict);
        areas.add(southDistrict);
        areas.add(downtown);
        areas.add(midtown);
        system.setAreas(areas);
        
        // Create mailmen
        john = new Mailman();
        john.setName("John");
        
        mike = new Mailman();
        mike.setName("Mike");
        
        peter = new Mailman();
        peter.setName("Peter");
        
        sarah = new Mailman();
        sarah.setName("Sarah");
        
        tom = new Mailman();
        tom.setName("Tom");
        
        jerry = new Mailman();
        jerry.setName("Jerry");
        
        // Create inhabitants
        alice = new Inhabitant();
        alice.setName("Alice");
        
        bob = new Inhabitant();
        bob.setName("Bob");
        
        carol = new Inhabitant();
        carol.setName("Carol");
        
        dave = new Inhabitant();
        dave.setName("Dave");
        
        eve = new Inhabitant();
        eve.setName("Eve");
        
        ieril = new Inhabitant();
        ieril.setName("Ieril");
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        // Add Mailman "John" to NorthDistrict
        system.addMailman(john, northDistrict);
        
        // Add Inhabitant "Alice" to NorthDistrict
        system.addInhabitant(alice, northDistrict);
        
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = new Letter();
        letter1.setAddressee(alice);
        
        // Action: Assign John to deliver Letter1
        boolean result = system.assignMailmanToDeliverMail(letter1, john, alice);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to deliver letter in same area", result);
        assertEquals("Mailman should be set correctly", john, letter1.getDeliveringMailman());
        assertEquals("Addressee should be set correctly", alice, letter1.getAddressee());
        assertTrue("Letter should be added to area deliveries", northDistrict.getDeliveries().contains(letter1));
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        // Add Mailman "Mike" to EastDistrict
        system.addMailman(mike, eastDistrict);
        
        // Add Inhabitant "Bob" to WestDistrict
        system.addInhabitant(bob, westDistrict);
        
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = new Parcel();
        parcel1.setAddressee(bob);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = system.assignMailmanToDeliverMail(parcel1, mike, bob);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Mailman should not be assigned to deliver parcel in different area", result);
        assertNull("Mailman should not be set", parcel1.getDeliveringMailman());
        assertFalse("Parcel should not be added to east district deliveries", eastDistrict.getDeliveries().contains(parcel1));
        assertFalse("Parcel should not be added to west district deliveries", westDistrict.getDeliveries().contains(parcel1));
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        // Note: Peter is created but NOT added to CentralDistrict
        
        // Add Inhabitant "Carol" to CentralDistrict
        system.addInhabitant(carol, centralDistrict);
        
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = new Letter();
        letter2.setAddressee(carol);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = system.assignMailmanToDeliverMail(letter2, peter, carol);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Non-existent mailman should not be assigned", result);
        assertNull("Mailman should not be set", letter2.getDeliveringMailman());
        assertFalse("Letter should not be added to deliveries", centralDistrict.getDeliveries().contains(letter2));
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        // Note: Dave is created but NOT added to SouthDistrict
        
        // Add Mailman "Sarah" to SouthDistrict
        system.addMailman(sarah, southDistrict);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = new Parcel();
        parcel2.setAddressee(dave);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = system.assignMailmanToDeliverMail(parcel2, sarah, dave);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Mail should not be assigned for non-existent inhabitant", result);
        assertNull("Mailman should not be set", parcel2.getDeliveringMailman());
        assertFalse("Parcel should not be added to deliveries", southDistrict.getDeliveries().contains(parcel2));
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        // Add Mailman "Tom" and "Jerry" to Downtown
        system.addMailman(tom, downtown);
        system.addMailman(jerry, downtown);
        
        // Add Inhabitant "Eve" to Downtown
        system.addInhabitant(eve, downtown);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = new Letter();
        letter3.setAddressee(eve);
        boolean initialAssignment = system.assignMailmanToDeliverMail(letter3, tom, eve);
        assertTrue("Initial assignment should be successful", initialAssignment);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = system.assignMailmanToDeliverMail(letter3, jerry, eve);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should not be able to reassign already assigned mail", result1);
        assertEquals("Original mailman should remain assigned", tom, letter3.getDeliveringMailman());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        // Add Inhabitant "Ieril" to Midtown
        system.addInhabitant(ieril, midtown);
        
        // Create RegisteredLetter "Letter4" for Ieril
        Letter letter4 = new Letter();
        letter4.setAddressee(ieril);
        
        // Assign to non-existent mailman (no mailman added to midtown)
        boolean result2 = system.assignMailmanToDeliverMail(letter4, new Mailman(), ieril);
        
        // Expected Output 2: false
        assertFalse("Should not be able to assign with non-existent mailman", result2);
    }
}