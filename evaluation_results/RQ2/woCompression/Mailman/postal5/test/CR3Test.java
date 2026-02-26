package edu.postal.postal5.test;

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
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private PostalFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the postal factory using Ecore factory pattern
        factory = PostalFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // SetUp: Create GeographicalArea "Riverside"
        GeographicalArea riverside = factory.createGeographicalArea();
        
        // Action: Add new Inhabitant "Linda" to Riverside
        Inhabitant linda = factory.createInhabitant();
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Should successfully add new inhabitant to area", result);
        assertTrue("Inhabitant should be in the area's inhabitants list", 
                   riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        GeographicalArea lakeside = factory.createGeographicalArea();
        
        // 2. Add Mailman "Ken" to Lakeside
        Mailman ken = factory.createMailman();
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        Inhabitant paul = factory.createInhabitant();
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = factory.createLetter();
        letter6.setAddressee(paul);
        letter6.setCarrier(ken);
        lakeside.getAllMails().add(letter6);
        
        // Verify setup
        assertTrue("Paul should be in inhabitants list", lakeside.getInhabitants().contains(paul));
        assertTrue("Letter6 should be in all mails list", lakeside.getAllMails().contains(letter6));
        assertEquals("Letter6 should be assigned to Ken", ken, letter6.getCarrier());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Should successfully remove inhabitant with assigned mail", result);
        assertFalse("Paul should be removed from inhabitants list", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from all mails list", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // SetUp: Create GeographicalArea "Downtown"
        GeographicalArea downtown = factory.createGeographicalArea();
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        Inhabitant linda = factory.createInhabitant();
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Should successfully add Linda", result1);
        assertTrue("Linda should be in inhabitants list", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        Inhabitant becy = factory.createInhabitant();
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Should successfully add Becy", result2);
        assertTrue("Becy should be in inhabitants list", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Should successfully remove Linda", result3);
        assertFalse("Linda should be removed from inhabitants list", downtown.getInhabitants().contains(linda));
        assertTrue("Becy should still be in inhabitants list", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // SetUp: Create GeographicalArea "Hillside"
        GeographicalArea hillside = factory.createGeographicalArea();
        
        // Action: Remove non-existent "Victor" from Hillside
        Inhabitant victor = factory.createInhabitant(); // Create but don't add to area
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Should return false when removing non-existent inhabitant", result);
        assertTrue("Inhabitants list should remain empty", hillside.getInhabitants().isEmpty());
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        GeographicalArea beachfront = factory.createGeographicalArea();
        
        // 2. Add Mailman "Amy" to Beachfront
        Mailman amy = factory.createMailman();
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        Inhabitant rachel = factory.createInhabitant();
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        Letter letter7 = factory.createLetter();
        letter7.setAddressee(rachel);
        letter7.setCarrier(amy);
        beachfront.getAllMails().add(letter7);
        
        //    - Parcel4 for Rachel (Amy)
        Parcel parcel4 = factory.createParcel();
        parcel4.setAddressee(rachel);
        parcel4.setCarrier(amy);
        beachfront.getAllMails().add(parcel4);
        
        // Verify setup
        assertTrue("Rachel should be in inhabitants list", beachfront.getInhabitants().contains(rachel));
        assertTrue("Letter7 should be in all mails list", beachfront.getAllMails().contains(letter7));
        assertTrue("Parcel4 should be in all mails list", beachfront.getAllMails().contains(parcel4));
        assertEquals("Letter7 should be assigned to Amy", amy, letter7.getCarrier());
        assertEquals("Parcel4 should be assigned to Amy", amy, parcel4.getCarrier());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Should successfully remove inhabitant with multiple mail items", result);
        assertFalse("Rachel should be removed from inhabitants list", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted from all mails list", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from all mails list", beachfront.getAllMails().contains(parcel4));
    }
}