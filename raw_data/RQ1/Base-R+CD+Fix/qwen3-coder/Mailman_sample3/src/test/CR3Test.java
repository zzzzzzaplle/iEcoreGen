import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private GeographicalArea riverside;
    private GeographicalArea lakeside;
    private GeographicalArea downtown;
    private GeographicalArea hillside;
    private GeographicalArea beachfront;
    
    private Mailman ken;
    private Mailman amy;
    private Inhabitant linda;
    private Inhabitant paul;
    private Inhabitant becy;
    private Inhabitant victor;
    private Inhabitant rachel;
    
    @Before
    public void setUp() {
        // Initialize geographical areas
        riverside = new GeographicalArea();
        lakeside = new GeographicalArea();
        downtown = new GeographicalArea();
        hillside = new GeographicalArea();
        beachfront = new GeographicalArea();
        
        // Initialize mailmen
        ken = new Mailman();
        amy = new Mailman();
        
        // Initialize inhabitants
        linda = new Inhabitant();
        paul = new Inhabitant();
        becy = new Inhabitant();
        victor = new Inhabitant();
        rachel = new Inhabitant();
    }
    
    @Test
    public void testCase1_AddNewInhabitantToArea() {
        // Test Case 1: "Add New Inhabitant to Area"
        // SetUp: Create GeographicalArea "Riverside"
        // Action: Add new Inhabitant "Linda" to Riverside
        boolean result = riverside.addInhabitant(linda);
        
        // Expected Output: true (successful addition)
        assertTrue("Adding new inhabitant Linda should return true", result);
        assertTrue("Riverside should contain inhabitant Linda", riverside.getInhabitants().contains(linda));
    }
    
    @Test
    public void testCase2_RemoveInhabitantWithAssignedMail() {
        // Test Case 2: "Remove Inhabitant with Assigned Mail"
        // SetUp:
        // 1. Create GeographicalArea "Lakeside"
        // 2. Add Mailman "Ken" to Lakeside
        lakeside.addMailman(ken);
        
        // 3. Add Inhabitant "Paul" to Lakeside
        lakeside.addInhabitant(paul);
        
        // 4. Create and assign Letter6 for Paul (Ken)
        Letter letter6 = new Letter();
        lakeside.getAllMails().add(letter6);
        boolean assignmentResult = lakeside.assignRegisteredMailDeliver(ken, paul, letter6);
        assertTrue("Mail assignment should succeed", assignmentResult);
        
        // Verify setup is correct
        assertNotNull("Letter6 should have a carrier assigned", letter6.getCarrier());
        assertEquals("Letter6 carrier should be Ken", ken, letter6.getCarrier());
        assertEquals("Letter6 addressee should be Paul", paul, letter6.getAddressee());
        
        // Action: Remove Paul from Lakeside
        boolean result = lakeside.removeInhabitant(paul);
        
        // Expected Output: true (Paul removed, Letter6 deleted)
        assertTrue("Removing inhabitant Paul should return true", result);
        assertFalse("Lakeside should not contain inhabitant Paul anymore", lakeside.getInhabitants().contains(paul));
        assertFalse("Letter6 should be deleted from all mails", lakeside.getAllMails().contains(letter6));
    }
    
    @Test
    public void testCase3_AddMultipleInhabitantsToArea() {
        // Test Case 3: "Add Multiple Inhabitants to Area"
        // SetUp: Create GeographicalArea "Downtown"
        
        // Action & Expected Output: 
        // - Add new Inhabitant "Linda" to Downtown, true
        boolean result1 = downtown.addInhabitant(linda);
        assertTrue("Adding Linda should return true", result1);
        assertTrue("Downtown should contain Linda", downtown.getInhabitants().contains(linda));
        
        // - Add new Inhabitant "Becy" to Downtown, true
        boolean result2 = downtown.addInhabitant(becy);
        assertTrue("Adding Becy should return true", result2);
        assertTrue("Downtown should contain Becy", downtown.getInhabitants().contains(becy));
        
        // - Remove Inhabitant "Linda" to Downtown, true
        boolean result3 = downtown.removeInhabitant(linda);
        assertTrue("Removing Linda should return true", result3);
        assertFalse("Downtown should not contain Linda anymore", downtown.getInhabitants().contains(linda));
        assertTrue("Downtown should still contain Becy", downtown.getInhabitants().contains(becy));
    }
    
    @Test
    public void testCase4_RemoveNonExistentInhabitant() {
        // Test Case 4: "Remove Non-existent Inhabitant"
        // SetUp: Create GeographicalArea "Hillside"
        
        // Action: Remove non-existent "Victor" from Hillside
        boolean result = hillside.removeInhabitant(victor);
        
        // Expected Output: false (inhabitant doesn't exist)
        assertFalse("Removing non-existent inhabitant should return false", result);
    }
    
    @Test
    public void testCase5_RemoveInhabitantWithMultipleMailItems() {
        // Test Case 5: "Remove Inhabitant with Multiple Mail Items"
        // SetUp:
        // 1. Create GeographicalArea "Beachfront"
        // 2. Add Mailman "Amy" to Beachfront
        beachfront.addMailman(amy);
        
        // 3. Add Inhabitant "Rachel" to Beachfront
        beachfront.addInhabitant(rachel);
        
        // 4. Create and assign:
        //    - Letter7 for Rachel (Amy)
        //    - Parcel4 for Rachel (Amy)
        Letter letter7 = new Letter();
        Parcel parcel4 = new Parcel();
        
        beachfront.getAllMails().add(letter7);
        beachfront.getAllMails().add(parcel4);
        
        boolean assignment1 = beachfront.assignRegisteredMailDeliver(amy, rachel, letter7);
        boolean assignment2 = beachfront.assignRegisteredMailDeliver(amy, rachel, parcel4);
        
        assertTrue("Letter7 assignment should succeed", assignment1);
        assertTrue("Parcel4 assignment should succeed", assignment2);
        
        // Verify setup is correct
        assertEquals("Letter7 carrier should be Amy", amy, letter7.getCarrier());
        assertEquals("Letter7 addressee should be Rachel", rachel, letter7.getAddressee());
        assertEquals("Parcel4 carrier should be Amy", amy, parcel4.getCarrier());
        assertEquals("Parcel4 addressee should be Rachel", rachel, parcel4.getAddressee());
        
        // Action: Remove Rachel from Beachfront
        boolean result = beachfront.removeInhabitant(rachel);
        
        // Expected Output: true (Rachel removed, both mail items deleted)
        assertTrue("Removing Rachel should return true", result);
        assertFalse("Beachfront should not contain Rachel anymore", beachfront.getInhabitants().contains(rachel));
        assertFalse("Letter7 should be deleted from all mails", beachfront.getAllMails().contains(letter7));
        assertFalse("Parcel4 should be deleted from all mails", beachfront.getAllMails().contains(parcel4));
    }
}