package edu.postal.postal3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.postal.PostalFactory;
import edu.postal.PostalPackage;
import edu.postal.GeographicalArea;
import edu.postal.Mailman;
import edu.postal.Inhabitant;
import edu.postal.Letter;
import edu.postal.Parcel;
import edu.postal.RegisteredMail;

public class CR1Test {
    
    private PostalFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = PostalFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AssignMailmanToLetterInSameArea() {
        // SetUp: Create GeographicalArea "NorthDistrict"
        GeographicalArea northDistrict = factory.createGeographicalArea();
        
        // Add Mailman "John" to NorthDistrict
        Mailman john = factory.createMailman();
        northDistrict.addMailman(john);
        
        // Add Inhabitant "Alice" to NorthDistrict
        Inhabitant alice = factory.createInhabitant();
        northDistrict.addInhabitant(alice);
        
        // Create Registered Letter "Letter1" for Alice
        Letter letter1 = factory.createLetter();
        letter1.setAddressee(alice);
        northDistrict.getAllMails().add(letter1);
        
        // Action: Assign John to deliver Letter1
        boolean result = northDistrict.assignRegisteredMailDeliver(john, alice, letter1);
        
        // Expected Output: true (successful assignment)
        assertTrue("Mailman should be successfully assigned to letter in same area", result);
        assertEquals("Letter should be assigned to John", john, letter1.getCarrier());
    }
    
    @Test
    public void testCase2_AssignMailmanToParcelInDifferentArea() {
        // SetUp: Create GeographicalArea "EastDistrict" and "WestDistrict"
        GeographicalArea eastDistrict = factory.createGeographicalArea();
        GeographicalArea westDistrict = factory.createGeographicalArea();
        
        // Add Mailman "Mike" to EastDistrict
        Mailman mike = factory.createMailman();
        eastDistrict.addMailman(mike);
        
        // Add Inhabitant "Bob" to WestDistrict
        Inhabitant bob = factory.createInhabitant();
        westDistrict.addInhabitant(bob);
        
        // Create RegisteredParcel "Parcel1" for Bob
        Parcel parcel1 = factory.createParcel();
        parcel1.setAddressee(bob);
        westDistrict.getAllMails().add(parcel1);
        
        // Action: Assign Mike to deliver Parcel1
        boolean result = westDistrict.assignRegisteredMailDeliver(mike, bob, parcel1);
        
        // Expected Output: false (mailman not in same area)
        assertFalse("Should fail when mailman is not in the same area", result);
        assertNull("Parcel should not have any carrier assigned", parcel1.getCarrier());
    }
    
    @Test
    public void testCase3_AssignNonExistentMailman() {
        // SetUp: Create GeographicalArea "CentralDistrict", create Mailman "Peter"
        GeographicalArea centralDistrict = factory.createGeographicalArea();
        Mailman peter = factory.createMailman();
        // Note: Peter is created but NOT added to CentralDistrict
        
        // Add Inhabitant "Carol" to CentralDistrict
        Inhabitant carol = factory.createInhabitant();
        centralDistrict.addInhabitant(carol);
        
        // Create RegisteredLetter "Letter2" for Carol
        Letter letter2 = factory.createLetter();
        letter2.setAddressee(carol);
        centralDistrict.getAllMails().add(letter2);
        
        // Action: Assign Mailman "Peter" to Letter2
        boolean result = centralDistrict.assignRegisteredMailDeliver(peter, carol, letter2);
        
        // Expected Output: false (mailman not in area)
        assertFalse("Should fail when mailman is not in the area", result);
        assertNull("Letter should not have any carrier assigned", letter2.getCarrier());
    }
    
    @Test
    public void testCase4_AssignMailmanToMailForNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "SouthDistrict", create Inhabitant "Dave"
        GeographicalArea southDistrict = factory.createGeographicalArea();
        Inhabitant dave = factory.createInhabitant();
        // Note: Dave is created but NOT added to SouthDistrict
        
        // Add Mailman "Sarah" to SouthDistrict
        Mailman sarah = factory.createMailman();
        southDistrict.addMailman(sarah);
        
        // Create RegisteredParcel "Parcel2" for non-existent "Dave"
        Parcel parcel2 = factory.createParcel();
        parcel2.setAddressee(dave);
        southDistrict.getAllMails().add(parcel2);
        
        // Action: Assign Sarah to deliver Parcel2
        boolean result = southDistrict.assignRegisteredMailDeliver(sarah, dave, parcel2);
        
        // Expected Output: false (addressee doesn't exist in area)
        assertFalse("Should fail when addressee is not in the area", result);
        assertNull("Parcel should not have any carrier assigned", parcel2.getCarrier());
    }
    
    @Test
    public void testCase5_ReassignMailmanToAlreadyAssignedMail() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = factory.createGeographicalArea();
        
        // Add Mailman "Tom" and "Jerry" to Downtown
        Mailman tom = factory.createMailman();
        Mailman jerry = factory.createMailman();
        downtown.addMailman(tom);
        downtown.addMailman(jerry);
        
        // Add Inhabitant "Eve" to Downtown
        Inhabitant eve = factory.createInhabitant();
        downtown.addInhabitant(eve);
        
        // Create RegisteredLetter "Letter3" for Eve, assigned to Tom
        Letter letter3 = factory.createLetter();
        letter3.setAddressee(eve);
        letter3.setCarrier(tom); // Already assigned
        downtown.getAllMails().add(letter3);
        
        // Action 1: Reassign Letter3 to Jerry
        boolean result1 = downtown.assignRegisteredMailDeliver(jerry, eve, letter3);
        
        // Expected Output 1: false (successful reassignment)
        assertFalse("Should fail when mail is already assigned to another mailman", result1);
        assertEquals("Letter should remain assigned to Tom", tom, letter3.getCarrier());
        
        // Action 2: Create RegisteredLetter "Letter4" for new Inhabitant "Ieril" to GeographicalArea "Midtown"
        GeographicalArea midtown = factory.createGeographicalArea();
        Inhabitant ieril = factory.createInhabitant();
        midtown.addInhabitant(ieril);
        
        Letter letter4 = factory.createLetter();
        letter4.setAddressee(ieril);
        midtown.getAllMails().add(letter4);
        
        // No mailman in Midtown, so assignment should fail
        Mailman someMailman = factory.createMailman();
        boolean result2 = midtown.assignRegisteredMailDeliver(someMailman, ieril, letter4);
        
        // Expected Output 2: false
        assertFalse("Should fail when mailman is not in the area", result2);
        assertNull("Letter should not have any carrier assigned", letter4.getCarrier());
    }
}